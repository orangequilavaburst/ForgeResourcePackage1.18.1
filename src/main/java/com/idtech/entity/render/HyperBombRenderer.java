package com.idtech.entity.render;

import com.idtech.entity.custom.HyperBombEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

public class HyperBombRenderer extends ThrownItemRenderer<HyperBombEntity> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public HyperBombRenderer(EntityRendererProvider.Context pContext, float pScale, boolean pFullBright) {
        super(pContext, pScale, pFullBright);
        this.itemRenderer = pContext.getItemRenderer();
        this.scale = pScale;
        this.fullBright = pFullBright;
    }

    public HyperBombRenderer(EntityRendererProvider.Context pContext) {
        this(pContext, 1.0F, false);
    }

    @Override
    public void render(HyperBombEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float s = this.scale;
        if (pEntity.tickCount + pPartialTicks > pEntity.lifeTime - 5){
            s += 0.25f * (pEntity.tickCount + pPartialTicks - (pEntity.lifeTime - 5));
        }
        else{
            s += Mth.sin(pEntity.tickCount + pPartialTicks) * 0.125f;
        }
        if (pEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25D)) {
            pPoseStack.pushPose();
            pPoseStack.scale(s, s, s);
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(pEntity.getItem(), ItemDisplayContext.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pEntity.level(), pEntity.getId());
            pPoseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        }
    }
}
