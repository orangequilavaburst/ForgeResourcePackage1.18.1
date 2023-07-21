package com.idtech.entity.render;

import com.idtech.entity.custom.BlockProjectileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class BlockProjectileRenderer extends EntityRenderer<BlockProjectileEntity> {

    private final BlockRenderDispatcher dispatcher;
    private final ItemRenderer itemRenderer;

    public BlockProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.5F;
        this.dispatcher = pContext.getBlockRenderDispatcher();
        this.itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public void render(BlockProjectileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        ItemStack itemStack = pEntity.getItem();
        Level level = pEntity.level();
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot())));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
        if (itemStack.getItem() instanceof BlockItem blockItem){
            BlockState blockstate = blockItem.getBlock().defaultBlockState();
            if (blockstate != level.getBlockState(pEntity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                BlockPos blockpos = BlockPos.containing(pEntity.getX(), pEntity.getBoundingBox().maxY, pEntity.getZ());
                pPoseStack.translate(-0.5D, 0.0D, -0.5D);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(BlockPos.ZERO)), net.minecraftforge.client.model.data.ModelData.EMPTY))
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, pPoseStack, pBuffer.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(BlockPos.ZERO), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);

            }
            pPoseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);

        }
        else if (itemStack.getItem() != Items.AIR){

            BakedModel bakedmodel = this.itemRenderer.getModel(itemStack, pEntity.level(), (LivingEntity)null, pEntity.getId());
            pPoseStack.popPose();
            this.itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, bakedmodel);

        }
        else {
            pPoseStack.popPose();
            super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BlockProjectileEntity pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
