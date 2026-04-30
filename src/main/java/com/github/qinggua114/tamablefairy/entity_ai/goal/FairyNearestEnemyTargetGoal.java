package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.actstate.ActState;
import com.github.qinggua114.tamablefairy.data.actstate.IActState;
import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

import static com.github.qinggua114.tamablefairy.data.Capabilities.ACT_STATE;

public class FairyNearestEnemyTargetGoal extends TargetGoal {
    private final TargetingConditions targetConditions;
    private LivingEntity target;

    public FairyNearestEnemyTargetGoal(EntityFairy fairy) {
        super(fairy, true, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance());
    }

    public boolean canUse() {
        IActState actState = mob.getCapability(ACT_STATE, null).orElse(new ActState());
        if (!actState.attackMode().equals(AttackModes.ACTIVE)) return false;
        this.findTarget();
        return target != null;
    }

    private AABB getTargetSearchArea(double distance) {
        return mob.getBoundingBox().inflate(distance, 4.0F, distance);
    }

    private void findTarget() {
        Level level = mob.level();
        AABB searchArea = this.getTargetSearchArea(this.getFollowDistance());
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchArea, (entity) -> entity instanceof Enemy);
        target = level.getNearestEntity(entities, this.targetConditions, mob, mob.getX(), mob.getEyeY(), mob.getZ());
    }

    public void start() {
        mob.setTarget(this.target);
        super.start();
    }
}
