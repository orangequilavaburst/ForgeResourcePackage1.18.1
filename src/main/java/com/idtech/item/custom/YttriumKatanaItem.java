package com.idtech.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class YttriumKatanaItem extends SwordItem {
    public YttriumKatanaItem(Properties pProperties) {
        super(Tiers.IRON, 1, 1.0f, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if (!pPlayer.getCooldowns().isOnCooldown(stack.getItem())) {
            pPlayer.awardStat(Stats.ITEM_USED.get(this));

            Vec3 look = pPlayer.getLookAngle().scale(3);

            pPlayer.push(look.z, look.y, look.z);
            pPlayer.startAutoSpinAttack(5);
            if (pPlayer.onGround()) {
                pPlayer.move(MoverType.SELF, new Vec3(0.0D, 1.1999999F, 0.0D));
            }

            pLevel.playSound(null, pPlayer, SoundEvents.TRIDENT_RIPTIDE_1, SoundSource.PLAYERS, 1.0F, 1.0F);

            pPlayer.getCooldowns().addCooldown(stack.getItem(), 60);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
