package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.fairy_enchs.FairyEnchs;
import com.github.qinggua114.tamablefairy.data.fairy_enchs.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.datagen.EnchantmentKeys;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.EnumMap;
import java.util.Map;

import static com.github.qinggua114.tamablefairy.data.Attachments.FAIRY_ENCHS;
import static java.lang.Math.max;

@EventBusSubscriber
public class UseEnchantedBookEvent {

    private static final Map<ResourceKey<Enchantment>, FairyEnchsEnum> enchsMapping = Map.of(
            Enchantments.MULTISHOT, FairyEnchsEnum.MULTI_SHOT,
            Enchantments.QUICK_CHARGE, FairyEnchsEnum.QUICK_CHARGE,
            Enchantments.POWER, FairyEnchsEnum.POWER,
            Enchantments.THORNS, FairyEnchsEnum.THORNS,
            Enchantments.PROTECTION, FairyEnchsEnum.PROTECTION,
            Enchantments.MENDING, FairyEnchsEnum.HEALING,
            EnchantmentKeys.SPEEDY, FairyEnchsEnum.SPEEDY,
            EnchantmentKeys.IMPEDING, FairyEnchsEnum.IMPEDING,
            EnchantmentKeys.ENDERS_ENDER, FairyEnchsEnum.ENDERS_ENDER
    );
    public UseEnchantedBookEvent(){
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

        ItemEnchantments toAddEnchs = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);
        System.out.println("OriginalEnch:");
        System.out.println(toAddEnchs);
        Map<FairyEnchsEnum, Integer> tmpFairyEnchsMap = new EnumMap<>(FairyEnchsEnum.class);
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : toAddEnchs.entrySet()) {
            FairyEnchsEnum fairyEnch = enchsMapping.get(entry.getKey().getKey());
            if (fairyEnch != null) {
                tmpFairyEnchsMap.put(fairyEnch, entry.getIntValue());
            }
        }
        System.out.println("tmpFairyEnchsMap:");
        System.out.println(tmpFairyEnchsMap);
        if (tmpFairyEnchsMap.isEmpty()) return;
        FairyEnchs fairyEnchs = fairy.getData(FAIRY_ENCHS);
        FairyEnchs newFairyEnchs = new FairyEnchs(mergeEnchs(fairyEnchs.fairyEnchsMap(), tmpFairyEnchsMap, player.isCreative()));
        fairy.setData(FAIRY_ENCHS, newFairyEnchs);
        spawnParticle(ParticleTypes.ENCHANT, fairy.getX(), fairy.getY() + 0.7, fairy.getZ(), (ServerLevel) fairy.level());
        playSound(fairy.level(), fairy);
    }

    private static Map<FairyEnchsEnum, Integer> mergeEnchs(
            Map<FairyEnchsEnum, Integer> existing,
            Map<FairyEnchsEnum, Integer> toAdd,
            boolean allowExceedingLimit
    ) {

        Map<FairyEnchsEnum, Integer> newMap = new EnumMap<>(FairyEnchsEnum.class);
        newMap.putAll(existing);

        toAdd.forEach((fairyEnch, level) ->{

            if (newMap.getOrDefault(fairyEnch, 0) == level && level > 0) {
                int newLevel = level + 1;
                if (newLevel >= fairyEnch.getLimit() && !allowExceedingLimit) newLevel = fairyEnch.getLimit();
                newMap.put(fairyEnch, newLevel);
            } else if (level > 0){
                int newLevel = max(level, newMap.getOrDefault(fairyEnch, 0));
                if (newLevel >= fairyEnch.getLimit() && !allowExceedingLimit) newLevel = fairyEnch.getLimit();
                newMap.put(fairyEnch, newLevel);
            }

        });
        System.out.println(newMap);
        return newMap;

    }

    private static void spawnParticle(ParticleOptions particleOptions, double x, double y, double z, ServerLevel serverLevel) {
        int count = 10;
        double xOffset = 0.5;
        double yOffset = 0.5;
        double zOffset = 0.5;
        double speed = 0.1;
        serverLevel.sendParticles(particleOptions, x, y, z, count, xOffset, yOffset, zOffset, speed);
    }

    private static void playSound(Level level, Entity entity) {
        level.playSound(null, entity, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }
}
