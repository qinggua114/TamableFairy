package com.github.qinggua114.tamablefairy.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public record TameData (boolean tamed, UUID owner){

    //序列化
    public static final Codec<TameData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("tamed").forGetter(TameData::tamed),
                    Codec.STRING.xmap(UUID::fromString, UUID::toString).optionalFieldOf("owner")
                            .forGetter(td -> Optional.ofNullable(td.owner()))
            ).apply(instance, (tamed, ownerOpt) -> new TameData(tamed, ownerOpt.orElse(null)))
    );

    //网络同步
    public static final StreamCodec<ByteBuf, TameData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull TameData decode(@NotNull ByteBuf byteBuf) {
            boolean tamed = ByteBufCodecs.BOOL.decode(byteBuf);
            UUID owner = null;
            if (tamed){
                //将拆成两个Long的UUID还原
                Long mostSigBits = ByteBufCodecs.VAR_LONG.decode(byteBuf);
                Long leastSigBits = ByteBufCodecs.VAR_LONG.decode(byteBuf);
                owner = new UUID(mostSigBits, leastSigBits);
            }
            return new TameData(tamed, owner);
        }
        @Override
        public void encode(@NotNull ByteBuf byteBuf, TameData tameData) {
            ByteBufCodecs.BOOL.encode(byteBuf, tameData.tamed());
            if (tameData.tamed()){
                //将UUID拆成最高有效位和最低有效位,作为两个Long类型数据分别传输
                ByteBufCodecs.VAR_LONG.encode(byteBuf, tameData.owner.getMostSignificantBits());
                ByteBufCodecs.VAR_LONG.encode(byteBuf, tameData.owner.getLeastSignificantBits());
            }
        }
    };

    public static final TameData EMPTY = new TameData(false, null);//创建默认空数据
}
