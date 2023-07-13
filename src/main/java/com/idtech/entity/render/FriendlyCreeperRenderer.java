package com.idtech.entity.render;

import com.idtech.BaseMod;
import com.idtech.entity.custom.FriendlyCreeperEntity;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FriendlyCreeperRenderer extends MobRenderer<FriendlyCreeperEntity, CreeperModel<FriendlyCreeperEntity>> {
    private static final ResourceLocation CREEPER_LOCATION = new ResourceLocation(BaseMod.MODID, "textures/entity/friendly_creeper.png");

    public FriendlyCreeperRenderer(EntityRendererProvider.Context p_173958_) {
        super(p_173958_, new CreeperModel<>(p_173958_.bakeLayer(ModelLayers.CREEPER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(FriendlyCreeperEntity pEntity) {
        return CREEPER_LOCATION;
    }

}
