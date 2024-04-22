package com.idtech.particle.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AfterImageRenderer {

    public static final AfterImageBufferSource AFTER_IMAGE_BUFFER_SOURCE = new AfterImageBufferSource(); //AfterImageBufferSource;
    public static final Supplier<Camera> CAMERA = () -> Minecraft.getInstance().gameRenderer.getMainCamera();
    public static final Supplier<EntityRenderDispatcher> ENTITY_RENDERER = () -> Minecraft.getInstance().getEntityRenderDispatcher();

    public static Map<RenderType, VertexBuffer> preRenderEntity(Entity entity, float partialTicks){

        PoseStack poseStack = new PoseStack();
        EntityRenderDispatcher entityRenderer = ENTITY_RENDERER.get();

        AfterImageBufferSource bufferSource = AFTER_IMAGE_BUFFER_SOURCE;
        EntityRenderer<? super Entity> renderer = entityRenderer.getRenderer(entity);

        if (renderer != null) {

            int light = LevelRenderer.getLightColor(entity.level(), new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()));

            poseStack.pushPose();
            //poseStack.translate(entity.position().x(), entity.position().y(), entity.position().z());
            poseStack.translate(-CAMERA.get().getPosition().x(), -CAMERA.get().getPosition().y(), -CAMERA.get().getPosition().z());
            poseStack.mulPose(CAMERA.get().rotation().invert());
            renderer.render(entity, entity.getViewYRot(partialTicks), partialTicks, poseStack, bufferSource, light);
            poseStack.popPose();

        }

        Map<RenderType, VertexBuffer> map = new HashMap<>();
        bufferSource.renderBuilders.forEach((renderType, bufferBuilder) -> {
            if (bufferBuilder.building() && renderType != null) {
                VertexBuffer buffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
                buffer.bind();
                buffer.upload(bufferBuilder.end());
                map.put(renderType, buffer);
            }
        });

        return map;

    }

}
