package com.idtech.entity.render;

import com.idtech.BaseMod;
import com.idtech.entity.custom.DragonstoneArrowEntity;
import com.idtech.entity.custom.YttriumArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class YttriumArrowRenderer extends ArrowRenderer<YttriumArrowEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(BaseMod.MODID, "textures/entity/yttrium_arrow.png");

    public YttriumArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(YttriumArrowEntity pEntity) {
        return TEXTURE;
    }
}
