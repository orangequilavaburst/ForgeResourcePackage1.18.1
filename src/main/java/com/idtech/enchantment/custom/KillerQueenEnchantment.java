package com.idtech.enchantment.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;

public class KillerQueenEnchantment extends Enchantment {
    public KillerQueenEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {
        if (!pTarget.level().isClientSide()){

            if (pAttacker != null) {

                pTarget.level().explode(pTarget, pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(), 2.0f, Level.ExplosionInteraction.NONE);

            }

        }

        super.doPostHurt(pTarget, pAttacker, pLevel);
    }
}
