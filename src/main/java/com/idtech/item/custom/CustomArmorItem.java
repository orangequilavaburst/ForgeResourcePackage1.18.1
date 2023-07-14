package com.idtech.item.custom;

import com.idtech.BaseMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CustomArmorItem extends ArmorItem {

    public CustomArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        int legs = (slot == EquipmentSlot.LEGS) ? 2 : 1;
        return BaseMod.MODID + ":textures/models/armor/" + this.material.getName() + "_layer_" + Integer.toString(legs) + ".png";
    }
}
