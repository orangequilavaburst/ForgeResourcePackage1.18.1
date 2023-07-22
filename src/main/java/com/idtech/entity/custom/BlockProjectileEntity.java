package com.idtech.entity.custom;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BlockProjectileEntity extends ThrowableItemProjectile {

    private float xRotStart;
    private float yRotStart;
    private float xRotSpeed;
    private float yRotSpeed;
    private Vec3 startPos;

    public BlockProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xRotStart = 0.0f;
        this.yRotStart = 0.0f;
        this.xRotSpeed = (this.random.nextFloat() - 0.5f) * 15.0f;
        this.yRotSpeed = (this.random.nextFloat() - 0.5f) * 15.0f;
        this.startPos = Vec3.ZERO;
    }

    public BlockProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
        this.xRotStart = 0.0f;
        this.yRotStart = 0.0f;
        this.xRotSpeed = (this.random.nextFloat() - 0.5f) * 15.0f;
        this.yRotSpeed = (this.random.nextFloat() - 0.5f) * 15.0f;
        this.startPos = Vec3.ZERO;
    }

    public BlockProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
        this.xRotStart = 0.0f;
        this.yRotStart = 0.0f;
        this.xRotSpeed = (this.random.nextFloat() - 0.5f) * 15.0f;
        this.yRotSpeed = (this.random.nextFloat() - 0.5f) * 15.0f;
        this.startPos = Vec3.ZERO;
    }

    @Override
    public void tick() {
        super.tick();
        this.updateRotation();
    }

    @Override
    public void updateRotation() {
        if (this.tickCount > 5) {
            this.setXRot(lerpRotation(this.xRotO,this.getXRot() + this.xRotSpeed));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot() + this.yRotSpeed));

            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GRASS_BLOCK;
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItem();
        return new ItemParticleOption(ParticleTypes.ITEM, itemstack);
    }

    @Override
    public float getGravity() {
        return 0.07F;
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for (int i = 0; i < 10; ++i) {
                this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), this.random.nextFloat() - 0.5f, this.random.nextFloat() - 0.5f, this.random.nextFloat() - 0.5f);
            }
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        int i = 4;
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float)i);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            SoundEvent sound = (this.getItem().getItem() instanceof BlockItem blockItem) ? blockItem.getBlock().defaultBlockState().getSoundType().getBreakSound() : SoundEvents.ITEM_BREAK;

            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, this.level());
            itemEntity.setItem(this.getItem());
            itemEntity.setPos(this.position());
            if (!this.level().isClientSide) this.level().addFreshEntity(itemEntity);

            this.playSound(sound);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    @Override
    public void setXRot(float pXRot) {
        this.xRotStart = pXRot;
        this.xRotO = pXRot;
        super.setXRot(pXRot);
    }

    @Override
    public void setYRot(float pYRot) {
        this.yRotStart = pYRot;
        this.yRotO = pYRot;
        super.setYRot(pYRot);
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
    }

    public Vec3 getStartPos() {
        return startPos;
    }

    public void setStartPos(Vec3 pos){
        this.startPos = pos;
    }

}
