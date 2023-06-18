package com.idtech.entity.custom;

import com.idtech.item.ItemMod;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class DragonstoneArrowEntity extends AbstractArrow {

    private static final EntityDataAccessor<String> OWNER = SynchedEntityData.defineId(DragonstoneArrowEntity.class, EntityDataSerializers.STRING);
    private int life;

    public DragonstoneArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.life = 0;
    }
    public DragonstoneArrowEntity(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
        setOwnerUUID(pShooter.getStringUUID());
        this.life = 0;
    }

    public DragonstoneArrowEntity(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pLevel);
        this.life = 0;
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(ItemMod.DRAGONSTONE_ARROW.get());
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

        if (this.onGround()){

            // vibe

        }
        else{

            // home into entities
            Level pLevel = this.level();
            LivingEntity target = null;
            float radius = 30.0f;
            AABB area = this.getBoundingBox().inflate(radius);
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, area).stream()
                    .filter(entry -> entry.hasLineOfSight(this))
                    .filter(entry -> entry.position().distanceTo(this.position()) <= radius)
                    .filter(entity -> !entity.getStringUUID().equals(getOwnerUUID()))
                    .sorted(Comparator.comparing(entry -> entry.position().distanceTo(this.position())))
                    .toList();

            if (!list.isEmpty()) {
                target = list.get(0);

                //Vec3 motion = target.getPosition(1.0f).add(0.0f, 0.0f + target.getBoundingBox().getYsize()/2, 0.0f).subtract(this.position()).normalize().scale(0.5F);
                //this.setDeltaMovement(this.getDeltaMovement().add(motion.x(), motion.y(), motion.z()));

                Vec3 targetVec = target.getPosition(1.0f).add(0.0f, 0.0f + target.getBoundingBox().getYsize()/2, 0.0f).subtract(this.position()).normalize();

                Vec3 a = this.getDeltaMovement().normalize();
                Vec3 b = targetVec;
                float lerpAmount = (float)Mth.lerp(Math.max(this.distanceTo(target), radius)/radius, ((this.isCritArrow())) ? 0.25f : 0.05f, 0.01);
                float maxSpeed = ((this.isCritArrow())) ? 2.5f : 1.0f;
                float savedSpeed = (float)this.getDeltaMovement().length();

                this.setDeltaMovement(a.add(b.subtract(a.scale(lerpAmount))));
                this.setDeltaMovement(this.getDeltaMovement().normalize().scale(Math.max(savedSpeed, maxSpeed)));

            }

        }

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
