package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.data.actstate.ActState;
import com.github.qinggua114.tamablefairy.data.actstate.IActState;
import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.github.qinggua114.tamablefairy.data.Capabilities.ACT_STATE;

public class ActStateSyncPacket {
    private final int entityId;
    private final AttackModes attackMode;
    private final boolean followOwnerEnabled;
    private final boolean moveAroundEnabled;
    private final int followDistance;
    private final int moveRange;
    private final Vec3 actRangeCenter;

    public ActStateSyncPacket(
            int entityId,
            AttackModes attackMode,
            boolean followOwnerEnabled,
            boolean moveAroundEnabled,
            int followDistance,
            int moveRange,
            Vec3 actRangeCenter
    ) {
        this.entityId = entityId;
        this.attackMode = attackMode;
        this.followOwnerEnabled = followOwnerEnabled;
        this.moveAroundEnabled = moveAroundEnabled;
        this.followDistance = followDistance;
        this.moveRange = moveRange;
        this.actRangeCenter = actRangeCenter;
    }

    public ActStateSyncPacket(int entityId, IActState actState) {
        this.entityId = entityId;
        this.attackMode = actState.attackMode();
        this.followOwnerEnabled = actState.followOwnerEnabled();
        this.moveAroundEnabled = actState.moveAroundEnabled();
        this.followDistance = actState.followDistance();
        this.moveRange = actState.moveRange();
        this.actRangeCenter = actState.actRangeCenter();
    }

    public static ActStateSyncPacket decode(FriendlyByteBuf byteBuf) {
        return new ActStateSyncPacket(
                byteBuf.readInt(),
                byteBuf.readEnum(AttackModes.class),
                byteBuf.readBoolean(),
                byteBuf.readBoolean(),
                byteBuf.readInt(),
                byteBuf.readInt(),
                new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble())
        );
    }


    public static void encode(ActStateSyncPacket packet, FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(packet.entityId);
        byteBuf.writeEnum(packet.attackMode);
        byteBuf.writeBoolean(packet.followOwnerEnabled);
        byteBuf.writeBoolean(packet.moveAroundEnabled);
        byteBuf.writeInt(packet.followDistance);
        byteBuf.writeInt(packet.moveRange);
        byteBuf.writeDouble(packet.actRangeCenter.x);
        byteBuf.writeDouble(packet.actRangeCenter.y);
        byteBuf.writeDouble(packet.actRangeCenter.z);
    }

    public static void handler(ActStateSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            if (FMLLoader.getDist().isDedicatedServer()) return;
            Level level = Minecraft.getInstance().level;
            if (level == null) return;
            Entity entity = level.getEntity(packet.entityId);
            if (entity == null) return;

            IActState actState = entity.getCapability(ACT_STATE).orElse(new ActState());
            actState.setData(packet.attackMode, packet.followOwnerEnabled, packet.moveAroundEnabled, packet.followDistance, packet.moveRange, packet.actRangeCenter);
        });
    }
}

