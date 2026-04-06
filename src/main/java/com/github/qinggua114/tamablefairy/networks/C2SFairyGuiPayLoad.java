package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.entity_ai.AttackModes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public record C2SFairyGuiPayLoad(
        int entityId,
        AttackModes attackMode,
        boolean followOwnerEnabled,
        boolean moveAroundEnabled,
        int followDistance,
        int moveRange,
        Vec3 actRangeCenter
) implements CustomPacketPayload {

    public static final Type<C2SFairyGuiPayLoad> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "c2s_fairy_data_package"));

    public static final StreamCodec<FriendlyByteBuf, C2SFairyGuiPayLoad> CODEC =
            new StreamCodec<FriendlyByteBuf, C2SFairyGuiPayLoad>() {
                @Override
                public C2SFairyGuiPayLoad decode(FriendlyByteBuf byteBuf) {
                    return new C2SFairyGuiPayLoad(
                            byteBuf.readInt(),
                            byteBuf.readEnum(AttackModes.class),
                            byteBuf.readBoolean(),
                            byteBuf.readBoolean(),
                            byteBuf.readInt(),
                            byteBuf.readInt(),
                            byteBuf.readVec3()
                    );
                }

                @Override
                public void encode(FriendlyByteBuf byteBuf, C2SFairyGuiPayLoad payLoad) {
                    byteBuf.writeInt(payLoad.entityId);
                    byteBuf.writeEnum(payLoad.attackMode);
                    byteBuf.writeBoolean(payLoad.followOwnerEnabled);
                    byteBuf.writeBoolean(payLoad.moveAroundEnabled);
                    byteBuf.writeInt(payLoad.followDistance);
                    byteBuf.writeInt(payLoad.moveRange);
                    byteBuf.writeVec3(payLoad.actRangeCenter);
                }
            };

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
