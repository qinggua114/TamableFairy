package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.qinggua114.tamablefairy.entity_ai.ModifyAI;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber
public class TameStateUpdate {
    public TameStateUpdate() {
    }

    @SubscribeEvent
    public static void onJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (event.getLevel().isClientSide) return;
        if (!(entity.getClass().equals(EntityFairy.class))) return;

        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (tameData.tamed()) {
            ModifyAI.letTamed((EntityFairy) entity);
        }

    }
}
