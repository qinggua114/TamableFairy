package com.github.qinggua114.tamablefairy.gui.sliders;

import com.github.qinggua114.tamablefairy.data.ActState;
import com.github.qinggua114.tamablefairy.networks.C2SFairyGuiPayLoad;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.github.qinggua114.tamablefairy.data.Attachments.ACT_STATE;

public class MoveRangeSlider extends AbstractSliderButton {
    private final EntityFairy fairy;

    public MoveRangeSlider(int x, int y, int width, int height, Component message, double value, EntityFairy entity) {
        super(x, y, width, height, message, value);
        fairy = entity;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(
                Component.translatable(
                        "slider.move_range",
                        String.valueOf((int)(this.value * 32))
                )
        );
    }

    @Override
    protected void applyValue() {

    }

    public double getOriginalValue() {
        return this.value;
    }

    public int getValue() {
        return (int)(this.value * 32);
    }

    public void setValue(double newValue) {
        this.value = newValue;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (!this.isMouseOver(mouseX, mouseY)) return false;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
}
