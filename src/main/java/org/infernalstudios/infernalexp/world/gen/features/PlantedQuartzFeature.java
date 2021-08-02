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

import com.mojang.serialization.Codec;
import org.infernalstudios.infernalexp.blocks.PlantedQuartzBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.world.gen.features.config.PlantedQuartzFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlantedQuartzFeature extends Feature<PlantedQuartzFeatureConfig> {

    public PlantedQuartzFeature(Codec<PlantedQuartzFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos pos, PlantedQuartzFeatureConfig config) {
        int i = 0;
        int amount = 15;

        // Attempt to place quartz 256 times
        for (int j = 0; j < 256; j++) {
            // Randomize the location of the next quartz to be placed
            BlockPos blockpos = pos.add(random.nextInt(10) - random.nextInt(20), random.nextInt(4) - random.nextInt(8), random.nextInt(10) - random.nextInt(20));
            List<BlockState> allowedBlockstates = new ArrayList<BlockState>(6);

            BlockState iterState;
            iterState = IEBlocks.PLANTED_QUARTZ.get().getDefaultState().with(PlantedQuartzBlock.FACE, AttachFace.FLOOR);
            if (iterState.isValidPosition(worldIn, blockpos)) allowedBlockstates.add(iterState);
            iterState = IEBlocks.PLANTED_QUARTZ.get().getDefaultState().with(PlantedQuartzBlock.FACE, AttachFace.CEILING);
            if (iterState.isValidPosition(worldIn, blockpos)) allowedBlockstates.add(iterState);
            for (int k = 0; k < 4; k++) {
                Direction iterDirection;
                if (i == 0) {
                    iterDirection = Direction.NORTH;
                } else if (i == 1) {
                    iterDirection = Direction.SOUTH;
                } else if (i == 2) {
                    iterDirection = Direction.EAST;
                } else {
                    iterDirection = Direction.WEST;
                }
                iterState = IEBlocks.PLANTED_QUARTZ.get().getDefaultState().with(PlantedQuartzBlock.FACE, AttachFace.CEILING).with(PlantedQuartzBlock.HORIZONTAL_FACING, iterDirection);
                if (iterState.isValidPosition(worldIn, blockpos)) allowedBlockstates.add(iterState);
            }
            if (allowedBlockstates.size() < 1) {
                continue;
            }
            BlockState state = allowedBlockstates.get(random.nextInt(allowedBlockstates.size()));
            // If it's a valid location, attempt a generation
            if (worldIn.isAirBlock(blockpos) && state.isValidPosition(worldIn, blockpos)) {
                // If there is quartz nearby or the chance to generate passes, generate it
                float chance = random.nextFloat();
                if (findOre(worldIn, blockpos) || chance > config.chanceToFail) {
                    worldIn.setBlockState(blockpos, state, 2);
                    i++;
                }
            }

            // If we have placed the max amount of quartz, then return
            if (i >= amount) {
                return true;
            }
        }

        return false;
    }

    public boolean findOre(ISeedReader world, BlockPos pos) {
        final int radius = 3;
        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y < pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    if (world.getBlockState(new BlockPos(x, y, z)).getBlock().equals(Blocks.NETHER_QUARTZ_ORE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
