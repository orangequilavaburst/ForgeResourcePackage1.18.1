package com.idtech.enchantment;

import com.idtech.BaseMod;
import com.idtech.enchantment.custom.GambleGearEnchantment;
import com.idtech.enchantment.custom.GrapplingEnchantment;
import com.idtech.enchantment.custom.KillerQueenEnchantment;
import com.idtech.enchantment.custom.SnowPointEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentMod {

    // make the register
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BaseMod.MODID);

    public static final RegistryObject<Enchantment> SNOW_POINT = ENCHANTMENTS.register("snow_point",
            () -> new SnowPointEnchantment(Enchantment.Rarity.RARE,EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static final RegistryObject<Enchantment> GAMBLE_GEAR = ENCHANTMENTS.register("gamble_gear",
            GambleGearEnchantment::new);

    public static final RegistryObject<Enchantment> KILLER_QUEEN = ENCHANTMENTS.register("killer_queen",
            KillerQueenEnchantment::new);

    public static final RegistryObject<Enchantment> GRAPPLING = ENCHANTMENTS.register("grappling", GrapplingEnchantment::new);

    // register all the enchantments so they can show up in game
    public static void register(IEventBus eventBus){

        ENCHANTMENTS.register(eventBus);

    }

}