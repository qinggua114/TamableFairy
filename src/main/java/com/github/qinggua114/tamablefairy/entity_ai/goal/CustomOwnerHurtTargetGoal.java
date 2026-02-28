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

public class CustomOwnerHurtTargetGoal extends TargetGoal {

    private LivingEntity lastHurt;

    public CustomOwnerHurtTargetGoal(Mob mob){
        super(mob, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse(){
        UUID ownerUUID = mob.getData(TAME_DATA).owner();
        if (ownerUUID == null) return false;
        Player owner = mob.level().getPlayerByUUID(ownerUUID);
        if (owner == null) return false;

        lastHurt = owner.getLastHurtMob();
        if (lastHurt == null) return false;

        TameData targetData = lastHurt.getData(TAME_DATA);
        if(targetData.owner().equals(ownerUUID)) return false;//同一个主人的女仆妖精不会内斗

        return lastHurt != mob && !(lastHurt instanceof EntityMaid) &&  lastHurt.isAlive();//确保lastHurt不是自己和女仆,且未死亡
    }

    @Override
    public void start(){
        mob.setTarget(lastHurt);
        super.start();
    }
}
