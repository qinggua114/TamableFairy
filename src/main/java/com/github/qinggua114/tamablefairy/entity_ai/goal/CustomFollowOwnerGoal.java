package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.TameData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import java.util.EnumSet;
import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class CustomFollowOwnerGoal extends Goal {
    private final Mob mob;
    private final double speed;
    private final float startDistance;
    private final float stopDistance;
    private final float teleportDistance;
    private final boolean enableTeleport;
    private Player owner = null;
    private int timeToRePathing;

    public CustomFollowOwnerGoal(Mob mob, double speedModifier, float startDistance, float stopDistance, boolean enableTeleport, float teleportDistance) {
        this.mob = mob;
        if(speedModifier == -1) this.speed = mob.getSpeed();//当speedModifier为-1时,使用生物当前默认速度
        else this.speed = speedModifier;//否则修改速度为speedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.teleportDistance = teleportDistance;
        this.enableTeleport = enableTeleport;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse(){
        //检查null值和驯服状态
        TameData tameData = mob.getData(TAME_DATA);
        if(!tameData.tamed()) return false;
        UUID ownerUUID = tameData.owner();
        if(ownerUUID == null) return false;
        this.owner = mob.level().getPlayerByUUID(ownerUUID);
        if(owner == null) return false;

        return mob.distanceToSqr(owner) > startDistance * startDistance;//距离大于startDistance时自动跟随

    }

    @Override
    public boolean canContinueToUse(){
        return owner != null && mob.distanceToSqr(owner) > stopDistance * stopDistance;//owner不为null且距离大于stopDistance时继续跟随
    }

    @Override
    public void start(){
        this.timeToRePathing = 0;
    }

    @Override
    public void stop(){
        this.owner = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick(){
        if(mob.distanceToSqr(owner) > teleportDistance * teleportDistance && enableTeleport)
            mob.teleportTo(owner.getX(), owner.getY(), owner.getZ());//若启用teleport,与主人的距离超过teleportDistance时将生物传送至主人处
        //定时重新寻路
        if(--timeToRePathing <= 0){
            timeToRePathing = 10;
            mob.getNavigation().moveTo(owner, speed);
        }
    }
}
