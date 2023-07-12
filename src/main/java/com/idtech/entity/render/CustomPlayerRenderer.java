package com.idtech.entity.render;

import com.idtech.BaseMod;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class CustomPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public String skinName;

    public CustomPlayerRenderer(EntityRendererProvider.Context pContext, PlayerModel<AbstractClientPlayer> pModel, float pShadowRadius, String skinName) {
        super(pContext, pModel, pShadowRadius);
        this.skinName = skinName;
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractClientPlayer pEntity) {
        return new ResourceLocation(BaseMod.MODID, "textures/entity/" + this.skinName + ".png");
    }

}
