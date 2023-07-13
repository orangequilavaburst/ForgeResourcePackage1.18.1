package com.idtech.entity.custom;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CuteAlienEntity extends FlyingMob implements FlyingAnimal {
    
    public CuteAlienEntity(EntityType<? extends FlyingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new CuteAlienEntity.cuteAlienMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.FOLLOW_RANGE, 15.0);
    }

    protected void registerGoals(){

        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 20.0f));
        this.goalSelector.addGoal(3, new CuteAlienEntity.RandomFloatAroundGoal(this));

    }

    @Override
    public boolean isFlying() {
        return true;
    }

    static class RandomFloatAroundGoal extends Goal {
        private final CuteAlienEntity cuteAlien;

        public RandomFloatAroundGoal(CuteAlienEntity pcuteAlien) {
            this.cuteAlien = pcuteAlien;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            MoveControl movecontrol = this.cuteAlien.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double d0 = movecontrol.getWantedX() - this.cuteAlien.getX();
                double d1 = movecontrol.getWantedY() - this.cuteAlien.getY();
                double d2 = movecontrol.getWantedZ() - this.cuteAlien.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            RandomSource randomsource = this.cuteAlien.getRandom();
            double d0 = this.cuteAlien.getX() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.cuteAlien.getY() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.cuteAlien.getZ() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.cuteAlien.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }

        public void tick() {
            if (this.cuteAlien.getTarget() == null) {
                Vec3 vec3 = this.cuteAlien.getDeltaMovement();
                this.cuteAlien.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                this.cuteAlien.yBodyRot = this.cuteAlien.getYRot();
            } else {
                LivingEntity livingentity = this.cuteAlien.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.cuteAlien) < 4096.0D) {
                    double d1 = livingentity.getX() - this.cuteAlien.getX();
                    double d2 = livingentity.getZ() - this.cuteAlien.getZ();
                    this.cuteAlien.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
                    this.cuteAlien.yBodyRot = this.cuteAlien.getYRot();
                }
            }

        }
        
    }

    static class cuteAlienMoveControl extends MoveControl {
        private final CuteAlienEntity cuteAlien;
        private int floatDuration;

        public cuteAlienMoveControl(CuteAlienEntity pcuteAlien) {
            super(pcuteAlien);
            this.cuteAlien = pcuteAlien;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.cuteAlien.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(this.wantedX - this.cuteAlien.getX(), this.wantedY - this.cuteAlien.getY(), this.wantedZ - this.cuteAlien.getZ());
                    double d0 = vec3.length();
                    vec3 = vec3.normalize();
                    if (this.canReach(vec3, Mth.ceil(d0))) {
                        this.cuteAlien.setDeltaMovement(this.cuteAlien.getDeltaMovement().add(vec3.scale(0.01D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }

            }
        }

        private boolean canReach(Vec3 pPos, int pLength) {
            AABB aabb = this.cuteAlien.getBoundingBox();

            for(int i = 1; i < pLength; ++i) {
                aabb = aabb.move(pPos);
                if (!this.cuteAlien.level().noCollision(this.cuteAlien, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }

}
