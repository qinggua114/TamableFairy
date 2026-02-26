package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.MonsterRoomHooks;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class TameStateUpdate {
    public TameStateUpdate(){}

    public static void onJoinLevel(EntityJoinLevelEvent event){
        Entity entity = event.getEntity();
        if(entity.level().isClientSide) return;
        if(!(entity instanceof EntityFairy)) return;

        TameData tameData = entity.getData(TAME_DATA);

    }
}
