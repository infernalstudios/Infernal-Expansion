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
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.infernalstudios.infernalexp.blocks.ShroomlightFungusBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;

public class ShroomlightTearFeature extends IEFeature<NoneFeatureConfiguration> {

    private static final int MAX_AMOUNT = 10;

    public ShroomlightTearFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean placeFeature(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean isWarpedForest = context.level().getBiome(context.origin()).is(Biomes.WARPED_FOREST);
        int amount = 0;

        // Try to place Shroomlight Tear 64 times
        for (int i = 0; i < 64; i++) {
            // Randomize the location of the next luminous fungus to be placed
            BlockState state = IEBlocks.SHROOMLIGHT_FUNGUS.get().defaultBlockState().setValue(ShroomlightFungusBlock.FACE, isWarpedForest ? AttachFace.FLOOR : AttachFace.CEILING);
            BlockPos blockpos = context.origin().offset(context.random().nextInt(17) - 8, context.random().nextInt(9) - 4, context.random().nextInt(17) - 8);

            // If the randomly chosen location is valid, then place the fungus
            if (context.level().isEmptyBlock(blockpos) && state.canSurvive(context.level(), blockpos) && (context.level().getBlockState(isWarpedForest ? blockpos.below() : blockpos.above()) == Blocks.SHROOMLIGHT.defaultBlockState())) {
                context.level().setBlock(blockpos, state, 2);
                amount++;
            }

            // If we have placed the max amount of Shroomlight Tear, then return
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

}
