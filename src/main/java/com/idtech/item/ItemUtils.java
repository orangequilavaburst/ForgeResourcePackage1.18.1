package com.idtech.item;

import com.idtech.BaseMod;
import net.minecraft.Util;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * Utilities for creating new item and doing things with item
 */
public class ItemUtils {

    /**
     *  Create a new armor material. Used for new armor sets that use custom materials.
     * @param nameIn the name of the material, can be whatever.
     * @param maxDamageFactorIn Multiplier for durability.
     * @param damageReductionAmountArrayIn Defense points for each armor slot.
     * @param enchantabilityIn Enchantability of armor with the item.
     * @param eqiupSoundIn Sound for equipping the armor.
     * @param toughnessIn How long it takes until the armor breaks.
     * @param knockbackResistanceIn A value for knockback resistance of the armor.
     * @param repairIngredientName A registry name of the ingredient needed to repair this tool e.g. "minecraft:stick"
     *      *                       or "examplemod:meteor_ingot".
     * @return the built Armor Material
     */
    public static ArmorMaterial buildArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent eqiupSoundIn,
                                                    float toughnessIn, float knockbackResistanceIn, String repairIngredientName){
        Supplier<Ingredient> ingredientSupplier = ()-> Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(repairIngredientName)));
        return buildArmorMaterial(nameIn, maxDamageFactorIn, damageReductionAmountArrayIn, enchantabilityIn, eqiupSoundIn, toughnessIn, knockbackResistanceIn, ingredientSupplier);

    }

    /**
     *  Alternate method to create an armor material that uses Item Tags instead of a single repair ingredient.
     *  Create a new armor material. Used for new armor sets that use custom materials.
     * @param nameIn the name of the material, can be whatever.
     * @param maxDamageFactorIn Multiplier for durability.
     * @param damageReductionAmountArrayIn Defense points for each armor slot.
     * @param enchantabilityIn Enchantability of armor with the item.
     * @param eqiupSoundIn Sound for equipping the armor.
     * @param toughnessIn How long it takes until the armor breaks.
     * @param knockbackResistanceIn A value for knockback resistance of the armor.
     * @param itemTag an Item Tag indicating a group or type of item that can repair this armor material.
     * @return the built Armor Material
     */
    public static ArmorMaterial buildArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent eqiupSoundIn,
                                                   float toughnessIn, float knockbackResistanceIn, ItemStack itemTag){
        Supplier<Ingredient> ingredientSupplier = () -> Ingredient.of(itemTag);
        return buildArmorMaterial(nameIn, maxDamageFactorIn, damageReductionAmountArrayIn, enchantabilityIn, eqiupSoundIn, toughnessIn, knockbackResistanceIn, ingredientSupplier);

    }

    /**
     * Builds a new custom armor material. Modelled exactly after Vanilla Mincraft armor material code.
     * @param nameIn name of the material
     * @param maxDamageFactorIn the maximum damage for the armor
     * @param damageReductionAmountArrayIn the damage reduction that each piece of armor does in an array. In order helm, chest, legs, boots.
     * @param enchantabilityIn the enchantability factor of the armor.
     * @param eqiupSoundIn the sound that the armor makes when being equipped
     * @param toughnessIn any additional toughness of the armor
     * @param knockbackResistanceIn additional knockback resistance in the armor
     * @param repairIngredientIn ingredient used to repair the armor.
     * @return
     */
    private static ArmorMaterial buildArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent eqiupSoundIn,
                                                     float toughnessIn, float knockbackResistanceIn, Supplier<Ingredient> repairIngredientIn) {

        final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

        return new ArmorMaterial() {

            final String name = nameIn;
            final int maxDamageFactor = maxDamageFactorIn;
            final int[] damageReductionAmountArray = damageReductionAmountArrayIn;
            final int enchantability = enchantabilityIn;
            final SoundEvent soundEvent = eqiupSoundIn;
            final float toughness = toughnessIn;
            final float knockbackResistance = knockbackResistanceIn;
            final LazyLoadedValue<Ingredient>  repairMaterial = new LazyLoadedValue<>(repairIngredientIn);
            ;



        @Override
        public int getDurabilityForType(ArmorItem.Type p_266807_) {
            return MAX_DAMAGE_ARRAY[p_266807_.getSlot().getIndex()] * this.maxDamageFactor;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type p_267168_) {
            return this.damageReductionAmountArray[p_267168_.getSlot().getIndex()];
        }

        public int getEnchantmentValue () {
        return this.enchantability;
    }

        public SoundEvent getEquipSound() {
            return this.soundEvent;
        }
        @Override
        public Ingredient getRepairIngredient () {
            return this.repairMaterial.get();
        }

        @Override
        public String getName () {
            return this.name;
        }
        @Override
        public float getToughness () {
            return this.toughness;
        }
        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    };

    }

    public static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }

    public static void makeBuster(Item item) {
        ItemProperties.register(item, new ResourceLocation("flash"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0;
            } else {
                float timer = p_174637_.getUseItem() != p_174635_ ? 0 : (p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks());
                if (timer >= 28){
                    return timer % 3;
                }
                else if (timer >= 10){
                    return (timer/2) % 2;
                }
                return 0;
            }
        });
    }

}
