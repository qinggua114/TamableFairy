package com.github.qinggua114.tamablefairy.events;


import com.github.qinggua114.tamablefairy.data.actstate.ActState;
import com.github.qinggua114.tamablefairy.data.actstate.IActState;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchs;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.IFairyEnchs;
import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.qinggua114.tamablefairy.networks.ActStateSyncPacket;
import com.github.qinggua114.tamablefairy.networks.FairyEnchsSyncPacket;
import com.github.qinggua114.tamablefairy.networks.NetWorks;
import com.github.qinggua114.tamablefairy.networks.TameDataSyncPacket;
import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TrackingEntityEvent {
    public TrackingEntityEvent() {
    }

    @SubscribeEvent
    public static void onStartTrackingFairies(PlayerEvent.StartTracking event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        Entity entity = event.getTarget();
        if (!(entity.getClass().equals(EntityFairy.class))) return;

        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;

        Player owner = CheckTameState.getOwner((EntityFairy) entity);
        String ownerName = null;
        if (owner != null) {
            ownerName = owner.getDisplayName().getString();
        }

        if (ownerName != null && (tameData.ownerName() == null || !tameData.ownerName().equals(ownerName))) {
            tameData.setOwnerName(ownerName);
        }

        IFairyEnchs fairyEnchs = entity.getCapability(FAIRY_ENCHS, null).orElse(new FairyEnchs());
        IActState actState = entity.getCapability(ACT_STATE, null).orElse(new ActState());

        NetWorks.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                new TameDataSyncPacket(entity.getId(), tameData.tamed(), tameData.owner())
        );

        NetWorks.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                new ActStateSyncPacket(entity.getId(), actState)
        );

        NetWorks.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                new FairyEnchsSyncPacket(entity.getId(), fairyEnchs.enchantmentsMap())
        );

    }
}
