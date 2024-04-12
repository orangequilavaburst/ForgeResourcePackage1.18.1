package com.idtech.utils;

import com.idtech.BaseMod;
import com.idtech.particle.ParticleMod;
import com.idtech.particle.custom.AfterImageParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BaseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusEvents {

    public static void registerParticleFactories(RegisterParticleProvidersEvent event){
        //event.registerSpecial(ParticleMod.AFTER_IMAGE_PARTICLE.get(), AfterImageParticle.Provider::new);
    }

}
