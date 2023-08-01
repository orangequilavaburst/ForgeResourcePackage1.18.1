package com.idtech.item.custom;

import com.idtech.item.ArmorMaterialMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FreddyMaskItem extends ArmorItem {
    public FreddyMaskItem(Properties p_40388_) {
        super(ArmorMaterialMod.FREDDY_MASK, Type.HELMET, p_40388_);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {

        //player.playSound(SoundMod.FREDDY_MASK_BREATHING.get(), 0.2f, 1.0f);

        super.onArmorTick(stack, level, player);
    }

}
