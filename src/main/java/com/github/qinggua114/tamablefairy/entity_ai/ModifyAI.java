package com.github.qinggua114.tamablefairy.entity_ai;

import com.github.qinggua114.tamablefairy.data.TameData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class ModifyAI {
    public ModifyAI(){
    }

    public static void letTamed(Mob fairy, Player owner){
        //删除原有目标选择器
        for (WrappedGoal goal : fairy.targetSelector.getAvailableGoals()) {
            fairy.targetSelector.removeGoal(goal);
        }

        if (owner != null) {
            TameData tameData = fairy.getData(TAME_DATA);
            UUID ownerUUID = tameData.owner();
        }

    }

}
