package com.idtech.entity.custom;

import com.idtech.entity.EntityMod;
import com.idtech.item.ItemMod;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class YttriumArrowEntity extends AbstractArrow {

    private static final EntityDataAccessor<String> OWNER = SynchedEntityData.defineId(YttriumArrowEntity.class, EntityDataSerializers.STRING);
    private int life;
    private Vec3 bounceVector = Vec3.ZERO;
    private int bounces = 0;
    private static final int MAX_BOUNCES = 3;

    public YttriumArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(EntityMod.YTTRIUM_ARROW_ENTITY.get(), pLevel);
        this.life = 0;
    }
    public YttriumArrowEntity(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(EntityMod.YTTRIUM_ARROW_ENTITY.get(), pShooter, pLevel);
        setOwnerUUID(pShooter.getStringUUID());
        this.life = 0;
    }

    public YttriumArrowEntity(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(EntityMod.YTTRIUM_ARROW_ENTITY.get(), pLevel);
        this.life = 0;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ItemMod.YTTRIUM_ARROW.get());
    }

    @Override
    protected void tickDespawn() {
        super.tickDespawn();
        ++this.life;
        if (this.life > 60){

            this.discard();

        }
    }

    @Override
    public void tick() {
        super.tick();
        //this.updateRotation();

        if (!this.inGround) {
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            this.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        bounceVector = getReflectionVector(this.getDeltaMovement(), hitResult.getDirection()).scale(0.75);
        this.bounces++;
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), this.level().getBlockState(hitResult.getBlockPos()).getSoundType().getHitSound(), SoundSource.NEUTRAL, 1f, 1f);

        if(bounces >= MAX_BOUNCES) {
            super.onHitBlock(hitResult);
        }
        else {
            this.setDeltaMovement(bounceVector);
            this.updateRotation();
        }
    }

    private Vec3 getReflectionVector(Vec3 deltaMovement, Direction dir) {
        Vec3 normalVector = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
        Vec3 normalizedProduct = normalVector.scale(2 * deltaMovement.dot(normalVector));
        return deltaMovement.subtract(normalizedProduct);
    }

    // thx SSKirilSS
    public String getOwnerUUID() {
        return this.getEntityData().get(OWNER);
    }

    public void setOwnerUUID(String uuid) {
        this.getEntityData().set(OWNER, uuid);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER, (this.getOwner() != null) ? this.getOwner().getStringUUID() : "");
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
