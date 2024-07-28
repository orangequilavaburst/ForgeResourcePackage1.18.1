package com.idtech.entity.custom;

import com.idtech.entity.EntityMod;
import com.idtech.particle.ParticleMod;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.awt.*;
import java.util.Random;

public class MeowmereProjectileEntity extends ThrowableProjectile {

    private int life;
    public int lifeTime = 60;
    private Vec3 bounceVector = Vec3.ZERO;

    private static final EntityDataAccessor<CatVariant> DATA_VARIANT_ID = SynchedEntityData.defineId(MeowmereProjectileEntity.class, EntityDataSerializers.CAT_VARIANT);

    public MeowmereProjectileEntity(EntityType<? extends MeowmereProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.life = 0;
    }

    public MeowmereProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(EntityMod.MEOWMERE_PROJECTILE_ENTITY.get(), pShooter, pLevel);
        this.life = 0;
    }

    public MeowmereProjectileEntity(Level pLevel, double pX, double pY, double pZ) {
        super(EntityMod.MEOWMERE_PROJECTILE_ENTITY.get(), pX, pY, pZ, pLevel);
        this.life = 0;
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        bounceVector = getReflectionVector(this.getDeltaMovement(), hitResult.getDirection()).scale(0.75);
        if (bounceVector.length() > 0.25f) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), this.level().getBlockState(hitResult.getBlockPos()).getSoundType().getHitSound(), SoundSource.NEUTRAL, 0.5f, 1f);
        }
        this.setDeltaMovement(bounceVector);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()){

            float dist = (float) this.getPosition(1.0f).subtract(this.getPosition(0.0f)).length();

            for (float i = 0.0f; i < dist; i += 0.05f) {
                int rgb = Color.HSBtoRGB(Mth.lerp((float) (this.life * 2 % this.lifeTime) / (float) this.lifeTime, 0.0f, 1.0f), 0.9f, 1.0f);
                Vec3 pos = new Vec3(
                        Mth.lerp(i/dist, this.getPosition(0.0f).x(), this.getPosition(1.0f).x()),
                        Mth.lerp(i/dist, this.getPosition(0.0f).y(), this.getPosition(1.0f).y()),
                        Mth.lerp(i/dist, this.getPosition(0.0f).z(), this.getPosition(1.0f).z())
                );
                this.level().addParticle(new DustParticleOptions(new Vector3f((float) ((rgb >> 16) & 0xFF) / 255.0f, (float) ((rgb >> 8) & 0xFF) / 255.0f, (float) (rgb & 0xFF) / 255.0f), 1.0f), pos.x, pos.y, pos.z, 0.0f, 0.0f, 0.0f);
            }

        }

        //this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.5f, this.getZ(), 0.0D, 0.0D, 0.0D);
        this.setDeltaMovement(this.getDeltaMovement().x * 0.95f, this.getDeltaMovement().y, this.getDeltaMovement().z * 0.95f);
        ++this.life;
        if (this.life >= this.lifeTime) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (pResult.getEntity() instanceof LivingEntity pTarget) {
            pTarget.hurt(this.damageSources().mobProjectile((Entity) this, (LivingEntity) this.getOwner()), 5.0f);
        }
        this.discard();
    }

    private Vec3 getReflectionVector(Vec3 deltaMovement, Direction dir) {
        Vec3 normalVector = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
        Vec3 normalizedProduct = normalVector.scale(2 * deltaMovement.dot(normalVector));
        return deltaMovement.subtract(normalizedProduct);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("variant", BuiltInRegistries.CAT_VARIANT.getKey(this.getVariant()).toString());
        pCompound.putShort("life", (short) this.life);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        CatVariant catvariant = BuiltInRegistries.CAT_VARIANT.get(ResourceLocation.tryParse(pCompound.getString("variant")));
        if (catvariant != null) {
            this.setVariant(catvariant);
        }
        this.life = pCompound.getShort("life");
    }

    public CatVariant getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(CatVariant pVariant) {
        this.entityData.set(DATA_VARIANT_ID, pVariant);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_VARIANT_ID, BuiltInRegistries.CAT_VARIANT.getOrThrow(CatVariant.BLACK));
    }

}
