package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchs;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.IFairyEnchs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.github.qinggua114.tamablefairy.data.Capabilities.FAIRY_ENCHS;

public class FairyEnchsSyncPacket {
    private final int entityId;
    private final Map<FairyEnchsEnum, Integer> enchantmentsMap;

    public FairyEnchsSyncPacket(int entityId, Map<FairyEnchsEnum, Integer> enchantmentsMap) {
        this.entityId = entityId;
        this.enchantmentsMap = enchantmentsMap;
    }

    public static FairyEnchsSyncPacket decode(FriendlyByteBuf byteBuf) {
        int entityId = byteBuf.readInt();
        int size = byteBuf.readInt();
        Map<FairyEnchsEnum, Integer> tmpEnchantmentsMap = new EnumMap<>(FairyEnchsEnum.class);
        for (int i = 0; i < size; i++) {
            tmpEnchantmentsMap.put(byteBuf.readEnum(FairyEnchsEnum.class), byteBuf.readInt());
        }
        return new FairyEnchsSyncPacket(entityId, tmpEnchantmentsMap);
    }

    public static void encode(FairyEnchsSyncPacket packet, FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(packet.entityId);
        byteBuf.writeInt(packet.enchantmentsMap.size());
        packet.enchantmentsMap.forEach((enchantment, level) -> {
            byteBuf.writeEnum(enchantment);
            byteBuf.writeInt(level);
        });
    }

    public static void handler(FairyEnchsSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        if (!ctx.get().getDirection().getReceptionSide().isClient()) return;
        ctx.get().enqueueWork(() -> clientHandler(packet));
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientHandler(FairyEnchsSyncPacket packet) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        Entity entity = level.getEntity(packet.entityId);
        if (entity == null) return;

        IFairyEnchs enchantments = entity.getCapability(FAIRY_ENCHS).orElse(new FairyEnchs());
        enchantments.setEnchantmentsMap(packet.enchantmentsMap);
    }
}
