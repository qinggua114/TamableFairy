package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchs;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.data.fairy_enchantments.IFairyEnchs;
import com.github.qinggua114.tamablefairy.networks.FairyEnchsSyncPacket;
import com.github.qinggua114.tamablefairy.networks.NetWorks;
import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.item.enchantment.EndersEnderEnchantment;
import com.github.tartaricacid.touhoulittlemaid.item.enchantment.ImpedingEnchantment;
import com.github.tartaricacid.touhoulittlemaid.item.enchantment.SpeedyEnchantment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.EnumMap;
import java.util.Map;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.FAIRY_ENCHS;
import static java.lang.Math.max;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UseEnchantedBookEvent {

    private static final Map<Class<? extends Enchantment>, FairyEnchsEnum> enchsMapping = Map.of(
            MultiShotEnchantment.class, FairyEnchsEnum.MULTI_SHOT,
            QuickChargeEnchantment.class, FairyEnchsEnum.QUICK_CHARGE,
            ArrowDamageEnchantment.class, FairyEnchsEnum.POWER,
            ThornsEnchantment.class, FairyEnchsEnum.THORNS,
            ProtectionEnchantment.class, FairyEnchsEnum.PROTECTION,
            MendingEnchantment.class, FairyEnchsEnum.HEALING,
            SpeedyEnchantment.class, FairyEnchsEnum.SPEEDY,
            ImpedingEnchantment.class, FairyEnchsEnum.IMPEDING,
            EndersEnderEnchantment.class, FairyEnchsEnum.ENDERS_ENDER
    );

    public UseEnchantedBookEvent() {
    }

    @SubscribeEvent
    public static void onUseEnchantedBook(PlayerInteractEvent.EntityInteract event) {
        Entity entity = event.getTarget();
        if (entity.level().isClientSide) return;

        ItemStack itemStack = event.getItemStack();
        if (!itemStack.is(Items.ENCHANTED_BOOK)) return;

        if (!CheckTameState.isFairy(entity)) return;
        Player player = event.getEntity();
        EntityFairy fairy = (EntityFairy) entity;
        if (!player.getUUID().equals(CheckTameState.getOwnerID(fairy)) && !player.isCreative()) return;

        Map<Enchantment, Integer> toAddEnchsMap = EnchantmentHelper.getEnchantments(itemStack);
        System.out.println("OriginalEnch:");
        System.out.println(toAddEnchsMap);
        Map<FairyEnchsEnum, Integer> tmpFairyEnchsMap = new EnumMap<>(FairyEnchsEnum.class);
        toAddEnchsMap.forEach((enchantment, level) -> {
            FairyEnchsEnum fairyEnch = enchsMapping.get(enchantment.getClass());
            if (fairyEnch != null) {
                tmpFairyEnchsMap.put(fairyEnch, level);
            }
        });
        System.out.println("tmpFairyEnchsMap:");
        System.out.println(tmpFairyEnchsMap);
        IFairyEnchs fairyEnchs = fairy.getCapability(FAIRY_ENCHS).orElse(new FairyEnchs());
        fairyEnchs.setEnchantmentsMap(mergeEnchs(fairyEnchs.enchantmentsMap(), tmpFairyEnchsMap, player.isCreative()));
        System.out.println(fairyEnchs.enchantmentsMap());
        NetWorks.CHANNEL.send(
                PacketDistributor.TRACKING_ENTITY.with(() -> fairy),
                new FairyEnchsSyncPacket(entity.getId(), fairyEnchs.enchantmentsMap())
        );
    }

    private static Map<FairyEnchsEnum, Integer> mergeEnchs(
            Map<FairyEnchsEnum, Integer> existing,
            Map<FairyEnchsEnum, Integer> toAdd,
            boolean allowExceedingLimit
    ) {

        Map<FairyEnchsEnum, Integer> newMap = new EnumMap<>(FairyEnchsEnum.class);
        newMap.putAll(existing);

        toAdd.forEach((fairyEnch, level) -> {

            if (newMap.getOrDefault(fairyEnch, 0) == level && level > 0) {
                int newLevel = level + 1;
                if (newLevel >= fairyEnch.getLimit() && !allowExceedingLimit) newLevel = fairyEnch.getLimit();
                newMap.put(fairyEnch, newLevel);
            } else if (level > 0) {
                int newLevel = max(level, newMap.getOrDefault(fairyEnch, 0));
                if (newLevel >= fairyEnch.getLimit() && !allowExceedingLimit) newLevel = fairyEnch.getLimit();
                newMap.put(fairyEnch, newLevel);
            }

        });
        System.out.println(newMap);
        return newMap;

    }
}
