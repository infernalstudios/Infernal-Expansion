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
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItemUseContext;

import javax.annotation.Nullable;

public class GlowdustBlock extends SnowBlock {

    public GlowdustBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        if (blockstate.matchesBlock(this)) {
            int i = blockstate.get(LAYERS);
            if (i < 7) {
                return blockstate.with(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
            } else {
                return IEBlocks.GLOWDUST_SAND.get().getDefaultState();
            }
        } else {
            return super.getStateForPlacement(context);
        }
    }
}
