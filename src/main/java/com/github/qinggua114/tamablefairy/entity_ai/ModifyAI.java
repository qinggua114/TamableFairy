package com.github.qinggua114.tamablefairy.entity_ai;

import com.github.qinggua114.tamablefairy.entity_ai.goal.CustomFollowOwnerGoal;
//import com.mojang.logging.LogUtils;
import com.github.qinggua114.tamablefairy.entity_ai.goal.CustomOwnerHurtByTargetGoal;
import com.github.qinggua114.tamablefairy.entity_ai.goal.CustomOwnerHurtTargetGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
//import org.slf4j.Logger;

public class ModifyAI {
    public ModifyAI(){
    }

    //private static final Logger LOGGER = LogUtils.getLogger();

    public static void letTamed(Mob fairy){
        //删除原有目标选择器
        for (WrappedGoal goal : fairy.targetSelector.getAvailableGoals()) {
            fairy.targetSelector.removeGoal(goal.getGoal());
        }
        fairy.setTarget(null);

        //跟随主人
        fairy.goalSelector.addGoal(1, new CustomFollowOwnerGoal(fairy, 1, 5, 3, true, 16));

        //
        fairy.targetSelector.addGoal(1,new CustomOwnerHurtByTargetGoal(fairy));
        fairy.targetSelector.addGoal(2, new CustomOwnerHurtTargetGoal(fairy));
    }

}
