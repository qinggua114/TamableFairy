package com.github.qinggua114.tamablefairy.data.fairy_enchs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;

import static com.github.qinggua114.tamablefairy.data.fairy_enchs.FairyEnchsEnum.FAIRY_ENCHS_ENUM_CODEC;

public record FairyEnchs(Map<FairyEnchsEnum, Integer> fairyEnchsMap) {
    public static final Codec<FairyEnchs> CODEC =
            Codec.unboundedMap(FAIRY_ENCHS_ENUM_CODEC, Codec.INT)
                    .fieldOf("fairy_enchs")
                    .xmap(FairyEnchs::new, FairyEnchs::fairyEnchsMap)
                    .codec();

    public static final StreamCodec<FriendlyByteBuf, Map<FairyEnchsEnum, Integer>> MAP_STREAM_CODEC =
            ByteBufCodecs.map(
                    HashMap::new,
                    ByteBufCodecs.fromCodec(FAIRY_ENCHS_ENUM_CODEC),
                    ByteBufCodecs.INT
            );

    public static final StreamCodec<FriendlyByteBuf, FairyEnchs> STREAM_CODEC =
            StreamCodec.composite(
                    MAP_STREAM_CODEC,
                    FairyEnchs::fairyEnchsMap,
                    FairyEnchs::new
            );

    public static final FairyEnchs EMPTY = new FairyEnchs(new HashMap<>());
}
