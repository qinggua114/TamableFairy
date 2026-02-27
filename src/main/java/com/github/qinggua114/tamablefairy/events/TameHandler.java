package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.qinggua114.tamablefairy.entity_ai.ModifyAI;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
//import com.mojang.logging.LogUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
//import org.slf4j.Logger;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class TameHandler {

    //private static final Logger LOGGER = LogUtils.getLogger();
    private static Entity target;

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event){

        Player player = event.getEntity();
        if(player.level().isClientSide) return;
        target = event.getTarget();
        ItemStack itemStack = event.getItemStack();
        TameData tameData = target.getData(TAME_DATA);

        if(target instanceof EntityFairy && !tameData.tamed()){
            if (event.getItemStack().is(Items.CAKE)){
                TameData newData = new TameData(true, player.getUUID());
                target.setData(TAME_DATA, newData);
                itemStack.shrink(1);
                ModifyAI.letTamed((Mob) target);
                spawnParticle((ServerLevel) event.getLevel());
            }
        }
    }

    private static void spawnParticle(ServerLevel serverLevel){
        ParticleOptions particleOptions = ParticleTypes.HEART;
        double x = target.getX();
        double y = target.getY()+0.7;
        double z = target.getZ();
        int count = 10;
        double xOffset = 0.5;
        double yOffset = 0.5;
        double zOffset = 0.5;
        double speed = 0.1;
        serverLevel.sendParticles(particleOptions, x, y, z, count, xOffset, yOffset, zOffset, speed);
    }
}
