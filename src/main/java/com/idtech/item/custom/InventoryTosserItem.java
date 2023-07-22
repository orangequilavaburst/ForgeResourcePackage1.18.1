package com.idtech.item.custom;

import com.idtech.entity.EntityMod;
import com.idtech.entity.custom.BlockProjectileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class InventoryTosserItem extends Item {
    public InventoryTosserItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        Vec3 pos = pPlayer.getEyePosition(1.0f);
        Vec3 lookAngle = pPlayer.getLookAngle();
        float speed = 1.5f;

        Random RNG = new Random();
        List<ItemStack> goodItems = pPlayer.inventoryMenu.getItems().stream().filter(item -> !item.isEmpty()).toList();
        int rand = RNG.nextInt(goodItems.size());
        ItemStack projectileItem = goodItems.get(rand);
        //BaseMod.LOGGER.info(projectileItem.getItem().toString());
        BlockProjectileEntity bpe = new BlockProjectileEntity(EntityMod.BLOCK_PROJECTILE_ENTITY.get(), pLevel);
        bpe.setPos(pos);
        bpe.setStartPos(pos);
        bpe.setDeltaMovement(lookAngle.multiply(speed, speed, speed));
        bpe.setXRot(pPlayer.getViewXRot(1.0f));
        bpe.setYRot(pPlayer.getViewYRot(1.0f));
        bpe.setOwner(pPlayer);
        bpe.setItem(projectileItem);


        if (!pPlayer.getAbilities().instabuild && !pLevel.isClientSide()) {
            pLevel.addFreshEntity(bpe);
            goodItems.get(rand).shrink(1);
        }

        return InteractionResultHolder.success(itemStack);
    }
}
