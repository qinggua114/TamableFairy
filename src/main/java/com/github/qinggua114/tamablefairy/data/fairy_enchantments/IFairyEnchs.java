package com.github.qinggua114.tamablefairy.data.fairy_enchantments;

import java.util.Map;

public interface IFairyEnchs {
    Map<FairyEnchsEnum, Integer> enchantmentsMap();

    void addEnchantment(FairyEnchsEnum enchantment, int level);

    void removeEnchantment(FairyEnchsEnum enchantment);

    void upgradeEnchantment(FairyEnchsEnum enchantments, int addedLevel);

    void modifyEnchantment(FairyEnchsEnum enchantment, int level);

    void clearEnchantments();

    void setEnchantmentsMap(Map<FairyEnchsEnum, Integer> enchantmentsMap);
}