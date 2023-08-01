package com.idtech.entity.model;

import com.idtech.BaseMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class FazbearMaskModel<T extends LivingEntity> extends HumanoidArmorModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BaseMod.MODID, "freddy_fazbear_mask"), "main");
    private final ModelPart Head;
    private final ModelPart Body;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;

    public FazbearMaskModel(ModelPart pRoot) {
        super(pRoot);
        this.Head = pRoot.getChild("Head");
        this.Body = pRoot.getChild("Body");
        this.RightArm = pRoot.getChild("RightArm");
        this.LeftArm = pRoot.getChild("LeftArm");
        this.RightLeg = pRoot.getChild("RightLeg");
        this.LeftLeg = pRoot.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mask = Head.addOrReplaceChild("mask", CubeListBuilder.create().texOffs(12, 20).addBox(-1.0F, -29.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
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

        PartDefinition cube_r1 = mask.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 8).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -32.5F, -4.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r2 = mask.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 3).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -32.5F, -4.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


}
