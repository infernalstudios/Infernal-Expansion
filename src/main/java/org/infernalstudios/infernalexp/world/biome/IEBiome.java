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

package org.infernalstudios.infernalexp.world.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

/**
 * In 1.18, the world generator uses six different parameters to decide where to place biomes.
 * These parameters are temperature, humidity, continentalness, erosion, depth and weirdness.
 * {@link net.minecraft.world.level.biome.Climate.Parameter#point(float)} can be used to set a parameter to a single value or,
 * {@link net.minecraft.world.level.biome.Climate.Parameter#span(float, float)} can be used to set a parameter to a range of values.
 */
public abstract class IEBiome {

    private static final float PARAMETER_MODIFIER = 0F;

    /**
     * Method to configure category
     *
     * @return Category for biome to use
     */
    protected abstract Biome.BiomeCategory configureCategory();

    /**
     * See {@link org.infernalstudios.infernalexp.world.biome.IEBiome} for documentation
     */
    protected abstract Climate.Parameter configureTemperature();

    /**
     * See {@link org.infernalstudios.infernalexp.world.biome.IEBiome} for documentation
     */
    protected abstract Climate.Parameter configureHumidity();

    /**
     * See {@link org.infernalstudios.infernalexp.world.biome.IEBiome} for documentation
     */
    protected abstract Climate.Parameter configureContinentalness();

    /**
     * See {@link org.infernalstudios.infernalexp.world.biome.IEBiome} for documentation
     */
    protected abstract Climate.Parameter configureErosion();

    /**
     * See {@link org.infernalstudios.infernalexp.world.biome.IEBiome} for documentation
     */
    protected abstract Climate.Parameter configureDepth();

    /**
     * See {@link org.infernalstudios.infernalexp.world.biome.IEBiome} for documentation
     */
    protected abstract Climate.Parameter configureWeirdness();

    /**
     * Method to configure offset value. Offset seems to work differently from the other six parameters. I'd say just leave it set to 0.0F
     *
     * @return Offset value for biome to use
     */
    protected float configureOffset() {
        return 0.0F;
    }

    ;

    /**
     * Method to configure ambience settings
     */
    protected abstract void configureAmbience(BiomeSpecialEffects.Builder ambience);

    /**
     * Method to configure climate settings
     */
    protected abstract Biome.ClimateSettings configureClimate();

    /**
     * Method  to configure generation settings
     */
    protected abstract void configureGeneration(BiomeGenerationSettings.Builder generation);

    /**
     * Method to configure mob spawn settings
     */
    protected abstract void configureSpawns(MobSpawnSettings.Builder spawns);

    public abstract BiomeDictionary.Type[] getBiomeTypes();

    public final Climate.ParameterPoint getBiomeParameters() {
        // We add the PARAMETER_MODIFIER to remove any chance that another mod adds a biome with the same parameter values.
        // If two biomes have the exact same parameter values only one of them will ever generate.

        return Climate.parameters(
            this.configureTemperature(),
            this.configureHumidity(),
            this.configureContinentalness(),
            this.configureErosion(),
            this.configureDepth(),
            this.configureWeirdness(),
            this.configureOffset());
    }

    /**
     * Builds the biome
     *
     * @return Returns the finished, built biome
     */
    public final Biome build() {
        Biome.BiomeBuilder builder = new Biome.BiomeBuilder();

        builder.biomeCategory(this.configureCategory());

        // Configure biome ambience
        BiomeSpecialEffects.Builder ambience = new BiomeSpecialEffects.Builder();
        this.configureAmbience(ambience);
        builder.specialEffects(ambience.build());

        // Configure biome climate
        Biome.ClimateSettings climate = configureClimate();
        builder.precipitation(climate.precipitation);
        builder.temperature(climate.temperature);
        builder.temperatureAdjustment(climate.temperatureModifier);
        builder.downfall(climate.downfall);

        // Configure biome generation settings
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
        this.configureGeneration(generation);
        builder.generationSettings(generation.build());

        // Configure biome mob spawn settings
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        this.configureSpawns(spawns);
        builder.mobSpawnSettings(spawns.build());

        return builder.build();
    }
}
