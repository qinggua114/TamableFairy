package com.github.qinggua114.tamablefairy.entity_ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class CustomOwnerHurtTargetGoal extends TargetGoal {

    private LivingEntity lastHurt;

    public CustomOwnerHurtTargetGoal(Mob mob){
        super(mob, true);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        UUID ownerUUID = mob.getData(TAME_DATA).owner();
        if(ownerUUID == null) return false;
        Player owner = mob.level().getPlayerByUUID(ownerUUID);
        if(owner == null) return false;

        lastHurt = owner.getLastHurtMob();
        if(lastHurt == null) return false;

        return lastHurt != mob && lastHurt.isAlive();//确保lastHurt不是自己,且未死亡
    }

    @Override
    public void start(){
        mob.setTarget(lastHurt);
        super.start();
    }
}
