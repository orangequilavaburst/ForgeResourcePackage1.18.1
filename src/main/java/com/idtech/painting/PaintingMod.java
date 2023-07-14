package com.idtech.painting;

import com.idtech.BaseMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PaintingMod {

    // make the register
    public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, BaseMod.MODID);

    public static final RegistryObject<PaintingVariant> DREAMLAND =
            PAINTINGS.register("dreamland", () -> new PaintingVariant(64, 32));

    public static final RegistryObject<PaintingVariant> RALSEI =
            PAINTINGS.register("ralsei", () -> new PaintingVariant(16, 16));

    public static final RegistryObject<PaintingVariant> MIMIKYU =
            PAINTINGS.register("mimikyu", () -> new PaintingVariant(32, 32));

    // register all the paintings so they can show up in game
    public static void register(IEventBus eventBus){

        PAINTINGS.register(eventBus);

    }

}
