package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class onFairyDeath {
    public onFairyDeath(){
    }

    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        if(entity.level().isClientSide()) return;
        if(!(entity instanceof EntityFairy)) return;

        TameData tameData = entity.getData(TAME_DATA);
        if(!tameData.tamed()) return;
        ServerPlayer owner = (ServerPlayer) entity.level().getPlayerByUUID(tameData.owner());
        if(owner == null) return;

        if(entity.getKillCredit() == null)
            owner.sendSystemMessage(Component.translatable(
                    "message.on_tamed_fairy_death.without_killer",
                    entity.getDisplayName().getString())
            );
        else
            owner.sendSystemMessage(Component.translatable(
                    "message.on_tamed_fairy_death",
                    entity.getDisplayName().getString(),
                    entity.getKillCredit().getDisplayName().getString())
            );
    }
}
