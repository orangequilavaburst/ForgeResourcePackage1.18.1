package com.idtech.entity.render;

import com.idtech.BaseMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class GoldenSkeletonRenderer extends SkeletonRenderer {

    private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation(BaseMod.MODID, "textures/entity/golden_skeleton.png");

    public GoldenSkeletonRenderer(EntityRendererProvider.Context p_174380_) {
        super(p_174380_);
    }

    public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
        return SKELETON_LOCATION;
    }

}
