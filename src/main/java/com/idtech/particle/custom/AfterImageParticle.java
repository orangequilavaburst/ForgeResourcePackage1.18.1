package com.idtech.particle.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.MobAppearanceParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class AfterImageParticle extends Particle {

    private final RenderBuffers renderBuffers;
    private final Entity referenceEntity;
    private final PoseStack poseStack;
    private final EntityRenderDispatcher entityRenderDispatcher;

    public AfterImageParticle(Vec3 pPos, EntityRenderDispatcher pEntityRenderDispatcher, RenderBuffers pBuffers, ClientLevel pLevel, Entity pRefEntity, PoseStack pPoseStack){
        this(pPos, pEntityRenderDispatcher, pBuffers, pLevel, pRefEntity, pPoseStack, Vec3.ZERO);
    }

    public AfterImageParticle(Vec3 pPos, EntityRenderDispatcher pEntityRenderDispatcher, RenderBuffers pBuffers, ClientLevel pLevel, Entity pRefEntity, PoseStack pPoseStack, Vec3 pSpeedVector) {
        super(pLevel, pPos.x, pPos.y, pPos.z, pSpeedVector.x, pSpeedVector.y, pSpeedVector.z);
        this.renderBuffers = pBuffers;
        this.referenceEntity = this.getSafeCopy(pRefEntity);
        this.entityRenderDispatcher = pEntityRenderDispatcher;
        this.poseStack = pPoseStack;
    }

    private Entity getSafeCopy(Entity pEntity) {
        return (Entity)(!(pEntity instanceof ItemEntity) ? pEntity : ((ItemEntity)pEntity).copy());
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        float t = ((float)this.age + pPartialTicks) / (float)this.lifetime;
        float alpha = Mth.lerp(0.5f, 0.0f, t);

        MultiBufferSource.BufferSource multibuffersource$buffersource = this.renderBuffers.bufferSource();
        this.entityRenderDispatcher.render(this.referenceEntity, this.x, this.y, this.z, this.referenceEntity.getYRot(), pPartialTicks, this.poseStack, multibuffersource$buffersource, this.entityRenderDispatcher.getPackedLightCoords(this.referenceEntity, pPartialTicks));

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final Entity entity;
        private final PoseStack poseStack;
        private final RenderBuffers renderBuffers;
        private final EntityRenderDispatcher entityRenderDispatcher;

        public Provider(Entity entity, PoseStack poseStack, RenderBuffers renderBuffers, EntityRenderDispatcher entityRenderDispatcher){
            this.entity = entity;
            this.poseStack = poseStack;
            this.renderBuffers = renderBuffers;
            this.entityRenderDispatcher = entityRenderDispatcher;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AfterImageParticle(new Vec3(pX, pY, pZ), this.entityRenderDispatcher, this.renderBuffers, pLevel, this.entity, this.poseStack, new Vec3(pXSpeed, pYSpeed, pZSpeed));
        }
    }

}
