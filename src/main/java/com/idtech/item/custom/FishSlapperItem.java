package com.idtech.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeTier;

public class FishSlapperItem extends SwordItem {

    private float knockbackPowerMin = 1.0f;
    private float knockbackPowerMax = 3.0f;
    private float upSpeed = 1.0f;
    private SoundEvent attackSound = SoundEvents.TROPICAL_FISH_FLOP;
    private int maxAttacks = 10;
    private String attacksTagName = "attacks";

    public FishSlapperItem(Properties pProperties) {
        super(new ForgeTier(3, 100, 10.0f, 10.0f, 0, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY),
                0, 0.0f, pProperties);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {

        pTarget.setDeltaMovement(pAttacker.getLookAngle().x() * getKnockbackPower(pStack),
                                pAttacker.getLookAngle().y() * getKnockbackPower(pStack) + this.upSpeed,
                                pAttacker.getLookAngle().z() * getKnockbackPower(pStack));
        pTarget.playSound(this.attackSound, 2.0f, 1.0f);

        if (getAttacks(pStack) >= this.maxAttacks){
            setAttacks(pStack, 0);

            pAttacker.level().explode(pAttacker, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 2.0f, Level.ExplosionInteraction.NONE);
        }
        else{
            setAttacks(pStack, getAttacks(pStack) + 1);
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return getAttacks(pStack) > 0;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0x20B5FA;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)getAttacks(pStack) * 13.0F / (float)this.maxAttacks);
    }

    public void setAttacks(ItemStack pStack, int num){
        pStack.getOrCreateTagElement(attacksTagName).putInt(attacksTagName, num);
    }

    public int getAttacks(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement(this.attacksTagName);
        return compoundtag != null && compoundtag.contains(this.attacksTagName, 99) ? compoundtag.getInt(this.attacksTagName) : 0;
    }

    public float getKnockbackPower(ItemStack pStack){
        return Mth.lerp(getAttacks(pStack)/(float)this.maxAttacks, this.knockbackPowerMin, this.knockbackPowerMax);
    }



}
