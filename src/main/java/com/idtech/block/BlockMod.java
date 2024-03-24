package com.idtech.block;


import com.idtech.BaseMod;
import com.idtech.block.custom.UnstableCrateBlock;
import com.idtech.item.ItemMod;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockMod {

    // make the register
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BaseMod.MODID);

    // block example
    public static final RegistryObject<Block> BASIC_BLOCK = registerBlock("basic_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    // BLOCKS

    public static final RegistryObject<Block> DRAGONSTONE_BLOCK = registerBlock("dragonstone_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(DyeColor.ORANGE)));

    public static final RegistryObject<Block> RAW_DRAGONSTONE_BLOCK = registerBlock("raw_dragonstone_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).mapColor(DyeColor.ORANGE)));

    public static final RegistryObject<Block> DRAGONSTONE_ORE = registerBlock("dragonstone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).mapColor(DyeColor.ORANGE), UniformInt.of(10, 20)));

    public static final RegistryObject<Block> DEEPSLATE_DRAGONSTONE_ORE = registerBlock("deepslate_dragonstone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).mapColor(DyeColor.ORANGE), UniformInt.of(10, 20)));

    public static final RegistryObject<Block> YTTRIUM_BLOCK = registerBlock("yttrium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(DyeColor.GRAY)));

    public static final RegistryObject<Block> RAW_YTTRIUM_BLOCK = registerBlock("raw_yttrium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).mapColor(DyeColor.GRAY)));

    public static final RegistryObject<Block> YTTRIUM_ORE = registerBlock("yttrium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).mapColor(DyeColor.GRAY), UniformInt.of(10, 20)));

    public static final RegistryObject<Block> DEEPSLATE_YTTRIUM_ORE = registerBlock("deepslate_yttrium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).mapColor(DyeColor.GRAY), UniformInt.of(10, 20)));

    public static final RegistryObject<Block> UNSTABLE_CRATE = registerBlock("unstable_crate",
            () -> new UnstableCrateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN)));

    public static final RegistryObject<Block> FIRE_FLOWER = registerBlock("fire_flower",
            () -> new FlowerBlock(() -> MobEffects.FIRE_RESISTANCE, 20,
            BlockBehaviour.Properties.copy(Blocks.ORANGE_TULIP).noOcclusion().mapColor(DyeColor.ORANGE)));

    public static final RegistryObject<Block> POTTED_FIRE_FLOWER = registerBlockWithoutItem("potted_fire_flower",
            () -> new FlowerPotBlock(null, FIRE_FLOWER, BlockBehaviour.Properties.copy(Blocks.POTTED_ORANGE_TULIP).noOcclusion()));

    // quick function for registering the block and its item
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;

    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block){

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;

    }

    // since you need the item to go along with the block, we register the block item here
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){

        // makes an item for our block
        return ItemMod.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

    }

    // register all the blocks so they can show up in game
    public static void register(IEventBus eventBus){

        BLOCKS.register(eventBus);

    }

}





