package com.idtech.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class UnstableCrateBlock extends Block {
    public UnstableCrateBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return false;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);

        for (int x = -1; x <= 1; x++){
            for (int y = -1; y <= 1; y++){
                for (int z = -1; z <= 1; z++){
                    if (!(x == 0 && y == 0 && z == 0)){

                        BlockPos otherPos = pPos.north(x).above(y).east(z);

                        if (pLevel.getBlockState(otherPos).is(this.asBlock())){

                            pLevel.destroyBlock(otherPos, true);

                        }

                    }
                }
            }
        }

        pLevel.addParticle(ParticleTypes.EXPLOSION, pPos.getX(), pPos.getY(), pPos.getZ(), 0.0f, 0.0f, 0.0f);

    }
}
