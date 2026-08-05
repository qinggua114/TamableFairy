package com.github.qinggua114.tamablefairy.utils;

import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.UUID;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

public class CheckTameState {
    public CheckTameState() {
    }

    public static boolean isFairy(Entity entity) {
        return entity.getClass().equals(EntityFairy.class);
    }

    public static boolean isTamed(EntityFairy fairy) {
        TameData tameData = fairy.getData(TAME_DATA);
        return tameData.tamed();
    }

    public static boolean isTamedFairy(Entity entity) {
        if (!isFairy(entity)) return false;
        return isTamed((EntityFairy) entity);
    }


    public static UUID getOwnerID(EntityFairy fairy) {
        TameData tameData = fairy.getData(TAME_DATA);
        return tameData.owner();
    }

    public static Player getOwner(EntityFairy fairy) {
        TameData tameData = fairy.getData(TAME_DATA);
        Level level = fairy.level();
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return null;
        return server.getPlayerList().getPlayer(tameData.owner());
    }

    public static String getOwnerName(EntityFairy fairy) {
        TameData tameData = fairy.getData(TAME_DATA);
        return tameData.ownerName();
    }

    public static boolean ownerIs(EntityFairy fairy, Player player) {
        UUID ownerID = getOwnerID(fairy);
        UUID playerID = player.getUUID();
        return ownerID.equals(playerID);
    }
}
