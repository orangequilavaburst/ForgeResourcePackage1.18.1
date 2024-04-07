package com.idtech.entity.custom;

import com.idtech.entity.EntityMod;
import com.idtech.item.ItemMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class DevilsknifeEntity extends AbstractArrow {
    private static final EntityDataAccessor<String> OWNER = SynchedEntityData.defineId(DevilsknifeEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(DevilsknifeEntity.class, EntityDataSerializers.BOOLEAN);
    private static ItemStack DEFAULT_ARROW_STACK = new ItemStack(ItemMod.DEVILSKNIFE.get());
    private static final float MAX_SPEED = 15.0f;
    private static final float ACCELERATION = 0.5f;
    private Vec3 bounceVector = Vec3.ZERO;
    public int clientSideDevilsknifeTickCount;
    private boolean dealtDamage;

    public float rotationStart;
    private float rotationSpeed;
    private final float ROTATION_SPEED = -30.0f;
    private int bounces = 0;
    private static final int MAX_BOUNCES = 1;

    public DevilsknifeEntity(Level p_36722_, LivingEntity p_312718_, ItemStack p_309639_) {
        super(EntityMod.DEVILSKNIFE_ENTITY.get(), p_312718_, p_36722_);
        this.entityData.set(ID_FOIL, p_309639_.hasFoil());
        setOwnerUUID(p_312718_.getStringUUID());
        this.rotationStart = 0.0f;
        this.rotationSpeed = ROTATION_SPEED;
    }

    public DevilsknifeEntity(EntityType<DevilsknifeEntity> devilsknifeEntityEntityType, Level level) {
        super(EntityMod.DEVILSKNIFE_ENTITY.get(), level);
        this.rotationStart = 0.0f;
        this.rotationSpeed = ROTATION_SPEED;

    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if (entity != null && (this.dealtDamage || this.isNoPhysics())) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY(), this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(ACCELERATION)));
                this.setDeltaMovement(this.getDeltaMovement().normalize().scale(Math.min(MAX_SPEED, this.getDeltaMovement().length())));

                ++this.clientSideDevilsknifeTickCount;
            }
        }

        super.tick();
        this.rotationStart += this.rotationSpeed;
        this.updateRotation();

    }

    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        bounceVector = getReflectionVector(this.getDeltaMovement(), hitResult.getDirection());
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), this.level().getBlockState(hitResult.getBlockPos()).getSoundType().getHitSound(), SoundSource.NEUTRAL, 1f, 1f);

        this.setDeltaMovement(bounceVector.scale(0.9));
        this.updateRotation();

        if (!this.isNoPhysics()){

            if (this.bounces < this.MAX_BOUNCES){
                this.bounces++;
            }
            else {
                this.setNoPhysics(true);
            }

        }

    }

    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity livingentity) {
            f += EnchantmentHelper.getDamageBonus(this.getPickupItemStackOrigin(), livingentity.getMobType());
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, entity1 == null ? this : entity1);
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
            }
        } /*else if (entity.getType().is(EntityTypeTags.)) {
            this.deflect();
            return;
        }*/

        this.playSound(soundevent, 1.0f, 1.0F);
    }

    @Override
    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float)((Mth.atan2(vec3.x, vec3.z)) * (double)(180F / (float)Math.PI))));

        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    private Vec3 getReflectionVector(Vec3 deltaMovement, Direction dir) {
        Vec3 normalVector = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
        Vec3 normalizedProduct = normalVector.scale(2 * deltaMovement.dot(normalVector));
        return deltaMovement.subtract(normalizedProduct);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
        this.entityData.define(OWNER, (this.getOwner() != null) ? this.getOwner().getStringUUID() : "");
    }

    public void readAdditionalSaveData(CompoundTag p_37578_) {
        super.readAdditionalSaveData(p_37578_);
        this.dealtDamage = p_37578_.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag p_37582_) {
        super.addAdditionalSaveData(p_37582_);
        p_37582_.putBoolean("DealtDamage", this.dealtDamage);
    }


    // thx SSKirilSS
    public String getOwnerUUID() {
        return this.getEntityData().get(OWNER);
    }

    public void setOwnerUUID(String uuid) {
        this.getEntityData().set(OWNER, uuid);
    }

    public void playerTouch(Player p_37580_) {
        if (this.ownedBy(p_37580_) || this.getOwner() == null) {
            super.playerTouch(p_37580_);
        }

    }

    protected float getWaterInertia() {
        return 1.0F;
    }

    public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
        return true;
    }

    public ItemStack getPickupItemStackOrigin() {
        return this.getPickupItem();
    }

    @Override
    public ItemStack getPickupItem() {
        return DEFAULT_ARROW_STACK;
    }
}
