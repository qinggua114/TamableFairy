package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.TamableFairy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetWorks {
    public static final String NETWORK_VERSION = "2.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TamableFairy.MODID, "main"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION)
    );

    public static void register() {
        CHANNEL.registerMessage(0, TameDataSyncPacket.class,
                TameDataSyncPacket::encode,
                TameDataSyncPacket::decode,
                TameDataSyncPacket::handler,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        CHANNEL.registerMessage(1, C2SFairyGuiPacket.class,
                C2SFairyGuiPacket::encode,
                C2SFairyGuiPacket::decode,
                C2SFairyGuiPacket::handler,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        CHANNEL.registerMessage(2, ActStateSyncPacket.class,
                ActStateSyncPacket::encode,
                ActStateSyncPacket::decode,
                ActStateSyncPacket::handler,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }
}