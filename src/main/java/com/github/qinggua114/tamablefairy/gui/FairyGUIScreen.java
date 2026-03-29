package com.github.qinggua114.tamablefairy.gui;

import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

public class FairyGUIScreen extends Screen {
    private final EntityFairy fairy;

    public FairyGUIScreen(EntityFairy entity) {
        super(entity.getDisplayName());
        fairy = entity;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(0, 0, this.width, this.height, 0x80000000);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
