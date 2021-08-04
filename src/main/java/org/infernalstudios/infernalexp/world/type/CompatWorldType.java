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

package org.infernalstudios.infernalexp.world.type;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.ForgeWorldType;
import org.infernalstudios.infernalexp.world.dimension.ModNetherBiomeProvider;

public class CompatWorldType extends ForgeWorldType {
    public CompatWorldType() {
        super(null);
    }

    @Override
    public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
        return new NoiseChunkGenerator(new OverworldBiomeProvider(seed, false, false, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(DimensionSettings.OVERWORLD));
    }

    public static SimpleRegistry<Dimension> getDefaultSimpleRegistry(Registry<DimensionType> lookUpRegistryDimensionType, Registry<Biome> registry, Registry<DimensionSettings> dimensionSettings, long seed) {
        SimpleRegistry<Dimension> simpleRegistry = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.stable());
        simpleRegistry.register(Dimension.OVERWORLD, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.OVERWORLD), new NoiseChunkGenerator(new OverworldBiomeProvider(seed, false, false, registry), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.OVERWORLD))), Lifecycle.stable());

        simpleRegistry.register(Dimension.THE_NETHER, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.THE_NETHER), new NoiseChunkGenerator(new ModNetherBiomeProvider(seed, registry, 6), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.NETHER))), Lifecycle.stable());

        simpleRegistry.register(Dimension.THE_END, new Dimension(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.THE_END), new NoiseChunkGenerator(new EndBiomeProvider(registry, seed), seed, () -> dimensionSettings.getOrThrow(DimensionSettings.END))), Lifecycle.stable());

        return simpleRegistry;
    }

    @Override
    public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean generateLoot, String generatorSettings) {
        return new HideWorldType(seed, generateStructures, generateLoot, getDefaultSimpleRegistry(dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY), dynamicRegistries.getRegistry(Registry.BIOME_KEY), dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY), seed));
    }

    public static class HideWorldType extends DimensionGeneratorSettings {

        public HideWorldType(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> dimensionSimpleRegistry) {
            super(seed, generateFeatures, bonusChest, dimensionSimpleRegistry);
        }

        @Override
        public boolean hasDebugChunkGenerator() {
            return true;
        }
    }
}
