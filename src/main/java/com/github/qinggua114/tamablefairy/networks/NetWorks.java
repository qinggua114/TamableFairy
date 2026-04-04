package com.github.qinggua114.tamablefairy.networks;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;


@EventBusSubscriber
public class NetWorks {

    public NetWorks() {
    }

    @SubscribeEvent
    public static void registerPayLoads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar register = event.registrar("1");

        register.playToServer(C2SFairyGuiPayLoad.TYPE, C2SFairyGuiPayLoad.CODEC, (payload, context) -> {

        });//发送至服务端的女仆妖精数据包

    }
}
