package com.github.qinggua114.tamablefairy.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;
import java.util.UUID;

public record TameData (boolean tamed, UUID owner){

    public static final Codec<TameData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("tamed").forGetter(TameData::tamed),
                    Codec.STRING.xmap(UUID::fromString, UUID::toString).optionalFieldOf("owner")
                            .forGetter(td -> Optional.ofNullable(td.owner()))
            ).apply(instance, (tamed, ownerOpt) -> new TameData(tamed, ownerOpt.orElse(null)))
    );

    public static final TameData EMPTY = new TameData(false, null);
}
