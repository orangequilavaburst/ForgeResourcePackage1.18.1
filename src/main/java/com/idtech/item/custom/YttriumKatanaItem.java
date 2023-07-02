package com.idtech.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class YttriumKatanaItem extends SwordItem {
    public YttriumKatanaItem(Properties pProperties) {
        super(Tiers.IRON, 1, 1.0f, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        if (!pPlayer.getCooldowns().isOnCooldown(itemStack.getItem())) {

            pPlayer.awardStat(Stats.ITEM_USED.get(this));

            float f7 = pPlayer.getYRot();
            float f = pPlayer.getXRot();
            float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
            float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
            float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
            float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
            float f5 = 3.0F;
            f1 *= f5 / f4;
            f2 *= f5 / f4;
            f3 *= f5 / f4;
            pPlayer.push((double)f1, (double)f2, (double)f3);
            pPlayer.startAutoSpinAttack(5);
            if (pPlayer.onGround()) {
                float f6 = 1.1999999F;
                pPlayer.move(MoverType.SELF, new Vec3(0.0D, (double)1.1999999F, 0.0D));
            }

            SoundEvent soundevent;
            soundevent = SoundEvents.TRIDENT_RIPTIDE_1;

            pLevel.playSound((Player)null, pPlayer, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);

            pPlayer.getCooldowns().addCooldown(itemStack.getItem(), 60);

        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
