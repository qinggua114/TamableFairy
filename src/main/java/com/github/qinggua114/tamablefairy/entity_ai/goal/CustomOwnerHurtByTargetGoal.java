package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
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
        super(mob, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        UUID ownerUUID = mob.getData(TAME_DATA).owner();
        if (ownerUUID == null) return false;
        Player owner = mob.level().getPlayerByUUID(ownerUUID);
        if (owner == null) return false;

        lastHurtBy = owner.getLastHurtByMob();
        if (lastHurtBy == null) return false;

        if(lastHurtBy instanceof EntityFairy){
            TameData targetData = lastHurtBy.getData(TAME_DATA);
            if (targetData.tamed() && targetData.owner() != null && targetData.owner().equals(ownerUUID))
                return false;//同一个主人的女仆妖精不会内斗
        }

        return lastHurtBy != mob && !(lastHurtBy instanceof EntityMaid) && lastHurtBy.isAlive();//确保lastHurtBy不是自己和女仆,且未死亡
    }

    @Override
    public void start(){
        mob.setTarget(lastHurtBy);
        super.start();
    }

}
