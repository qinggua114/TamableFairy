package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.ActState;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

import static com.github.qinggua114.tamablefairy.data.Attachments.ACT_STATE;

public class FairyMoveTowardsRestrictionGoal extends Goal {
    private final Mob mob;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private final double speedModifier;

    public FairyMoveTowardsRestrictionGoal(Mob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        ActState actState = mob.getData(ACT_STATE);
        if (!actState.moveAroundEnabled()) return false;
        if (mob.isWithinRestriction()) return false;
        Vec3 vec3 = DefaultRandomPos.getPosTowards((PathfinderMob) this.mob, actState.moveRange(), actState.moveRange(), Vec3.atBottomCenterOf(this.mob.getRestrictCenter()), (double) ((float) Math.PI / 2F));
        if (vec3 == null) return false;
        this.wantedX = vec3.x;
        this.wantedY = vec3.y;
        this.wantedZ = vec3.z;
        return true;
    }

    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone() && mob.getData(ACT_STATE).moveAroundEnabled();
    }

    public void start() {
        this.mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
    }
}
