package com.github.qinggua114.tamablefairy.events;


import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class FairyDespawnEvent {
    public FairyDespawnEvent() {
    }

    @SubscribeEvent
    public static void onFairyDespawn(MobDespawnEvent event) {//解决在切换至和平模式后已驯服的女仆妖精消失的问题
        Entity entity = event.getEntity();
        if (entity.level().isClientSide || !(entity.getClass().equals(EntityFairy.class))) return;

        TameData tameData = entity.getData(TAME_DATA);
        if (!tameData.tamed()) return;

        event.setResult(MobDespawnEvent.Result.DENY);
    }
}
