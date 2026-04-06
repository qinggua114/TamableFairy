package com.github.qinggua114.tamablefairy.networks;

import com.github.qinggua114.tamablefairy.data.ActState;
import com.github.qinggua114.tamablefairy.data.TameData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.github.qinggua114.tamablefairy.data.Attachments.ACT_STATE;
import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;


@EventBusSubscriber
public class NetWorks {

    public NetWorks() {
    }

    @SubscribeEvent
    public static void registerPayLoads(RegisterPayloadHandlersEvent event) {//发送至服务端的女仆妖精数据包
        PayloadRegistrar register = event.registrar("1");

        register.playToServer(C2SFairyGuiPayLoad.TYPE, C2SFairyGuiPayLoad.CODEC, NetWorks::severPackageHandler);

    }

    public static void severPackageHandler(C2SFairyGuiPayLoad payload, IPayloadContext context) {//服务端用于处理数据包的函数
        Player player = context.player();
        Level level = player.level();
        Entity entity = level.getEntity(payload.entityId());
        if (entity == null) return;
        TameData tameData = entity.getData(TAME_DATA);
        if (!tameData.tamed()) return;
        if (!tameData.owner().equals(player.getUUID())) return;

        ActState newData = new ActState(
                payload.attackMode(),
                payload.followOwnerEnabled(),
                payload.moveAroundEnabled(),
                payload.followDistance(),
                payload.moveRange(),
                payload.actRangeCenter()
        );
        entity.setData(ACT_STATE, newData);
    }
}
