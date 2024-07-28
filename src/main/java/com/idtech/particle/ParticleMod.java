package com.idtech.particle;

import com.idtech.BaseMod;
import com.idtech.particle.custom.AfterImageParticle;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ParticleMod {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BaseMod.MODID);

    public static final RegistryObject<ParticleType<AfterImageParticle.AfterImageParticleOptions>> AFTER_IMAGE_PARTICLE =
            PARTICLE_TYPES.register("after_image", () -> new ParticleType<>(false, AfterImageParticle.AfterImageParticleOptions.DESERIALIZER) {
                @Override
                public @NotNull Codec<AfterImageParticle.AfterImageParticleOptions> codec() {
                    return null;
                }
            });

    public static final RegistryObject<SimpleParticleType> CONFETTI_PARTICLE =
            PARTICLE_TYPES.register("confetti", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }

}
