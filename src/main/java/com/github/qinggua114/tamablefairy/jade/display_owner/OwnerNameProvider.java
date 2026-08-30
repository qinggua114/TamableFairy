package com.github.qinggua114.tamablefairy.jade.display_owner;

import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public enum OwnerNameProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        Entity entity = entityAccessor.getEntity();
        if (!CheckTameState.isTamedFairy(entity)) return;
        if (!entityAccessor.getServerData().contains("owner_name")) return;
        tooltip.add(Component.translatable("tooltip.owner", entityAccessor.getServerData().getString("owner_name")));
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, EntityAccessor entityAccessor) {
        EntityFairy fairy = (EntityFairy) entityAccessor.getEntity();
        if (CheckTameState.getOwnerName(fairy) != null)
            compoundTag.putString("owner_name", CheckTameState.getOwnerName(fairy));
        else
            compoundTag.putString("owner_name", "Unknown");
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(MODID, "display_owner");
    }
}
