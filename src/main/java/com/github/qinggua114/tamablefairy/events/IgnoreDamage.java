package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class IgnoreDamage {

    public IgnoreDamage(){
    }

    @SubscribeEvent
    public static void onDamage(LivingIncomingDamageEvent event){
        //如果是同一个主人的女仆妖精,则忽略伤害
        Entity entity = event.getEntity();
        if(!(entity instanceof EntityFairy) || entity.level().isClientSide) return;
        Entity source = event.getSource().getEntity();
        if(!(source instanceof EntityFairy)) return;

        TameData tameData = entity.getData(TAME_DATA);
        if (!tameData.tamed()) return;
        TameData sourceData = source.getData(TAME_DATA);
        if (!sourceData.tamed()) return;

        if ( !( tameData.owner().equals(sourceData.owner()) ) ) return;

        event.setCanceled(true);
    }
}
