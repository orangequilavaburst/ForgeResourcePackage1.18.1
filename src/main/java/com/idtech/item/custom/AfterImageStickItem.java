package com.idtech.item.custom;

import com.idtech.BaseMod;
import com.idtech.particle.custom.AfterImageParticle;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AfterImageStickItem extends Item {
    public AfterImageStickItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel){
            int particles = serverLevel.sendParticles(new AfterImageParticle.AfterImageParticleOptions(pPlayer.getId()),
                    pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    0, 0.0D, 0.0D, 0.0D, 1.0D);
            BaseMod.LOGGER.info("Particle result: ".concat(Integer.toString(particles)));
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
