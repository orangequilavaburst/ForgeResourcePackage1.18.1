package com.idtech.item.custom;

import com.idtech.BaseMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FireballWandItem extends Item {
    public FireballWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        Vec3 pos = pPlayer.getEyePosition(1.0f);
        Vec3 lookAngle = pPlayer.getLookAngle();
        float speed = 3.0f;

        LargeFireball fireball = new LargeFireball(pLevel, pPlayer, lookAngle.x, lookAngle.y, lookAngle.z, 3);

        fireball.setPos(pos);
        fireball.setDeltaMovement(lookAngle.multiply(speed, speed, speed));

        // Thanks Random on Discord

        double finalP = speed/19;
        double pRatio = finalP / 0.1; // power magnitude is set to 0.1 in AbstractHurtingProjectile constructor

        Vec3 pVec = lookAngle.scale(finalP);
        fireball.xPower = pVec.x;
        fireball.yPower = pVec.y;
        fireball.zPower = pVec.z;
        fireball.setOwner(pPlayer);

        if (pos != null){
            pLevel.addFreshEntity(fireball);
            pPlayer.playSound(SoundEvents.BLAZE_SHOOT);
            return InteractionResultHolder.success(itemStack);
        }
        else{
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        String tipItemName = pStack.getItem().toString();
        tipItemName = "tooltip." + BaseMod.MODID + "." + tipItemName;
        pTooltipComponents.add(Component.translatable(tipItemName + ".prefix")
                        .append(" ")
                        .append(Minecraft.getInstance().options.keyUse.getKey().getDisplayName())
                        .append(" ")
                        .append(Component.translatable(tipItemName + ".suffix")).withStyle(ChatFormatting.GRAY));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
