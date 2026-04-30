package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.data.actstate.ActState;
import com.github.qinggua114.tamablefairy.data.actstate.IActState;
import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

import static com.github.qinggua114.tamablefairy.data.Capabilities.ACT_STATE;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

public class C2SFairyGuiPacket {
    private final int entityId;
    private final AttackModes attackMode;
    private final boolean followOwnerEnabled;
    private final boolean moveAroundEnabled;
    private final int followDistance;
    private final int moveRange;
    private final Vec3 actRangeCenter;

    public C2SFairyGuiPacket(
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

    public static C2SFairyGuiPacket decode(FriendlyByteBuf byteBuf) {
        return new C2SFairyGuiPacket(
                byteBuf.readInt(),
                byteBuf.readEnum(AttackModes.class),
                byteBuf.readBoolean(),
                byteBuf.readBoolean(),
                byteBuf.readInt(),
                byteBuf.readInt(),
                new Vec3(byteBuf.readDouble(), byteBuf.readDouble(), byteBuf.readDouble())
        );
    }


    public static void encode(C2SFairyGuiPacket packet, FriendlyByteBuf byteBuf) {
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

    public static void handler(C2SFairyGuiPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        {
            Player player = ctx.get().getSender();
            if (player == null) return;
            Entity entity = player.level().getEntity(packet.entityId);
            if (entity == null) return;
            ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
            if (!tameData.tamed()) return;
            if (!tameData.owner().equals(player.getUUID())) return;

            IActState actState = entity.getCapability(ACT_STATE).orElse(new ActState());
            actState.setData(packet.attackMode, packet.followOwnerEnabled, packet.moveAroundEnabled, packet.followDistance, packet.moveRange, packet.actRangeCenter);

            if (actState.attackMode().equals(AttackModes.DISABLED)) ((Mob) entity).setTarget(null);

            NetWorks.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> entity),
                    new ActStateSyncPacket(entity.getId(), actState)
            );
        });
    }
}

