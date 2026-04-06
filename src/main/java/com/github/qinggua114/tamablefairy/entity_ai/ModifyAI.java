package com.github.qinggua114.tamablefairy.entity_ai;

import com.github.qinggua114.tamablefairy.entity_ai.goal.*;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

import java.util.ArrayList;
import java.util.List;

public class ModifyAI {
    public ModifyAI() {
    }


    public static void letTamed(EntityFairy fairy) {
        //删除原有目标选择器
        List<WrappedGoal> goalSelectors = new ArrayList<>(fairy.targetSelector.getAvailableGoals());
        for (WrappedGoal goal : goalSelectors) {
            if (goal != null) {
                fairy.targetSelector.removeGoal(goal.getGoal());
            }
        }
        fairy.setTarget(null);

        //删除不需要的原始Goal
        List<WrappedGoal> goals = new ArrayList<>(fairy.goalSelector.getAvailableGoals());
        for (WrappedGoal goal : goals) {
            if (goal.getGoal() instanceof MoveTowardsRestrictionGoal ||
                    goal.getGoal() instanceof RandomStrollGoal) {
                fairy.goalSelector.removeGoal(goal.getGoal());
            }
        }

        //跟随主人
        fairy.goalSelector.addGoal(1, new FairyFollowOwnerGoal(fairy, 1, 5, true, 16));
        //新的随机移动Goal
        fairy.goalSelector.addGoal(3, new FairyRandomStrollGoal(fairy, 1));

        //攻击主人的目标
        fairy.targetSelector.addGoal(1, new FairyOwnerHurtByTargetGoal(fairy));
        fairy.targetSelector.addGoal(2, new FairyOwnerHurtTargetGoal(fairy));
        //主动攻击
        fairy.targetSelector.addGoal(1, new FairyNearestEnemyTargetGoal(fairy));

        //更改属性,稍微加强一点,要不太不抗揍了
        AttributeInstance maxHealth = fairy.getAttribute(Attributes.ARMOR);
        if (maxHealth != null) maxHealth.setBaseValue(5);
        //AttributeInstance attackDamage = fairy.getAttribute(Attributes.ATTACK_DAMAGE);
        //if(attackDamage != null) attackDamage.setBaseValue(1145);弹幕伤害是硬编码的,不能通过属性修改(悲)(才不是我懒得Mixin!)
        AttributeInstance flyingSpeed = fairy.getAttribute(Attributes.FLYING_SPEED);
        if (flyingSpeed != null) flyingSpeed.setBaseValue(0.7);
        AttributeInstance movementSpeed = fairy.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) movementSpeed.setBaseValue(0.5);
    }

}
