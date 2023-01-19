/*
 * Copyright 2022 Infernal Studios
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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class NetherrackPathBlock extends DirtPathBlock {

    public NetherrackPathBlock(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), Blocks.NETHERRACK.defaultBlockState(), context.getLevel(), context.getClickedPos()) : super.getStateForPlacement(context);
    }

    @Override
    public void tick(@NotNull BlockState state, ServerLevel worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, Blocks.NETHERRACK.defaultBlockState(), worldIn, pos));
    }
}
