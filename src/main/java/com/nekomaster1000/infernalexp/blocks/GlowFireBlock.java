package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.IETags;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class GlowFireBlock extends AbstractFireBlock {

    public GlowFireBlock(Properties builder) {
            super(builder, 2.0F);
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
            return this.isValidPosition(stateIn, worldIn, currentPos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
            return isGlowFireBase(worldIn.getBlockState(pos.down()).getBlock());
    }

    public static boolean isGlowFireBase(Block block) {
            return block.isIn(IETags.Blocks.GLOW_FIRE_BASE_BLOCKS);
    }

    protected boolean canBurn(BlockState stateIn) {
            return true;
    }

}
