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

package org.infernalstudios.infernalexp.world.gen.surfacerules;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.mixin.common.SurfaceRulesContextAccessor;
import org.infernalstudios.infernalexp.mixin.common.SurfaceSystemAccessor;
import org.jetbrains.annotations.NotNull;

public record ChanceConditionSource(String name, float percentageChance) implements SurfaceRules.ConditionSource {
    public static final Codec<ChanceConditionSource> CODEC = RecordCodecBuilder.create((conditionSource) ->
        conditionSource.group(Codec.STRING.fieldOf("name").forGetter(ChanceConditionSource::name), Codec.FLOAT.fieldOf("percentage_chance").forGetter(ChanceConditionSource::percentageChance)).apply(conditionSource, ChanceConditionSource::new));

    @NotNull
    @Override
    public Codec<? extends SurfaceRules.ConditionSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.Condition apply(SurfaceRules.Context context) {
        class ChanceCondition extends SurfaceRules.LazyYCondition {
            private final PositionalRandomFactory randomFactory = ((SurfaceSystemAccessor) ((SurfaceRulesContextAccessor) (Object) context).getSystem()).invokeGetOrCreateRandomFactory(new ResourceLocation(InfernalExpansion.MOD_ID, name()));

            protected ChanceCondition(SurfaceRules.Context context) {
                super(context);
            }

            @Override
            protected boolean compute() {
                SurfaceRulesContextAccessor contextAccessor = (SurfaceRulesContextAccessor) (Object) context;
                RandomSource randomSource = randomFactory.at(contextAccessor.getBlockX(), contextAccessor.getBlockY(), contextAccessor.getBlockZ());

                return randomSource.nextFloat() < ChanceConditionSource.this.percentageChance();
            }
        }

        return new ChanceCondition(context);
    }
}
