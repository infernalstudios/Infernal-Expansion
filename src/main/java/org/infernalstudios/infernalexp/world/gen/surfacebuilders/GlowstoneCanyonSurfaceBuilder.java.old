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

package org.infernalstudios.infernalexp.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import org.infernalstudios.infernalexp.init.IEBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.Random;

public class GlowstoneCanyonSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
    public GlowstoneCanyonSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> p_i232136_1_) {
        super(p_i232136_1_);
    }


    @Override
    public void apply(Random random, ChunkAccess chunk, Biome biome, int x, int z, int terrainHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, int unknown, long seed, SurfaceBuilderBaseConfiguration config) {
        this.buildSurface(random, chunk, biome, x, z, terrainHeight, noise, defaultBlock, defaultFluid, config.getTopMaterial(), config.getUnderMaterial(), config.getUnderwaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, ChunkAccess chunk, Biome biome, int x, int z, int terrainHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState underwaterBlock, int seaLevel) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int depth = -1; // Will be used to know how deep we are in solid blocks so we know when to stop placing middleBlock
        int middleBlockExtraDepth = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);

        // Start at top land and loop downward
        for (int y = terrainHeight; y >= 0; --y) {

            // Get the block in the world (Nether will always give Netherrack, Lava, or Air)
            mutable.set(x, y, z);
            BlockState currentBlockInWorld = chunk.getBlockState(mutable);

            // Reset the depth counter as we are not in land anymore
            if (currentBlockInWorld.isAir()) {
                depth = -1;
            } else if (currentBlockInWorld.getFluidState().isEmpty() && currentBlockInWorld.getBlock() != Blocks.BEDROCK) {
                // We are in solid land now if fluid check fails. Skip Bedrock as we shouldn't replace that

                // -1 depth means we are switching from air to solid land. Place the surface block now
                if (depth == -1) {
                    depth = 0;

                    if (y >= seaLevel) {
                        // The typical surface of the biome.
                        BlockState blockBelow = chunk.getBlockState(mutable.below());

                        // If the block is floating, make it Glimmer Gravel, otherwise let it be the regular surface
                        if (blockBelow.isAir()) {
                            chunk.setBlockState(mutable, IEBlocks.TRAPPED_GLOWDUST_SAND.get().defaultBlockState(), false);
                        } else {
                            chunk.setBlockState(mutable, topBlock, false);
                        }
                    } else {
                        // Makes the blocks at sealevel be dullstone to make cool border with lava.
                        if (random.nextInt(70) == 1) {
                            chunk.setBlockState(mutable, IEBlocks.DIMSTONE.get().defaultBlockState(), false);
                        } else {
                            chunk.setBlockState(mutable, IEBlocks.DULLSTONE.get().defaultBlockState(), false);
                        }
                    }

                    // Make this a feature instead so it places the layers correctly on edges and slopes unlike Surface Builders.
//                    // Place glowdust above if there is room as surface block is glowdust sand
//                    if(chunk.getBlockState(mutable.up()).isAir()){
//                        chunk.setBlockState(mutable.up(), IEBlocks.GLOWDUST.get().getDefaultState(), false);
//                    }
                } else if (depth <= 2 + middleBlockExtraDepth) {
                    // Place block only when under surface and down to as deep as the scaledNoise says to go.
                    // Increment depth to keep track of how deep we have gone
                    chunk.setBlockState(mutable, middleBlock, false);
                } else {
                    // replaces all underground solid blocks with dullstone/dimstone mix
                    if (random.nextInt(50) == 1) {
                        chunk.setBlockState(mutable, IEBlocks.DIMSTONE.get().defaultBlockState(), false);
                    } else {
                        chunk.setBlockState(mutable, IEBlocks.DULLSTONE.get().defaultBlockState(), false);
                    }
                }

                // Increment depth so we can start placing middle blocks when moving down next loop as we go deeper
                depth++;
            }
        }
    }
}
