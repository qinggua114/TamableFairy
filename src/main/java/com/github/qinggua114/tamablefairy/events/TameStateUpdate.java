package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.qinggua114.tamablefairy.entity_ai.ModifyAI;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.slf4j.Logger;

import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class TameStateUpdate {
    public TameStateUpdate(){}

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onJoinLevel(EntityJoinLevelEvent event){
        LOGGER.info("===========================================");
        Entity entity = event.getEntity();
        Level level = event.getLevel();
        if(level.isClientSide) return;
        if(!(entity instanceof EntityFairy)) return;

        TameData tameData = entity.getData(TAME_DATA);
        if(tameData.tamed()){
            ModifyAI.letTamed((Mob) entity);
        }

    }
}
