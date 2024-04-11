package com.idtech.entity;

import com.idtech.BaseMod;
import com.idtech.entity.custom.*;
import com.idtech.entity.render.*;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.http.util.EntityUtils;

public class EntityMod {

    // make the register
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BaseMod.MODID);

    // your entity register should probably look something like this
    // public static final RegistryObject<EntityType<BasicEntity>> BASIC_ENTITY = ENTITY_TYPES.register("basic_entity", () -> EntityType.Builder.of(BasicEntity::new, ModCategory.MONSTER).sized(0.4f, 1.5f).build(new ResourceLocation(BaseMod.MOD_ID, "basic_entity").toString()));

    public static final RegistryObject<EntityType<DragonstoneArrowEntity>> DRAGONSTONE_ARROW_ENTITY = ENTITY_TYPES.register("dragonstone_arrow",
            () -> EntityType.Builder.of((EntityType.EntityFactory<DragonstoneArrowEntity>) DragonstoneArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(BaseMod.MODID, "dragonstone_arrow").toString()));

    public static final RegistryObject<EntityType<YttriumArrowEntity>> YTTRIUM_ARROW_ENTITY = ENTITY_TYPES.register("yttrium_arrow",
            () -> EntityType.Builder.of((EntityType.EntityFactory<YttriumArrowEntity>) YttriumArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(BaseMod.MODID, "yttrium_arrow").toString()));

    public static final RegistryObject<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE_ENTITY = ENTITY_TYPES.register("block_projectile",
            () -> EntityType.Builder.of((EntityType.EntityFactory<BlockProjectileEntity>) BlockProjectileEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(new ResourceLocation(BaseMod.MODID, "block_projectile").toString()));

    public static final RegistryObject<EntityType<FriendlyCreeperEntity>> FRIENDLY_CREEPER = ENTITY_TYPES.register("friendly_creeper",
            () -> EntityType.Builder.of(FriendlyCreeperEntity::new, MobCategory.CREATURE).sized(0.5f, 1.5f).build(new ResourceLocation(BaseMod.MODID, "friendly_creeper").toString()));

    public static final RegistryObject<EntityType<GoldenSkeletonEntity>> GOLDEN_SKELETON = ENTITY_TYPES.register("golden_skeleton",
            () -> EntityType.Builder.of(GoldenSkeletonEntity::new, MobCategory.MONSTER).sized(0.5f, 1.5f).build(new ResourceLocation(BaseMod.MODID, "golden_skeleton").toString()));

    public static final RegistryObject<EntityType<CuteAlienEntity>> CUTE_ALIEN = ENTITY_TYPES.register("cute_alien",
            () -> EntityType.Builder.of(CuteAlienEntity::new, MobCategory.CREATURE).sized(0.5f, 0.5f).build(new ResourceLocation(BaseMod.MODID, "cute_alien").toString()));

    public static final RegistryObject<EntityType<DevilsknifeEntity>> DEVILSKNIFE_ENTITY = ENTITY_TYPES.register("devilsknife",
            () -> EntityType.Builder.of((EntityType.EntityFactory<DevilsknifeEntity>) DevilsknifeEntity::new, MobCategory.MISC).sized(1.5f, 1.5f).build(new ResourceLocation(BaseMod.MODID, "devilsknife").toString()));

    public static final RegistryObject<EntityType<HyperBombEntity>> HYPER_BOMB_ENTITY = ENTITY_TYPES.register("hyper_bomb",
            () -> EntityType.Builder.of((EntityType.EntityFactory<HyperBombEntity>) HyperBombEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(BaseMod.MODID, "hyper_bomb").toString()));

    public static void register(IEventBus bus){

        ENTITY_TYPES.register(bus);

    }
    @SubscribeEvent
    public static void entityRenderers(final EntityRenderersEvent.RegisterRenderers event){

        event.registerEntityRenderer(EntityMod.DRAGONSTONE_ARROW_ENTITY.get(), DragonstoneArrowRenderer::new);
        event.registerEntityRenderer(EntityMod.YTTRIUM_ARROW_ENTITY.get(), YttriumArrowRenderer::new);
        event.registerEntityRenderer(EntityMod.BLOCK_PROJECTILE_ENTITY.get(), BlockProjectileRenderer::new);
        event.registerEntityRenderer(EntityMod.FRIENDLY_CREEPER.get(), FriendlyCreeperRenderer::new);
        event.registerEntityRenderer(EntityMod.GOLDEN_SKELETON.get(), GoldenSkeletonRenderer::new);
        event.registerEntityRenderer(EntityMod.CUTE_ALIEN.get(), CuteAlienRenderer::new);
        event.registerEntityRenderer(EntityMod.DEVILSKNIFE_ENTITY.get(), DevilsknifeEntityRenderer::new);
        event.registerEntityRenderer(EntityMod.HYPER_BOMB_ENTITY.get(), HyperBombRenderer::new);

    }

    // this is different than in 1.16 but everything else is the same
    // I do think this makes more sense than the other way but alas change is usually hard.
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(FRIENDLY_CREEPER.get(), FriendlyCreeperEntity.createAttributes().build());
        event.put(GOLDEN_SKELETON.get(), GoldenSkeletonEntity.createAttributes().build());
        event.put(CUTE_ALIEN.get(), CuteAlienEntity.createAttributes().build());
    }

}
