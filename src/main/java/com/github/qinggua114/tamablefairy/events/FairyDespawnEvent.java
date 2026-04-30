package com.github.qinggua114.tamablefairy.events;


import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FairyDespawnEvent {

    public FairyDespawnEvent() {
    }

    @SubscribeEvent
    public static void onFairyDespawn(MobSpawnEvent.AllowDespawn event) {//解决在切换至和平模式后已驯服的女仆妖精消失的问题
        Entity entity = event.getEntity();
        if (entity.level().isClientSide || !(entity.getClass().equals(EntityFairy.class))) return;

        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;

        event.setResult(Event.Result.DENY);
    }
}
