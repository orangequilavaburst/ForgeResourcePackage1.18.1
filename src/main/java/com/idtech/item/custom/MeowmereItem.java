package com.idtech.item.custom;

import com.idtech.entity.custom.MeowmereProjectileEntity;
import com.idtech.item.ItemMod;
import com.idtech.utils.TagMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class MeowmereItem extends SwordItem {

    public MeowmereItem(Properties pProperties) {
        super(new ForgeTier(Tiers.NETHERITE.getLevel(), 4000, 12.0f, 20.0f, 20,
                TagMod.Blocks.NEEDS_DRAGONITE_TOOL, ()-> Ingredient.EMPTY), 0, 0.0f, pProperties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {

        MeowmereProjectileEntity proj = new MeowmereProjectileEntity(entity.level(), entity);
        proj.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.5F, 0.75f);
        entity.level().addFreshEntity(proj);
        proj.playSound(SoundEvents.CAT_AMBIENT);

        return super.onEntitySwing(stack, entity);
    }
}
