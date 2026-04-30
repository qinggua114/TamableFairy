package com.github.qinggua114.tamablefairy.data.actstate;

import com.github.qinggua114.tamablefairy.data.Capabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActStateProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ActState actState = new ActState();
    private final LazyOptional<IActState> optional = LazyOptional.of(() -> actState);

    public ActStateProvider() {
    }

    @Override
    public CompoundTag serializeNBT() {
        return actState.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        actState.deserializeNBT(compoundTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return Capabilities.ACT_STATE.orEmpty(capability, optional);
    }
}
