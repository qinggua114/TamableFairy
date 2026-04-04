package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.qinggua114.tamablefairy.gui.FairyMenuProvider;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class OpenFairyGui {
    public OpenFairyGui() {
    }

    @SubscribeEvent
    public static void openFairyGui(PlayerInteractEvent.EntityInteract event) {
        Entity target = event.getTarget();
        if (target.level().isClientSide) return;
        if (!target.getClass().equals(EntityFairy.class)) return;
        TameData tameData = target.getData(TAME_DATA);
        if (!tameData.tamed()) return;

        Player player = event.getEntity();
        if (!(player.getUUID().equals(tameData.owner()))) return;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (mainHand.is(Items.CAKE) || mainHand.is(Items.SUGAR) ||
                offHand.is(Items.CAKE) || offHand.is(Items.SUGAR)) {
            return;
        }

        player.openMenu(new FairyMenuProvider((EntityFairy) target),
                (byteBuf) -> byteBuf.writeInt(target.getId()));
    }
}
