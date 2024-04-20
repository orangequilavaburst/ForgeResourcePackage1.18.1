package com.idtech.item.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class LaserDrillItem extends Item {

    private final float miningRange;
    private int lastSentState;
    private BlockPos blockMiningPos;

    public LaserDrillItem(Properties pProperties, float miningRange) {
        super(pProperties.stacksTo(1));
        this.miningRange = miningRange;
        this.lastSentState = 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        BlockHitResult hit = pLevel.clip(new ClipContext(pPlayer.getEyePosition(), pPlayer.getEyePosition().add(pPlayer.getLookAngle().scale(miningRange)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pPlayer));
        if (hit.getType() != HitResult.Type.MISS){

            BlockPos blockPos = hit.getBlockPos();
            BlockState blockState = pLevel.getBlockState(blockPos);

            pLevel.addParticle(ParticleTypes.FLAME, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 0.0f, 0.0f, 0.0f);

            this.blockMiningPos = blockPos;

            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemStack);

        }

        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player pPlayer) {
            pLevel.destroyBlockProgress(pLivingEntity.getId(), this.blockMiningPos, -1);
            this.lastSentState = 0;
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {

        if (pLivingEntity instanceof Player pPlayer) {
            BlockHitResult hit = pLevel.clip(new ClipContext(pPlayer.getEyePosition(), pPlayer.getEyePosition().add(pPlayer.getLookAngle().scale(miningRange)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pPlayer));
            if (hit.getType() != HitResult.Type.MISS) {

                BlockPos blockPos = hit.getBlockPos();
                BlockState blockState = pLevel.getBlockState(blockPos);
                //blockState.getDestroyProgress(pPlayer, pLevel, hit.getBlockPos());
                if (pPlayer.getAbilities().instabuild) {
                    pLevel.destroyBlock(blockPos, true, pLivingEntity);
                }
                else{
                    if (this.blockMiningPos.equals(blockPos)) {
                        int t = pStack.getUseDuration() - pRemainingUseDuration;
                        this.incrementDestroyProgress(blockState, blockPos, t, pPlayer);
                        float f1 = blockState.getDestroyProgress(pPlayer, pLevel, this.blockMiningPos);
                        if (f1 >= 0.7F) {
                            pLevel.destroyBlockProgress(pPlayer.getId(), this.blockMiningPos, -1);
                            pLevel.destroyBlock(this.blockMiningPos, true, pPlayer);
                            return;
                        }
                        pPlayer.displayClientMessage(Component.literal(String.format("Block progress: %d", this.lastSentState)), true);
                    }
                    else{
                        pLevel.destroyBlockProgress(pPlayer.getId(), this.blockMiningPos, -1);
                        this.lastSentState = 0;
                        pPlayer.displayClientMessage(Component.literal("Missed block!"), true);
                        this.use(pLevel, pPlayer, pPlayer.getUsedItemHand());
                    }
                }

                pLevel.addParticle(ParticleTypes.FLAME, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 0.0f, 0.0f, 0.0f);

            }
        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 120000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }

    private float incrementDestroyProgress(BlockState pState, BlockPos pPos, int pUseDuration, Player pPlayer) {
        int i = pUseDuration;
        float f = pState.getDestroyProgress(pPlayer, pPlayer.level(), pPos) * (float)(i + 1);
        int j = (int)(f * 10.0F);
        if (j != this.lastSentState) {
            pPlayer.level().destroyBlockProgress(pPlayer.getId(), pPos, j);
            this.lastSentState = j;
        }

        return f;
    }

}
