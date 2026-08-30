package com.github.qinggua114.tamablefairy.data.fairy_enchantments;

import com.github.qinggua114.tamablefairy.data.Capabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FairyEnchsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final FairyEnchs fairyEnchs = new FairyEnchs();
    private final LazyOptional<IFairyEnchs> optional = LazyOptional.of(() -> fairyEnchs);

    public FairyEnchsProvider() {
    }

    @Override
    public CompoundTag serializeNBT() {
        return fairyEnchs.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        fairyEnchs.deserializeNBT(compoundTag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return Capabilities.FAIRY_ENCHS.orEmpty(capability, optional);
    }
}
