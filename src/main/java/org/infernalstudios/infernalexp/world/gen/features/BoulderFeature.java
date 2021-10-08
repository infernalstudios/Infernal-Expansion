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
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.util.ShapeUtil;

import java.util.Random;

public class BoulderFeature extends Feature<BlockStateConfiguration> {

    public BoulderFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        int radius = new int[]{1, 1, 2, 2, 2, 2, 3}[context.random().nextInt(7)];

        if (!context.level().isEmptyBlock(context.origin()) || context.level().getBlockState(context.origin().below()).getBlock() != IEBlocks.GLOWDUST_SAND.get() || context.level().isEmptyBlock(context.origin().below(radius)) || context.random().nextInt(3) == 2) {
            return false;
        } else {
            placeSphere(context.level(), context.random(), context.origin().below(Math.floorDiv(radius, 3)), radius, context.config());
            return true;
        }
    }

    private void placeSphere(WorldGenLevel world, Random random, BlockPos pos, int radius, BlockStateConfiguration config) {

        // Checks to see if block is within radius to the center of the sphere, if so, fill in blocks

        for (BlockPos point : ShapeUtil.generateSolidSphere(radius)) {
            world.setBlock(pos.offset(point), config.state, 2);

            // Add some randomness so that the boulders aren't perfect
            randomNoise(world, random, pos, config);
        }
    }

    private void randomNoise(WorldGenLevel world, Random random, BlockPos pos, BlockStateConfiguration config) {
        switch (random.nextInt(8)) {
            case 0:
                if (!world.getBlockState(pos.west().below()).isAir()) {
                    world.setBlock(pos.west(), config.state, 2);
                }
            case 1:
                if (!world.getBlockState(pos.north().below()).isAir()) {
                    world.setBlock(pos.north(), config.state, 2);
                }
            case 2:
                if (!world.getBlockState(pos.east().below()).isAir()) {
                    world.setBlock(pos.east(), config.state, 2);
                }
            case 3:
                if (!world.getBlockState(pos.south().below()).isAir()) {
                    world.setBlock(pos.south(), config.state, 2);
                }
            case 4:
                if (!world.getBlockState(pos.above().below()).isAir()) {
                    world.setBlock(pos.above(), config.state, 2);
                }
        }
    }
}
