package com.idtech.item.custom;

import com.idtech.particle.ParticleMod;
import com.idtech.sound.SoundMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class HonestyTotemItem extends Item {

    public HonestyTotemItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        int count = 32;
        Random random = new Random();

        Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack.copy());

        for(int i = 0; i < count; ++i) {
            double d0 = random.nextFloat() * 2.0F - 1.0F;
            double d1 = random.nextFloat() * 2.0F - 1.0F;
            double d2 = random.nextFloat() * 2.0F - 1.0F;
            if (!(d0 * d0 + d1 * d1 + d2 * d2 > 1.0D)) {
                double d3 = pPlayer.getX(d0 / 4.0D);
                double d4 = pPlayer.getY(0.5D + d1 / 4.0D);
                double d5 = pPlayer.getZ(d2 / 4.0D);
                pPlayer.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, false, d3, d4, d5, d0, d1 + 0.2D, d2);
                pPlayer.level().addParticle(ParticleMod.CONFETTI_PARTICLE.get(), false, d3, d4, d5, d0 + random.nextFloat() * 2.0F - 1.0F, d1 + 0.2D + random.nextFloat() * 2.0F - 1.0F, d2 + random.nextFloat() * 2.0F - 1.0F);
            }
        }

        pPlayer.playSound(SoundMod.HONESTY_TOTEM_USE.get());
        pPlayer.playSound(SoundEvents.TOTEM_USE);

        if ((!pPlayer.getAbilities().instabuild)){
            itemStack.shrink(1);
        }
        pPlayer.addEffect(new MobEffectInstance(MobEffects.JUMP, 360, 2));
        pPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 360, 2));

        return InteractionResultHolder.success(itemStack);

    }
}
