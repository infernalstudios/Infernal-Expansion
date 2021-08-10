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

package org.infernalstudios.infernalexp.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import org.infernalstudios.infernalexp.mixin.common.ChunkGeneratorAccessor;
import org.infernalstudios.infernalexp.mixin.common.NoiseChunkGeneratorAccessor;
import org.infernalstudios.infernalexp.world.dimension.ModNetherBiomeProvider;

public class NoiseChunkGeneratorUtil {
    public static final Codec<NoiseChunkGenerator> OLD_field_236079_d_ = NoiseChunkGenerator.field_236079_d_;

    public static final Codec<NoiseChunkGenerator> CUSTOM_field_236079_d_ = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((noiseChunkGenerator) -> {
            Registry<DimensionSettings> dimensionSettingsRegistry = DynamicRegistries.func_239770_b_().getRegistry(Registry.NOISE_SETTINGS_KEY);
            if (dimensionSettingsRegistry.getOrThrow(DimensionSettings.NETHER) == ((NoiseChunkGeneratorAccessor) (Object) noiseChunkGenerator).getDimensionSettings().get()) {
                Registry<Biome> biomeRegistry = DynamicRegistries.func_239770_b_().getRegistry(Registry.BIOME_KEY);
                return new ModNetherBiomeProvider(((NoiseChunkGeneratorAccessor) (Object) noiseChunkGenerator).getSeed(), biomeRegistry, 6);
            } else {
                return ((ChunkGeneratorAccessor) (Object) noiseChunkGenerator).getBiomeProviderField();
            }
        }), Codec.LONG.fieldOf("seed").stable().forGetter((noiseChunkGenerator) -> {
            return ((NoiseChunkGeneratorAccessor) (Object) noiseChunkGenerator).getSeed();
        }), DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((noiseChunkGenerator) -> {
            return ((NoiseChunkGeneratorAccessor) (Object) noiseChunkGenerator).getDimensionSettings();
        })).apply(instance, instance.stable(NoiseChunkGenerator::new));
    });

    public static void useCustomNetherBiomeProvider() {
        NoiseChunkGenerator.field_236079_d_ = CUSTOM_field_236079_d_;
    }

    public static void useDefaultNetherBiomeProvider() {
        NoiseChunkGenerator.field_236079_d_ = OLD_field_236079_d_;
    }
}
