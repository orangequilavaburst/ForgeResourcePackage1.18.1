package com.idtech.item;

import com.idtech.BaseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabMod {

    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BaseMod.MODID);

    // example tab
    public static RegistryObject<CreativeModeTab> BASIC_CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("basic_tab",
            () -> CreativeModeTab.builder().title(Component.translatable("creativemodetab." + BaseMod.MODID + ".basic_tab")).icon(() -> new ItemStack(ItemMod.BASIC_ITEM.get())).build());

    public static void register(IEventBus eventBus){

        CREATIVE_MODE_TABS.register(eventBus);

    }

}
