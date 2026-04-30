package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.networks.NetWorks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.github.qinggua114.tamablefairy.TamableFairy.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OnStartUp {
    public OnStartUp(){
    }

    @SubscribeEvent
    public static void onStartUp(FMLCommonSetupEvent event) {
        NetWorks.register();
    }
}
