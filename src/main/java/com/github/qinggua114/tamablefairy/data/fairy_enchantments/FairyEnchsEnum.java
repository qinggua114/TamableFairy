package com.github.qinggua114.tamablefairy.data.fairy_enchantments;

import java.util.Map;

public enum FairyEnchsEnum {
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

    public int getLimit() {
        return limitMap.getOrDefault(this, 0);
    }
}
