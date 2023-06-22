package com.idtech.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.Random;
import java.util.random.RandomGenerator;

public class SheerColdSwordItem extends SwordItem {

    public SheerColdSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.setTicksFrozen(pTarget.getTicksFrozen() + pTarget.getTicksRequiredToFreeze() * 3);
        pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, pTarget.getTicksFrozen() + pTarget.getTicksRequiredToFreeze() * 3, 1));

        for (int i = 0; i < 8; i++) {
            RandomGenerator random = RandomGenerator.getDefault();
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            pAttacker.level().addParticle(ParticleTypes.CLOUD, pTarget.getX(), pTarget.getY() + pTarget.getEyeHeight() / 2.0, pTarget.getZ(), 0.25 - Math.random() * 0.5, 0.25 - Math.random() * 0.5, 0.25 - Math.random() * 0.5);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
