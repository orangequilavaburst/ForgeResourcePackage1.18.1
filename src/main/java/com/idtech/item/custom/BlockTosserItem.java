package com.idtech.item.custom;

import com.idtech.BaseMod;
import com.idtech.entity.EntityMod;
import com.idtech.entity.custom.BlockProjectileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class BlockTosserItem extends Item {
    public BlockTosserItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        Vec3 pos = pPlayer.getEyePosition(1.0f);
        Vec3 lookAngle = pPlayer.getLookAngle();
        float speed = 1.5f;

        Random RNG = new Random();
        List<Item> goodItems = BuiltInRegistries.ITEM.stream()
                //.filter((i) -> i instanceof BlockItem)
                .filter((i) -> !(i.equals(Items.BEDROCK) || i.equals(Items.AIR)))
                .toList();
        ItemStack projectileItem = new ItemStack(goodItems.get(RNG.nextInt(goodItems.size())));
        //BaseMod.LOGGER.info(projectileItem.getItem().toString());
        BlockProjectileEntity bpe = new BlockProjectileEntity(EntityMod.BLOCK_PROJECTILE_ENTITY.get(), pLevel);
        bpe.setPos(pos);
        bpe.setStartPos(pos);
        bpe.setDeltaMovement(lookAngle.multiply(speed, speed, speed));
        bpe.setXRot(pPlayer.getViewXRot(1.0f));
        bpe.setYRot(pPlayer.getViewYRot(1.0f));
        bpe.setOwner(pPlayer);
        bpe.setItem(projectileItem);

        pLevel.addFreshEntity(bpe);

        return InteractionResultHolder.success(itemStack);
    }
}
