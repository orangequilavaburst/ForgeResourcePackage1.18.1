package com.idtech.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class ToolTierMod {

    public static Tier DRAGONSTONE;
    public static Tier YTTRIUM;

    static{

        DRAGONSTONE = new ForgeTier(Tiers.DIAMOND.getLevel(), 3000, 10.0f, 8.0f, 20,
                BlockTags.NEEDS_DIAMOND_TOOL, ()->Ingredient.of(ItemMod.DRAGONSTONE.get()));
        YTTRIUM = new ForgeTier(Tiers.DIAMOND.getLevel(), 3000, 10.0f, 8.0f, 10,
                BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemMod.YTTRIUM.get()));

    }

}
