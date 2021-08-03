/*
 * Copyright 2021 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.blocks;

import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IETags;
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
    protected static final VoxelShape FLOOR_SHAPE = makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
    protected static final VoxelShape CEILING_SHAPE = makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape WALL_SHAPE_NORTH = makeCuboidShape(2.0D, 2.0D, 4.0D, 14.0D, 14.0D, 16.0D);
    protected static final VoxelShape WALL_SHAPE_SOUTH = makeCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 12.0D);
    protected static final VoxelShape WALL_SHAPE_EAST = makeCuboidShape(0.0D, 2.0D, 2.0D, 12.0D, 14.0D, 14.0D);
    protected static final VoxelShape WALL_SHAPE_WEST = makeCuboidShape(4.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);

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
            case FLOOR:
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
