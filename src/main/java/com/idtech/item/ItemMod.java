package com.idtech.item;

import com.idtech.BaseMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemMod {

    // make the register
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BaseMod.MODID);

    // item example
    public static final RegistryObject<Item> BASIC_ITEM = ITEMS.register("basic_item",
            () -> new Item(new Item.Properties()));

    // ITEMS

    // TOOLS

    // FOOD

    // ARMOR

    // PROJECTILES

    // SPAWN EGGS

    // spawn egg example
    /*public static final RegistryObject<Item> BASIC_ENTITY_SPAWN_EGG = ITEMS.register("basic_entity_spawn_egg",
        () -> new ForgeSpawnEggItem(EntityMod.BASIC_ENTITY, 0xAAAAAA, 0xBBBBBB,
            new Item.Properties()));
     */

    // register all the items so they can show up in game
    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);

    }

}
