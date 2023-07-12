package com.idtech.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ZombieConverterItem extends Item {
    public ZombieConverterItem(Properties pProperties, int uses) {
        super(pProperties.defaultDurability(uses));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof Villager villager){

            Level level = villager.level();
            ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
            zombie.setPos(villager.position());
            zombie.setXRot(villager.getXRot());
            zombie.setYRot(villager.getYRot());
            zombie.setYHeadRot(villager.getYHeadRot());
            zombie.setVillagerData(villager.getVillagerData());

            level.addFreshEntity(zombie);
            if (level.isClientSide()){

                int particleAmount = 5;
                Random random = new Random();
                float speed = 0.25f;

                for (int i = 0; i < 5; i++){

                    level.addParticle(ParticleTypes.SOUL,
                            villager.getBoundingBox().getCenter().x(), villager.getBoundingBox().getCenter().y(), villager.getBoundingBox().getCenter().z(),
                            random.nextFloat(-speed, speed), random.nextFloat(-speed, speed), random.nextFloat(-speed, speed));
                    level.addParticle(ParticleTypes.SMOKE,
                            villager.getBoundingBox().getCenter().x(), villager.getBoundingBox().getCenter().y(), villager.getBoundingBox().getCenter().z(),
                            random.nextFloat(-speed, speed), random.nextFloat(-speed, speed), random.nextFloat(-speed, speed));

                }

            }

            villager.remove(Entity.RemovalReason.DISCARDED);
            level.addFreshEntity(zombie);
            zombie.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED);
            pStack.hurtAndBreak(1, pPlayer, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(pUsedHand);
            });

            return InteractionResult.SUCCESS;

        }
        else if (pInteractionTarget.isPickable() && !pInteractionTarget.isSpectator()){

            Random random = new Random();
            int amount = random.nextInt(8, 16);

            for (int i = 0; i < amount; i++){
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, pInteractionTarget.level());
                itemEntity.setItem(new ItemStack(Items.IRON_INGOT));
                itemEntity.setPos(pInteractionTarget.getPosition(1.0f));
                itemEntity.setDeltaMovement(random.nextFloat(-0.25f, 0.25f), random.nextFloat(0.25f, 0.75f), random.nextFloat(-0.25f, 0.25f));
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().scale(1.0/3.0));
                pInteractionTarget.level().addFreshEntity(itemEntity);
            }

            pInteractionTarget.playSound(SoundEvents.ITEM_BREAK);
            pInteractionTarget.kill();

            pStack.hurtAndBreak(1, pPlayer, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(pUsedHand);
            });

            return InteractionResult.SUCCESS;

        }
        return InteractionResult.FAIL;
    }
}
