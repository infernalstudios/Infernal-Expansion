package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.init.IETags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.CheckForNull;

public class PlantedQuartzBlock extends HorizontalBushBlock {
    protected static final VoxelShape FLOOR_SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    protected static final VoxelShape CEILING_SHAPE = Block.makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape WALL_SHAPE_NORTH = Block.makeCuboidShape(2.0D, 2.0D, 4.0D, 14.0D, 14.0D, 16.0D);
    protected static final VoxelShape WALL_SHAPE_SOUTH = Block.makeCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 12.0D);
    protected static final VoxelShape WALL_SHAPE_EAST = Block.makeCuboidShape(0.0D, 2.0D, 2.0D, 12.0D, 14.0D, 14.0D);
    protected static final VoxelShape WALL_SHAPE_WEST = Block.makeCuboidShape(4.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);

    public PlantedQuartzBlock(Properties builder) {
        super(builder);
        this.setDefaultState(this.getDefaultState().with(FACE, AttachFace.FLOOR).with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock().isIn(IETags.Blocks.PLANTED_QUARTZ_BASE_BLOCKS);
    }

    @CheckForNull
    public BlockState getPlaceableState(World world, BlockPos pos, Direction placeSide) {
        if (world.getBlockState(pos).getMaterial().isReplaceable() && world.getBlockState(pos).getBlock() != IEBlocks.PLANTED_QUARTZ.get()) {
            Direction attachdirection;
            if (this.isValidGround(world.getBlockState(pos.offset(placeSide.getOpposite())), world, pos)) {
                attachdirection = placeSide.getOpposite();
            } else if (this.isValidGround(world.getBlockState(pos.offset(placeSide)), world, pos)) {
                attachdirection = placeSide;
            } else {
                return null;
            }
            AttachFace attachface;
            if (attachdirection == Direction.UP) {
                attachface = AttachFace.CEILING;
            } else if (attachdirection == Direction.DOWN) {
                attachface = AttachFace.FLOOR;
            } else {
                attachface = AttachFace.WALL;
            }
            if (attachface == AttachFace.WALL) {
                return this.getDefaultState().with(FACE, attachface).with(HORIZONTAL_FACING, attachdirection.getOpposite());
            } else {
                return this.getDefaultState().with(FACE, attachface);
            }
        }
        return null;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return canAttach(worldIn, pos, getFacing(state).getOpposite());
    }

    public boolean canAttach(IWorldReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction);
        return isValidGround(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACE)) {
            case WALL:
                switch (state.get(HORIZONTAL_FACING)) {
                    case NORTH:
                        return WALL_SHAPE_NORTH;
                    case SOUTH:
                        return WALL_SHAPE_SOUTH;
                    case EAST:
                        return WALL_SHAPE_EAST;
                    case WEST:
                    default:
                        return WALL_SHAPE_WEST;
                }
            case FLOOR :
                return FLOOR_SHAPE;
            case CEILING:
            default:
                return CEILING_SHAPE;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builderIn) {
        builderIn.add(HORIZONTAL_FACING, FACE);
    }

    @Override
    public Item asItem() {
        return Items.QUARTZ;
    }
}
