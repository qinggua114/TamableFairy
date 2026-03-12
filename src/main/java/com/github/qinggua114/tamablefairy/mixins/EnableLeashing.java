package com.github.qinggua114.tamablefairy.mixins;


import com.github.qinggua114.tamablefairy.data.TameData;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.qinggua114.tamablefairy.data.Attachments.TAME_DATA;

@Mixin(Mob.class)
public class EnableLeashing {
    @Inject(method = "canBeLeashed", at = @At("RETURN"), cancellable = true)
    private void modifyCanBeLeashed(CallbackInfoReturnable<Boolean> cir) {
        boolean originalRtnVal = cir.getReturnValue();
        if (!originalRtnVal){
            Mob mob = (Mob) (Object) this;
            if (mob.getClass().equals(EntityFairy.class)){
                if (mob.getData(TAME_DATA).tamed()) cir.setReturnValue(true);
            }
        }
    }
}
