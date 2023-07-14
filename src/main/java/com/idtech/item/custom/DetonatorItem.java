package com.idtech.item.custom;

import com.idtech.BaseMod;
import com.idtech.entity.custom.FriendlyCreeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.List;
import java.util.stream.Collectors;

public class DetonatorItem extends Item {
    public DetonatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        boolean success = false;

        List<BlockPos> blocks = BlockPos.betweenClosedStream(
                pPlayer.getBoundingBox().inflate(10.0))
                .filter( (bs) -> pLevel.getBlockState(bs).is(Blocks.TNT))
                .map(BlockPos::immutable)
                .collect(Collectors.toList());
        if (blocks.size() > 0){
            success = true;
            for (BlockPos bp : blocks){
                pLevel.removeBlock(bp, false);
                pLevel.explode(pPlayer, bp.getX(), bp.getY(), bp.getZ(), 3.0f, Level.ExplosionInteraction.TNT);
            }
        }

        List<LivingEntity> entities = pLevel.getEntities(EntityTypeTest.forClass(LivingEntity.class),
                pPlayer.getBoundingBox().inflate(10.0), (entity) -> {
                    return entity.isPickable() && !entity.isInWater() && (entity instanceof Creeper || entity instanceof FriendlyCreeperEntity);
                });
        if (entities.size() > 0){
            success = true;
            for (LivingEntity le : entities){
                pLevel.explode(pPlayer, le.getX(), le.getY(), le.getZ(), 3.0f, Level.ExplosionInteraction.TNT);
                le.remove(Entity.RemovalReason.DISCARDED);
            }
        }

        return (success) ? InteractionResultHolder.success(stack) : InteractionResultHolder.fail(stack);
    }
}
