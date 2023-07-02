package com.idtech.item.custom;

import com.idtech.BaseMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class WhirlwindPickaxeItem extends PickaxeItem {

    private String speedName = "mining_speed_modifier";
    private float speedModMax = 20.0f;

    public WhirlwindPickaxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        ItemProperties.register(this.asItem(), new ResourceLocation(BaseMod.MODID, this.speedName), (p1, p2, p3, p4)->{
            return this.getSpeedMod(p1);
        });
    }

    public void setSpeedMod(ItemStack pStack, float num){
        pStack.getOrCreateTagElement(this.speedName).putFloat(this.speedName, num);
    }

    public float getSpeedMod(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement(this.speedName);
        return compoundtag != null && compoundtag.contains(this.speedName, 99) ? compoundtag.getFloat(this.speedName) : 0;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return this.getSpeedMod(pStack) > 0;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return Color.HSBtoRGB(Mth.lerp(this.getSpeedMod(pStack)/this.speedModMax, 0.0f, 1.0f), 1.0f, 1.0f);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round(this.getSpeedMod(pStack) * 13.0F / this.speedModMax);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        float ds = super.getDestroySpeed(pStack, pState);
        return ds + this.getSpeedMod(pStack) * (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, pStack) + 1);
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pEntityLiving.getMainHandItem() == pStack) {
            if (this.getSpeedMod(pStack) < this.speedModMax) {
                this.setSpeedMod(pStack, this.getSpeedMod(pStack) + 1.5f);
            } else {
                this.setSpeedMod(pStack, this.speedModMax);
            }
        }

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player p){

            if (!p.isCreative()){

                if (!(p.getMainHandItem() == pStack && (p.isUsingItem() || p.swinging))){

                    if (this.getSpeedMod(pStack) > 0) {
                        this.setSpeedMod(pStack, this.getSpeedMod(pStack) - 0.25f);
                    }
                    else{

                        this.setSpeedMod(pStack, 0);

                    }

                }

            }

        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String tipItemName = pStack.getItem().toString();
        tipItemName = "tooltip." + BaseMod.MODID + "." + tipItemName;
        ChatFormatting style = (this.getSpeedMod(pStack) < this.speedModMax && this.getSpeedMod(pStack) > 0) ? ChatFormatting.DARK_AQUA : ChatFormatting.AQUA;
        pTooltipComponents.add(Component.translatable(tipItemName).append(" " + String.format("%.2f", this.getSpeedMod(pStack))).withStyle(style));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
