package com.github.qinggua114.tamablefairy.events;

import com.github.tartaricacid.touhoulittlemaid.entity.item.EntityPowerPoint;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class OnDrops {
    public OnDrops(){
    }

    @SubscribeEvent
    public static void onDropItem(LivingDropsEvent event) {
        Entity source = event.getSource().getEntity();
        if (source == null) return;
        if (!source.getClass().equals(EntityFairy.class)) return;
        if (!source.getData(TAME_DATA).tamed()) return;

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
