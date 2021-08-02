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

import org.infernalstudios.infernalexp.init.IETags;

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
