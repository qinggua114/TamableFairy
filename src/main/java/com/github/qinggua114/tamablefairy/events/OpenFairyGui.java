package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.qinggua114.tamablefairy.gui.FairyMenuProvider;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OpenFairyGui {
    public OpenFairyGui() {
    }

    @SubscribeEvent
    public static void openFairyGui(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        if (target.level().isClientSide) return;
        if (!target.getClass().equals(EntityFairy.class)) return;
        ITameData tameData = target.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;

        Player player = event.getEntity();
        if (!(player.getUUID().equals(tameData.owner()))) return;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (mainHand.is(Items.CAKE) || mainHand.is(Items.SUGAR) ||
                offHand.is(Items.CAKE) || offHand.is(Items.SUGAR)) {
            return;
        }

        NetworkHooks.openScreen((ServerPlayer) player, new FairyMenuProvider((EntityFairy) target), byteBuf -> {
            byteBuf.writeInt(target.getId());
        });
    }
}
