package com.github.qinggua114.tamablefairy.events;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.level.BlockEvent;

public class TestEvent {
    public static void onJump(LivingEvent.LivingJumpEvent event){
        LivingEntity entity = event.getEntity();
        if(!entity.level().isClientSide()){
            entity.heal(1);
        }
    }
}
