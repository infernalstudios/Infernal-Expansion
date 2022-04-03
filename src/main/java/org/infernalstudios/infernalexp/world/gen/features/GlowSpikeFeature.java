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
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.util.ShapeUtil;
import org.infernalstudios.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;

import java.util.List;
import java.util.Random;

public class GlowSpikeFeature extends Feature<GlowSpikeFeatureConfig> {

    public GlowSpikeFeature(Codec<GlowSpikeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<GlowSpikeFeatureConfig> context) {
        if ((context.level().isEmptyBlock(context.origin()) || context.level().getBlockState(context.origin().below()).getBlock() == IEBlocks.GLOWDUST_SAND.get()) || (context.level().getBlockState(context.origin()).getBlock() == Blocks.LAVA && context.level().getBlockState(context.origin().below()).getBlock() != Blocks.LAVA)) {
            // Generate a random height, diameter and offset
            int height = context.config().minHeight + context.random().nextInt(context.config().maxHeight - context.config().minHeight);
            int diameter = context.config().minDiameter + context.random().nextInt(context.config().maxDiameter - context.config().minDiameter);
            int xOffset = -context.config().maxXOffset + context.random().nextInt(context.config().maxXOffset * 2);
            int zOffset = -context.config().maxZOffset + context.random().nextInt(context.config().maxZOffset * 2);

            List<BlockPos> points = ShapeUtil.generateSolidCircle((float) diameter / 2);

            // Check to see if the glowspike will generate on the ground instead of
            // floating, if not, move one down and try again
            for (BlockPos point : points) {
                BlockPos pointPos = new BlockPos(context.origin().getX() + point.getX(), context.origin().getY(), context.origin().getZ() + point.getZ());

                if (context.level().getBlockState(pointPos.below()).isAir()) {
                    if (context.random().nextBoolean() && context.random().nextBoolean()) {
                        return place(new FeaturePlaceContext<>(context.topFeature(), context.level(), context.chunkGenerator(), context.random(), context.origin().below(), context.config()));
                    }
                    return false;
                } else if (context.level().getBlockState(pointPos.below()).getBlock() == Blocks.LAVA) {
                    return place(new FeaturePlaceContext<>(context.topFeature(), context.level(), context.chunkGenerator(), context.random(), context.origin().below(), context.config()));
                }
            }

            // Place lines from each point in base to the peak point
            for (BlockPos point : points) {
                placeGlowSpikeLine(context.level(), context.origin().offset(point.getX(), 0, point.getZ()), context.origin().offset(xOffset, height, zOffset), context.random(), context.config());
            }

            return true;
        } else {
            return false;
        }
    }

    private void placeGlowSpikeLine(WorldGenLevel world, BlockPos startPos, BlockPos endPos, Random random, GlowSpikeFeatureConfig config) {
        List<BlockPos> line = ShapeUtil.generateLine(startPos, endPos);

        for (int i = 0; i < line.size(); i++) {
            BlockPos pos = line.get(i);

            // Skip parts of the spike generating above bedrock
            if (pos.getY() >= 128 || world.getBlockState(pos).equals(Blocks.BEDROCK.defaultBlockState())) {
                continue;
            }

            // Finds what percentage of the line has been built and then adds some
            // randomness to it to make for a
            // more gradual change between blocks
            float percentage = (((float) i / line.size()) - config.blockDitheringAmount / 2) + (random.nextFloat() * config.blockDitheringAmount);

            // Generate blocks from dullstone to dimstone to glowstone, or reversed if
            // darkAtTop is true
            if (percentage <= 0.33) {
                if (config.darkAtTop)
                    world.setBlock(pos, Blocks.GLOWSTONE.defaultBlockState(), 2);
                else
                    world.setBlock(new BlockPos(pos), IEBlocks.DULLSTONE.get().defaultBlockState(), 2);
            } else if (percentage > 0.33 && percentage <= 0.66) {
                world.setBlock(new BlockPos(pos), IEBlocks.DIMSTONE.get().defaultBlockState(), 2);
            } else {
                if (config.darkAtTop)
                    world.setBlock(new BlockPos(pos), IEBlocks.DULLSTONE.get().defaultBlockState(), 2);
                else
                    world.setBlock(new BlockPos(pos), Blocks.GLOWSTONE.defaultBlockState(), 2);
            }
        }
    }
}
