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

package org.infernalstudios.infernalexp.world.gen.carvers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class GlowstoneRavineCarver extends CanyonWorldCarver {

    public GlowstoneRavineCarver(Codec<CanyonCarverConfiguration> config) {
        super(config);
    }

    /**
     * Ripped from NetherCarver so that this ravine does not create floating lava in the nether.
     */
    @Override
    protected boolean carveBlock(CarvingContext context, CanyonCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomePos, CarvingMask carvingMask, BlockPos.MutableBlockPos mutablePos, BlockPos.MutableBlockPos p_159294_, Aquifer aquifer, MutableBoolean mutableBoolean) {
        if (this.canReplaceBlock(config, chunk.getBlockState(mutablePos))) {
            BlockState blockstate;
            if (mutablePos.getY() <= context.getMinGenY() + 31) {
                blockstate = LAVA.createLegacyBlock();
            } else {
                blockstate = CAVE_AIR;
            }

            chunk.setBlockState(mutablePos, blockstate, false);

            return true;
        } else {
            return false;
        }
    }
}
