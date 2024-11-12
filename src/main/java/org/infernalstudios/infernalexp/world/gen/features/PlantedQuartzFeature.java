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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.infernalstudios.infernalexp.blocks.PlantedQuartzBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.world.gen.features.config.PlantedQuartzFeatureConfig;

public class PlantedQuartzFeature extends IEFeature<PlantedQuartzFeatureConfig> {

    private static final int MAX_AMOUNT = 15;

    public PlantedQuartzFeature(Codec<PlantedQuartzFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean placeFeature(FeaturePlaceContext<PlantedQuartzFeatureConfig> context) {
        int amount = 0;

        // Attempt to place quartz 128 times
        for (int j = 0; j < 128; j++) {
            // Randomize the direction the quartz is facing
            int random = context.random().nextInt(6);

            // Randomize the location of the next quartz to be placed
            BlockPos pos = context.origin().offset(context.random().nextInt(17) - 8, context.random().nextInt(9) - 4, context.random().nextInt(17) - 8);
            BlockState state = IEBlocks.PLANTED_QUARTZ.get().defaultBlockState().setValue(PlantedQuartzBlock.FACE, AttachFace.values()[(Math.min(random, 2) + 2) % 3]); // This gives AttachFace.WALL a greater chance of being picked

            if (random >= 2) {
                state.setValue(PlantedQuartzBlock.FACING, Direction.values()[random]);
            }

            // If the state can't survive, try generating again
            if (!state.canSurvive(context.level(), pos))
                continue;

            // If it's a valid location, attempt a generation
            if (context.level().isEmptyBlock(pos) && state.canSurvive(context.level(), pos)) {
                // If there is quartz nearby or the chance to generate passes, generate it
                float chance = context.random().nextFloat();
                if (findOre(context.level(), pos) || chance > context.config().chanceToFail) {
                    context.level().setBlock(pos, state, 2);
                    amount++;
                }
            }

            // If we have placed the max amount of quartz, then return
            if (amount >= MAX_AMOUNT) {
                return true;
            }
        }

        return false;
    }

    @Override
    boolean shouldPlaceOnStructures() {
        return true;
    }

    public boolean findOre(WorldGenLevel world, BlockPos pos) {
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
