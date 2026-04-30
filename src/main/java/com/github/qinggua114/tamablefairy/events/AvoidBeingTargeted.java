package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AvoidBeingTargeted {
    public AvoidBeingTargeted(){
    }

    @SubscribeEvent
    public static void onTargetSet(LivingChangeTargetEvent event){
        //防止驯服的女仆妖精被铁傀儡等主动攻击敌对生物的生物设为目标
        Entity entity = event.getEntity();
        if (entity.level().isClientSide) return;
        Entity target = event.getNewTarget();
        if (target == null) return;
        if (!(target.getClass().equals(EntityFairy.class))) return;
        //若未驯服,保留目标设定事件
        ITameData targetTameData = target.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!targetTameData.tamed()) return;

        if (entity instanceof Monster && !(entity.getClass().equals(EntityFairy.class))) return;//如果生物是怪物,且不是女仆妖精,保留目标设定事件

        //如果是同一个主人的女仆妖精,取消事件
        if (entity.getClass().equals(EntityFairy.class)){
            ITameData entityTameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());

            if (targetTameData.owner() == entityTameData.owner()) {
                event.setCanceled(true);
            }
            else {
                return;//主人不同则不取消
            }
        }

        event.setCanceled(true);//生物既不是怪物也不是女仆妖精(例如铁傀儡,驯服的狼等),取消目标设定事件
    }
}
