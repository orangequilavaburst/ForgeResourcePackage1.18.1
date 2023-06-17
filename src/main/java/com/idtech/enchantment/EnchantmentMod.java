package com.idtech.enchantment;

import com.idtech.BaseMod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentMod {

    // make the register
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BaseMod.MODID);

    // register all the enchantments so they can show up in game
    public static void register(IEventBus eventBus){

        ENCHANTMENTS.register(eventBus);

    }

}