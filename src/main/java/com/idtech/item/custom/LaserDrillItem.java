package com.idtech.item.custom;

import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class LaserDrillItem extends PickaxeItem {

    private final float miningRange;

    public LaserDrillItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, float miningRange) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.miningRange = miningRange;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        BlockHitResult hit = pLevel.clip(new ClipContext(pPlayer.getEyePosition(), pPlayer.position().add(pPlayer.getLookAngle().scale(miningRange)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pPlayer));
        if (hit.getType() != HitResult.Type.MISS){

            BlockPos blockPos = hit.getBlockPos();
            BlockState blockState = pLevel.getBlockState(blockPos);
            //blockState.getDestroyProgress(pPlayer, pLevel, hit.getBlockPos());
            blockState.attack(pLevel, blockPos, pPlayer);

            pLevel.addParticle(ParticleTypes.FLAME, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 0.0f, 0.0f, 0.0f);

            return InteractionResultHolder.success(itemStack);

        }

        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 12000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }
}
