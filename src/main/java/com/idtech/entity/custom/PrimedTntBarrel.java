package com.idtech.entity.custom;

import com.idtech.entity.EntityMod;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PrimedTntBarrel extends PrimedTnt {

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedTntBarrel.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_DIRECTION_ID = SynchedEntityData.defineId(PrimedTntBarrel.class, EntityDataSerializers.INT);
    private static final int DEFAULT_FUSE_TIME = 80;

    @javax.annotation.Nullable
    private LivingEntity owner;

    public PrimedTntBarrel(EntityType<? extends PrimedTntBarrel> pEntityType, Level pLevel) {
        super(EntityMod.PRIMED_TNT_BARREL.get(), pLevel);
        this.blocksBuilding = true;
    }

    public PrimedTntBarrel(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        super(EntityMod.PRIMED_TNT_BARREL.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    /**
     * Returns {@code true} if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return !this.isRemoved();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        /*
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }
        */

        Vec3 dir = new Vec3(this.getDirection().getNormal().getX(), this.getDirection().getNormal().getY(), this.getDirection().getNormal().getZ());
        this.setDeltaMovement(this.getDeltaMovement().add(dir.scale(0.1)));

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FUSE_ID, this.getFuse());
        this.entityData.define(DATA_DIRECTION_ID, this.getDir());
    }

    protected void explode() {
        float f = 4.0F;
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
    }
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        pCompound.putShort("Fuse", (short)this.getFuse());
        pCompound.putShort("Direction", (short)this.getDir());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFuse(pCompound.getShort("Fuse"));
        this.setDir(pCompound.getShort("Direction"));
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @javax.annotation.Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.15F;
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    public void setDir(int dir) {
        this.entityData.set(DATA_DIRECTION_ID, dir);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getDir() {
        return this.entityData.get(DATA_DIRECTION_ID);
    }

    public Direction getDirection(){
        return Direction.from3DDataValue(this.getDir());
    }

}
