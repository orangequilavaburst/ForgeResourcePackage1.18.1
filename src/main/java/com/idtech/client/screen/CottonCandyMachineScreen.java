package com.idtech.client.screen;

import com.idtech.BaseMod;
import com.idtech.client.container.CottonCandyMachineMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class CottonCandyMachineScreen extends AbstractContainerScreen<CottonCandyMachineMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BaseMod.MODID, "textures/gui/container/cotton_candy_machine.png");

    public CottonCandyMachineScreen(CottonCandyMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 8;
        this.inventoryLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.getSugarProgress() > 0){
            pGuiGraphics.blit(TEXTURE, x + 33, y + 16, 176, 0/*54 - menu.getSugarProgress()*/, 18, menu.getSugarProgress());
        }
        pGuiGraphics.drawString(this.font, String.format("%d/%d", menu.getSugar(), menu.getMaxSugar()), x + 33, y + 71, Color.WHITE.getRGB());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

}
