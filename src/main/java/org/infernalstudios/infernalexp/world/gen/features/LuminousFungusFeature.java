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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.infernalstudios.infernalexp.blocks.LuminousFungusBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;

public class LuminousFungusFeature extends Feature<NoneFeatureConfiguration> {
    public LuminousFungusFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int i = 0;
        int amount;
        AttachFace face;

        int pickFacing = context.random().nextInt(3);

        // Pick whether the fungus will spawn on the ceiling or on the floor and set the amount to spawn appropriately
        if (pickFacing == 0) {
            face = AttachFace.CEILING;
            amount = 4;
        } else {
            face = AttachFace.FLOOR;
            amount = 10;
        }

        // Try to place luminous fungus 128 times
        for (int j = 0; j < 128; j++) {
            // Randomize the location of the next luminous fungus to be placed
            BlockState state = IEBlocks.LUMINOUS_FUNGUS.get().defaultBlockState().setValue(LuminousFungusBlock.FACE, face);
            BlockPos blockpos = context.origin().offset(context.random().nextInt(10) - context.random().nextInt(20), context.random().nextInt(4) - context.random().nextInt(8), context.random().nextInt(10) - context.random().nextInt(20));

            // If the randomly chosen location is valid, then place the fungus
            if (context.level().isEmptyBlock(blockpos) && state.canSurvive(context.level(), blockpos) && (context.level().getBlockState(blockpos.above()) == IEBlocks.DULLSTONE.get().defaultBlockState() || context.level().getBlockState(blockpos.below()) == IEBlocks.GLOWDUST_SAND.get().defaultBlockState())) {
                context.level().setBlock(blockpos, state, 2);
                i++;
            }

            // If we have placed the max amount of luminous fungus, then return
            if (i >= amount) {
                return true;
            }
        }

        return false;
    }
}

