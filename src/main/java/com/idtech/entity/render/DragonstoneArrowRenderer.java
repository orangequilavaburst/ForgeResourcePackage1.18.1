package com.idtech.entity.render;

import com.idtech.BaseMod;
import com.idtech.entity.custom.DragonstoneArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DragonstoneArrowRenderer extends ArrowRenderer<DragonstoneArrowEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BaseMod.MODID, "textures/entity/dragonstone_arrow.png");

    public DragonstoneArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(DragonstoneArrowEntity pEntity) {
        return TEXTURE;
    }
}
