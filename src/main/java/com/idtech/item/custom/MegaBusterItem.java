package com.idtech.item.custom;

import com.idtech.sound.SoundMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.phys.Vec3;

public class MegaBusterItem extends Item {
    public MegaBusterItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        Vec3 pos = pPlayer.getEyePosition(1.0f);
        Vec3 lookAngle = pPlayer.getLookAngle();
        float speed = 1.5f;

        Snowball proj = new Snowball(EntityType.SNOWBALL, pLevel);
        proj.setPos(pos);
        proj.setDeltaMovement(lookAngle.multiply(speed, speed, speed));
        proj.setOwner(pPlayer);

        pLevel.addFreshEntity(proj);
        pPlayer.playSound(SoundMod.MEGA_BUSTER_SHOT.get());

        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {

        if (pLivingEntity instanceof Player pPlayer) {

            int timer = this.getUseDuration(pStack) - pRemainingUseDuration;

            if (timer == 10) {

                pPlayer.playSound(SoundMod.MEGA_BUSTER_CHARGING.get());

            } else if (timer == 28) {

                Minecraft.getInstance().getSoundManager().stop(SoundMod.MEGA_BUSTER_CHARGING.getId(), pPlayer.getSoundSource());
                pPlayer.playSound(SoundMod.MEGA_BUSTER_CHARGED.get());

            }

        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player pPlayer) {

            Vec3 pos = pPlayer.getEyePosition(1.0f);
            Vec3 lookAngle = pPlayer.getLookAngle();
            float speed = 1.5f;

            int timer = this.getUseDuration(pStack) - pTimeLeft;

            if (timer >= 28) {

                speed = 3.0f;
                Snowball proj = new Snowball(EntityType.SNOWBALL, pLevel);
                proj.setPos(pos);
                proj.setDeltaMovement(lookAngle.multiply(speed, speed, speed));
                proj.setOwner(pPlayer);

                Minecraft.getInstance().getSoundManager().stop(SoundMod.MEGA_BUSTER_CHARGED.getId(), pPlayer.getSoundSource());
                pPlayer.playSound(SoundMod.MEGA_BUSTER_CHARGED_SHOT.get());

                pLevel.addFreshEntity(proj);

            }
            else if (timer >= 10){

                speed = 2.0f;
                Snowball proj = new Snowball(EntityType.SNOWBALL, pLevel);
                proj.setPos(pos);
                proj.setDeltaMovement(lookAngle.multiply(speed, speed, speed));
                proj.setOwner(pPlayer);

                Minecraft.getInstance().getSoundManager().stop(SoundMod.MEGA_BUSTER_CHARGING.getId(), pPlayer.getSoundSource());
                pPlayer.playSound(SoundMod.MEGA_BUSTER_HALF_CHARGED_SHOT.get());

                pLevel.addFreshEntity(proj);

            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));

        }
    }

}
