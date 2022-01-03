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

package org.infernalstudios.infernalexp.world.type;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraftforge.common.world.ForgeWorldType;
import org.infernalstudios.infernalexp.world.dimension.ModNetherBiomeProvider;

public class CompatWorldType extends ForgeWorldType {
    public CompatWorldType() {
        super(null);
    }

    @Override
    public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<NoiseGeneratorSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
        return new NoiseBasedChunkGenerator(new OverworldBiomeSource(seed, false, false, biomeRegistry), seed, () -> dimensionSettingsRegistry.getOrThrow(NoiseGeneratorSettings.OVERWORLD));
    }

    public static MappedRegistry<LevelStem> getDefaultSimpleRegistry(Registry<DimensionType> lookUpRegistryDimensionType, Registry<Biome> registry, Registry<NoiseGeneratorSettings> dimensionSettings, long seed) {
        MappedRegistry<LevelStem> simpleRegistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.stable());
        simpleRegistry.register(LevelStem.OVERWORLD, new LevelStem(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.OVERWORLD_LOCATION), new NoiseBasedChunkGenerator(new OverworldBiomeSource(seed, false, false, registry), seed, () -> dimensionSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD))), Lifecycle.stable());

        simpleRegistry.register(LevelStem.NETHER, new LevelStem(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.NETHER_LOCATION), new NoiseBasedChunkGenerator(new ModNetherBiomeProvider(seed, registry, 6), seed, () -> dimensionSettings.getOrThrow(NoiseGeneratorSettings.NETHER))), Lifecycle.stable());

        simpleRegistry.register(LevelStem.END, new LevelStem(() -> lookUpRegistryDimensionType.getOrThrow(DimensionType.END_LOCATION), new NoiseBasedChunkGenerator(new TheEndBiomeSource(registry, seed), seed, () -> dimensionSettings.getOrThrow(NoiseGeneratorSettings.END))), Lifecycle.stable());

        return simpleRegistry;
    }

    @Override
    public WorldGenSettings createSettings(RegistryAccess dynamicRegistries, long seed, boolean generateStructures, boolean generateLoot, String generatorSettings) {
        return new HideWorldType(seed, generateStructures, generateLoot, getDefaultSimpleRegistry(dynamicRegistries.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), dynamicRegistries.registryOrThrow(Registry.BIOME_REGISTRY), dynamicRegistries.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), seed));
    }

    public static class HideWorldType extends WorldGenSettings {

        public HideWorldType(long seed, boolean generateFeatures, boolean bonusChest, MappedRegistry<LevelStem> dimensionSimpleRegistry) {
            super(seed, generateFeatures, bonusChest, dimensionSimpleRegistry);
        }

        @Override
        public boolean isDebug() {
            return true;
        }
    }
}
