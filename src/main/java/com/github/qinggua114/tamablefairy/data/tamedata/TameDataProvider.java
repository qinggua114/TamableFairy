package com.github.qinggua114.tamablefairy.data.tamedata;

import com.github.qinggua114.tamablefairy.data.Capabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TameDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final TameData tameData = new TameData();
    private final LazyOptional<ITameData> optional = LazyOptional.of(() -> tameData);

    public TameDataProvider() {
    }

    @Override
    public CompoundTag serializeNBT() {
        return tameData.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        tameData.deserializeNBT(compoundTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return Capabilities.TAME_DATA.orEmpty(capability, optional);
    }
}
