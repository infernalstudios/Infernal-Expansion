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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.util.ShapeUtil;

import java.util.Random;

public class HangingGiantBrownMushroomFeature extends Feature<NoFeatureConfig> {
    public HangingGiantBrownMushroomFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int minSize = 3;
    private static final int maxSize = 7;

    private static final ResourceLocation enhancedMushroomsBrownStem = new ResourceLocation("enhanced_mushrooms", "brown_mushroom_stem");

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!world.isAirBlock(pos) || world.getBlockState(pos.up()) != IEBlocks.DULLSTONE.get().getDefaultState()) {
            return false;
        } else {
            // Generate size between minSize and maxSize
            int size = minSize + random.nextInt(maxSize - minSize);
            BlockState enhancedMushroomsBrownStemBlockState = null;

            if (ModList.get().isLoaded("enhanced_mushrooms")) {
                enhancedMushroomsBrownStemBlockState = ForgeRegistries.BLOCKS.getValue(enhancedMushroomsBrownStem).getDefaultState();
            }

            // Generate stem
            for (int y = 0; y <= size; y++) {
                if (enhancedMushroomsBrownStemBlockState != null) {
                    world.setBlockState(pos.down(y), enhancedMushroomsBrownStemBlockState, 2);
                } else {
                    world.setBlockState(pos.down(y), Blocks.MUSHROOM_STEM.getDefaultState(), 2);
                }
            }

            // Generate mushroom cap
            for (BlockPos point : ShapeUtil.generateSolidCircle((float) (size / 2) + 1)) {
                world.setBlockState(pos.add(point.getX(), point.getY() - size, point.getZ()), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), 2);
            }

            return true;
        }
    }
}
