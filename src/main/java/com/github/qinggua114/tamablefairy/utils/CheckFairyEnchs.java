package com.github.qinggua114.tamablefairy.utils;

import com.github.qinggua114.tamablefairy.data.fairy_enchs.FairyEnchs;
import com.github.qinggua114.tamablefairy.data.fairy_enchs.FairyEnchsEnum;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;

import java.util.Map;

import static com.github.qinggua114.tamablefairy.data.Attachments.FAIRY_ENCHS;

public class CheckFairyEnchs {

    public CheckFairyEnchs(){
    }

    public static Map<FairyEnchsEnum, Integer> getFairyEnchs(EntityFairy fairy){
        FairyEnchs fairyEnchs = fairy.getData(FAIRY_ENCHS);
        return fairyEnchs.fairyEnchsMap();
    }

}
