package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.utils.TagMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ToolTierMod {

    public static Tier DRAGONSTONE;
    public static Tier YTTRIUM;
    public static Tier DRAGONITE;

    static{

        DRAGONSTONE = new ForgeTier(Tiers.DIAMOND.getLevel(), 3000, 10.0f, 8.0f, 20,
                BlockTags.NEEDS_DIAMOND_TOOL, ()->Ingredient.of(ItemMod.DRAGONSTONE.get()));
        YTTRIUM = new ForgeTier(Tiers.DIAMOND.getLevel(), 3000, 10.0f, 8.0f, 10,
                BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemMod.YTTRIUM.get()));
        DRAGONITE = new ForgeTier(Tiers.NETHERITE.getLevel(), 4000, 12.0f, 10.0f, 10,
                TagMod.Blocks.NEEDS_DRAGONITE_TOOL, ()->Ingredient.of(ItemMod.DRAGONITE_INGOT.get()));

                /*TierSortingRegistry.registerTier(
                new ForgeTier(5, 4000, 8f, 5f, 10, ModTags.Blocks.NEEDS_DRAGONITE_TOOL, () -> Ingredient.of(ModItems.DRAGONITE_INGOT.get())),
                new ResourceLocation(BaseMod.MODID, "dragonite"), List.of(Tiers.NETHERITE), List.of());*/

    }

}
