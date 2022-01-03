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

package org.infernalstudios.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.infernalstudios.infernalexp.init.IEBlocks;

public abstract class IEStructure<F extends FeatureConfiguration> extends StructureFeature<F> {

    public IEStructure(Codec<F> codec) {
        super(codec);
    }

    public abstract StructureFeatureConfiguration getSeparationSettings();

    public abstract boolean shouldTransformLand();

    // Gets the y-value for where the structure will spawn
    public static int getYPos(ChunkGenerator chunkGenerator, int x, int z, WorldgenRandom random, LevelHeightAccessor levelHeightAccessor) {
        // Gets a random value between sea level and the max build height and grabs its column
        int y = chunkGenerator.getSeaLevel() + random.nextInt(chunkGenerator.getGenDepth() - 2 - chunkGenerator.getSeaLevel());
        NoiseColumn noiseColumn = chunkGenerator.getBaseColumn(x, z, levelHeightAccessor);

        BlockPos pos = new BlockPos(x, y, z);

        // Proceeds downwards through the column until it finds a space where there is a solid block with air above it and returns that height
        while (y > chunkGenerator.getSeaLevel()) {
            BlockState checkAir = noiseColumn.getBlockState(pos.below(y));
            BlockState checkBlock = noiseColumn.getBlockState(pos.below(y + 1));

            if (checkAir.isAir() && (checkBlock.is(IEBlocks.GLOWDUST_SAND.get()) || checkBlock.isFaceSturdy((BlockGetter) levelHeightAccessor, pos.below(y), Direction.UP))) {
                return y;
            }

            y--;
        }

        // Returns 0 if no valid space is found
        return 0;
    }

    public static abstract class IEStart<F extends FeatureConfiguration> extends StructureStart<F> {

        public IEStart(StructureFeature<F> structure, ChunkPos chunkPos, int reference, long seed) {
            super(structure, chunkPos, reference, seed);
        }

        // Gets the y-value for where the structure will spawn
        public static int getYPos(ChunkGenerator chunkGenerator, int x, int z, WorldgenRandom random, LevelHeightAccessor levelHeightAccessor) {
            // Gets a random value between sea level and the max build height and grabs its column
            int y = chunkGenerator.getSeaLevel() + random.nextInt(chunkGenerator.getGenDepth() - 2 - chunkGenerator.getSeaLevel());
            NoiseColumn noiseColumn = chunkGenerator.getBaseColumn(x, z, levelHeightAccessor);

            BlockPos pos = new BlockPos(x, y, z);

            // Proceeds downwards through the column until it finds a space where there is a solid block with air above it and returns that height
            while (y > chunkGenerator.getSeaLevel()) {
                BlockState checkAir = noiseColumn.getBlockState(pos.below(y));
                BlockState checkBlock = noiseColumn.getBlockState(pos.below(y + 1));

                if (checkAir.isAir() && (checkBlock.is(IEBlocks.GLOWDUST_SAND.get()) || checkBlock.isFaceSturdy((BlockGetter) levelHeightAccessor, pos.below(y), Direction.UP))) {
                    return y;
                }

                y--;
            }

            // Returns 0 if no valid space is found
            return 0;
        }
    }
}
