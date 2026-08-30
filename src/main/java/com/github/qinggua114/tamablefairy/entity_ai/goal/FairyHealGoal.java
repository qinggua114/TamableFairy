package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.fairy_enchs.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.utils.CheckFairyEnchs;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.ai.goal.Goal;

public class FairyHealGoal extends Goal {

    private final EntityFairy fairy;
    private int timeCounter;

    public FairyHealGoal(EntityFairy entity) {
        this.fairy = entity;
    }

    @Override
    public boolean canUse() {
        return CheckFairyEnchs.getFairyEnchs(fairy).getOrDefault(FairyEnchsEnum.HEALING, 0) > 0;
    }

    @Override
    public boolean canContinueToUse() {
        return CheckFairyEnchs.getFairyEnchs(fairy).getOrDefault(FairyEnchsEnum.HEALING, 0) > 0;
    }

    @Override
    public void tick() {
        if (++timeCounter >= getInterval()) {
            fairy.heal(getHealAmount());
            timeCounter = 0;
        }
    }

    @Override
    public void start() {
        this.timeCounter = 0;
    }

    private int getInterval() {
        return 30;
    }

    private float getHealAmount() {
        return 1;
    }
}
