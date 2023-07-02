package com.idtech.item.custom;

import com.idtech.sound.SoundMod;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class FazerBlasterItem extends Item {

    private int maxAttacks = 5;
    private String attacksTagName = "attacks";

    public FazerBlasterItem(Properties pProperties) {
        super(pProperties);
    }

    public FazerBlasterItem(Properties pProperties, int uses) {
        super(pProperties);
        this.maxAttacks = uses;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return getAttacks(pStack) > 0;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0xFFCC47;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)(this.maxAttacks - getAttacks(pStack)) * 13.0F / (float)this.maxAttacks);
    }

    public void setAttacks(ItemStack pStack, int num){
        pStack.getOrCreateTagElement(attacksTagName).putInt(attacksTagName, num);
    }

    public int getAttacks(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement(this.attacksTagName);
        return compoundtag != null && compoundtag.contains(this.attacksTagName, 99) ? compoundtag.getInt(this.attacksTagName) : 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        if (!pPlayer.getCooldowns().isOnCooldown(itemStack.getItem())) {

            pPlayer.awardStat(Stats.ITEM_USED.get(this));

            pPlayer.playSound(SoundMod.FAZER_BLASTER.get());

            if (!pPlayer.getAbilities().instabuild) {
                setAttacks(itemStack, getAttacks(itemStack) + 1);
                if (getAttacks(itemStack) >= this.maxAttacks) {
                    setAttacks(itemStack, 0);
                    pPlayer.getCooldowns().addCooldown(itemStack.getItem(), 120);
                }
            }

            HitResult hit = ProjectileUtil.getHitResultOnViewVector(pPlayer, (entity -> {
                return !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity;
            }), 20.0D);
            if (hit.getType() != HitResult.Type.MISS){

                Vec3 hitSpot = hit.getLocation();
                Vec3 origin = pPlayer.getEyePosition(1.0f).add(0.0f, -0.5, 0.0);
                Random random = new Random();

                if (hit.getType() == HitResult.Type.ENTITY){

                    LivingEntity target = (LivingEntity) ((EntityHitResult)hit).getEntity();
                    target.hurt(target.damageSources().mobAttack(pPlayer), 4.0f);
                    target.knockback(0.25, -pPlayer.getViewVector(1.0f).x(), -pPlayer.getViewVector(1.0f).z());

                    hitSpot = target.getBoundingBox().getCenter();

                }

                if (pLevel.isClientSide) {

                    for (float i = 0; i < origin.distanceTo(hitSpot); i += 0.1f) {

                        Vec3 newPos = origin.lerp(hitSpot, i / origin.distanceTo(hitSpot));
                        pLevel.addParticle(ParticleTypes.GLOW_SQUID_INK, newPos.x(), newPos.y(), newPos.z(), 0.0f, 0.0f, 0.0f);

                    }

                    for (int i = 0; i < 10; i++){

                        pLevel.addParticle(ParticleTypes.GLOW_SQUID_INK, hitSpot.x(), hitSpot.y(), hitSpot.z(), random.nextFloat(-0.25f, 0.25f), random.nextFloat(-0.25f, 0.25f), random.nextFloat(-0.25f, 0.25f));

                    }

                }

            }
            else{

                Vec3 hitSpot = pPlayer.getEyePosition(1.0f).add(pPlayer.getViewVector(1.0f).scale(20.0d));
                Vec3 origin = pPlayer.getEyePosition(1.0f).add(0.0f, -0.5, 0.0);

                for (float i = 0; i < origin.distanceTo(hitSpot); i += 0.1f) {

                    Vec3 newPos = origin.lerp(hitSpot, i / origin.distanceTo(hitSpot));
                    pLevel.addParticle(ParticleTypes.GLOW_SQUID_INK, newPos.x(), newPos.y(), newPos.z(), 0.0f, 0.0f, 0.0f);

                }

                return InteractionResultHolder.success(itemStack);

            }

            return InteractionResultHolder.success(itemStack);

        }

        return InteractionResultHolder.fail(itemStack);

    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }
}
