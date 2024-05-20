package com.idtech.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class LaserDrillItem extends Item{

    // look at BundleItem for storing tools

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

        if (!pLevel.isClientSide()) {
            if (pLivingEntity instanceof ServerPlayer serverPlayer) {

                serverPlayer.connection.send(new ClientboundBlockDestructionPacket(serverPlayer.getId(), this.blockMiningPos, -1));

            }
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
                } else {
                    if (this.blockMiningPos.equals(blockPos)) {
                        pPlayer.displayClientMessage(Component.literal(String.format("Looking at: ")).append(Component.translatable(pLevel.getBlockState(this.blockMiningPos).getBlock().getDescriptionId())), true);

                    } else {
                        pPlayer.displayClientMessage(Component.literal("Missed block!"), true);

                        this.use(pLevel, pPlayer, pPlayer.getUsedItemHand());
                        return;
                    }
                }

                pLevel.addParticle(ParticleTypes.FLAME, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, 0.0f, 0.0f, 0.0f);
                Vec3 hitNormal = new Vec3(hit.getDirection().getNormal().getX(), hit.getDirection().getNormal().getY(), hit.getDirection().getNormal().getZ());
                pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, pLevel.getBlockState(this.blockMiningPos)), hit.getLocation().x, hit.getLocation().y, hit.getLocation().z, hitNormal.x(), hitNormal.y(), hitNormal.z());

            }
            else{
                pPlayer.displayClientMessage(Component.literal("You're not hitting anything!"), true);
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
        return UseAnim.BOW;
    }

}
