package com.github.qinggua114.tamablefairy.events;


import com.github.qinggua114.tamablefairy.data.actstate.ActStateProvider;
import com.github.qinggua114.tamablefairy.data.tamedata.TameDataProvider;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapabilities {

    public AttachCapabilities(){
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject().getClass().equals(EntityFairy.class)){
            event.addCapability(new ResourceLocation(MODID, "tamedata"), new TameDataProvider());
            event.addCapability(new ResourceLocation(MODID, "act_state"), new ActStateProvider());
        }
    }
}
