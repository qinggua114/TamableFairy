package com.github.qinggua114.tamablefairy.mixins;

import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.utils.CheckFairyEnchs;
import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.DanmakuShoot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(DanmakuShoot.class)
public class ModifyDanmaku {

    @Shadow(remap = false)
    private LivingEntity thrower;

    @Shadow(remap = false)
    private boolean hurtEnderman;

    @Inject(method = "setHurtEnderman", at = @At("RETURN"), remap = false)
    private void modifySetHurtEnderman(CallbackInfoReturnable<DanmakuShoot> cir) {
        if (CheckTameState.isFairy(this.thrower)) {
            Map<FairyEnchsEnum, Integer> fairyEnchs = CheckFairyEnchs.getFairyEnchs((EntityFairy) this.thrower);
            if (fairyEnchs.getOrDefault(FairyEnchsEnum.ENDERS_ENDER, 0) > 0) {
                this.hurtEnderman = true;
            }
        }
    }
}
