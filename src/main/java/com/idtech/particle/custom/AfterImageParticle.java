package com.idtech.particle.custom;

import com.idtech.particle.ParticleMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;

// thank you so much Radoncoding!
public class AfterImageParticle<T extends AfterImageParticle.AfterImageParticleOptions> extends TextureSheetParticle {

    private final int entityId;

    @Nullable
    private Entity entity;

    private float yRot;
    private float yRot0;
    private float yHeadRot;
    private float yHeadRot0;
    private float yBodyRot;
    private float yBodyRotO;

    private float position;
    private float speed;


    protected AfterImageParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, T options) {
        super(pLevel, pX, pY, pZ);

        this.lifetime = 8;

        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;

        this.entityId = options.entityID();
    }

    @Override
    public void tick() {
        super.tick();

        this.alpha = 1.0F - ((float) this.age / this.lifetime);

        if (this.entity == null) {
            this.entity = this.level.getEntity(this.entityId);

            if (this.entity == null) return;

            this.yRot = this.entity.getYRot();
            this.yRot0 = this.entity.yRotO;

            if (this.entity instanceof LivingEntity living) {
                this.yHeadRot = living.yHeadRot;
                this.yHeadRot0 = living.yHeadRotO;

                this.yBodyRot = living.yBodyRot;
                this.yBodyRotO = living.yBodyRotO;

                this.position = living.walkAnimation.position();
                this.speed = living.walkAnimation.speed();
            }
        }
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        if (this.entity != null) {
            PoseStack stack = new PoseStack();

            float yRot = this.entity.getYRot();
            float yRotO = this.entity.yRotO;

            float yHeadRot = 0.0F;
            float yHeadRotO = 0.0F;
            float yBodyRot = 0.0F;
            float yBodyRotO = 0.0F;

            boolean invisible = this.entity.isInvisible();

            if (this.entity instanceof LivingEntity living) {
                yHeadRot = living.yHeadRot;
                yHeadRotO = living.yHeadRotO;

                living.yHeadRot = this.yHeadRot;
                living.yHeadRotO = this.yHeadRot0;

                yBodyRot = living.yHeadRot;
                yBodyRotO = living.yHeadRotO;

                living.yBodyRot = this.yBodyRot;
                living.yBodyRotO = this.yBodyRotO;
            }

            this.entity.setYRot(this.yRot);
            this.entity.yRotO = this.yRot0;

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

            EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
            EntityRenderer<? super Entity> renderer = manager.getRenderer(this.entity);

            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            Vec3 offset = renderer.getRenderOffset(this.entity, pPartialTicks);
            stack.translate((this.x - pRenderInfo.getPosition().x) + offset.x, (this.y - pRenderInfo.getPosition().y) + offset.y, (this.z - pRenderInfo.getPosition().z) + offset.z);
            renderer.render(this.entity, 0.0F, pPartialTicks, stack, buffer, manager.getPackedLightCoords(this.entity, pPartialTicks));
            buffer.getBuffer(RenderType.translucent());
            buffer.endBatch();

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            this.entity.yRotO = yRotO;
            this.entity.setYRot(yRot);

            if (this.entity instanceof LivingEntity living) {
                living.yBodyRotO = yBodyRotO;
                living.yBodyRot = yBodyRot;

                living.yHeadRotO = yHeadRotO;
                living.yHeadRot = yHeadRot;
            }

            this.entity.setInvisible(invisible);

        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public record AfterImageParticleOptions(int entityID) implements ParticleOptions{

        public static Deserializer<AfterImageParticleOptions> DESERIALIZER =
                new Deserializer<AfterImageParticleOptions>() {
                    @Override
                    public @NotNull AfterImageParticleOptions fromCommand(@NotNull ParticleType<AfterImageParticleOptions> pParticleType, @NotNull StringReader pReader) throws CommandSyntaxException {
                        return new AfterImageParticleOptions(pReader.readInt());
                    }

                    @Override
                    public @NotNull AfterImageParticleOptions fromNetwork(@NotNull ParticleType<AfterImageParticleOptions> pParticleType, @NotNull FriendlyByteBuf pBuffer) {
                        return new AfterImageParticleOptions(pBuffer.readInt());
                    }
                };

        @Override
        public @NotNull ParticleType<?> getType() {
            return ParticleMod.AFTER_IMAGE_PARTICLE.get();
        }

        @Override
        public void writeToNetwork(FriendlyByteBuf pBuffer) {
            pBuffer.writeInt(this.entityID);
        }

        @Override
        public @NotNull String writeToString() {
            return String.format(Locale.ROOT, "%s %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.entityID);
        }
    }

    public static class Provider implements ParticleProvider<AfterImageParticleOptions>{

        public Provider(SpriteSet ignored){}

        public Particle createParticle(@NotNull AfterImageParticle.AfterImageParticleOptions pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AfterImageParticle<>(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pType);
        }
    }

}
