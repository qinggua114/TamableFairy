package com.github.qinggua114.tamablefairy.mixins;

import com.github.qinggua114.tamablefairy.data.fairy_enchantments.FairyEnchsEnum;
import com.github.qinggua114.tamablefairy.utils.CheckFairyEnchs;
import com.github.tartaricacid.touhoulittlemaid.entity.monster.EntityFairy;
import com.github.tartaricacid.touhoulittlemaid.entity.projectile.DanmakuShoot;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(EntityFairy.class)
public class ModifyAttack {

    @WrapMethod(method = "m_6504_", remap = false)
    private void modifiedPerformRangedAttack(LivingEntity target, float distanceFactor, Operation<Void> original) {
        EntityFairy fairy = (EntityFairy) (Object) this;
        Map<FairyEnchsEnum, Integer> fairyEnchs = CheckFairyEnchs.getFairyEnchs(fairy);

        float damageBase = 1.0F;
        Difficulty difficulty = target.level().getDifficulty();
        switch (difficulty) {
            case NORMAL -> damageBase = 1.5F;
            case HARD -> damageBase = 2.0F;
        }

        damageBase = calculateDamage(damageBase, fairyEnchs.getOrDefault(FairyEnchsEnum.POWER, 0));
        int impedingLevel = fairyEnchs.getOrDefault(FairyEnchsEnum.IMPEDING, 0);
        int velocityCoefficient = fairyEnchs.getOrDefault(FairyEnchsEnum.SPEEDY, 0) + 1;

        if (impedingLevel > 0) {
            if ((double) fairy.getRandom().nextFloat() <= 0.9 && fairyEnchs.getOrDefault(FairyEnchsEnum.MULTI_SHOT, 0) <= 0) {
                DanmakuShoot.create().setWorld(fairy.level())
                        .setThrower(fairy).setTarget(target)
                        .setRandomColor().setRandomType()
                        .setDamage(distanceFactor + damageBase)
                        .setGravity(0.0F).setVelocity(0.2F * (distanceFactor + 1.0F) * velocityCoefficient).setInaccuracy(0.2F)
                        .setImpedingLevel(impedingLevel)
                        .aimedShot();
            } else {
                DanmakuShoot.create().setWorld(fairy.level())
                        .setThrower(fairy).setTarget(target)
                        .setRandomColor().setRandomType()
                        .setDamage(distanceFactor + damageBase + 0.5F)
                        .setGravity(0.0F).setVelocity(0.2F * (distanceFactor + 1.0F) * velocityCoefficient).setInaccuracy(0.2F)
                        .setImpedingLevel(impedingLevel)
                        .setFanNum(3).setYawTotal((Math.PI / 6D))
                        .fanShapedShot();
            }
        } else {
            if ((double) fairy.getRandom().nextFloat() <= 0.9 && fairyEnchs.getOrDefault(FairyEnchsEnum.MULTI_SHOT, 0) <= 0) {
                DanmakuShoot.create().setWorld(fairy.level())
                        .setThrower(fairy).setTarget(target)
                        .setRandomColor().setRandomType()
                        .setDamage(distanceFactor + damageBase)
                        .setGravity(0.0F).setVelocity(0.2F * (distanceFactor + 1.0F) * velocityCoefficient).setInaccuracy(0.2F)
                        .aimedShot();
            } else {
                DanmakuShoot.create().setWorld(fairy.level())
                        .setThrower(fairy).setTarget(target)
                        .setRandomColor().setRandomType()
                        .setDamage(distanceFactor + damageBase + 0.5F)
                        .setGravity(0.0F).setVelocity(0.2F * (distanceFactor + 1.0F) * velocityCoefficient).setInaccuracy(0.2F)
                        .setFanNum(3).setYawTotal((Math.PI / 6D))
                        .fanShapedShot();
            }
        }

    }

    @Unique
    private float calculateDamage(float damageBase, int powerEnchLevel) {
        if (powerEnchLevel == 0) return damageBase;
        damageBase += 0.5F + powerEnchLevel * 0.5F;
        return damageBase;
    }
}

