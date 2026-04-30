package com.github.qinggua114.tamablefairy.mixins;

import com.github.qinggua114.tamablefairy.data.tamedata.ITameData;
import com.github.qinggua114.tamablefairy.data.tamedata.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.qinggua114.tamablefairy.data.Capabilities.TAME_DATA;

@Mixin(Mob.class)
public class EnableLeashing {
    @Inject(method = "canBeLeashed", at = @At("RETURN"), cancellable = true)
    private void modifyCanBeLeashed(CallbackInfoReturnable<Boolean> cir) {
        boolean originalRtnVal = cir.getReturnValue();
        if (!originalRtnVal){
            Mob mob = (Mob) (Object) this;
            if (mob.getClass().equals(EntityFairy.class)){
                ITameData tameData = mob.getCapability(TAME_DATA).orElse(new TameData());
                System.out.println("Mixin: fairy=" + mob + ", tamed=" + tameData.tamed() + ", owner=" + tameData.owner());
                if (tameData.tamed()) cir.setReturnValue(true);
            }
        }
    }
}