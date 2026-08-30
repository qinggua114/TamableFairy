package com.github.qinggua114.tamablefairy.data.fairy_enchs;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Map;

public enum FairyEnchsEnum implements StringRepresentable {
    MULTI_SHOT,
    QUICK_CHARGE,
    POWER,
    SPEEDY,
    IMPEDING,
    ENDERS_ENDER,
    THORNS,
    PROTECTION,
    HEALING;

    private static final Map<FairyEnchsEnum, Integer> limitMap = Map.of(
            MULTI_SHOT, 1,
            QUICK_CHARGE, 3,
            POWER, 5,
            SPEEDY, 2,
            IMPEDING, 4,
            ENDERS_ENDER, 1,
            THORNS, 3,
            PROTECTION, 4,
            HEALING, 1
    );

    public int getLimit(){
        return limitMap.getOrDefault(this, 0);
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }

    public static final Codec<FairyEnchsEnum> FAIRY_ENCHS_ENUM_CODEC = StringRepresentable.fromEnum(FairyEnchsEnum::values);
}
