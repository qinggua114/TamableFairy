package com.github.qinggua114.tamablefairy.gamerules;

import net.minecraft.world.level.GameRules;

public class TamableFairyGameRules {
    public static GameRules.Key<GameRules.BooleanValue> ALLOW_OWNER_HURT_FAIRY;
    public TamableFairyGameRules() {
        ALLOW_OWNER_HURT_FAIRY =
                GameRules.register("allowOwnerHurtFairy", GameRules.Category.MOBS, GameRules.BooleanValue.create(false));
    }

}
