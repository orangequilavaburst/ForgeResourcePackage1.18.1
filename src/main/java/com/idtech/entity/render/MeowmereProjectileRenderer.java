package com.idtech.entity.render;

import com.idtech.entity.custom.MeowmereProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class MeowmereProjectileRenderer extends EntityRenderer<MeowmereProjectileEntity> {
    public MeowmereProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(MeowmereProjectileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);



    }

    @Override
    public ResourceLocation getTextureLocation(MeowmereProjectileEntity pEntity) {
        return null;
    }
}
