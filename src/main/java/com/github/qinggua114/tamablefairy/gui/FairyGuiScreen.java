package com.github.qinggua114.tamablefairy.gui;

import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public class FairyGuiScreen extends AbstractContainerScreen<FairyGuiMenu> {


    private EntityFairy fairy;

    public FairyGuiScreen(FairyGuiMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        fairy = this.menu.fairy;
        if (fairy == null) fairy = new EntityFairy(playerInventory.player.level());
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MODID, "texture/gui/fairy_gui.png");

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 6, this.topPos + 6, this.leftPos + 44, this.topPos + 76, 20, 0, mouseX, mouseY, fairy);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
