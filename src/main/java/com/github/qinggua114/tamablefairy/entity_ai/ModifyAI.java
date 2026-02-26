package com.github.qinggua114.tamablefairy.entity_ai;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.slf4j.Logger;

public class ModifyAI {
    public ModifyAI(){
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public static void letTamed(Mob fairy){
        //删除原有目标选择器
        for (WrappedGoal goal : fairy.targetSelector.getAvailableGoals()) {
            fairy.targetSelector.removeGoal(goal.getGoal());
            LOGGER.info(goal.getGoal().getClass().getSimpleName());
        }
        fairy.setTarget(null);

    }

}
