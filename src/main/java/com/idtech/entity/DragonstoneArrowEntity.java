package com.idtech.entity;

import com.idtech.item.ItemMod;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;
import java.util.Objects;

public class DragonstoneArrowEntity extends AbstractArrow {

    public DragonstoneArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public DragonstoneArrowEntity(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    public DragonstoneArrowEntity(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemMod.DRAGONSTONE_ARROW.get());
    }


    /*
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide()) {
            for (int i = 0; i < 5; i++){
                Vec3 angle = this.getForward().scale(-1.0);
                Vec3 newAngle = angle.add(Mth.randomBetween(RandomSource.create(), -0.1f, 0.1f), Mth.randomBetween(RandomSource.create(), -0.1f, 0.1f), Mth.randomBetween(RandomSource.create(), -0.1f, 0.1f));
                Arrow newArrow = new Arrow(this.level(), this.position().x, this.position().y, this.position().z);
                newArrow.setPos(this.position().add(angle.scale(0.125f)));
                newArrow.addDeltaMovement(newAngle.scale(2.0f));
                newArrow.setOwner(this.getOwner());
                newArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                this.level().addFreshEntity(newArrow);
                this.level().playSound(null, this.blockPosition(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL);
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void tickDespawn() {
        super.tickDespawn();
        if (!this.level().isClientSide()) {
            if (this.inGroundTime < 10) {
                Vec3 angle = this.getForward().scale(-1.0);
                if (this.inGroundTime % 2 == 0) {

                    Vec3 newAngle = angle.add(Mth.randomBetween(RandomSource.create(), -0.1f, 0.1f), Mth.randomBetween(RandomSource.create(), -0.1f, 0.1f), Mth.randomBetween(RandomSource.create(), -0.1f, 0.1f));
                    Arrow newArrow = new Arrow(this.level(), this.position().x, this.position().y, this.position().z);
                    newArrow.setPos(this.position().add(angle.scale(0.125f)));
                    newArrow.addDeltaMovement(newAngle.scale(2.0f));
                    newArrow.setOwner(this.getOwner());
                    newArrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                    this.level().addFreshEntity(newArrow);
                    this.level().playSound(null, this.blockPosition(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL);

                }
            } else {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
    */

    @Override
    public void tick() {
        super.tick();

        if (!this.inGround) {

            // home into entities
            Level pLevel = this.level();
            LivingEntity target = null;
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(30.0));
            if (!list.isEmpty()) {

                // find nearest target
                for (LivingEntity livingentity : list) {

                    if (livingentity != target && livingentity != null) {

                        if (!livingentity.is(this.getOwner())) {

                            if (target == null){
                                target = livingentity;
                            }
                            else {
                                if (livingentity.distanceTo(this) < target.distanceTo(this)) {

                                    target = livingentity;

                                }
                            }

                        }

                    }

                }
            }

            if (target != null) {

                if (!target.is(this.getOwner())) {

                    /*if (pLevel.isClientSide) {

                        pLevel.addParticle(ParticleTypes.SMOKE,
                                target.getEyePosition().x(), target.getEyePosition().y() + 0.5, target.getEyePosition().z(),
                                Mth.randomBetween(RandomSource.create(), -0.05f, 0.05f),
                                Mth.randomBetween(RandomSource.create(), -0.05f, 0.05f),
                                Mth.randomBetween(RandomSource.create(), -0.05f, 0.05f));

                    }*/

                    // home in
                    Vec3 homingVector = target.getEyePosition().subtract(this.position());
                    Vec3 newVector = homingVector.normalize();

                    Vec3 a = this.getDeltaMovement().normalize();
                    Vec3 b = newVector;

                    float homingSpeed = 1.5f;
                    float lerpAmount = 0.01f;
                    Vec3 lerpVector = a.add(b.subtract(a.scale(lerpAmount)));
                    this.setDeltaMovement(lerpVector.normalize().scale(homingSpeed));

                }

            }

            // rotation code

            Vec3 vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(d5, d1) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(d6, d4) * (double)(180F / (float)Math.PI)));

        }

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
