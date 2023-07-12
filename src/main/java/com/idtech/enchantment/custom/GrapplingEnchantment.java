package com.idtech.enchantment.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GrapplingEnchantment extends Enchantment {
    public GrapplingEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
