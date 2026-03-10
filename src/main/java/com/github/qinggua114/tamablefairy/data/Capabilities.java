package com.github.qinggua114.tamablefairy.data;


import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Capabilities {
    public static final Capability<ITameData> TAME_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    public Capabilities(){
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.register(ITameData.class);
    }

}
