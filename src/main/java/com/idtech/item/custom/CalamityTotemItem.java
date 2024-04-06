package com.idtech.item.custom;

import com.idtech.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class CalamityTotemItem extends Item {
    public CalamityTotemItem(Properties pProperties) {
        super(pProperties.durability(5));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        HitResult hit = ProjectileUtil.getHitResultOnViewVector(pPlayer, (entity -> {
            return !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity;
        }), 20.0D);
        if (hit.getType() != HitResult.Type.MISS) {

            Vec3 hitSpot = hit.getLocation();
            BlockPos hitBlockSpot = new BlockPos((int)hitSpot.x, (int)hitSpot.y, (int)hitSpot.z);

            Utils.strikeLightning(pLevel, hitBlockSpot);
            Utils.createExplosion(pLevel, hitBlockSpot, 2.0f);

            int count = 32;
            Random random = new Random();

            for (int i = 0; i < count; ++i) {
                double d0 = random.nextFloat() * 2.0F - 1.0F;
                double d1 = random.nextFloat() * 2.0F - 1.0F;
                double d2 = random.nextFloat() * 2.0F - 1.0F;
                if (!(d0 * d0 + d1 * d1 + d2 * d2 > 1.0D)) {
                    double d3 = pPlayer.getX(d0 / 4.0D);
                    double d4 = pPlayer.getY(0.5D + d1 / 4.0D);
                    double d5 = pPlayer.getZ(d2 / 4.0D);
                    pPlayer.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, false, d3, d4, d5, d0, d1 + 0.2D, d2);
                }
            }

            pPlayer.playSound(SoundEvents.TOTEM_USE);

            if ((!pPlayer.getAbilities().instabuild)) {

                if (this.getDamage(itemStack) >= this.getMaxDamage(itemStack) - 1) {

                    Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack.copy());

                }

                itemStack.hurtAndBreak(1, pPlayer, (p_40992_) -> {
                    p_40992_.broadcastBreakEvent(pUsedHand);
                });

            }

            return InteractionResultHolder.success(itemStack);

        }

        return InteractionResultHolder.fail(itemStack);

    }
}
