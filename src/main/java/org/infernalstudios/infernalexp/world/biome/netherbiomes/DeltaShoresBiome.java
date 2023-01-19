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

package org.infernalstudios.infernalexp.world.biome.netherbiomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;
import org.infernalstudios.infernalexp.init.IEPlacedFeatures;
import org.infernalstudios.infernalexp.world.biome.IEBiome;

public class DeltaShoresBiome extends IEBiome {

    @Override
    protected Climate.Parameter configureTemperature() {
        return Climate.Parameter.span(-0.32F, -0.31F);
    }

    @Override
    protected Climate.Parameter configureHumidity() {
        return Climate.Parameter.point(0);
    }

    @Override
    protected Climate.Parameter configureContinentalness() {
        return Climate.Parameter.point(0);
    }

    @Override
    protected Climate.Parameter configureErosion() {
        return Climate.Parameter.point(0);
    }

    @Override
    protected Climate.Parameter configureDepth() {
        return Climate.Parameter.point(0);
    }

    @Override
    protected Climate.Parameter configureWeirdness() {
        return Climate.Parameter.point(0);
    }

    @Override
    protected float configureOffset() {
        return 0.175F;
    }

    @Override
    protected void configureAmbience(BiomeSpecialEffects.Builder ambience) {
        ambience
            .waterColor(4144704)
            .waterFogColor(4341314)
            .fogColor(6840176)
            .skyColor(IEBiome.calcSkyColor(2.0f))
            .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
            .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
            .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 8000, 8, 2.0D))
            .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
            .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS));
    }

    @Override
    protected Biome.ClimateSettings configureClimate() {
        return new Biome.ClimateSettings(Biome.Precipitation.NONE, 2.0f, Biome.TemperatureModifier.NONE, 0.0f);
    }


    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder generation) {
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.BASALT_BLOBS);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.SMALL_BASALT_COLUMNS);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED_DOUBLE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_BASALT_IRON_DELTA_SHORES);

        BiomeDefaultFeatures.addNetherDefaultOres(generation);
    }

    @Override
    protected void configureSpawns(MobSpawnSettings.Builder spawns) {
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));
    }

    @Override
    public BiomeDictionary.Type[] getBiomeTypes() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.NETHER};
    }

}
