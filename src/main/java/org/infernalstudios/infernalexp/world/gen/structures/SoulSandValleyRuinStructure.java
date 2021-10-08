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

package org.infernalstudios.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.infernalstudios.infernalexp.InfernalExpansion;

public class SoulSandValleyRuinStructure extends IEStructure<NoneFeatureConfiguration> {
    public SoulSandValleyRuinStructure(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return SoulSandValleyRuinStructure.Start::new;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public StructureFeatureConfiguration getSeparationSettings() {
        return new StructureFeatureConfiguration(4, 2, 29456392);
    }

    @Override
    public boolean shouldTransformLand() {
        return true;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeProvider, long seed, WorldgenRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, NoneFeatureConfiguration config, LevelHeightAccessor levelHeightAccessor) {
        WorldgenRandom random = new WorldgenRandom(seed + (chunkPos.x * (chunkPos.z * 17L)));

        // Makes cheap check first, if it passes this check, it does a more in-depth check
        if (super.isFeatureChunk(chunkGenerator, biomeProvider, seed, chunkRandom, chunkPos, biome, chunkPos2, config, levelHeightAccessor)) {

            int posX = chunkPos.x << 4;
            int posZ = chunkPos.z << 4;
            int posY = getYPos(chunkGenerator, posX, posZ, random, levelHeightAccessor);

            // Checks 9 points within a small area of the spawn location
            for (int curX = posX - 5; curX <= posX + 5; curX += 5) {
                for (int curZ = posZ - 5; curZ <= posZ + 5; curZ += 5) {

                    // Starts 5 blocks below to check for solid land in each column
                    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(curX, posY - 5, curZ);
                    NoiseColumn noiseColumn = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ(), levelHeightAccessor);

                    // Flag represents a block with air above it
                    boolean flag = false;

                    while (mutable.getY() <= posY + 5) {
                        BlockState state = noiseColumn.getBlockState(mutable);
                        if (state.canOcclude()) {
                            mutable.move(Direction.UP);
                            state = noiseColumn.getBlockState(mutable);

                            if (state.isAir()) {
                                flag = true;
                                break;
                            }

                        } else {
                            mutable.move(Direction.UP);
                        }
                    }

                    // If there is no block with air above it in this range, the structure can't spawn
                    if (!flag) {
                        return false;
                    }

                    // Checks if there are 55 blocks of air above the 5 checked for solid to spawn the structure
                    int minValidSpace = 5;
                    int maxHeight = Math.min(chunkGenerator.getGenDepth(), posY + minValidSpace);

                    while (mutable.getY() < maxHeight) {
                        BlockState state = noiseColumn.getBlockState(mutable);
                        if (!state.isAir()) {
                            return false;
                        }
                        mutable.move(Direction.UP);
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    public static class Start extends IEStart<NoneFeatureConfiguration> {
        private final long seed;

        public Start(StructureFeature<NoneFeatureConfiguration> structure, ChunkPos chunkPos, int reference, long seed) {
            super(structure, chunkPos, reference, seed);
            this.seed = seed;
        }

        @Override
        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManager, ChunkPos chunkPos, Biome biome, NoneFeatureConfiguration config, LevelHeightAccessor levelHeightAccessor) {
            WorldgenRandom random = new WorldgenRandom(seed + (chunkPos.x * (chunkPos.z * 17L)));
            int x = chunkPos.x << 4;
            int z = chunkPos.z << 4;

            BlockPos pos = new BlockPos(x, getYPos(chunkGenerator, x, z, random, levelHeightAccessor), z);

            if (pos.getY() != 0) {
                JigsawPlacement.addPieces(
                    dynamicRegistryManager,
                    new JigsawConfiguration(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(InfernalExpansion.MOD_ID, "soul_sand_valley_ruin/start_pool")), 1),
                    PoolElementStructurePiece::new,
                    chunkGenerator,
                    templateManager,
                    pos,
                    this,
                    this.random,
                    false,
                    false,
                    levelHeightAccessor
                );

                this.createBoundingBox();
            }
        }
    }
}
