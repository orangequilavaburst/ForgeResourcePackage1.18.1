package com.idtech.block.entity;

import com.idtech.BaseMod;
import com.idtech.block.BlockMod;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityMod {

    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BaseMod.MODID);

    public static final RegistryObject<BlockEntityType<CottonCandyMachineBlockEntity>> COTTON_CANDY_MACHINE =
            BLOCK_ENTITIES.register("cotton_candy_machine_block_entity", () ->
                    BlockEntityType.Builder.of(CottonCandyMachineBlockEntity::new, BlockMod.COTTON_CANDY_MACHINE.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
