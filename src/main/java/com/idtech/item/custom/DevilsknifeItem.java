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

    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        }
        else {
            p_43406_.startUsingItem(p_43407_);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if (p_43396_ instanceof Player player) {
            int i = this.getUseDuration(p_43394_) - p_43397_;
            if (i >= 10) {
                DevilsknifeEntity thrownKnife = new DevilsknifeEntity(p_43395_, player, p_43394_);
                thrownKnife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                thrownKnife.setXRot(player.getViewXRot(1.0f));
                thrownKnife.setYRot(player.getViewYRot(1.0f));

                if (player.getAbilities().instabuild) {
                    thrownKnife.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                p_43395_.addFreshEntity(thrownKnife);
                p_43395_.playSound((Player)null, thrownKnife, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(p_43394_);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        String tipItemName = stack.getItem().toString();
        tipItemName = this.getDescriptionId() + ".credits";
        components.add(Component.translatable("tooltip.yourmodname.sprite_credits_generic").append(Component.translatable(tipItemName)).withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(stack, level, components, flag);
    }
}
