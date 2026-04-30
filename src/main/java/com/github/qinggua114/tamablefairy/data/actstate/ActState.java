package com.github.qinggua114.tamablefairy.data.actstate;

import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import com.github.qinggua114.tamablefairy.networks.NetWorks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.PacketDistributor;

public class ActState implements IActState, INBTSerializable<CompoundTag> {
    private AttackModes attackMode = AttackModes.PASSIVE;
    private boolean followOwnerEnabled = true;
    private boolean moveAroundEnabled = true;
    private int followDistance = 7;
    private int moveRange = 5;
    private Vec3 actRangeCenter = Vec3.ZERO;

    @Override
    public AttackModes attackMode() {
        return attackMode;
    }

    @Override
    public boolean followOwnerEnabled() {
        return followOwnerEnabled;
    }

    @Override
    public boolean moveAroundEnabled() {
        return moveAroundEnabled;
    }

    @Override
    public int followDistance() {
        return followDistance;
    }

    @Override
    public int moveRange() {
        return moveRange;
    }

    @Override
    public Vec3 actRangeCenter() {
        return actRangeCenter;
    }

    @Override
    public void setData(AttackModes attackMode, boolean followOwnerEnabled, boolean moveAroundEnabled, int followDistance, int moveRange, Vec3 actRangeCenter) {
        this.attackMode = attackMode;
        this.followOwnerEnabled = followOwnerEnabled;
        this.moveAroundEnabled = moveAroundEnabled;
        this.followDistance = followDistance;
        this.moveRange = moveRange;
        this.actRangeCenter = actRangeCenter;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tempTag = new CompoundTag();
        tempTag.putString(TagKeys.ATTACK_MODE, attackMode.getSerializedName());
        tempTag.putBoolean(TagKeys.FOLLOW_OWNER_ENABLED, followOwnerEnabled);
        tempTag.putBoolean(TagKeys.MOVE_AROUND_ENABLED, moveAroundEnabled);
        tempTag.putInt(TagKeys.FOLLOW_DISTANCE, followDistance);
        tempTag.putInt(TagKeys.MOVE_RANGE, moveRange);

        int[] actCenterArray = new int[3];
        actCenterArray[0] = (int) actRangeCenter.x;
        actCenterArray[1] = (int) actRangeCenter.y;
        actCenterArray[2] = (int) actRangeCenter.z;
        tempTag.putIntArray(TagKeys.ACT_RANGE_CENTER, actCenterArray);
        return tempTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        this.attackMode = AttackModes.valueOf(compoundTag.getString(TagKeys.ATTACK_MODE).toUpperCase());
        this.followOwnerEnabled = compoundTag.getBoolean(TagKeys.FOLLOW_OWNER_ENABLED);
        this.moveAroundEnabled = compoundTag.getBoolean(TagKeys.MOVE_AROUND_ENABLED);
        this.followDistance = compoundTag.getInt(TagKeys.FOLLOW_DISTANCE);
        this.moveRange = compoundTag.getInt(TagKeys.MOVE_RANGE);

        int[] actCenterArray = compoundTag.getIntArray(TagKeys.ACT_RANGE_CENTER);
        this.actRangeCenter = new Vec3(actCenterArray[0], actCenterArray[1], actCenterArray[2]);
    }
}
