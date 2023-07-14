package com.idtech.item.custom;

import com.idtech.BaseMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.stream.Collectors;

public class DetonatorItem extends Item {
    public DetonatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        List<BlockPos> blocks = BlockPos.betweenClosedStream(
                pPlayer.getBoundingBox().inflate(10.0))
                .filter( (bs) -> pLevel.getBlockState(bs).is(Blocks.TNT))
                .map(BlockPos::immutable)
                .collect(Collectors.toList());
        if (blocks.size() > 0){
            for (BlockPos bp : blocks){
                pLevel.removeBlock(bp, false);
                pLevel.explode(pPlayer, bp.getX(), bp.getY(), bp.getZ(), 3.0f, Level.ExplosionInteraction.TNT);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(stack);
    }
}
