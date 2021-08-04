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

package org.infernalstudios.infernalexp.world.gen.features;

import java.util.Random;

import com.mojang.serialization.Codec;
import org.infernalstudios.infernalexp.blocks.DullthornsBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class DullthornsFeature extends Feature<NoFeatureConfig> {
    public DullthornsFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        int height = random.nextInt(9) + 1;
        int top = 0;

        if (!worldIn.isAirBlock(pos) || worldIn.getBlockState(pos.down()).getBlock() != IEBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            // Generate dullthorns up "height" blocks unless there is something in the way
            for (int i = 0; i < height; i++) {
                if (worldIn.isAirBlock(pos.up(i))) {
                    worldIn.setBlockState(pos.up(i), IEBlocks.DULLTHORNS.get().getDefaultState(), 10);
                    top = i;
                }
            }

            worldIn.setBlockState(pos.up(top), IEBlocks.DULLTHORNS.get().getDefaultState().with(DullthornsBlock.TIP, true), 10);

            return true;
        }
    }
}

