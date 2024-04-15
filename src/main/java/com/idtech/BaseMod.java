package com.idtech;

import com.idtech.block.BlockMod;
import com.idtech.block.loot.LootModifierMod;
import com.idtech.client.FreddyMaskOverlay;
import com.idtech.enchantment.EnchantmentMod;
import com.idtech.entity.EntityMod;
import com.idtech.entity.model.CuteAlienModel;
import com.idtech.entity.render.CustomPlayerRenderer;
import com.idtech.item.CreativeModeTabMod;
import com.idtech.item.ItemMod;
import com.idtech.item.ItemUtils;
import com.idtech.painting.PaintingMod;
import com.idtech.particle.ParticleMod;
import com.idtech.sound.SoundMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
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

import java.awt.*;
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
    public static final Logger LOGGER = LogManager.getLogger(BaseMod.MODID);

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
        PaintingMod.register(modEventBus);
        LootModifierMod.register(modEventBus);
        ParticleMod.register(modEventBus);

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
            event.accept(BlockMod.YTTRIUM_ORE);
            event.accept(BlockMod.DEEPSLATE_YTTRIUM_ORE);
            event.accept(BlockMod.YTTRIUM_BLOCK);
            event.accept(BlockMod.RAW_YTTRIUM_BLOCK);
            event.accept(BlockMod.DRAGONITE_ORE);
            event.accept(BlockMod.DEEPSLATE_DRAGONITE_ORE);
            event.accept(BlockMod.DRAGONITE_BLOCK);
            event.accept(BlockMod.RAW_DRAGONITE_BLOCK);

        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS){

            event.accept(BlockMod.FIRE_FLOWER);
            event.accept(ItemMod.STARFRUIT_SEEDS);

        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){

            event.accept(BlockMod.UNSTABLE_CRATE);

        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){

            event.accept(ItemMod.BASIC_ITEM);

            event.accept(ItemMod.DRAGONSTONE);
            event.accept(ItemMod.RAW_DRAGONSTONE);
            event.accept(ItemMod.YTTRIUM);
            event.accept(ItemMod.RAW_YTTRIUM);
            event.accept(ItemMod.DRAGONITE_INGOT);
            event.accept(ItemMod.RAW_DRAGONITE);

            event.accept(ItemMod.STARFRUIT);
            event.accept(ItemMod.MAGICAL_STICK);
            event.accept(ItemMod.ROCK_SALT);
            event.accept(ItemMod.SALT);

            event.accept(ItemMod.SPEECH_BUBBLE);

        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT){

            event.accept(ItemMod.DRAGONSTONE_SWORD);
            event.accept(ItemMod.DRAGONSTONE_ARROW);
            event.accept(ItemMod.DRAGONSTONE_HELMET);
            event.accept(ItemMod.DRAGONSTONE_CHESTPLATE);
            event.accept(ItemMod.DRAGONSTONE_LEGGINGS);
            event.accept(ItemMod.DRAGONSTONE_BOOTS);
            event.accept(ItemMod.DRAGONSTONE_ARROW);
            event.accept(ItemMod.YTTRIUM_SWORD);
            event.accept(ItemMod.YTTRIUM_KATANA);
            event.accept(ItemMod.YTTRIUM_HELMET);
            event.accept(ItemMod.YTTRIUM_CHESTPLATE);
            event.accept(ItemMod.YTTRIUM_LEGGINGS);
            event.accept(ItemMod.YTTRIUM_BOOTS);
            event.accept(ItemMod.YTTRIUM_BOW);
            event.accept(ItemMod.YTTRIUM_ARROW);
            event.accept(ItemMod.SHEER_COLD_SWORD);
            event.accept(ItemMod.FISH_SLAPPER);
            event.accept(ItemMod.FIREBALL_WAND);
            event.accept(ItemMod.FAZER_BLASTER);
            event.accept(ItemMod.GOLDEN_FAZER_BLASTER);
            event.accept(ItemMod.EVIL_GAMEBOY);
            event.accept(ItemMod.BLOCK_TOSSER);
            event.accept(ItemMod.INVENTORY_TOSSER);
            event.accept(ItemMod.MEGA_BUSTER);
            event.accept(ItemMod.TOTEM_OF_HONESTY);
            event.accept(ItemMod.TOTEM_OF_FLUIDIY);
            event.accept(ItemMod.TOTEM_OF_CALAMITY);
            event.accept(ItemMod.FREDDY_FAZBEAR_MASK);
            event.accept(ItemMod.DEVILSKNIFE);
            event.accept(ItemMod.HYPER_BOMB);
            event.accept(ItemMod.SHURIKEN);
            event.accept(ItemMod.STAR_ROD);

        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){

            event.accept(ItemMod.DRAGONSTONE_AXE);
            event.accept(ItemMod.DRAGONSTONE_HOE);
            event.accept(ItemMod.DRAGONSTONE_SHOVEL);
            event.accept(ItemMod.DRAGONSTONE_PICKAXE);
            event.accept(ItemMod.DRAGONSTONE_MAGNET);
            event.accept(ItemMod.YTTRIUM_AXE);
            event.accept(ItemMod.YTTRIUM_HOE);
            event.accept(ItemMod.YTTRIUM_SHOVEL);
            event.accept(ItemMod.YTTRIUM_PICKAXE);
            event.accept(ItemMod.DRAGONITE_AXE);
            event.accept(ItemMod.DRAGONITE_HOE);
            event.accept(ItemMod.DRAGONITE_SHOVEL);
            event.accept(ItemMod.DRAGONITE_PICKAXE);
            event.accept(ItemMod.WHIRLWIND_PICKAXE);
            event.accept(ItemMod.EXPERIENCE_JAR);
            event.accept(ItemMod.TOTEM_OF_ELECTRICITY);
            event.accept(ItemMod.DETONATOR);
            event.accept(ItemMod.LASER_DRILL);

        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){

            event.accept(ItemMod.CHEESEBURGER);
            event.accept(ItemMod.HOT_DOG);
            event.accept(ItemMod.BAGUETTE);
            event.accept(ItemMod.SLICED_BREAD);
            event.accept(ItemMod.CHERRY);
            event.accept(ItemMod.STARFRUIT);
            event.accept(ItemMod.COTTON_CANDY);

        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){

            event.accept(ItemMod.FRIENDLY_CREEPER_SPAWN_EGG);
            event.accept(ItemMod.GOLDEN_SKELETON_SPAWN_EGG);
            event.accept(ItemMod.CUTE_ALIEN_SPAWN_EGG);

        }
        if (event.getTabKey() == CreativeModeTabMod.BASIC_CREATIVE_MODE_TAB.getKey()){

            event.accept(ItemMod.BASIC_ITEM);
            event.accept(BlockMod.BASIC_BLOCK);

            event.accept(ItemMod.SPEECH_BUBBLE);

            event.accept(BlockMod.DRAGONSTONE_ORE);
            event.accept(BlockMod.DEEPSLATE_DRAGONSTONE_ORE);
            event.accept(BlockMod.DRAGONSTONE_BLOCK);
            event.accept(BlockMod.RAW_DRAGONSTONE_BLOCK);
            event.accept(BlockMod.YTTRIUM_ORE);
            event.accept(BlockMod.DEEPSLATE_YTTRIUM_ORE);
            event.accept(BlockMod.YTTRIUM_BLOCK);
            event.accept(BlockMod.RAW_YTTRIUM_BLOCK);
            event.accept(BlockMod.DRAGONITE_ORE);
            event.accept(BlockMod.DEEPSLATE_DRAGONITE_ORE);
            event.accept(BlockMod.DRAGONITE_BLOCK);
            event.accept(BlockMod.RAW_DRAGONITE_BLOCK);

            event.accept(ItemMod.DRAGONSTONE);
            event.accept(ItemMod.RAW_DRAGONSTONE);
            event.accept(ItemMod.YTTRIUM);
            event.accept(ItemMod.RAW_YTTRIUM);
            event.accept(ItemMod.DRAGONITE_INGOT);
            event.accept(ItemMod.RAW_DRAGONITE);

            event.accept(BlockMod.UNSTABLE_CRATE);
            event.accept(BlockMod.FIRE_FLOWER);

            event.accept(ItemMod.DRAGONSTONE_AXE);
            event.accept(ItemMod.DRAGONSTONE_HOE);
            event.accept(ItemMod.DRAGONSTONE_SHOVEL);
            event.accept(ItemMod.DRAGONSTONE_PICKAXE);
            event.accept(ItemMod.DRAGONSTONE_SWORD);
            event.accept(ItemMod.DRAGONSTONE_ARROW);
            event.accept(ItemMod.DRAGONSTONE_MAGNET);
            event.accept(ItemMod.DRAGONSTONE_HELMET);
            event.accept(ItemMod.DRAGONSTONE_CHESTPLATE);
            event.accept(ItemMod.DRAGONSTONE_LEGGINGS);
            event.accept(ItemMod.DRAGONSTONE_BOOTS);
            event.accept(ItemMod.YTTRIUM_AXE);
            event.accept(ItemMod.YTTRIUM_HOE);
            event.accept(ItemMod.YTTRIUM_SHOVEL);
            event.accept(ItemMod.YTTRIUM_PICKAXE);
            event.accept(ItemMod.YTTRIUM_SWORD);
            event.accept(ItemMod.YTTRIUM_BOW);
            event.accept(ItemMod.YTTRIUM_ARROW);
            event.accept(ItemMod.YTTRIUM_HELMET);
            event.accept(ItemMod.YTTRIUM_CHESTPLATE);
            event.accept(ItemMod.YTTRIUM_LEGGINGS);
            event.accept(ItemMod.YTTRIUM_BOOTS);
            event.accept(ItemMod.DRAGONITE_AXE);
            event.accept(ItemMod.DRAGONITE_HOE);
            event.accept(ItemMod.DRAGONITE_SHOVEL);
            event.accept(ItemMod.DRAGONITE_PICKAXE);
            event.accept(ItemMod.DRAGONITE_SWORD);
            event.accept(ItemMod.LASER_DRILL);

            event.accept(ItemMod.TOTEM_OF_HONESTY);
            event.accept(ItemMod.TOTEM_OF_FLUIDIY);
            event.accept(ItemMod.TOTEM_OF_CALAMITY);
            event.accept(ItemMod.TOTEM_OF_ELECTRICITY);

            event.accept(ItemMod.FIREBALL_WAND);
            event.accept(ItemMod.FISH_SLAPPER);
            event.accept(ItemMod.WHIRLWIND_PICKAXE);
            event.accept(ItemMod.SHEER_COLD_SWORD);
            event.accept(ItemMod.YTTRIUM_KATANA);
            event.accept(ItemMod.FAZER_BLASTER);
            event.accept(ItemMod.GOLDEN_FAZER_BLASTER);
            event.accept(ItemMod.FREDDY_FAZBEAR_MASK);
            event.accept(ItemMod.DEVILSKNIFE);
            event.accept(ItemMod.HYPER_BOMB);
            event.accept(ItemMod.SHURIKEN);
            event.accept(ItemMod.STAR_ROD);

            event.accept(ItemMod.EXPERIENCE_JAR);
            event.accept(ItemMod.EVIL_GAMEBOY);
            event.accept(ItemMod.DETONATOR);
            event.accept(ItemMod.BLOCK_TOSSER);
            event.accept(ItemMod.INVENTORY_TOSSER);
            event.accept(ItemMod.MEGA_BUSTER);

            event.accept(ItemMod.CHEESEBURGER);
            event.accept(ItemMod.HOT_DOG);
            event.accept(ItemMod.BAGUETTE);
            event.accept(ItemMod.SLICED_BREAD);

            event.accept(ItemMod.CHERRY);
            event.accept(ItemMod.STARFRUIT);
            event.accept(ItemMod.STARFRUIT_SEEDS);
            event.accept(ItemMod.MAGICAL_STICK);
            event.accept(ItemMod.ROCK_SALT);
            event.accept(ItemMod.SALT);
            event.accept(ItemMod.COTTON_CANDY);

            event.accept(ItemMod.FRIENDLY_CREEPER_SPAWN_EGG);
            event.accept(ItemMod.GOLDEN_SKELETON_SPAWN_EGG);
            event.accept(ItemMod.CUTE_ALIEN_SPAWN_EGG);

            event.accept(ItemMod.AFTER_IMAGE_DEBUG_STICK);

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
       // TierSortingRegistry.registerTier(ItemMod.GEL_TIER, new ResourceLocation(MODID, "gelore"), List.of(Tiers.NETHERITE), List.of());+

        BaseMod.LOGGER.info("Command registration here hopefully.");
//        MinecraftForge.EVENT_BUS.register(CustomEvent.class);
//        MinecraftForge.EVENT_BUS.addListener(EventMod::isHoldingEvent);
        //Adds the RegisterCommandEvent as an event and sets a listener for it during FMLCommonSetup
        ItemUtils.makeBow(ItemMod.YTTRIUM_BOW.get());
        ItemUtils.makeBuster(ItemMod.MEGA_BUSTER.get());
        event.enqueueWork(() ->{
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(BlockMod.FIRE_FLOWER.getId(), BlockMod.POTTED_FIRE_FLOWER);
        });

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

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(CuteAlienModel.LAYER_LOCATION, CuteAlienModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerGUIOverlays(final RegisterGuiOverlaysEvent event){

            event.registerBelowAll("freddy_mask", FreddyMaskOverlay.FREDDY_MASK_OVERLAY);

        }

    }

    @Mod.EventBusSubscriber(modid = BaseMod.MODID, bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ModClientEvents{

        @SubscribeEvent
        public static void nameTagEvent(RenderNameTagEvent event) {
            if (event.getEntity().getDisplayName().getString().equals("jeb_")) {
                String name = event.getContent().getString();
                Component nameTag = Component.empty();
                float t = (Minecraft.getInstance().player.level().getGameTime() / 50.0f) % 1.0f;
                for (int i = 0; i < name.length(); i++){

                    Color color = Color.getHSBColor((t + (i/33.3f)) % 1.0f, 0.5f, 1.0f);
                    nameTag = nameTag.copy().append(Component.literal(Character.toString(name.charAt(i))).withStyle(style -> style.withColor(color.getRGB())));

                }
                event.setContent(nameTag);
                event.setResult(Event.Result.ALLOW);
            }
        }

        @SubscribeEvent
        public static void renderPlayerEvent(RenderPlayerEvent event){

            if (event.getEntity().getMainHandItem().is(ItemMod.TOTEM_OF_ELECTRICITY.get()) ||
                    event.getEntity().getOffhandItem().is(ItemMod.TOTEM_OF_ELECTRICITY.get())) {
                EntityRendererProvider.Context newContext = new EntityRendererProvider.Context(Minecraft.getInstance().getEntityRenderDispatcher(),
                        Minecraft.getInstance().getItemRenderer(),
                        Minecraft.getInstance().getBlockRenderer(),
                        Minecraft.getInstance().gameRenderer.itemInHandRenderer,
                        Minecraft.getInstance().getResourceManager(),
                        Minecraft.getInstance().getEntityModels(),
                        Minecraft.getInstance().font);
                PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
                CustomPlayerRenderer newRenderer = new CustomPlayerRenderer(newContext, model, 1.0f, "werewire_skin");
                newRenderer.render((AbstractClientPlayer) event.getEntity(), event.getEntity().getYRot(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());

                event.setCanceled(true);
            }

        }

        @SubscribeEvent
        public static void renderArmEvent(RenderArmEvent event){

            /*
            event.setCanceled(true);

            Minecraft mc = Minecraft.getInstance();
            PlayerRenderer renderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(event.getPlayer());
            ModelPart model = renderer.getModel().rightArm;

            event.getPoseStack().pushPose();
            //event.getPoseStack().mulPose(Axis.YP.rotationDegrees(-45.0F));
            model.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(mc.player.getSkinTextureLocation())),
                    event.getPackedLight(), OverlayTexture.RED_OVERLAY_V);
            event.getPoseStack().popPose();
            */

        }

    }

    @SubscribeEvent
    public static void renderItemEvent(RenderHandEvent event){

        /*
        ItemStack itemStack = event.getItemStack();
        if (itemStack.is(ItemMod.MEGA_BUSTER.get())){

            event.setCanceled(true);

            Minecraft mc = Minecraft.getInstance();
            Player player = Minecraft.getInstance().player;
            ItemInHandRenderer renderer = mc.getEntityRenderDispatcher().getItemInHandRenderer();

            ItemModelShaper itemModelShaper = new net.minecraftforge.client.model.ForgeItemModelShaper(Minecraft.getInstance().getModelManager());
            BakedModel bakedmodel;
            if (itemStack.is(Items.TRIDENT)) {
                bakedmodel = itemModelShaper.getModelManager().getModel(TRIDENT_IN_HAND_MODEL);
            } else if (itemStack.is(Items.SPYGLASS)) {
                bakedmodel = itemModelShaper.getModelManager().getModel(SPYGLASS_IN_HAND_MODEL);
            } else {
                bakedmodel = itemModelShaper.getItemModel(itemStack);
            }

            event.getPoseStack().pushPose();
            renderer.renderItem(player, itemStack,
                    (event.getHand().equals(InteractionHand.MAIN_HAND)) ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                    true, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());

            event.getPoseStack().popPose();

        }
        */

    }

    @Mod.EventBusSubscriber(modid = BaseMod.MODID)
    public static class ModEvents {
        @SubscribeEvent
        public static void useItemFinishEvent(final LivingEntityUseItemEvent.Finish event){

            if (event.getEntity() instanceof Player player){

                ItemStack itemStack = event.getItem();

                //BaseMod.LOGGER.info(itemStack.getDisplayName().getString() + " was used! (Finish)");

            }

        }

        @SubscribeEvent
        public static void useItemStartEvent(final LivingEntityUseItemEvent.Start event){

            if (event.getEntity() instanceof Player player){

                ItemStack itemStack = event.getItem();

                //BaseMod.LOGGER.info(itemStack.getDisplayName().getString() + " was used! (Start)");

            }

        }

        @SubscribeEvent
        public static void useItemStopEvent(final LivingEntityUseItemEvent.Stop event){

            if (event.getEntity() instanceof Player player){

                ItemStack itemStack = event.getItem();

                //BaseMod.LOGGER.info(itemStack.getDisplayName().getString() + " was used! (Stop)");

            }

        }

        @SubscribeEvent
        public static void rightClickItemEvent(PlayerInteractEvent.RightClickItem event){

            ItemStack itemStack = event.getItemStack();
            Player player = event.getEntity();

            int e = EnchantmentHelper.getEnchantmentLevel(EnchantmentMod.GRAPPLING.get(), player);
            Level level = player.level();
            FishingHook hook = player.fishing;
            if (hook != null && e > 0){

                if (hook.isInWall() || hook.onGround() || hook.getHookedIn() != null) {

                    Vec3 playerPos = player.position();
                    Vec3 hookPos = hook.getPosition(1.0f);
                    Vec3 angleVec = hookPos.subtract(playerPos);
                    double speed = ((float) (e + 1)*5.0f) / 100.0f * angleVec.length();

                    player.setDeltaMovement(angleVec.normalize().scale(speed));
                    player.setDeltaMovement(player.getDeltaMovement().x(), Math.max(0, player.getDeltaMovement().y()) + 0.25, player.getDeltaMovement().z());
                    player.resetFallDistance();

                    itemStack.hurtAndBreak(1, player, (p_41288_) -> {
                        p_41288_.broadcastBreakEvent(itemStack.getEquipmentSlot());
                    });

                }

            }

        }

        @SubscribeEvent
        public static void equipmentChangeEvent(LivingEquipmentChangeEvent event){

            ItemStack from = event.getFrom();
            ItemStack to = event.getTo();
            EquipmentSlot slot = event.getSlot();

            //String f = (from != null) ? from.toString() : "nothing";
            //String t = (to != null) ? to.toString() : "nothing";
            //String s = (slot != null) ? slot.getName() : "empty slot";

            if (from.is(ItemMod.FREDDY_FAZBEAR_MASK.get())){

                if (event.getEntity() instanceof Player p){
                    //p.displayClientMessage(Component.literal("From " + f + " to " + t + ", " + s), false);

                    //p.displayClientMessage(Component.literal("From " + f + " to " + s), false);
                    if (slot == EquipmentSlot.HEAD) {
                        //p.displayClientMessage(Component.literal("har har har harrr harrr"), false);
                        Minecraft.getInstance().getSoundManager().stop(SoundMod.FREDDY_MASK_BREATHING.getId(), p.getSoundSource());
                        Minecraft.getInstance().getSoundManager().play(new SimpleSoundInstance(SoundMod.FREDDY_MASK_REMOVE.get(),
                                p.getSoundSource(), 0.25f, 1.0f, RandomSource.create(), p.blockPosition()));

                    }
                }

            }

        }

        @SubscribeEvent
        public static void livingChangeTargetEvent(LivingChangeTargetEvent event){

            LivingEntity oldTarget = event.getOriginalTarget();
            if (oldTarget != null) {
                if (event.getOriginalTarget().getItemBySlot(EquipmentSlot.HEAD).is(ItemMod.FREDDY_FAZBEAR_MASK.get())) {
                    event.setCanceled(true);
                }
            }

            LivingEntity newTarget = event.getNewTarget();
            if (newTarget != null) {
                if (event.getNewTarget().getItemBySlot(EquipmentSlot.HEAD).is(ItemMod.FREDDY_FAZBEAR_MASK.get())) {
                    event.setCanceled(true);
                }
            }

        }

    }



}


