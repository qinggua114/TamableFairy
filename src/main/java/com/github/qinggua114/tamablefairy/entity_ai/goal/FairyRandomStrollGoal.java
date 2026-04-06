package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.ActState;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

import static com.github.qinggua114.tamablefairy.data.Attachments.ACT_STATE;

public class FairyRandomStrollGoal extends RandomStrollGoal {
    private final Mob mob;
    private double wantedX;
    private double wantedY;
    private double wantedZ;
    private final double speedModifier;
    ;

    public FairyRandomStrollGoal(Mob mob, double speedModifier) {
        super((PathfinderMob) mob, speedModifier);
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.getRandom().nextInt(60) != 0) return false;
        ActState actState = mob.getData(ACT_STATE);
        if (!actState.moveAroundEnabled()) return false;
        Vec3 vec3 = this.getRandomPositionAroundCenter(actState.actRangeCenter(), actState.moveRange());
        if (vec3 == null) return false;
        this.wantedX = vec3.x;
        this.wantedY = vec3.y;
        this.wantedZ = vec3.z;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone() && mob.getData(ACT_STATE).moveAroundEnabled();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
    }

    public Vec3 getRandomPositionAroundCenter(Vec3 center, int radius) {
        double r = radius * Math.cbrt(this.mob.getRandom().nextDouble());
        double theta = 2 * Math.PI * this.mob.getRandom().nextDouble();
        double phi = Math.acos(2 * this.mob.getRandom().nextDouble() - 1);

        double x = r * Math.sin(phi) * Math.cos(theta);
        double y = r * Math.sin(phi) * Math.sin(theta);
        double z = r * Math.cos(phi);

        return center.add(x, y, z);
    }
}
