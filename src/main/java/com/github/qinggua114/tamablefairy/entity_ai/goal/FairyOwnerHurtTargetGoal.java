package com.github.qinggua114.tamablefairy.entity_ai.goal;

import com.github.qinggua114.tamablefairy.data.actstate.ActState;
import com.github.qinggua114.tamablefairy.data.actstate.IActState;
import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Capabilities.ACT_STATE;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

public class FairyOwnerHurtTargetGoal extends TargetGoal {

    private LivingEntity lastHurt;

    public FairyOwnerHurtTargetGoal(Mob mob) {
        super(mob, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        ITameData tameData = mob.getCapability(TAME_DATA, null).orElse(new TameData());
        IActState actState = mob.getCapability(ACT_STATE, null).orElse(new ActState());
        if (!actState.attackMode().equals(AttackModes.PASSIVE)) return false;
        UUID ownerUUID = tameData.owner();
        if (ownerUUID == null) return false;
        Player owner = mob.level().getPlayerByUUID(ownerUUID);
        if (owner == null) return false;

        lastHurt = owner.getLastHurtMob();
        if (lastHurt == null) return false;

        if (lastHurt.getClass().equals(EntityFairy.class)) {
            ITameData targetData = lastHurt.getCapability(TAME_DATA, null).orElse(new TameData());
            if (targetData.tamed() && targetData.owner() != null && targetData.owner().equals(ownerUUID))
                return false;//同一个主人的女仆妖精不会内斗
        }

        return lastHurt != mob && !(lastHurt instanceof EntityMaid) && lastHurt.isAlive();//确保lastHurt不是自己和女仆,且未死亡
    }

    @Override
    public void start() {
        mob.setTarget(lastHurt);
        super.start();
    }
}
