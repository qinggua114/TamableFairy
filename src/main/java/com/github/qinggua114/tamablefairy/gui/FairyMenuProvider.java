package com.github.qinggua114.tamablefairy.gui;

import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FairyMenuProvider implements MenuProvider {
    private final Component title;
    private final EntityFairy fairy;

    public FairyMenuProvider(EntityFairy entity) {
        fairy = entity;
        title = fairy.getDisplayName();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return title;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new FairyGuiMenu(containerId, inventory, fairy);
    }
}
