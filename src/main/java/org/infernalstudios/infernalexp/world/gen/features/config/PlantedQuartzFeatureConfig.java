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

package org.infernalstudios.infernalexp.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class PlantedQuartzFeatureConfig implements IFeatureConfig {
    public static final Codec<PlantedQuartzFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                Codec.FLOAT.fieldOf("chance_to_fail").forGetter((config) -> config.chanceToFail))
            .apply(builder, PlantedQuartzFeatureConfig::new);
    });

    public final float chanceToFail;

    public PlantedQuartzFeatureConfig(float chanceToFail) {
        this.chanceToFail = chanceToFail;
    }
}
