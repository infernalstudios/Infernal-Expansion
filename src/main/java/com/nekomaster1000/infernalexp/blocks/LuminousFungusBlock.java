package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class LuminousFungusBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);

    public LuminousFungusBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(ModBlocks.GLOWDUST_SAND.get()) || state.isIn(Blocks.SAND) || state.isIn(Blocks.RED_SAND) || state.isIn(ModBlocks.DULLSTONE.get());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);
        return SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }


}
