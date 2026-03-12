package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.ITameData;
import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEvents {

    public DamageEvents(){
    }

    @SubscribeEvent
    public static void onTamedFairyDamage(LivingDamageEvent event){
        //如果是同一个主人的女仆妖精,则忽略伤害
        LivingEntity entity = event.getEntity();
        if(!(entity.getClass().equals(EntityFairy.class)) || entity.level().isClientSide) return;
        Entity source = event.getSource().getEntity();
        if(!(source.getClass().equals(EntityFairy.class))) return;

        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;
        ITameData sourceData = source.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!sourceData.tamed()) return;

        if ( !( tameData.owner().equals(sourceData.owner()) ) ) return;

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event){
        if ( !(event.getEntity() instanceof Player player) ) return;
        Entity source = event.getSource().getEntity();
        if (source == null) return;

        ITameData tameData = source.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;

        Player owner = source.level().getPlayerByUUID(tameData.owner());

        if (player.equals(owner)) event.setCanceled(true);//如果弹幕打到自己的主人,则忽略伤害

    }

    @SubscribeEvent
    public static void onDamageAndSetTarget(LivingDamageEvent event){
        //解决野生女仆妖精被驯服的妖精打到不还手的问题
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide || !(entity instanceof EntityFairy)) return;
        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (tameData.tamed()) return;//如果要受伤的是已驯服的女仆妖精,不设置目标,防止误伤引起内斗

        Entity source = event.getSource().getEntity();
        if (source == null) return;
        if ( !(source instanceof EntityFairy) ) return;

        ITameData sourceData = source.getCapability(TAME_DATA, null).orElse(new TameData());
        if ( !sourceData.tamed() ) return;//如果造成伤害的是野生的,不设置目标

        if ( ((Mob) entity).getTarget() != null) return;//若已有正在攻击的目标,不再重新设置
        ((Mob) entity).setTarget((LivingEntity) source);//将目标设置为攻击者
    }
}
