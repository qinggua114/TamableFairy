//已弃用，改用Attachment向客户端同步数据
package com.github.qinggua114.tamablefairy.networks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public record S2CFairyGuiPayLoad(
        int entityId,
        boolean attackEnabled,
        boolean followOwnerEnabled,
        boolean moveAroundEnabled,
        int followDistance,
        int moveRange
) implements CustomPacketPayload {

    public static final Type<S2CFairyGuiPayLoad> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "s2c_data_package"));

    public static final StreamCodec<FriendlyByteBuf, S2CFairyGuiPayLoad> CODEC =
            StreamCodec.composite(
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    S2CFairyGuiPayLoad::entityId,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    S2CFairyGuiPayLoad::attackEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    S2CFairyGuiPayLoad::followOwnerEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeBoolean, FriendlyByteBuf::readBoolean),
                    S2CFairyGuiPayLoad::moveAroundEnabled,
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    S2CFairyGuiPayLoad::followDistance,
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    S2CFairyGuiPayLoad::moveRange,
                    S2CFairyGuiPayLoad::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
