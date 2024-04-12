package com.idtech.item.custom;

import com.idtech.BaseMod;
import com.idtech.particle.custom.AfterImageParticle;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DragonstoneArmorItem extends CustomArmorItem {
    public DragonstoneArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);

        if (level instanceof ServerLevel serverLevel){
            if (hasFullSuitOfArmorOn(getMaterial(), player)){
                int particles = serverLevel.sendParticles(new AfterImageParticle.AfterImageParticleOptions(player.getId()),
                        player.getX(), player.getY(), player.getZ(),
                        0, 0.0D, 0.0D, 0.0D, 1.0D);
                // BaseMod.LOGGER.info(particles);
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(ArmorMaterial material, Player player){

        for (ItemStack itemStack : player.getInventory().armor){
            if (!(itemStack.getItem() instanceof ArmorItem)){
                return false;
            }
        }

        ArmorItem boots = (ArmorItem)player.getInventory().getArmor(0).getItem();
        ArmorItem leggings = (ArmorItem)player.getInventory().getArmor(1).getItem();
        ArmorItem breastplate = (ArmorItem)player.getInventory().getArmor(2).getItem();
        ArmorItem helmet = (ArmorItem)player.getInventory().getArmor(3).getItem();

        boolean check = true;
        if (helmet.getMaterial() != material || breastplate.getMaterial() != material || leggings.getMaterial() != material || boots.getMaterial() != material){
            check = false;
        }

        return check;

    }
}
