package com.idtech.item.custom;

import com.idtech.BaseMod;
import com.idtech.entity.model.FazbearMaskModel;
import com.idtech.item.ArmorMaterialMod;
import com.idtech.sound.SoundMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class FreddyMaskItem extends ArmorItem {

    private boolean breathed;
    public FreddyMaskItem(Properties p_40388_) {
        super(ArmorMaterialMod.FREDDY_MASK, Type.HELMET, p_40388_);
        this.breathed = false;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {

        //player.playSound(SoundMod.FREDDY_MASK_BREATHING.get(), 0.2f, 1.0f);

        super.onArmorTick(stack, level, player);
    }

    // thanks Barion on Discord!!
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
                //HumanoidModel<? extends LivingEntity> model = new FazbearMaskModel<>();
                return new FazbearMaskModel<>();
            }

            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
                HumanoidModel<?> replacement = getHumanoidArmorModel(livingEntity, itemStack, slot, original);
                if (replacement == original){
                    return original;
                }

                setModelProperties(replacement, original, slot);
                return replacement;
            }

            @SuppressWarnings("unchecked")
            public static <T extends LivingEntity> void setModelProperties(HumanoidModel<?> replacement, HumanoidModel<T> original, EquipmentSlot slot){
                original.copyPropertiesTo((HumanoidModel<T>) replacement);
                replacement.head.visible = slot == EquipmentSlot.HEAD;
                replacement.hat.visible = slot == EquipmentSlot.HEAD;
                replacement.body.visible = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS;
                replacement.rightArm.visible = slot == EquipmentSlot.CHEST;
                replacement.leftArm.visible = slot == EquipmentSlot.CHEST;
                replacement.rightLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
                replacement.leftLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return BaseMod.MODID + ":textures/models/armor/freddy_fazbear_mask_layer_0.png";
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide()) {
            if (pSlotId == EquipmentSlot.HEAD.getIndex()) {
                if (!this.breathed) {
                    pEntity.playSound(SoundMod.FREDDY_MASK_BREATHING.get(), 0.2f, 1.0f);
                    this.breathed = true;
                }
            } else {
                this.breathed = false;
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
