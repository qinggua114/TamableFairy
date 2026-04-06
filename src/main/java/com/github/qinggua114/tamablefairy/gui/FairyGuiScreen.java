package com.github.qinggua114.tamablefairy.gui;

import com.github.qinggua114.tamablefairy.data.ActState;
import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import com.github.qinggua114.tamablefairy.gui.sliders.FollowDistanceSlider;
import com.github.qinggua114.tamablefairy.gui.sliders.MoveRangeSlider;
import com.github.qinggua114.tamablefairy.networks.C2SFairyGuiPayLoad;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Attachments.ACT_STATE;

public class FairyGuiScreen extends AbstractContainerScreen<FairyGuiMenu> {

    private EntityFairy fairy;
    private Button attackModeBtn;
    private Button enableFollowOwnerBtn;
    private Button enableMoveAroundBtn;
    private FollowDistanceSlider followDistanceSlider;
    private MoveRangeSlider moveRangeSlider;
    private boolean followOwnerEnabled;
    private boolean moveAroundEnabled;
    AttackModes newMode;

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

    @Override
    public void init() {
        super.init();
        attackModeBtn = Button.builder(
                Component.translatable("btn.attack_mode"),
                this::onAttackModeBtnClicked
        ).bounds(this.leftPos + 50, this.topPos + 6, 120, 20).build();
        this.addRenderableWidget(attackModeBtn);

        enableFollowOwnerBtn = Button.builder(
                Component.translatable("btn.enable_follow"),
                this::onEnableFollowOwnerBtnClicked
        ).bounds(this.leftPos + 50, this.topPos + 28, 50, 20).build();
        this.addRenderableWidget(enableFollowOwnerBtn);

        enableMoveAroundBtn = Button.builder(
                Component.translatable("btn.enable_move_around"),
                this::onEnableMoveAroundBtnClicked
        ).bounds(this.leftPos + 50, this.topPos + 50, 50, 20).build();
        this.addRenderableWidget(enableMoveAroundBtn);

        followDistanceSlider = new FollowDistanceSlider(this.leftPos + 120, this.topPos + 28, 50, 20,
                Component.translatable("slider.follow_range"),
                0F, fairy);
        this.addRenderableWidget(followDistanceSlider);

        moveRangeSlider = new MoveRangeSlider(this.leftPos + 120, this.topPos + 50, 50, 20,
                Component.translatable("slider.move_range"),
                0F, fairy);
        this.addRenderableWidget(moveRangeSlider);

        syncState();
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

    @Override
    public void containerTick() {
        super.containerTick();

        String attackMode = switch (newMode) {
            case ACTIVE -> "text.active";
            case PASSIVE -> "text.passive";
            case DISABLED -> "text.disabled";
        };

        this.attackModeBtn.setMessage(
                Component.translatable(
                        "btn.attack_mode",
                        Component.translatable(attackMode)
                )
        );

        this.enableFollowOwnerBtn.setMessage(
                Component.translatable(
                        "btn.enable_follow",
                        followOwnerEnabled ?
                                Component.translatable("text.enabled"):
                                Component.translatable("text.disabled")
                )
        );

        this.enableMoveAroundBtn.setMessage(
                Component.translatable(
                        "btn.enable_move_around",
                        moveAroundEnabled ?
                                Component.translatable("text.enabled"):
                                Component.translatable("text.disabled")
                )
        );
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.followDistanceSlider.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            return true;
        }
        if (this.moveRangeSlider.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private void onAttackModeBtnClicked(Button button) {
        newMode = switch (newMode) {
            case ACTIVE -> AttackModes.PASSIVE;
            case PASSIVE -> AttackModes.DISABLED;
            case DISABLED -> AttackModes.ACTIVE;
        };
    }

    private void onEnableFollowOwnerBtnClicked(Button button) {
        followOwnerEnabled = !followOwnerEnabled;
    }

    private void onEnableMoveAroundBtnClicked(Button button) {
        moveAroundEnabled = !moveAroundEnabled;
    }

    private void syncState() {

        ActState actState = fairy.getData(ACT_STATE);
        newMode = actState.attackMode();
        followOwnerEnabled = actState.followOwnerEnabled();
        moveAroundEnabled = actState.moveAroundEnabled();

        this.followDistanceSlider.setMessage(
                Component.translatable(
                        "slider.follow_range",
                        String.valueOf(actState.followDistance())
                )
        );
        this.followDistanceSlider.setValue((double) actState.followDistance() / 32);

        this.moveRangeSlider.setMessage(
                Component.translatable(
                        "slider.move_range",
                        String.valueOf(actState.moveRange())
                )
        );
            this.moveRangeSlider.setValue((double) actState.moveRange() / 32);
    }

    @Override
    public void onClose() {
        Vec3 actRangeCenter = fairy.position();
        PacketDistributor.sendToServer(new C2SFairyGuiPayLoad(
                fairy.getId(),
                newMode,
                followOwnerEnabled,
                moveAroundEnabled,
                followDistanceSlider.getValue(),
                moveRangeSlider.getValue(),
                actRangeCenter
                )
        );
        super.onClose();
    }
}
