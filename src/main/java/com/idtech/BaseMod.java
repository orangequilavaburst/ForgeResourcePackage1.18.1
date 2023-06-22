package com.idtech;

import com.idtech.block.BlockMod;
import com.idtech.enchantment.EnchantmentMod;
import com.idtech.entity.EntityMod;
import com.idtech.item.CreativeModeTabMod;
import com.idtech.item.ItemMod;
import com.idtech.sound.SoundMod;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

/**
 * The BaseMod class holds any static variables your mod needs and runs all registry events. You'll add registry lines
 * in this file for all of your block, item, entities, etc. that you add to Minecraft
 */
@Mod(BaseMod.MODID)
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BaseMod {

    // Change your modid here. Whenever modid is needed, use BaseMod.MODID
    public static final String MODID = "yourmodname";
    private static final Logger LOGGER = LogManager.getLogger(BaseMod.MODID);

    public BaseMod() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modEventBus.addListener(this::processIMC);
        // Register adding stuff to the creative mode menu
        modEventBus.addListener(this::addCreative);

        // Register items, blocks, and other registry things
        CreativeModeTabMod.register(modEventBus);
        ItemMod.register(modEventBus);
        BlockMod.register(modEventBus);
        EntityMod.register(modEventBus);
        EnchantmentMod.register(modEventBus);
        SoundMod.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    // register your items here!
    private void addCreative(BuildCreativeModeTabContentsEvent event){

        // different items go in different creative mode tabs!
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){

            event.accept(BlockMod.BASIC_BLOCK);

            event.accept(BlockMod.DRAGONSTONE_ORE);
            event.accept(BlockMod.DEEPSLATE_DRAGONSTONE_ORE);
            event.accept(BlockMod.DRAGONSTONE_BLOCK);
            event.accept(BlockMod.RAW_DRAGONSTONE_BLOCK);

        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){

            event.accept(BlockMod.UNSTABLE_CRATE);

        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){

            event.accept(ItemMod.BASIC_ITEM);

            event.accept(ItemMod.DRAGONSTONE);
            event.accept(ItemMod.RAW_DRAGONSTONE);

        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT){

            event.accept(ItemMod.DRAGONSTONE_SWORD);
            event.accept(ItemMod.DRAGONSTONE_ARROW);
            event.accept(ItemMod.FIREBALL_WAND);
            event.accept(ItemMod.FISH_SLAPPER);
            event.accept(ItemMod.TOTEM_OF_HONESTY);

        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){

            event.accept(ItemMod.DRAGONSTONE_AXE);
            event.accept(ItemMod.DRAGONSTONE_HOE);
            event.accept(ItemMod.DRAGONSTONE_SHOVEL);
            event.accept(ItemMod.DRAGONSTONE_PICKAXE);
            event.accept(ItemMod.DRAGONSTONE_MAGNET);
            event.accept(ItemMod.WHIRLWIND_PICKAXE);
            event.accept(ItemMod.EXPERIENCE_JAR);

        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){

            event.accept(ItemMod.CHEESEBURGER);
            event.accept(ItemMod.HOT_DOG);

        }
        if (event.getTabKey() == CreativeModeTabMod.BASIC_CREATIVE_MODE_TAB.getKey()){

            event.accept(ItemMod.BASIC_ITEM);
            event.accept(BlockMod.BASIC_BLOCK);

            event.accept(BlockMod.DRAGONSTONE_ORE);
            event.accept(BlockMod.DEEPSLATE_DRAGONSTONE_ORE);
            event.accept(BlockMod.DRAGONSTONE_BLOCK);
            event.accept(BlockMod.RAW_DRAGONSTONE_BLOCK);

            event.accept(ItemMod.DRAGONSTONE);
            event.accept(ItemMod.RAW_DRAGONSTONE);

            event.accept(BlockMod.UNSTABLE_CRATE);

            event.accept(ItemMod.DRAGONSTONE_AXE);
            event.accept(ItemMod.DRAGONSTONE_HOE);
            event.accept(ItemMod.DRAGONSTONE_SHOVEL);
            event.accept(ItemMod.DRAGONSTONE_PICKAXE);
            event.accept(ItemMod.DRAGONSTONE_SWORD);
            event.accept(ItemMod.DRAGONSTONE_ARROW);
            event.accept(ItemMod.DRAGONSTONE_MAGNET);
            event.accept(ItemMod.FIREBALL_WAND);
            event.accept(ItemMod.FISH_SLAPPER);
            event.accept(ItemMod.TOTEM_OF_HONESTY);
            event.accept(ItemMod.WHIRLWIND_PICKAXE);
            event.accept(ItemMod.EXPERIENCE_JAR);

            event.accept(ItemMod.CHEESEBURGER);
            event.accept(ItemMod.HOT_DOG);

        }

    }

    /**
     * Setup step after all other registry events - if you need to do anything after everything else has loaded, put it here.
     *
     * @param event event info
     */
    public void setup(FMLCommonSetupEvent event) {
        // Do any mod setup steps here. Occurs after all registry events.
        // Put biome manager registry stuff here.
        BaseMod.LOGGER.info("Mod Setup Step");
//        WorldMod.setupBiomes();
       // TierSortingRegistry.registerTier(ItemMod.GEL_TIER, new ResourceLocation(MODID, "gelore"), List.of(Tiers.NETHERITE), List.of());

        BaseMod.LOGGER.info("Command registration here hopefully.");
//        MinecraftForge.EVENT_BUS.register(CustomEvent.class);
//        MinecraftForge.EVENT_BUS.addListener(EventMod::isHoldingEvent);
        //Adds the RegisterCommandEvent as an event and sets a listener for it during FMLCommonSetup
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(MODID, "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

        // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void entityRenderers(final EntityRenderersEvent.RegisterRenderers event){
            EntityMod.entityRenderers(event);
        }
        @SubscribeEvent
        public static void attributeRegister(EntityAttributeCreationEvent event) {
            EntityMod.onAttributeCreate(event);
        }


    }
}


