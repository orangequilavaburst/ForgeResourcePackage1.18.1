package com.idtech.utils;

import com.idtech.BaseMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagMod {

    public static class Blocks {

        public static final TagKey<Block> NEEDS_DRAGONITE_TOOL = tag("needs_dragonite_tool");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(BaseMod.MODID, name));
        }

    }

    public static class Items {

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(BaseMod.MODID, name));
        }

    }

}
