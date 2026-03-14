package com.github.qinggua114.tamablefairy.events;


import com.github.qinggua114.tamablefairy.data.ITameData;
import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.qinggua114.tamablefairy.networks.NetWorks;
import com.github.qinggua114.tamablefairy.networks.TameDataSyncPacket;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TrackingEntityEvent {
    public TrackingEntityEvent(){
    }

    @SubscribeEvent
    public static void onStartTrackingFairies(PlayerEvent.StartTracking event){
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        Entity entity = event.getTarget();
        if (!( entity.getClass().equals(EntityFairy.class) )) return;

        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;

        NetWorks.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                new TameDataSyncPacket(entity.getId(), tameData.tamed(), tameData.owner())
        );

    }
}
