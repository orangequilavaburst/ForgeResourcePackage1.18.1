package com.idtech.item.custom;

import com.idtech.BaseMod;
import com.idtech.entity.custom.DevilsknifeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DevilsknifeItem extends SwordItem {

    public static final Tier DEVILSKNIFE = TierSortingRegistry.registerTier(
            new ForgeTier(5, 5000, 8f, 8f, 20, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.of(Items.PHANTOM_MEMBRANE)),
            new ResourceLocation(BaseMod.MODID, "devilsknife"), List.of(Tiers.NETHERITE), List.of());

    public DevilsknifeItem(int p_43270_, float p_43271_, Properties p_43272_) {
        super(DEVILSKNIFE, p_43270_, p_43271_, p_43272_);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        }
        else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int p_43397_) {
        if (user instanceof Player player) {
            int i = this.getUseDuration(stack) - p_43397_;
            if (i >= 10) {
                DevilsknifeEntity thrownKnife = new DevilsknifeEntity(level, player, stack.copy());
                thrownKnife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                thrownKnife.setXRot(player.getViewXRot(1.0f));
                thrownKnife.setYRot(player.getViewYRot(1.0f));

                if (player.getAbilities().instabuild) {
                    thrownKnife.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(thrownKnife);
                level.playSound(null, thrownKnife, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(stack);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        String tipItemName = this.getDescriptionId() + ".credits";
        components.add(Component.translatable("tooltip.yourmodname.sprite_credits_generic").append(Component.translatable(tipItemName)).withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(stack, level, components, flag);
    }
}
