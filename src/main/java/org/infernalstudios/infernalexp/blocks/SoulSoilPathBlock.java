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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SoulSoilPathBlock extends GrassPathBlock {

    public SoulSoilPathBlock(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return !this.getDefaultState().isValidPosition(context.getWorld(), context.getPos()) ? Block.nudgeEntitiesWithNewState(this.getDefaultState(), Blocks.SOUL_SOIL.getDefaultState(), context.getWorld(), context.getPos()) : super.getStateForPlacement(context);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockState(pos, nudgeEntitiesWithNewState(state, Blocks.SOUL_SOIL.getDefaultState(), worldIn, pos));
    }
}
