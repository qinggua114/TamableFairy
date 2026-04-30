package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;
import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OnFairyDeath {
    public OnFairyDeath() {
    }

    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;
        if (!(entity.getClass().equals(EntityFairy.class))) return;

        ITameData tameData = entity.getCapability(TAME_DATA, null).orElse(new TameData());
        if (!tameData.tamed()) return;
        ServerPlayer owner = (ServerPlayer) entity.level().getPlayerByUUID(tameData.owner());
        if (owner == null) return;

        if (entity.getKillCredit() == null)
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
