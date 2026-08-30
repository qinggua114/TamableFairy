package com.github.qinggua114.tamablefairy.mixins;

import com.github.qinggua114.tamablefairy.utils.CheckTameState;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.EntityDanmaku;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EntityDanmaku.class)
public class ModifyDanmaku {

    @Inject(method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void checkFairyHit(EntityHitResult result, CallbackInfo ci) {
        EntityDanmaku danmaku = (EntityDanmaku) (Object) this;
        Entity thrower = danmaku.getOwner();
        if (thrower == null) return;
        Entity hit = result.getEntity();
        if (thrower instanceof TamableAnimal tamableThrower) {
            if (hit instanceof EntityFairy fairyHit) {
                if (!CheckTameState.hasSameOwner(tamableThrower, fairyHit)) return;
                danmaku.discard();
                ci.cancel();
            }
        }
        if (thrower.getClass().equals(EntityFairy.class)) {
            if (hit instanceof TamableAnimal tamableHit) {
                if (!CheckTameState.hasSameOwner(tamableHit, (EntityFairy) thrower)) return;
                danmaku.discard();
                ci.cancel();
            }
            if (hit instanceof Player playerHit) {
                if (!CheckTameState.isTamed((EntityFairy) thrower)) return;
                danmaku.discard();
                ci.cancel();
            }
        }
    }
}
