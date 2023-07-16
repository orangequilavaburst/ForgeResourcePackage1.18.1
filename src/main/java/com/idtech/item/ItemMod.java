package com.idtech.item;

import com.idtech.BaseMod;
import com.idtech.entity.EntityMod;
import com.idtech.item.custom.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
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

    public static final RegistryObject<Item> YTTRIUM = ITEMS.register("yttrium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_YTTRIUM = ITEMS.register("raw_yttrium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TOTEM_OF_ELECTRICITY = ITEMS.register("totem_of_electricity",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

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

    public static final RegistryObject<Item> DRAGONSTONE_HELMET = ITEMS.register("dragonstone_helmet",
            () -> new CustomArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_CHESTPLATE = ITEMS.register("dragonstone_chestplate",
            () -> new CustomArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_LEGGINGS = ITEMS.register("dragonstone_leggings",
            () -> new CustomArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_BOOTS = ITEMS.register("dragonstone_boots",
            () -> new CustomArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_ARROW = ITEMS.register("dragonstone_arrow",
            () -> new DragonstoneArrowItem(new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_MAGNET = ITEMS.register("dragonstone_magnet",
            () -> new DragonstoneMagnetItem(new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_AXE = ITEMS.register("yttrium_axe",
            () -> new AxeItem(ToolTierMod.YTTRIUM, 5.0f, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_HOE = ITEMS.register("yttrium_hoe",
            () -> new HoeItem(ToolTierMod.YTTRIUM, -3, 0, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_SHOVEL = ITEMS.register("yttrium_shovel",
            () -> new ShovelItem(ToolTierMod.YTTRIUM, 1.5f, -3.0f, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_PICKAXE = ITEMS.register("yttrium_pickaxe",
            () -> new PickaxeItem(ToolTierMod.YTTRIUM, 1, -2.8f, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_SWORD = ITEMS.register("yttrium_sword",
            () -> new SwordItem(ToolTierMod.YTTRIUM, 3, -2.0f, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_HELMET = ITEMS.register("yttrium_helmet",
            () -> new CustomArmorItem(ArmorMaterialMod.YTTRIUM, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_CHESTPLATE = ITEMS.register("yttrium_chestplate",
            () -> new CustomArmorItem(ArmorMaterialMod.YTTRIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_LEGGINGS = ITEMS.register("yttrium_leggings",
            () -> new CustomArmorItem(ArmorMaterialMod.YTTRIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_BOOTS = ITEMS.register("yttrium_boots",
            () -> new CustomArmorItem(ArmorMaterialMod.YTTRIUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_ARROW = ITEMS.register("yttrium_arrow",
            () -> new YttriumArrowItem(new Item.Properties()));

    public static final RegistryObject<Item> YTTRIUM_BOW = ITEMS.register("yttrium_bow",
            () -> new CustomBowItem(new Item.Properties(), 15, 0.0f, 2));

    public static final RegistryObject<Item> YTTRIUM_KATANA = ITEMS.register("yttrium_katana",
            () -> new YttriumKatanaItem(new Item.Properties()));
    public static final RegistryObject<Item> FIREBALL_WAND = ITEMS.register("fireball_wand",
            () -> new FireballWandItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> FISH_SLAPPER = ITEMS.register("fish_slapper",
            () -> new FishSlapperItem(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> TOTEM_OF_HONESTY = ITEMS.register("totem_of_honesty",
            () -> new HonestyTotemItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));

    public static final RegistryObject<Item> TOTEM_OF_FLUIDIY = ITEMS.register("totem_of_fluidity",
            () -> new FluidityTotemItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));

    public static final RegistryObject<Item> TOTEM_OF_CALAMITY = ITEMS.register("totem_of_calamity",
            () -> new CalamityTotemItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));

    public static final RegistryObject<Item> EXPERIENCE_JAR = ITEMS.register("experience_jar",
            () -> new ExperienceJarItem(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> WHIRLWIND_PICKAXE = ITEMS.register("whirlwind_pickaxe",
            () -> new WhirlwindPickaxeItem(Tiers.NETHERITE, 10, -1.0f, new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SHEER_COLD_SWORD = ITEMS.register("sheer_cold_sword",
            () -> new SheerColdSwordItem(Tiers.NETHERITE, 10, 0.0f, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> FAZER_BLASTER = ITEMS.register("fazer_blaster",
            () -> new FazerBlasterItem(new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_FAZER_BLASTER = ITEMS.register("golden_fazer_blaster",
            () -> new FazerBlasterItem(new Item.Properties(), 10));

    public static final RegistryObject<Item> EVIL_GAMEBOY = ITEMS.register("evil_gameboy",
            () -> new ZombieConverterItem(new Item.Properties(), 10));

    public static final RegistryObject<Item> DETONATOR = ITEMS.register("detonator",
            () -> new DetonatorItem(new Item.Properties()));

    // FOOD

    public static final RegistryObject<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .meat()
                            .saturationMod(4.0f)
                            .nutrition(6)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1), 1.0f).build())));

    public static final RegistryObject<Item> HOT_DOG = ITEMS.register("hot_dog",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .meat()
                            .saturationMod(2.0f)
                            .nutrition(5).build())));

    // ARMOR

    // PROJECTILES

    // SPAWN EGGS

    public static final RegistryObject<Item> FRIENDLY_CREEPER_SPAWN_EGG = ITEMS.register("friendly_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityMod.FRIENDLY_CREEPER, 0xE56921, 0x942E49,
                    new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_SKELETON_SPAWN_EGG = ITEMS.register("golden_skeleton_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityMod.GOLDEN_SKELETON, 0xfee048, 0xfee048,
                    new Item.Properties()));

    public static final RegistryObject<Item> CUTE_ALIEN_SPAWN_EGG = ITEMS.register("cute_alien_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityMod.CUTE_ALIEN, 0x64F2A8, 0x9235ED,
                    new Item.Properties()));

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
