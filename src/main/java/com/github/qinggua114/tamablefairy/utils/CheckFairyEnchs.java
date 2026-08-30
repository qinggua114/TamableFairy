package com.github.qinggua114.tamablefairy.utils;

import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchs;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.IFairyEnchs;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;

import java.util.Map;

import static com.github.qinggua114.tamablefairy.data.Capabilities.FAIRY_ENCHS;

public class CheckFairyEnchs {

    public CheckFairyEnchs() {
    }

    public static Map<FairyEnchsEnum, Integer> getFairyEnchs(EntityFairy fairy) {
        IFairyEnchs fairyEnchs = fairy.getCapability(FAIRY_ENCHS).orElse(new FairyEnchs());
        return fairyEnchs.enchantmentsMap();
    }

}
