package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OnDrops {
    public OnDrops(){
    }

    @SubscribeEvent
    public static void onDropItem(LivingDropsEvent event) {
        Entity source = event.getSource().getEntity();
        if (source == null) return;
        if (source.level().isClientSide) return;
        if (!source.getClass().equals(EntityFairy.class)) return;
        if (!source.getCapability(TAME_DATA).orElse(new TameData()).tamed()) return;

        dropPowerPoint(event.getEntity(), ((EntityFairy)source).getPowerPoint());
    }

    public static void dropPowerPoint(LivingEntity entity, int powerPointCount) {
        if (!entity.level().isClientSide) {
            while(powerPointCount > 0) {
                int powerSplit = EntityPowerPoint.getPowerValue(powerPointCount);
                powerPointCount -= powerSplit;
                EntityPowerPoint powerPoint = new EntityPowerPoint(entity.level(), entity.getX(), entity.getY(), entity.getZ(), powerSplit);
                entity.level().addFreshEntity(powerPoint);
            }
        }

    }
}
