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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.util.ShapeUtil;

public class HangingGiantBrownMushroomFeature extends IEFeature<NoneFeatureConfiguration> {

    public HangingGiantBrownMushroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static final int minSize = 3;
    private static final int maxSize = 7;

    private static final ResourceLocation enhancedMushroomsBrownStem = new ResourceLocation("enhanced_mushrooms", "brown_mushroom_stem");

    @Override
    public boolean placeFeature(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (!context.level().isEmptyBlock(context.origin()) || context.level().getBlockState(context.origin().above()) != IEBlocks.DULLSTONE.get().defaultBlockState()) {
            return false;
        } else {
            // Generate size between minSize and maxSize
            int size = minSize + context.random().nextInt(maxSize - minSize);
            BlockState enhancedMushroomsBrownStemBlockState = null;

            if (ModList.get().isLoaded("enhanced_mushrooms")) {
                enhancedMushroomsBrownStemBlockState = ForgeRegistries.BLOCKS.getValue(enhancedMushroomsBrownStem).defaultBlockState();
            }

            // Generate stem
            for (int y = 0; y <= size; y++) {
                if (enhancedMushroomsBrownStemBlockState != null) {
                    context.level().setBlock(context.origin().below(y), enhancedMushroomsBrownStemBlockState, 2);
                } else {
                    context.level().setBlock(context.origin().below(y), Blocks.MUSHROOM_STEM.defaultBlockState(), 2);
                }
            }

            // Generate mushroom cap
            for (BlockPos point : ShapeUtil.generateSolidCircle((float) (size / 2) + 1)) {
                context.level().setBlock(context.origin().offset(point.getX(), point.getY() - size, point.getZ()), Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState(), 2);
            }

            return true;
        }
    }

    @Override
    boolean shouldPlaceOnStructures() {
        return false;
    }

}
