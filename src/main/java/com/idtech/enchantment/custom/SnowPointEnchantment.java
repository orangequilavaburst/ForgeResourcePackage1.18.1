package com.idtech.enchantment.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;

public class SnowPointEnchantment extends Enchantment {
    public SnowPointEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 10 + 20 * (pEnchantmentLevel - 1);
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return super.getMinCost(pEnchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {

        if (pTarget != null){

            if (pTarget instanceof LivingEntity livingEntity){

                if (!livingEntity.level().isClientSide()){

                    livingEntity.setTicksFrozen(livingEntity.getTicksFrozen() + (int) (livingEntity.getTicksRequiredToFreeze()*(pLevel/2.0f)));

                }

            }

        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

    @Override
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {
        if (!pAttacker.level().isClientSide()){

            ServerLevel world = (ServerLevel) pAttacker.level();
            for (int i = 0; i <= pLevel*3; i++){

                Snowball ball = new Snowball(world.getLevel(), pTarget);
                ball.shootFromRotation(pTarget, pTarget.getXRot(), pTarget.getYRot(), 0.0f, 1.5f + pLevel, 30.0f - pLevel*8.0f);
                world.addFreshEntity(ball);

            }

        }
        super.doPostHurt(pTarget, pAttacker, pLevel);
    }
}
