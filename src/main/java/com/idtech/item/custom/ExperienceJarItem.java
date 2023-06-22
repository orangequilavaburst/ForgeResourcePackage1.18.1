package com.idtech.item.custom;

import com.idtech.BaseMod;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ExperienceJarItem extends Item {

    private String expName = "experience";
    private int maxExpLevels = 10;

    public ExperienceJarItem(Properties properties) {
        super(properties.stacksTo(1));
        ItemProperties.register(this.asItem(), new ResourceLocation(BaseMod.MODID,"experience"), (p1, p2, p3, p4)->
        {
            return this.getLevels(p1);
        });
    }

    public void setLevels(ItemStack pStack, int num){
        pStack.getOrCreateTagElement(expName).putInt(expName, num);
    }

    public int getLevels(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement(this.expName);
        return compoundtag != null && compoundtag.contains(this.expName, 99) ? compoundtag.getInt(this.expName) : 0;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return getLevels(pStack) > 0 && getLevels(pStack) < this.maxExpLevels;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return Color.HSBtoRGB(Mth.lerp((float)getLevels(pStack)/(float)this.maxExpLevels, 0.0f, 1.0f), 1.0f, 1.0f);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)getLevels(pStack) * 13.0F / (float)this.maxExpLevels);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()){ // add exp

            if (player.experienceLevel > 0 && this.getLevels(itemstack) < this.maxExpLevels){
                this.setLevels(itemstack, this.getLevels(itemstack) + 1);
                player.experienceLevel--;
                player.playSound(SoundEvents.PLAYER_BREATH, 1.0f, 1.0f);
            }
            else{
                return InteractionResultHolder.fail(itemstack);
            }

        }
        else{ // give exp

            if (this.getLevels(itemstack) > 0){
                this.setLevels(itemstack, this.getLevels(itemstack) - 1);
                player.experienceLevel++;
                player.playSound(SoundEvents.EXPERIENCE_BOTTLE_THROW, 1.0f, 1.0f);
            }
            else{
                return InteractionResultHolder.fail(itemstack);
            }

        }

        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, java.util.List<net.minecraft.network.chat.Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String tipItemName = pStack.getItem().toString();
        tipItemName = "tooltip." + BaseMod.MODID + "." + tipItemName;
        pTooltipComponents.add(Component.translatable(tipItemName).append(" " + this.getLevels(pStack) + "/" + this.maxExpLevels));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
