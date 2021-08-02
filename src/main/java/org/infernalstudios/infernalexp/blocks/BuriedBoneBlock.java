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
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.CheckForNull;

public class BuriedBoneBlock extends HorizontalBushBlock {
    protected static final VoxelShape FLOOR_SHAPE = makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape CEILING_SHAPE = makeCuboidShape(5.0D, 6.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public BuriedBoneBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(FACE, AttachFace.FLOOR));
    }

    @CheckForNull
    public BlockState getPlaceableState(World world, BlockPos pos, Direction placeSide) {
        if (world.getBlockState(pos).getMaterial().isReplaceable() && world.getBlockState(pos).getBlock() != IEBlocks.BURIED_BONE.get()) {
            if (placeSide.getAxis() != Axis.Y) {
                placeSide = Direction.UP;
            }
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
            } else {
                attachface = AttachFace.FLOOR;
            }
            return this.getDefaultState().with(FACE, attachface);
        }
        return null;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock().isIn(IETags.Blocks.BURIED_BONE_BASE_BLOCKS);
    }

    public boolean canAttach(IWorldReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.offset(direction);
        return isValidGround(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return !state.get(FACE).equals(AttachFace.WALL) && canAttach(worldIn, pos, getFacing(state).getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vector3d = state.getOffset(worldIn, pos);

        switch (state.get(FACE)) {
            case FLOOR:
                return FLOOR_SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
            case CEILING:
            default:
                return CEILING_SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builderIn) {
        builderIn.add(HORIZONTAL_FACING, FACE);
    }

    @Override
    public Item asItem() {
        return Items.BONE;
    }
}
