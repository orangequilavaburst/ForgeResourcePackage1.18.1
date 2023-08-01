package com.idtech.sound;

import com.idtech.BaseMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundMod {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BaseMod.MODID);

    // sounds

    public static final RegistryObject<SoundEvent> HONESTY_TOTEM_USE = registerSoundEvent("honesty_totem_use");
    public static final RegistryObject<SoundEvent> FAZER_BLASTER = registerSoundEvent("fazer_blaster");
    public static final RegistryObject<SoundEvent> MEGA_BUSTER_SHOT = registerSoundEvent("mega_buster_shot");
    public static final RegistryObject<SoundEvent> MEGA_BUSTER_HALF_CHARGED_SHOT = registerSoundEvent("mega_buster_half_charged_shot");
    public static final RegistryObject<SoundEvent> MEGA_BUSTER_CHARGED_SHOT = registerSoundEvent("mega_buster_charged_shot");
    public static final RegistryObject<SoundEvent> MEGA_BUSTER_CHARGING = registerSoundEvent("mega_buster_charging");
    public static final RegistryObject<SoundEvent> MEGA_BUSTER_CHARGED = registerSoundEvent("mega_buster_charged");
    public static final RegistryObject<SoundEvent> FREDDY_MASK_EQUIP = registerSoundEvent("freddy_mask_equip");
    public static final RegistryObject<SoundEvent> FREDDY_MASK_REMOVE = registerSoundEvent("freddy_mask_remove");
    public static final RegistryObject<SoundEvent> FREDDY_MASK_BREATHING = registerSoundEvent("freddy_mask_breathing");

    // register

    private static RegistryObject<SoundEvent> registerSoundEvent(String name, float pitchRange){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(BaseMod.MODID, name), pitchRange));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(BaseMod.MODID, name), 1.0f));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }

}
