package com.nekomaster1000.infernalexp.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class GlowSandBlock extends SandBlock {

    public GlowSandBlock(int dustColorIn, Properties properties) {
        super(dustColorIn, properties);
    }

    /**
     * Override this method to return true if the block should behave like an infinite fire source
     */
    @Override
    public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
        return true;
    }
}
