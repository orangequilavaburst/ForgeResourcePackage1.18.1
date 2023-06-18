package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.item.custom.DragonstoneArrowItem;
import com.idtech.item.custom.DragonstoneMagnetItem;
import net.minecraft.world.item.*;
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

    public static final RegistryObject<Item> DRAGONSTONE = ITEMS.register("dragonstone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_DRAGONSTONE = ITEMS.register("raw_dragonstone",
            () -> new Item(new Item.Properties()));

    // TOOLS

    public static final RegistryObject<Item> DRAGONSTONE_AXE = ITEMS.register("dragonstone_axe",
            () -> new AxeItem(ToolTierMod.DRAGONSTONE, 5.0f, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_HOE = ITEMS.register("dragonstone_hoe",
            () -> new HoeItem(ToolTierMod.DRAGONSTONE, -3, 0, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_SHOVEL = ITEMS.register("dragonstone_shovel",
            () -> new ShovelItem(ToolTierMod.DRAGONSTONE, 1.5f, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_PICKAXE = ITEMS.register("dragonstone_pickaxe",
            () -> new PickaxeItem(ToolTierMod.DRAGONSTONE, 1, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_SWORD = ITEMS.register("dragonstone_sword",
            () -> new AxeItem(ToolTierMod.DRAGONSTONE, 3.5f, -2.0f, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_ARROW = ITEMS.register("dragonstone_arrow",
            () -> new DragonstoneArrowItem(new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_MAGNET = ITEMS.register("dragonstone_magnet",
            () -> new DragonstoneMagnetItem(new Item.Properties()));

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
