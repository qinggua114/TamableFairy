package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class AvoidBeingTargeted {
    public AvoidBeingTargeted(){
    }

    @SubscribeEvent
    public static void onTargetSet(LivingChangeTargetEvent event){
        //防止驯服的女仆妖精被铁傀儡等主动攻击敌对生物的生物设为目标
        Entity entity = event.getEntity();
        if(entity.level().isClientSide) return;
        Entity target = event.getNewAboutToBeSetTarget();
        if(!(target instanceof EntityFairy)) return;
        //若未驯服,继续目标设定事件
        TameData targetTameData = target.getData(TAME_DATA);
        if(!targetTameData.tamed()) return;

        if(entity instanceof Monster && !(entity instanceof EntityFairy)) return;//如果生物是怪物,且不是女仆妖精,继续目标设定事件

        //如果是同一个主人的女仆妖精,取消事件
        if(entity instanceof EntityFairy){
            TameData entityTameData = entity.getData(TAME_DATA);

            if(targetTameData.owner() == entityTameData.owner()) {
                event.setCanceled(true);
            }
            else {
                return;//主人不同则不取消
            }
        }

        event.setCanceled(true);//生物既不是怪物也不是女仆妖精(例如铁傀儡,驯服的狼等),取消目标设定事件
    }
}
