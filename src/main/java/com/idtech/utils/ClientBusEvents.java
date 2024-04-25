package com.idtech.utils;

import com.idtech.BaseMod;
import com.idtech.client.MenuTypeMod;
import com.idtech.client.screen.CottonCandyMachineScreen;
import com.idtech.particle.ParticleMod;
import com.idtech.particle.custom.AfterImageParticle;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BaseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusEvents {

    @SubscribeEvent
    public static void onClientStartup(FMLClientSetupEvent event){
        MenuScreens.register(MenuTypeMod.COTTON_CANDY_MACHINE_MENU.get(), CottonCandyMachineScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ParticleMod.AFTER_IMAGE_PARTICLE.get(), AfterImageParticle.Provider::new);
    }

}
