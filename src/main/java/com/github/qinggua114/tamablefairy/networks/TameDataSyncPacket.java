package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.data.ITameData;
import com.github.qinggua114.tamablefairy.data.TameData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

public class TameDataSyncPacket {
    private final int entityId;
    private final boolean tamed;
    private final UUID owner;

    public TameDataSyncPacket(int entityId, boolean tamed, UUID owner) {
        this.entityId = entityId;
        this.tamed = tamed;
        this.owner = owner;
    }

    public static void encode(TameDataSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.entityId);
        buf.writeBoolean(packet.tamed);
        buf.writeUUID(packet.owner);
    }

    public static TameDataSyncPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean tamed = buf.readBoolean();
        UUID owner = buf.readUUID();
        return new TameDataSyncPacket(entityId, tamed, owner);
    }

    public static void handle(TameDataSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Entity entity = null;
            if (Minecraft.getInstance().level != null) {
                entity = Minecraft.getInstance().level.getEntity(packet.entityId);
            }
            if (entity != null) {
                ITameData tameData = entity.getCapability(TAME_DATA).orElse(new TameData());
                tameData.setTamed(packet.tamed);
                tameData.setOwner(packet.owner);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}