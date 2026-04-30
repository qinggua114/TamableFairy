/*package com.github.qinggua114.tamablefairy.networks;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenFairyGuiPacket {
    private final int entityId;


    public OpenFairyGuiPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(OpenFairyGuiPacket packet, FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(packet.entityId);
    }

    public static OpenFairyGuiPacket decode(FriendlyByteBuf byteBuf) {
        return new OpenFairyGuiPacket(byteBuf.readInt());
    }

    public static void handler(OpenFairyGuiPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() ->
        {
            Minecraft.getInstance()
        });
    }
}
*/