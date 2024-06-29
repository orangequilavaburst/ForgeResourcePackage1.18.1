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
    public static final RegistryObject<Item> RAW_DRAGONITE = ITEMS.register("raw_dragonite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_INGOT = ITEMS.register("dragonite_ingot", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TOTEM_OF_ELECTRICITY = ITEMS.register("totem_of_electricity",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> SPEECH_BUBBLE = ITEMS.register("speech_bubble",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ROCK_SALT = ITEMS.register("rock_salt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGICAL_STICK = ITEMS.register("magical_stick", () -> new Item(new Item.Properties()));

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
            () -> new DragonstoneArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_CHESTPLATE = ITEMS.register("dragonstone_chestplate",
            () -> new DragonstoneArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_LEGGINGS = ITEMS.register("dragonstone_leggings",
            () -> new DragonstoneArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONSTONE_BOOTS = ITEMS.register("dragonstone_boots",
            () -> new DragonstoneArmorItem(ArmorMaterialMod.DRAGONSTONE, ArmorItem.Type.BOOTS, new Item.Properties()));

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

    public static final RegistryObject<Item> DRAGONITE_SWORD = ITEMS.register("dragonite_sword", () -> new SwordItem(ToolTierMod.DRAGONITE, 4, 2, new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_PICKAXE = ITEMS.register("dragonite_pickaxe", () -> new PickaxeItem(ToolTierMod.DRAGONITE, 1, 1, new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_AXE = ITEMS.register("dragonite_axe", () -> new AxeItem(ToolTierMod.DRAGONITE, 7, 1, new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_SHOVEL = ITEMS.register("dragonite_shovel", () -> new ShovelItem(ToolTierMod.DRAGONITE, 0, 0, new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_HOE = ITEMS.register("dragonite_hoe", () -> new HoeItem(ToolTierMod.DRAGONITE, 0, 0, new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_SWISS_ARMY_KNIFE = ITEMS.register("wooden_swiss_army_knife",
            () -> new MultiToolItem(Tiers.WOOD, new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_SWISS_ARMY_KNIFE = ITEMS.register("golden_swiss_army_knife",
            () -> new MultiToolItem(Tiers.GOLD, new Item.Properties()));
    public static final RegistryObject<Item> STONE_SWISS_ARMY_KNIFE = ITEMS.register("stone_swiss_army_knife",
            () -> new MultiToolItem(Tiers.STONE, new Item.Properties()));
    public static final RegistryObject<Item> IRON_SWISS_ARMY_KNIFE = ITEMS.register("iron_swiss_army_knife",
            () -> new MultiToolItem(Tiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SWISS_ARMY_KNIFE = ITEMS.register("diamond_swiss_army_knife",
            () -> new MultiToolItem(Tiers.DIAMOND, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_SWISS_ARMY_KNIFE = ITEMS.register("netherite_swiss_army_knife",
            () -> new MultiToolItem(Tiers.NETHERITE, new Item.Properties()));
    public static final RegistryObject<Item> DRAGONSTONE_SWISS_ARMY_KNIFE = ITEMS.register("dragonstone_swiss_army_knife",
            () -> new MultiToolItem(ToolTierMod.DRAGONSTONE, new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_SWISS_ARMY_KNIFE = ITEMS.register("dragonite_swiss_army_knife",
            () -> new MultiToolItem(ToolTierMod.DRAGONITE, new Item.Properties()));
    public static final RegistryObject<Item> YTTRIUM_SWISS_ARMY_KNIFE = ITEMS.register("yttrium_swiss_army_knife",
            () -> new MultiToolItem(ToolTierMod.YTTRIUM, new Item.Properties()));

    public static final RegistryObject<Item> LASER_DRILL = ITEMS.register("laser_drill",
            () -> new LaserDrillItem(new Item.Properties(), 10.0f));
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

    public static final RegistryObject<Item> FREDDY_FAZBEAR_MASK = ITEMS.register("freddy_fazbear_mask",
            () -> new FreddyMaskItem(new Item.Properties()));

    public static final RegistryObject<Item> EVIL_GAMEBOY = ITEMS.register("evil_gameboy",
            () -> new ZombieConverterItem(new Item.Properties(), 10));

    public static final RegistryObject<Item> DETONATOR = ITEMS.register("detonator",
            () -> new DetonatorItem(new Item.Properties()));

    public static final RegistryObject<Item> BLOCK_TOSSER = ITEMS.register("block_tosser",
            () -> new BlockTosserItem(new Item.Properties()));

    public static final RegistryObject<Item> INVENTORY_TOSSER = ITEMS.register("inventory_tosser",
            () -> new InventoryTosserItem(new Item.Properties()));

    public static final RegistryObject<Item> MEGA_BUSTER = ITEMS.register("mega_buster",
            () -> new MegaBusterItem(new Item.Properties()));

    public static final RegistryObject<Item> DEVILSKNIFE = ITEMS.register("devilsknife", () -> new DevilsknifeItem(4, 2, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> HYPER_BOMB = ITEMS.register("hyper_bomb", () -> new HyperBombItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> SHURIKEN = ITEMS.register("shuriken", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STAR_ROD = ITEMS.register("star_rod", () -> new Item(new Item.Properties().stacksTo(1)));

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
    public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(1.0f).build())));
    public static final RegistryObject<Item> SLICED_BREAD = ITEMS.register("sliced_bread", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));

    public static final RegistryObject<Item> STARFRUIT = ITEMS.register("starfruit", () -> new Item(new Item.Properties().food(Items.BREAD.getFoodProperties())));
    public static final RegistryObject<Item> STARFRUIT_SEEDS = ITEMS.register("starfruit_seeds", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHERRY = ITEMS.register("cherry", () -> new Item(new Item.Properties().food(Items.APPLE.getFoodProperties())));
    public static final RegistryObject<Item> COTTON_CANDY = ITEMS.register("cotton_candy", () -> new CottonCandyItem(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(4).saturationMod(0.75f).build())));
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

    // DEBUG

    public static final RegistryObject<Item> AFTER_IMAGE_DEBUG_STICK = ITEMS.register("after_image_debug_stick",
            () -> new AfterImageStickItem(new Item.Properties().stacksTo(1)));

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
