package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.gui.FairyGUIScreen;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
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

        register.playToClient(OpenFairyGuiPayLoad.TYPE, OpenFairyGuiPayLoad.CODEC, (payload, context) -> {
            Minecraft.getInstance().execute(() -> {
                ClientLevel level = Minecraft.getInstance().level;
                if (level == null) return;
                Entity entity = level.getEntity(payload.entityId());
                if (entity == null) return;
                Minecraft.getInstance().setScreen(new FairyGUIScreen((EntityFairy) entity));

            });
        });//服务端发送的打开GUI包

        register.playToClient(C2SFairyGuiPayLoad.TYPE, C2SFairyGuiPayLoad.CODEC, (payload, context) -> {

        });//发送至服务端的女仆妖精数据包

    }
}
