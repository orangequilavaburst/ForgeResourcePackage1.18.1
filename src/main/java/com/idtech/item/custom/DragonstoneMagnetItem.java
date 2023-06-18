package com.idtech.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DragonstoneMagnetItem extends Item {
    public DragonstoneMagnetItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof LivingEntity livingEntity){

            if (livingEntity.isHolding(this.asItem())){

                List<ItemEntity> items = pLevel.getEntitiesOfClass(ItemEntity.class, pEntity.getBoundingBox().inflate(10.0f));
                for (ItemEntity i : items){

                    Vec3 homingVector = livingEntity.getPosition(1).subtract(i.getEyePosition());
                    Vec3 newVector = homingVector.normalize();

                    i.setDeltaMovement(newVector);

                }

            }

        }
    }
}
