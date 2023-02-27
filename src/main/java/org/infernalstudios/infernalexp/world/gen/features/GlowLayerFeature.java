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

package org.infernalstudios.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import org.infernalstudios.infernalexp.blocks.GlowdustBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowLayerFeature extends Feature<NoFeatureConfig> {

    public GlowLayerFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable().setPos(pos);
        BlockPos.Mutable mutableBlockPosNeighbors = new BlockPos.Mutable().setPos(mutableBlockPos);
        boolean doExpandedPlacing = isMultipleBiomesInChunk(world, pos, mutableBlockPos);

        IChunk cachedChunk = world.getChunk(mutableBlockPos);
        int minimumRange = doExpandedPlacing ? -12 : 0;
        int maxRange = doExpandedPlacing ? 28 : 16;
        for (int x = minimumRange; x < maxRange; x++) {
            for (int z = minimumRange; z < maxRange; z++) {

                // Only check between top land and sealevel.
                // Prevents shimmer_sheet in caves below sealevel and better performance if player removes ceiling of Nether with datapack.
                int maxY = generator.getHeight(pos.getX() + x, pos.getZ() + z, Heightmap.Type.MOTION_BLOCKING);
                for (int y = maxY; y > generator.getSeaLevel(); y--) {
                    mutableBlockPos.setPos(pos).move(x, y, z);

                    // recache chunk if we need to. Faster performance this way when chunk scanning like we are.
                    if (cachedChunk.getPos().x != x >> 4 || cachedChunk.getPos().z != z >> 4) {
                        cachedChunk = world.getChunk(mutableBlockPos);
                    }

                    // Checks for if we are at shimmer_sheet sand and moves up to check for air space.
                    BlockState currentBlock = cachedChunk.getBlockState(mutableBlockPos);
                    if (currentBlock.matchesBlock(IEBlocks.SHIMMER_SAND.get()) &&
                        cachedChunk.getBlockState(mutableBlockPos.move(Direction.UP)).isAir()) {
                        // we are now in the air space above Glowdust sand. Check if any of the 8 blocks around it is shimmer_sheet sand
                        // maximum return is 8.
                        int shimmer_sheetLayerHeight = numberOfGlowdustSandNearby(world, mutableBlockPos, mutableBlockPosNeighbors);
                        if (shimmer_sheetLayerHeight > 0) {
                            world.setBlockState(mutableBlockPos, IEBlocks.SHIMMER_SHEET.get().getDefaultState().with(GlowdustBlock.LAYERS, shimmer_sheetLayerHeight), 3);
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean isMultipleBiomesInChunk(ISeedReader world, BlockPos pos, BlockPos.Mutable mutableBlockPos) {
        // check some edges of the chunk for invalid biomes to know when to
        // do better placement that follows biome borders better.
        Biome centerBiome = world.getBiome(mutableBlockPos.setPos(pos).move(8, 0, 8));
        for (int x = 0; x <= 16; x += 8) {
            for (int z = 0; z <= 16; z += 8) {
                if (x != 8 && z != 8) {
                    mutableBlockPos.setPos(pos);
                    mutableBlockPos.move(x, 0, z);

                    // move position back to edge of the chunk we are in instead of next chunk over
                    if (x == 16) mutableBlockPos.move(-1, 0, 0);
                    if (z == 16) mutableBlockPos.move(0, 0, -1);

                    if (centerBiome != world.getBiome(mutableBlockPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int numberOfGlowdustSandNearby(ISeedReader world, BlockPos.Mutable mutableBlockPos, BlockPos.Mutable mutableBlockPosNeighbors) {
        int shimmer_sheetSandCount = 0;
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x == 0 && z == 0) continue;

                mutableBlockPosNeighbors.setPos(mutableBlockPos).move(x, 0, z);
                BlockState neighborBlock = world.getBlockState(mutableBlockPosNeighbors);
                // Do not use .isSolid check because Glowdust Sand is marked notSolid (cause it uses Glowstone properties)
                if (neighborBlock.matchesBlock(IEBlocks.SHIMMER_SAND.get())) {
                    shimmer_sheetSandCount++;
                }
            }
        }
        // changes the shape and height of layers. Modify the algorithm for neat effects

        // change radius to 1 and use this version for only 1 layer high shimmer_sheet layer right next to ledges
        //return Math.min(shimmer_sheetSandCount, 1);

        // change radius to 2 and use this version for 2x2 barely sloping dust that looks neat
        return Math.min((int) Math.ceil((shimmer_sheetSandCount) / 6D), 8);
    }
}
