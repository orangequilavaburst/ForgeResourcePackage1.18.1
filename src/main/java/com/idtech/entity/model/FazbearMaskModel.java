package com.idtech.entity.model;

import com.idtech.BaseMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class FazbearMaskModel<T extends LivingEntity> extends HumanoidModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BaseMod.MODID, "freddy_fazbear_mask"), "main");
    //public final ModelPart head;
    public FazbearMaskModel() {
        super(createBodyLayer().bakeRoot());
        //this.head = pRoot.getChild("Head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lear = head.addOrReplaceChild("lear", CubeListBuilder.create(), PartPose.offset(-5.5F, -8.5F, -4.0F));

        PartDefinition cube_r1 = lear.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 8).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rear = head.addOrReplaceChild("rear", CubeListBuilder.create(), PartPose.offset(5.5F, -8.5F, -4.0F));

        PartDefinition cube_r2 = rear.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 3).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create().texOffs(12, 20).addBox(-1.0F, -29.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-2.0F, -34.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(13, 14).addBox(-1.5F, -37.0F, -5.4F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(6, 17).addBox(-5.0F, -32.0F, -5.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(4.0F, -32.0F, -5.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -33.0F, -5.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 13).addBox(-4.0F, -29.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(3.0F, -29.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-4.0F, -32.0F, -5.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-4.0F, -27.0F, -6.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 12).addBox(-4.0F, -24.0F, -5.5F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

}
