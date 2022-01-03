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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.util.ShapeUtil;

public class SinkHoleFeature extends Feature<NoneFeatureConfiguration> {
    public SinkHoleFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static final int minRadius = 2;
    private static final int maxRadius = 4;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos().set(context.origin());
        if (!context.level().isEmptyBlock(mutableBlockPos) || context.level().getBlockState(mutableBlockPos.move(Direction.DOWN)).getBlock() != IEBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            int radius = minRadius + context.random().nextInt(maxRadius - minRadius);
            int depth = 10 + context.random().nextInt(6);

            // Check to see if we are on the bottom layer of the hole
            mutableBlockPos.set(context.origin());
            for (int y = 1; y < depth; y++) {
                mutableBlockPos.move(Direction.DOWN);
                if (context.level().getBlockState(mutableBlockPos).is(Blocks.AIR)) return false;
            }

            // Build the walls down a few blocks so if the sink hole spawns on a slope it isn't open from the side
            for (BlockPos point : ShapeUtil.generateSolidCircle(radius + 1)) {
                mutableBlockPos.set(context.origin());
                mutableBlockPos.move(point.getX(), point.getY(), point.getZ());
                for (int y = 0; y < 3; y++) {
                    context.level().setBlock(mutableBlockPos.move(Direction.DOWN), IEBlocks.GLOWDUST_SAND.get().defaultBlockState(), 2);
                }
            }

            // Dig down by depth
            for (BlockPos point : ShapeUtil.generateSolidCircle(radius)) {
                mutableBlockPos.set(context.origin());
                mutableBlockPos.move(point.getX(), point.getY(), point.getZ());
                mutableBlockPos.move(Direction.DOWN);
                context.level().setBlock(mutableBlockPos, IEBlocks.TRAPPED_GLOWDUST_SAND.get().defaultBlockState(), 2);

                for (int y = 2; y < depth; y++) {
                    mutableBlockPos.move(Direction.DOWN);
                    carveSpot(context.level(), context.chunkGenerator(), mutableBlockPos);
                }
            }

            // Round off the bottom with a sphere
            for (BlockPos point : ShapeUtil.generateSolidSphere(radius)) {
                mutableBlockPos.set(context.origin());
                mutableBlockPos.move(point.getX(), point.getY(), point.getZ());
                mutableBlockPos.move(Direction.DOWN, depth + 1);
                carveSpot(context.level(), context.chunkGenerator(), mutableBlockPos);
            }

            // Create fairy ring of luminous fungus around sinkhole
//            for (BlockPos point : ShapeUtil.generateCircle(radius + 2)) {
//                for (int y = -5; y < 5; y++) {
//                    if (IEBlocks.LUMINOUS_FUNGUS.get().getDefaultState().isValidPosition(world, pos.add(point).down(y))) {
//                        world.setBlockState(pos.add(point).down(y), IEBlocks.LUMINOUS_FUNGUS.get().getDefaultState(), 2);
//                        break;
//                    }
//                }
//            }

            return true;
        }
    }

    private void carveSpot(WorldGenLevel world, ChunkGenerator generator, BlockPos.MutableBlockPos mutableBlockPos) {
        // only carve spot if space isnt liquid and above isnt liquid
        if (world.getBlockState(mutableBlockPos).getFluidState().isEmpty()) {

            if (mutableBlockPos.getY() < generator.getSeaLevel()) {
                world.setBlock(mutableBlockPos, Blocks.LAVA.defaultBlockState(), 3);
            } else {
                world.setBlock(mutableBlockPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
}
