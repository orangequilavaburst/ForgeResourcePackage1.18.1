package com.idtech.utils;

import com.idtech.BaseMod;
import com.idtech.particle.ParticleMod;
import com.idtech.particle.custom.AfterImageParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BaseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ParticleMod.AFTER_IMAGE_PARTICLE.get(), AfterImageParticle.Provider::new);
    }

}
