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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.infernalstudios.infernalexp.blocks.LuminousFungusBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IETags;
import org.jetbrains.annotations.NotNull;

public class LuminousFungusFeature extends IEFeature<NoneFeatureConfiguration> {

    public LuminousFungusFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
        // Pick whether the fungus will spawn on the ceiling or on the floor and set the amount to spawn appropriately
        boolean onCeiling = context.random().nextInt(3) == 0;
        int maxAmount = onCeiling ? 4 : 10;
        int amount = 0;

        // Try to place luminous fungus 128 times
        for (int i = 0; i < 128; i++) {
            // Randomize the location of the next luminous fungus to be placed
            BlockState state = IEBlocks.LUMINOUS_FUNGUS.get().defaultBlockState().setValue(LuminousFungusBlock.FACE, onCeiling ? AttachFace.CEILING : AttachFace.FLOOR);
            BlockPos pos = context.origin().offset(context.random().nextInt(17) - 8, context.random().nextInt(9) - 4, context.random().nextInt(17) - 8);

            // If the randomly chosen location is valid, then place the fungus
            if (context.level().isEmptyBlock(pos) && state.canSurvive(context.level(), pos) && context.level().getBlockState(onCeiling ? pos.above() : pos.below()).is(IETags.Blocks.LUMINOUS_FUNGUS_SPAWNABLE_ON_BLOCKS)) {
                context.level().setBlock(pos, state, 2);
                amount++;
            }

            // If we have placed the max amount of luminous fungus, then return
            if (amount >= maxAmount) {
                return true;
            }
        }

        return false;
    }

    @Override
    boolean placeFeature(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return true;
    }

    @Override
    boolean shouldPlaceOnStructures() {
        return false;
    }

}

