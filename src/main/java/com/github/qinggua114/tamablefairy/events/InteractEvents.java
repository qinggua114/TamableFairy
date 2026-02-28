package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@EventBusSubscriber
public class InteractEvents {


    public InteractEvents(){
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event){
        Entity target = event.getTarget();
        if (target.level().isClientSide) return;
        TameData tameData = target.getData(TAME_DATA);
        if (!tameData.tamed()) return;

        Player player = event.getEntity();
        if (!( player.getUUID().equals(tameData.owner()) )) return;//不是主人不予理会

        ItemStack itemStack = event.getItemStack();
        if(itemStack.is(Items.SUGAR)) useSugar(event);//吃糖回血

    }

    private static void useSugar(PlayerInteractEvent.EntityInteract event){
        LivingEntity target = (LivingEntity) event.getTarget();
        Player player = event.getEntity();

        if (target.getHealth() == target.getMaxHealth()) return;//满血时不继续回血
        target.heal(2);
        spawnParticle(ParticleTypes.HEART, target.getX(), target.getY()+0.7, target.getZ(), (ServerLevel) event.getLevel());
        if (player.getAbilities().instabuild) return;
        event.getItemStack().shrink(1);

    }

    private static void spawnParticle(ParticleOptions particleOptions, double x, double y, double z, ServerLevel serverLevel){
        int count = 10;
        double xOffset = 0.5;
        double yOffset = 0.5;
        double zOffset = 0.5;
        double speed = 0.1;
        serverLevel.sendParticles(particleOptions, x, y, z, count, xOffset, yOffset, zOffset, speed);
    }
}
