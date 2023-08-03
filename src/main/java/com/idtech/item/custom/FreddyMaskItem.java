package com.idtech.item.custom;

import com.idtech.entity.model.FazbearMaskModel;
import com.idtech.item.ArmorMaterialMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FreddyMaskItem extends ArmorItem {
    public FreddyMaskItem(Properties p_40388_) {
        super(ArmorMaterialMod.FREDDY_MASK, Type.HELMET, p_40388_);
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


}
