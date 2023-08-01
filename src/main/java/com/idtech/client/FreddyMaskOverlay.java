package com.idtech.client;

import com.idtech.BaseMod;
import com.idtech.item.ItemMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

public class FreddyMaskOverlay {

    private static final ResourceLocation FREDDY_MASK = new ResourceLocation(BaseMod.MODID,
            "textures/gui/freddy_fazbear_mask_overlay.png");

    public static final IGuiOverlay FREDDY_MASK_OVERLAY = ((gui, poseStack, partialTick, width, height) -> {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, FREDDY_MASK);

        Minecraft minecraft = Minecraft.getInstance();
        GuiGraphics graphics = new GuiGraphics(minecraft, minecraft.renderBuffers().bufferSource());
        Player player = minecraft.player;
        ItemStack itemstack = player.getInventory().getArmor(EquipmentSlot.HEAD.getIndex());

        if (minecraft.options.getCameraType().isFirstPerson() && !itemstack.isEmpty())
        {
            Item item = itemstack.getItem();
            if (item == ItemMod.FREDDY_FAZBEAR_MASK.get())
            {
                renderTextureOverlay(graphics, FREDDY_MASK, 1.0f, width, height, 1.1f, 1.5f);


            }else
            {
                IClientItemExtensions.of(item).renderHelmetOverlay(itemstack, minecraft.player, (int)(width*1.1), (int)(height * 1.5), partialTick);
            }
        }

    });

    private static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation resourceLocation, float alpha, int width, int height, float xscale, float yscale) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        double timer = (Minecraft.getInstance().player.level().getGameTime() / 10.0f);
        int xd = (int)(width * xscale) - width;
        int yd = (int)(height * yscale) - height;

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(resourceLocation,
                (int) (Math.sin(timer)*5) - xd/2,
                (int) (Math.cos(timer/2.0f)*5) - yd/2,
                -90, 0.0F, 0.0F,
                (int) (width*xscale), (int) (height*yscale),
                (int) (width*xscale), (int) (height*yscale));
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
