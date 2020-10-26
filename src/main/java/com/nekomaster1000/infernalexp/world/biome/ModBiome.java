package com.nekomaster1000.infernalexp.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public abstract class ModBiome {

    /**
     * Method to configure category
     * @return Category for biome to use
     */
    protected abstract Biome.Category configureCategory();

    /**
     * Method to configure depth value
     * @return Depth value for biome to use
     */
    protected abstract float configureDepth();

    /**
     * Method to configure scale value
     * @return Scale value for biome to use
     */
    protected abstract float configureScale();

    /**
     * Method to configure ambience settings
     */
    protected abstract void configureAmbience(BiomeAmbience.Builder ambience);

    /**
     * Method to configure climate settings
     */
    protected abstract Biome.Climate configureClimate();

    /**
     * Method to configure surface builder
     */
    protected abstract ConfiguredSurfaceBuilder<?> configureSurfaceBuilder();

    /**
     * Method  to configure generation settings
     */
    protected abstract void configureGeneration(BiomeGenerationSettings.Builder generation);

    /**
     * Method to configure mob spawn settings
     */
    protected abstract void configureSpawns(MobSpawnInfo.Builder spawns);

    /**
     * Builds the biome
     * @return Returns the finished, built biome
     */
    public final Biome build() {

        Biome.Builder builder = new Biome.Builder();

        builder.category(this.configureCategory());
        builder.depth(this.configureDepth());
        builder.scale(this.configureScale());

        // Configure biome ambience
        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder();
        this.configureAmbience(ambience);
        builder.setEffects(ambience.build());

        // Configure biome climate
        Biome.Climate climate = configureClimate();
        builder.precipitation(climate.precipitation);
        builder.temperature(climate.temperature);
        builder.withTemperatureModifier(climate.temperatureModifier);
        builder.downfall(climate.downfall);

        // Configure biome generation settings
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
        this.configureGeneration(generation);
        generation.withSurfaceBuilder(this.configureSurfaceBuilder());
        builder.withGenerationSettings(generation.build());

        // Configure biome mob spawn settings
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        this.configureSpawns(spawns);
        builder.withMobSpawnSettings(spawns.copy());

        return builder.build();
    }
}
