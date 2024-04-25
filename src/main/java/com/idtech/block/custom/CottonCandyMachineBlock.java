package com.idtech.block.custom;

import com.idtech.BaseMod;
import com.idtech.block.entity.BlockEntityMod;
import com.idtech.block.entity.CottonCandyMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CottonCandyMachineBlock extends BaseEntityBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.".concat(BaseMod.MODID).concat(".cotton_candy_machine"));
    private static final int MAX_SUGAR = 256;
    private static final String SUGAR_NAME = "sugar";

    public CottonCandyMachineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CottonCandyMachineBlockEntity cc){
                cc.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CottonCandyMachineBlockEntity cc){
                NetworkHooks.openScreen((ServerPlayer) pPlayer, cc, pPos);
            }
            else{
                throw new IllegalStateException("Container provider is missing for the cotton candy machine!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, BlockEntityMod.COTTON_CANDY_MACHINE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> {
                    pBlockEntity.tick(pLevel1, pPos, pState1);
                }));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CottonCandyMachineBlockEntity(pPos, pState);
    }
}
