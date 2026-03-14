package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.TamableFairy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetWorks {
    public static final String NETWORK_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(TamableFairy.MODID, "main"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION)
    );

    public static void register() {
        CHANNEL.registerMessage(0, TameDataSyncPacket.class,
                TameDataSyncPacket::encode,
                TameDataSyncPacket::decode,
                TameDataSyncPacket::handle);
    }
}