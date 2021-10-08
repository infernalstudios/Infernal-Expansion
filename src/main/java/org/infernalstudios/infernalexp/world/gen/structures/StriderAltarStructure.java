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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
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

public class StriderAltarStructure extends IEStructure<NoneFeatureConfiguration> {
    public StriderAltarStructure(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return StriderAltarStructure.Start::new;
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public StructureFeatureConfiguration getSeparationSettings() {
        return new StructureFeatureConfiguration(10, 5, 80837592);
    }

    @Override
    public boolean shouldTransformLand() {
        return true;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeProvider, long seed, WorldgenRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, NoneFeatureConfiguration config, LevelHeightAccessor levelHeightAccessor) {

        // Makes cheap check first, if it passes this check, it does a more in-depth check
        if (super.isFeatureChunk(chunkGenerator, biomeProvider, seed, chunkRandom, chunkPos, biome, chunkPos2, config, levelHeightAccessor)) {

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(chunkPos.x << 4, chunkGenerator.getSeaLevel(), chunkPos.z << 4);
            NoiseColumn noiseColumn = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ(), levelHeightAccessor);

            // Makes sure there are at least 20 blocks of air above the lava ocean to spawn the altar
            int minValidSpace = 20;
            int maxHeight = Math.min(chunkGenerator.getGenDepth(), chunkGenerator.getSeaLevel() + minValidSpace);

            while (mutable.getY() < maxHeight) {
                BlockState state = noiseColumn.getBlockState(mutable);
                if (!state.isAir()) {
                    return false;
                }
                mutable.move(Direction.UP);
            }
        } else {
            return false;
        }

        return true;
    }

    public static class Start extends IEStart<NoneFeatureConfiguration> {
        public Start(StructureFeature<NoneFeatureConfiguration> structure, ChunkPos chunkPos, int reference, long seed) {
            super(structure, chunkPos, reference, seed);
        }

        public int getLavaY(ChunkGenerator chunkGenerator, int x, int z, LevelHeightAccessor levelHeightAccessor) {
            int y = chunkGenerator.getSeaLevel();
            NoiseColumn noiseColumn = chunkGenerator.getBaseColumn(x, z, levelHeightAccessor);

            BlockPos pos = new BlockPos(x, y, z);

            int topDown = 1;

            if (noiseColumn.getBlockState(pos).canOcclude()) {
                return 0;
            }

            while (topDown <= y) {
                BlockState checkLava = noiseColumn.getBlockState(pos.below(topDown));
                BlockState checkBlock = noiseColumn.getBlockState(pos.below(topDown + 1));

                if (checkLava.is(Blocks.LAVA) && checkBlock.isFaceSturdy((BlockGetter) levelHeightAccessor, pos.below(y), Direction.UP)) {
                    return y - topDown;
                }

                topDown++;
            }

            return 0;
        }


        @Override
        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManager, ChunkPos chunkPos, Biome biome, NoneFeatureConfiguration config, LevelHeightAccessor levelHeightAccessor) {
            int x = chunkPos.x << 4;
            int z = chunkPos.z << 4;

            BlockPos pos = new BlockPos(x, getLavaY(chunkGenerator, x, z, levelHeightAccessor), z);

            if (pos.getY() != 0) {
                JigsawPlacement.addPieces(
                    dynamicRegistryManager,
                    new JigsawConfiguration(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(InfernalExpansion.MOD_ID, "strider_altar/start_pool")), 1),
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
