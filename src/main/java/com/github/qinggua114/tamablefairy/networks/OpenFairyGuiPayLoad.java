package com.github.qinggua114.tamablefairy.networks;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

public record OpenFairyGuiPayLoad(int entityId) implements CustomPacketPayload {
    public static final Type<OpenFairyGuiPayLoad> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "open_entity_gui"));

    public static final StreamCodec<FriendlyByteBuf, OpenFairyGuiPayLoad> CODEC =
            StreamCodec.composite(
                    StreamCodec.of(FriendlyByteBuf::writeInt, FriendlyByteBuf::readInt),
                    OpenFairyGuiPayLoad::entityId,
                    OpenFairyGuiPayLoad::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
