package com.github.qinggua114.tamablefairy.events;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;
import static com.machinezoo.noexception.Exceptions.log;

@EventBusSubscriber
public class TameHandler {

    private static final Logger log = LoggerFactory.getLogger(TameHandler.class);
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event){

        Player player = event.getEntity();
        if(player.level().isClientSide) return;
        Entity target = event.getTarget();
        ItemStack itemStack = event.getItemStack();
        TameData tameData = target.getData(TAME_DATA);

        LOGGER.info(String.valueOf(tameData));
        if(target instanceof EntityFairy && !tameData.tamed()){
            if (event.getItemStack().is(Items.CAKE)){
                TameData newData = new TameData(true, player.getUUID());
                target.setData(TAME_DATA, newData);
                itemStack.shrink(1);
                //LOGGER.info(String.valueOf(newData));
            }
        }
    }
}
