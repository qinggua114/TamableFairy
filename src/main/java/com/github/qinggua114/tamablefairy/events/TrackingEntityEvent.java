package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class TrackingEntityEvent {

    public TrackingEntityEvent(){
    }

    @SubscribeEvent
    public static void onStartTrackingFairies(PlayerEvent.StartTracking event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        Entity entity = event.getTarget();
        if (!CheckTameState.isTamedFairy(entity)) return;

        Player owner = CheckTameState.getOwner((EntityFairy) entity);
        String ownerName = null;
        if (owner != null) {
            ownerName = owner.getDisplayName().getString();
        }

        TameData tameData = entity.getData(TAME_DATA);
        if (ownerName != null && (tameData.ownerName() == null || !tameData.ownerName().equals(ownerName))) {
            entity.setData(TAME_DATA, new TameData(true, tameData.owner(), ownerName));
        }
    }
}
