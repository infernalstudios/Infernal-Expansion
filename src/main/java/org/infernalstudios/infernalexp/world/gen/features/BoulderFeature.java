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

import java.util.Random;

import com.mojang.serialization.Codec;

import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.util.ShapeUtil;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class BoulderFeature extends Feature<BlockStateFeatureConfig> {

    public BoulderFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, BlockStateFeatureConfig config) {
        int radius = new int[]{1, 1, 2, 2, 2, 2, 3}[random.nextInt(7)];

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != IEBlocks.SHIMMER_SAND.get() || world.isAirBlock(pos.down(radius)) || random.nextInt(3) == 2) {
            return false;
        } else {
            placeSphere(world, random, pos.down(Math.floorDiv(radius, 3)), radius, config);
            return true;
        }
    }

    private void placeSphere(ISeedReader world, Random random, BlockPos pos, int radius, BlockStateFeatureConfig config) {

        // Checks to see if block is within radius to the center of the sphere, if so, fill in blocks

        for (BlockPos point : ShapeUtil.generateSolidSphere(radius)) {
            world.setBlockState(pos.add(point), config.state, 2);

            // Add some randomness so that the boulders aren't perfect
            randomNoise(world, random, pos, config);
        }
    }

    private void randomNoise(ISeedReader world, Random random, BlockPos pos, BlockStateFeatureConfig config) {
        switch (random.nextInt(8)) {
            case 0:
                if (!world.getBlockState(pos.west().down()).isAir()) {
                    world.setBlockState(pos.west(), config.state, 2);
                }
            case 1:
                if (!world.getBlockState(pos.north().down()).isAir()) {
                    world.setBlockState(pos.north(), config.state, 2);
                }
            case 2:
                if (!world.getBlockState(pos.east().down()).isAir()) {
                    world.setBlockState(pos.east(), config.state, 2);
                }
            case 3:
                if (!world.getBlockState(pos.south().down()).isAir()) {
                    world.setBlockState(pos.south(), config.state, 2);
                }
            case 4:
                if (!world.getBlockState(pos.up().down()).isAir()) {
                    world.setBlockState(pos.up(), config.state, 2);
                }
        }
    }
}
