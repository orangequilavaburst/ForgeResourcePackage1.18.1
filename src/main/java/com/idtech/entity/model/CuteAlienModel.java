package com.idtech.entity.model;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.idtech.BaseMod;
import com.idtech.entity.custom.CuteAlienEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CuteAlienModel<T extends CuteAlienEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BaseMod.MODID, "cute_alien"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart ribbonLeft;
	private final ModelPart ribbonRight;

	public CuteAlienModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.ribbonLeft = root.getChild("ribbonLeft");
		this.ribbonRight = root.getChild("ribbonRight");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition left_ear_r1 = head.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(8, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_ear_r1 = head.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(18, 13).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition ribbonLeft = partdefinition.addOrReplaceChild("ribbonLeft", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition ribbon_left_r1 = ribbonLeft.addOrReplaceChild("ribbon_left_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition ribbonRight = partdefinition.addOrReplaceChild("ribbonRight", CubeListBuilder.create(), PartPose.offset(-2.0F, 27.0F, 0.0F));

		PartDefinition ribbon_right_r1 = ribbonRight.addOrReplaceChild("ribbon_right_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;

		this.ribbonLeft.xRot = Mth.cos(ageInTicks / 10.0f) * 0.5f;
		this.ribbonLeft.z += Mth.cos(ageInTicks / 10.0f) * 0.001f;
		this.ribbonRight.xRot = Mth.cos(-ageInTicks / 10.0f) * 0.5f;
		this.ribbonRight.z += Mth.cos(-ageInTicks / 10.0f) * 0.001f;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		ribbonLeft.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		ribbonRight.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}