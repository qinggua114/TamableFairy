package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.ActState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

import static com.github.qinggua114.tamablefairy.data.Attachments.ACT_STATE;
import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class TamedFairyAttackGoal extends Goal {
    private static final int MAX_WITH_IN_RANGE_TIME = 20;
    private final EntityFairy entityFairy;
    private final double minDistance;
    private final double speedIn;
    private Path path;
    private int withInRangeTime;

    public TamedFairyAttackGoal(EntityFairy entityFairy, double minDistance, double speedIn) {
        this.entityFairy = entityFairy;
        this.minDistance = minDistance;
        this.speedIn = speedIn;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity target = this.entityFairy.getTarget();
        if (target == null || !target.isAlive()) return false;
        this.path = this.entityFairy.getNavigation().createPath(target, 0);
        return this.path != null;
    }

    public void start() {
        if (!entityFairy.getData(ACT_STATE).moveAroundEnabled()) return;
        this.entityFairy.getNavigation().moveTo(this.path, this.speedIn);
    }

    public void tick() {
        LivingEntity target = this.entityFairy.getTarget();
        ActState actState = entityFairy.getData(ACT_STATE);
        if (target != null && target.isAlive()) {
            this.entityFairy.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distance = (double)this.entityFairy.distanceTo(target);
            if (this.entityFairy.getSensing().hasLineOfSight(target) && distance >= this.minDistance) {
                if (actState.moveAroundEnabled()) {
                    this.entityFairy.getNavigation().moveTo(target, this.speedIn);
                }
                this.withInRangeTime = 0;
            } else if (distance < this.minDistance) {
                this.entityFairy.getNavigation().stop();
                ++this.withInRangeTime;
                Vec3 motion = this.entityFairy.getDeltaMovement();
                this.entityFairy.setDeltaMovement(motion.x, (double)0.0F, motion.z);
                this.entityFairy.setNoGravity(true);
                if (this.withInRangeTime > MAX_WITH_IN_RANGE_TIME) {
                    float percent = (float)(distance / this.minDistance);
                    this.entityFairy.performRangedAttack(target, 1.0F - percent);
                    this.withInRangeTime = 0;
                }
            } else {
                this.withInRangeTime = 0;
            }
        }
    }

    public boolean canContinueToUse() {
        LivingEntity target = this.entityFairy.getTarget();
        ActState actState = entityFairy.getData(ACT_STATE);
        if (target == null || !target.isAlive()) return false;

        boolean isPlayerAndCanNotBeAttacked = target instanceof Player && (target.isSpectator() || ((Player)target).isCreative());
        if (isPlayerAndCanNotBeAttacked) return false;

        if (target.getData(TAME_DATA).tamed()) return false;

        return true;
    }

    public void stop() {
        this.entityFairy.setTarget(null);
        this.entityFairy.getNavigation().stop();
        this.withInRangeTime = 0;
    }
}
