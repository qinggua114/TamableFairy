package com.github.qinggua114.tamablefairy.networks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public record C2SFairyGuiPayLoad(
        int entityId,
        boolean attackEnabled,
        boolean followOwnerEnabled,
        boolean moveAroundEnabled,
        int followDistance,
        int moveRange
) implements CustomPacketPayload {

    public static final Type<C2SFairyGuiPayLoad> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "c2s_data_package"));

    public static final StreamCodec<FriendlyByteBuf, C2SFairyGuiPayLoad> CODEC =
            StreamCodec.composite(
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    C2SFairyGuiPayLoad::entityId,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    C2SFairyGuiPayLoad::attackEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    C2SFairyGuiPayLoad::followOwnerEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    C2SFairyGuiPayLoad::moveAroundEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    C2SFairyGuiPayLoad::followDistance,
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    C2SFairyGuiPayLoad::moveRange,
                    C2SFairyGuiPayLoad::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
