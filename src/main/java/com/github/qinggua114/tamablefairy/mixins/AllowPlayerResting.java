package com.github.qinggua114.tamablefairy.mixins;

import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mixin(Monster.class)
public class AllowPlayerResting {
    @Inject(method = "isPreventingPlayerRest", at = @At("RETURN"), cancellable = true)
    private void modifyIsPreventingPlayerRest(CallbackInfoReturnable<Boolean> cir) {
        boolean originalRtnVal = cir.getReturnValue();
        if (originalRtnVal) {
            Monster monster = (Monster) (Object) this;
            if (monster.getClass().equals(EntityFairy.class)) {
                ITameData tameData = monster.getCapability(TAME_DATA).orElse(new TameData());
                if (tameData.tamed()) cir.setReturnValue(false);
            }
        }
    }
}
