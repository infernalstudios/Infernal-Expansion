package com.nekomaster1000.infernalexp.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.INoiseRandom;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModBiomeBuilder extends Biome.Builder {
    public static List<ModBiomeBuilder> biomeBuilders = new ArrayList<>();
    private final Biome.Builder biome = new Biome.Builder();

    public ModBiomeBuilder(Biome.RainType precipitation, Biome.TemperatureModifier temperatureModifier, float temperature, float downfall, Biome.Category category, float depth, float scale, BiomeAmbience ambience, BiomeGenerationSettings generationSettings, MobSpawnInfo spawnInfo) {
        biome.precipitation(precipitation);
        biome.withTemperatureModifier(temperatureModifier);
        biome.temperature(temperature);
        biome.downfall(downfall);
        biome.category(category);
        biome.depth(depth);
        biome.scale(scale);
        biome.setEffects(ambience);
        biome.withGenerationSettings(generationSettings);
        biome.withMobSpawnSettings(spawnInfo);

        biomeBuilders.add(this);
    }

    public Biome getBiome() {
        return biome.build();
    }

    @Nullable
    public Biome getHill(INoiseRandom rand) {
        return null;
    }
}
