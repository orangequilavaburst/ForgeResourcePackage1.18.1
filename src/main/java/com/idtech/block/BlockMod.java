package com.idtech.block;


import com.idtech.BaseMod;
import com.idtech.item.ItemMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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



    // quick function for registering the block and its item
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
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





