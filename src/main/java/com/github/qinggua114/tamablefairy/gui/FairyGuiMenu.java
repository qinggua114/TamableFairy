package com.github.qinggua114.tamablefairy.gui;

import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static com.github.qinggua114.tamablefairy.gui.Menus.FAIRY_GUI_MENU;

public class FairyGuiMenu extends AbstractContainerMenu {
    public EntityFairy fairy = null;

    public FairyGuiMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, extraData.readInt());
    }

    public FairyGuiMenu(int containerId, Inventory playerInventory, int entityId) {
        super(FAIRY_GUI_MENU.get(), containerId);
        if (!playerInventory.player.level().isClientSide) return;
        fairy = (EntityFairy) playerInventory.player.level().getEntity(entityId);
        this.createInv(playerInventory);
    }

    public FairyGuiMenu(int containerId, Inventory playerInventory, EntityFairy entity) {
        super(FAIRY_GUI_MENU.get(), containerId);
        fairy = entity;
        this.createInv(playerInventory);
    }

    private void createInv(Inventory playerInv) {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlot(new Slot(playerInv, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));

    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack originalItemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack itemStack = slot.getItem();
        originalItemStack = itemStack.copy();
        if (index < 27) {
            if (!moveItemStackTo(itemStack, 27, 36, false)){
                return ItemStack.EMPTY;
            }
        }

        if (index > 26 && index < 36) {
            if (!moveItemStackTo(itemStack, 0, 27, false)){
                return ItemStack.EMPTY;
            }
        }
        return originalItemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
