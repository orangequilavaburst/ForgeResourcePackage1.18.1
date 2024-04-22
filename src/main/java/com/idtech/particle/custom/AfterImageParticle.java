package com.idtech.particle.custom;

import com.idtech.particle.ParticleMod;
import com.idtech.particle.render.AfterImageRenderer;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Map;

// thank you so much Radoncoding!
public class AfterImageParticle<T extends AfterImageParticle.AfterImageParticleOptions> extends TextureSheetParticle {

    private final int entityId;

    @Nullable
    private Entity entity;
    @Nullable
    private Map<RenderType, VertexBuffer> savedFrame;

    private float yRot;
    private float yRot0;
    private float yHeadRot;
    private float yHeadRot0;
    private float yBodyRot;
    private float yBodyRot0;

    private float position;
    private float speed;



    public AfterImageParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, T options) {
        super(pLevel, pX, pY, pZ);

        this.lifetime = 20;

        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;

        this.entityId = options.entityID();

        //BaseMod.LOGGER.info("LETS FUCKING GOOOOOOO");
    }

    @Override
    public void tick() {
        super.tick();

        this.alpha = (0.5F - ((float) this.age / this.lifetime)*0.5f);

        if (this.entity == null) {
            this.entity = this.level.getEntity(this.entityId);

            //BaseMod.LOGGER.info("Entity ID is now ".concat(Integer.toString(this.entityId)).concat(", which is a ").concat(this.entity.getName().getString()));

            if (this.entity != null) return;

            this.yRot = this.entity.getYRot();
            this.yRot0 = this.entity.yRotO;

            if (this.entity instanceof LivingEntity living) {
                this.yHeadRot = living.yHeadRot;
                this.yHeadRot0 = living.yHeadRotO;

                this.yBodyRot = living.yBodyRot;
                this.yBodyRot0 = living.yBodyRotO;

                this.position = living.walkAnimation.position();
                this.speed = living.walkAnimation.speed();
            }

        }
        //BaseMod.LOGGER.info("Entity ID is ".concat(Integer.toString(this.entityId)).concat(", which is a ").concat(this.entity.getName().getString()));

    }

    @Override
    public void remove() {
        super.remove();
        this.savedFrame.forEach(((renderType, vertexBuffer) -> {
            vertexBuffer.close();
        }));
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        if (this.entity != null) {
            PoseStack stack = new PoseStack();
            boolean invisible = this.entity.isInvisible();

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
            EntityRenderer<? super Entity> renderer = manager.getRenderer(this.entity);

            if (this.savedFrame == null){

                this.savedFrame = AfterImageRenderer.preRenderEntity(this.entity, pPartialTicks);

            }
            else{
                this.savedFrame.forEach((renderType, buffer) -> {
                    renderType.setupRenderState();

                    ShaderInstance shader = RenderSystem.getShader();
                    if (shader != null){
                        return;
                    }
                    Uniform modelViewMatrix = shader.MODEL_VIEW_MATRIX;
                    if (modelViewMatrix != null){
                        modelViewMatrix.set(stack.last().pose());
                        modelViewMatrix.upload();
                    }

                    buffer.bind();
                    buffer.draw();
                });
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            this.entity.setInvisible(invisible);

        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
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
            return String.format(Locale.ROOT, "%s %d", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.entityID);
        }
    }

    public static class Provider implements ParticleProvider<AfterImageParticleOptions>{

        public Provider(SpriteSet ignored){}

        public Particle createParticle(@NotNull AfterImageParticle.AfterImageParticleOptions pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AfterImageParticle<>(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pType);
        }
    }

}
