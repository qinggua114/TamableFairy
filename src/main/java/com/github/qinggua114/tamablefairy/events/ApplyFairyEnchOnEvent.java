package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.utils.CheckFairyEnchs;
import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static net.minecraft.world.damagesource.DamageTypes.MAGIC;
import static net.minecraft.world.damagesource.DamageTypes.MOB_ATTACK;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ApplyFairyEnchOnEvent {

    public ApplyFairyEnchOnEvent() {
    }

    @SubscribeEvent
    public static void applyProtectionEnch(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;
        if (!CheckTameState.isFairy(entity)) return;
        if (event.getSource().is(MAGIC)) return;
        Map<FairyEnchsEnum, Integer> fairyEnchs = CheckFairyEnchs.getFairyEnchs((EntityFairy) entity);
        int EPF = fairyEnchs.getOrDefault(FairyEnchsEnum.PROTECTION, 0) * 2;
        if (EPF > 20) EPF = 20;
        float originalDamage = event.getAmount();
        float newDamage = originalDamage * (1 - (float) EPF / 25);
        event.setAmount(newDamage);
    }

    @SubscribeEvent
    public static void applyThornsEnch(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;
        if (!CheckTameState.isFairy(entity)) return;
        DamageSource damageSource = event.getSource();
        Entity source = damageSource.getEntity();
        if (source == null) return;
        if (!damageSource.is(MOB_ATTACK)) return;
        int thornsLevel = CheckFairyEnchs.getFairyEnchs((EntityFairy) entity).getOrDefault(FairyEnchsEnum.THORNS, 0);
        if (entity.getRandom().nextFloat() < thornsLevel * 0.15) {
            DamageSource thornsDamage = entity.level().damageSources().thorns(entity);
            float damage = entity.getRandom().nextInt(5) + 1;
            source.hurt(thornsDamage, damage);
        }
    }
}
