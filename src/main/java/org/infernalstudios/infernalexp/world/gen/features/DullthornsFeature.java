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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.infernalstudios.infernalexp.blocks.DullthornsBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;

public class DullthornsFeature extends Feature<NoneFeatureConfiguration> {
    public DullthornsFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int height = context.random().nextInt(9) + 1;
        int top = 0;

        if (!context.level().isEmptyBlock(context.origin()) || context.level().getBlockState(context.origin().below()).getBlock() != IEBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            // Generate dullthorns up "height" blocks unless there is something in the way
            for (int i = 0; i < height; i++) {
                if (context.level().isEmptyBlock(context.origin().above(i))) {
                    context.level().setBlock(context.origin().above(i), IEBlocks.DULLTHORNS.get().defaultBlockState(), 10);
                    top = i;
                }
            }

            context.level().setBlock(context.origin().above(top), IEBlocks.DULLTHORNS.get().defaultBlockState().setValue(DullthornsBlock.TIP, true), 10);

            return true;
        }
    }
}

