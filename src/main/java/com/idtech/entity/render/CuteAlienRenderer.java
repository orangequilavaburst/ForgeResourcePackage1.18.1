package com.idtech.entity.render;

import com.idtech.BaseMod;
import com.idtech.entity.custom.CuteAlienEntity;
import com.idtech.entity.model.CuteAlienModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CuteAlienRenderer extends MobRenderer<CuteAlienEntity, CuteAlienModel<CuteAlienEntity>> {
    public CuteAlienRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CuteAlienModel<>(pContext.bakeLayer(CuteAlienModel.LAYER_LOCATION)), 0.1F);
    }

    @Override
    public ResourceLocation getTextureLocation(CuteAlienEntity pEntity) {
        return new ResourceLocation(BaseMod.MODID, "textures/entity/cute_alien.png");
    }
}
