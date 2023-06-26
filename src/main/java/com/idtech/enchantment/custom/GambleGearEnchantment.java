package com.idtech.enchantment.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GambleGearEnchantment extends Enchantment {
    public GambleGearEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
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
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {

        if (!pAttacker.level().isClientSide()){

            List<MobEffect> negativeEffects = new ArrayList<MobEffect>();
            for (MobEffect mobEffect : BuiltInRegistries.MOB_EFFECT.stream().toList()){
                if (!mobEffect.isBeneficial()){
                    negativeEffects.add(mobEffect);
                }
            }
            ServerLevel world = (ServerLevel) pAttacker.level();
            Random random = new Random();
            for (int i = 0; i <= pLevel*3; i++){

                ItemStack potionItem = new ItemStack(Items.SPLASH_POTION);
                Collection<MobEffectInstance> collection = new ArrayList<MobEffectInstance>();
                collection.add(new MobEffectInstance(
                        negativeEffects.get(random.nextInt(negativeEffects.size())), 300*pLevel, 0));
                PotionUtils.setCustomEffects(potionItem, collection);
                ThrownPotion potion = new ThrownPotion(world, pTarget);
                potion.setItem(potionItem);
                potion.setPos(pTarget.getPosition(1.0f).add(0.0f, pTarget.getBbHeight() + 0.05f, 0.0f));
                potion.setDeltaMovement(0.0f, 1.0f, 0.0f);
                potion.setDeltaMovement(potion.getDeltaMovement().add((1.0f - 2.0f*random.nextFloat())/3.0f,
                        (1.0f - 2.0f*random.nextFloat())/5.0f,
                        (1.0f - 2.0f*random.nextFloat())/3.0f));
                world.addFreshEntity(potion);

            }

        }

        super.doPostHurt(pTarget, pAttacker, pLevel);
    }
}
