package com.github.qinggua114.tamablefairy.entity_ai.goal;

//import com.github.qinggua114.tamablefairy.data.TameData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class CustomOwnerHurtByTargetGoal extends TargetGoal {

    private LivingEntity lastHurtBy;

    public CustomOwnerHurtByTargetGoal(Mob mob){
        super(mob, true);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        UUID ownerUUID = mob.getData(TAME_DATA).owner();
        if(ownerUUID == null) return false;
        Player owner = mob.level().getPlayerByUUID(ownerUUID);
        if(owner == null) return false;

        lastHurtBy = owner.getLastHurtByMob();
        if(lastHurtBy == null) return false;

        return lastHurtBy != mob && lastHurtBy.isAlive();//确保lastHurtBy不是自己,且未死亡
    }

    @Override
    public void start(){
        mob.setTarget(lastHurtBy);
        super.start();
    }

}
