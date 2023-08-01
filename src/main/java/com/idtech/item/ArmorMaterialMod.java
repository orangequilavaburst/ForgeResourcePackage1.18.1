package com.idtech.item;

import com.idtech.sound.SoundMod;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class ArmorMaterialMod {

    public static ArmorMaterial DRAGONSTONE = ItemUtils.buildArmorMaterial("dragonstone", 300, new int[]{3, 8, 6, 3} ,5,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 4.0f, 0.0f,ItemMod.DRAGONSTONE.get().toString());
    public static ArmorMaterial YTTRIUM = ItemUtils.buildArmorMaterial("yttrium", 300, new int[]{3, 8, 6, 3} ,5,
            SoundEvents.ARMOR_EQUIP_IRON, 4.0f, 0.0f,ItemMod.YTTRIUM.get().toString());

    public static ArmorMaterial FREDDY_MASK = ItemUtils.buildArmorMaterial("freddy_mask", 10, new int[]{1, 1, 1, 1}, 0,
            SoundMod.FREDDY_MASK_EQUIP.get(), 0.0f, 0.0f, () -> Ingredient.EMPTY);

}
