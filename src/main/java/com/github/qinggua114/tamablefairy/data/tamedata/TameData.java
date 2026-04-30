package com.github.qinggua114.tamablefairy.data.tamedata;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class TameData implements ITameData, INBTSerializable<CompoundTag> {

    private boolean tamed = false;
    private UUID owner = null;

    public TameData() {
    }

    @Override
    public boolean tamed() {
        return tamed;
    }

    @Override
    public UUID owner() {
        return owner;
    }

    @Override
    public void setTamed(boolean _tamed) {
        tamed = _tamed;
    }

    @Override
    public void setOwner(UUID _owner) {
        owner = _owner;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tempTag = new CompoundTag();
        tempTag.putBoolean("tamed", tamed);
        if (owner != null)
            tempTag.putUUID("owner", owner);
        return tempTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        this.tamed = compoundTag.getBoolean("tamed");
        if (compoundTag.contains("owner"))
            this.owner = compoundTag.getUUID("owner");
        else
            this.owner = null;
    }
}