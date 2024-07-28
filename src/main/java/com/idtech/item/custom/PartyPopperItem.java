package com.idtech.item.custom;

import com.idtech.particle.ParticleMod;
import com.idtech.sound.SoundMod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PartyPopperItem extends Item {
    public PartyPopperItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        Vec3 lookVec = pPlayer.getLookAngle();
        pPlayer.playSound(SoundMod.PARTY_POPPER.get(), 1.0f, 1.0f + (0.5f-pPlayer.getRandom().nextFloat())/10.0f);
        if (pLevel.isClientSide()) {
            for (int i = 0; i < 32; i++) {
                Vec3 shootVec = lookVec.add(new Vec3((0.5f - pPlayer.getRandom().nextFloat()), (0.5f - pPlayer.getRandom().nextFloat()), (0.5f - pPlayer.getRandom().nextFloat()))).normalize();
                float power = pPlayer.getRandom().nextFloat() * 3.0f + 3.0f;
                pLevel.addParticle(ParticleMod.CONFETTI_PARTICLE.get(), pPlayer.getX() + shootVec.scale(0.5).x, pPlayer.getY() + pPlayer.getBbHeight()/2.0f + shootVec.scale(0.5).y, + pPlayer.getZ() + shootVec.scale(0.5).z, shootVec.scale(power).x, shootVec.scale(power).y, shootVec.scale(power).z);
            }
        }
        if ((!pPlayer.getAbilities().instabuild)){
            itemStack.shrink(1);
        }
        return InteractionResultHolder.success(itemStack);
    }
}
