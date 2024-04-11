package com.idtech.entity.custom;

import com.idtech.entity.EntityMod;
import com.idtech.item.ItemMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class HyperBombEntity extends ThrowableItemProjectile {

    private int life;
    public int lifeTime = 60;
    private Vec3 bounceVector = Vec3.ZERO;

    public HyperBombEntity(EntityType<? extends HyperBombEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.life = 0;
    }

    public HyperBombEntity(Level pLevel, LivingEntity pShooter) {
        super(EntityMod.HYPER_BOMB_ENTITY.get(), pShooter, pLevel);
        this.life = 0;
    }

    public HyperBombEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityMod.HYPER_BOMB_ENTITY.get(), pX, pY, pZ, pLevel);
        this.life = 0;
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        bounceVector = getReflectionVector(this.getDeltaMovement(), hitResult.getDirection()).scale(0.875);
        if (bounceVector.length() > 0.25f) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), this.level().getBlockState(hitResult.getBlockPos()).getSoundType().getHitSound(), SoundSource.NEUTRAL, 1f, 1f);
        }
        this.setDeltaMovement(bounceVector);
    }

    @Override
    public void tick() {
        super.tick();
        //this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.5f, this.getZ(), 0.0D, 0.0D, 0.0D);
        this.setDeltaMovement(this.getDeltaMovement().x * 0.95f, this.getDeltaMovement().y, this.getDeltaMovement().z * 0.95f);
        ++this.life;
        if (this.life >= this.lifeTime) {
            this.discard();
            this.explode();
        }
    }

    protected void explode() {
        float f = 4.0F;
        if (!this.level().isClientSide()) {
            this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        this.discard();
        this.explode();
    }

    private Vec3 getReflectionVector(Vec3 deltaMovement, Direction dir) {
        Vec3 normalVector = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
        Vec3 normalizedProduct = normalVector.scale(2 * deltaMovement.dot(normalVector));
        return deltaMovement.subtract(normalizedProduct);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemMod.HYPER_BOMB.get();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putShort("life", (short) this.life);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.life = pCompound.getShort("life");
    }

}
