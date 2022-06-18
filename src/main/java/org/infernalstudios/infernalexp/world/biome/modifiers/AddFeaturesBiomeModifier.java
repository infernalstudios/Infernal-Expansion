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

package org.infernalstudios.infernalexp.world.biome.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.util.List;

public record AddFeaturesBiomeModifier(Holder<Biome> biome, List<FeaturesAtStep> featuresAtSteps) implements BiomeModifier {

    public static final Codec<AddFeaturesBiomeModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        Biome.CODEC.fieldOf("biome").forGetter(AddFeaturesBiomeModifier::biome),
        FeaturesAtStep.LIST_CODEC.fieldOf("features_at_steps").forGetter(AddFeaturesBiomeModifier::featuresAtSteps)
    ).apply(builder, AddFeaturesBiomeModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || biome != biome()) return;

        featuresAtSteps().forEach(featuresAtStep -> {
            featuresAtStep.features().forEach(feature -> builder.getGenerationSettings().addFeature(featuresAtStep.step(), feature));
        });
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    private record FeaturesAtStep(GenerationStep.Decoration step, HolderSet<PlacedFeature> features) {

        private static final Codec<FeaturesAtStep> CODEC = RecordCodecBuilder.create(builder -> builder.group(GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(FeaturesAtStep::step), PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(FeaturesAtStep::features)).apply(builder, FeaturesAtStep::new));

        private static final Codec<List<FeaturesAtStep>> LIST_CODEC = CODEC.listOf();

    }

}
