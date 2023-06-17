package com.idtech.entity;

import com.idtech.BaseMod;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityMod {

    // make the register
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BaseMod.MODID);

    // your entity register should probably look something like this
    // public static final RegistryObject<EntityType<BasicEntity>> BASIC_ENTITY = ENTITY_TYPES.register("basic_entity", () -> EntityType.Builder.of(BasicEntity::new, ModCategory.MONSTER).sized(0.4f, 1.5f).build(new ResourceLocation(BaseMod.MOD_ID, "basic_entity").toString()));

    public static void register(IEventBus bus){

        ENTITY_TYPES.register(bus);

    }
    @SubscribeEvent
    public static void entityRenderers(final EntityRenderersEvent.RegisterRenderers event){

    }

    // this is different than in 1.16 but everything else is the same
    // I do think this makes more sense than the other way but alas change is usually hard.
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {

    }

}
