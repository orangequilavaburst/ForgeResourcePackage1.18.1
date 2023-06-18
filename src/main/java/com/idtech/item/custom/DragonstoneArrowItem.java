package com.idtech.item.custom;

import com.idtech.entity.custom.DragonstoneArrowEntity;
import com.idtech.entity.EntityMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DragonstoneArrowItem extends ArrowItem {
    public DragonstoneArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        DragonstoneArrowEntity d = new DragonstoneArrowEntity(EntityMod.DRAGONSTONE_ARROW_ENTITY.get(), pShooter, pLevel);
        return d;
    }
}
