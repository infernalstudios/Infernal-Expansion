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

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;

public class ShroomlightFungusBlock extends HorizontalBushBlock {
    protected static final VoxelShape FLOOR_SHAPE = box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape CEILING_SHAPE = box(5.0D, 6.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public ShroomlightFungusBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACE, AttachFace.FLOOR).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected boolean isValidGround(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return
            state.is(Blocks.GRASS_BLOCK) ||
                state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.FARMLAND) ||
                state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM) ||

                state.is(Blocks.NETHER_WART_BLOCK) || state.is(Blocks.WARPED_WART_BLOCK) ||
                state.is(Blocks.CRIMSON_NYLIUM) || state.is(Blocks.WARPED_NYLIUM) ||

                state.is(IEBlocks.CRIMSON_FUNGUS_CAP.get()) || state.is(IEBlocks.WARPED_FUNGUS_CAP.get()) ||
                state.is(IEBlocks.LUMINOUS_FUNGUS_CAP.get()) ||

                state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL) ||

                state.is(Blocks.SHROOMLIGHT);
    }

    public boolean canAttachToSurface(LevelReader reader, BlockPos pos, Direction direction) {
        BlockPos blockpos = pos.relative(direction);
        return isValidGround(reader.getBlockState(blockpos), reader, blockpos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return !state.getValue(FACE).equals(AttachFace.WALL) && canAttachToSurface(worldIn, pos, getConnectedDirection(state).getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 vector3d = state.getOffset(worldIn, pos);

        switch (state.getValue(FACE)) {
            case FLOOR:
                return FLOOR_SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
            case CEILING:
            default:
                return CEILING_SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builderIn) {
        builderIn.add(FACING, FACE);
    }
}
