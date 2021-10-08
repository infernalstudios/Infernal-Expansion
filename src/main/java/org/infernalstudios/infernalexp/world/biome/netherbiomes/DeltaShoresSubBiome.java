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

package org.infernalstudios.infernalexp.world.biome.netherbiomes;

import net.minecraftforge.common.BiomeDictionary;
import org.infernalstudios.infernalexp.init.IEConfiguredFeatures;
import org.infernalstudios.infernalexp.init.IESurfaceBuilders;
import org.infernalstudios.infernalexp.world.biome.BiomeHelper;
import org.infernalstudios.infernalexp.world.biome.ModBiome;

import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;

public class DeltaShoresSubBiome extends ModBiome {

    @Override
    protected Biome.BiomeCategory configureCategory() {
        return Biome.BiomeCategory.NETHER;
    }

    @Override
    protected float configureDepth() {
        return 0.1f;
    }

    @Override
    protected float configureScale() {
        return 0.1f;
    }

    @Override
    protected void configureAmbience(BiomeSpecialEffects.Builder ambience) {
        ambience
            .waterColor(4144704)
            .waterFogColor(4341314)
            .fogColor(6840176)
            .skyColor(BiomeHelper.calcSkyColor(2.0f))
            .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
            .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
            .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 8000, 8, 2.0D))
            .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
            .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS));
    }

    @Override
    protected Biome.ClimateSettings configureClimate() {
        return new Biome.ClimateSettings(Biome.Precipitation.RAIN, 2.0f, Biome.TemperatureModifier.NONE, 0.0f);
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> configureSurfaceBuilder() {
        return BiomeHelper.newConfiguredSurfaceBuilder
            ("delta_shores", new ConfiguredSurfaceBuilder(IESurfaceBuilders.DELTA_SHORES_SURFACE_BUILDER,
                IESurfaceBuilders.ModSurfaceBuilderConfig.DELTA_SHORES_CONFIG));
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder generation) {
        generation.addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER);
        generation.addStructureStart(StructureFeatures.NETHER_BRIDGE);
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        //generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA);
                /*FeatureSpread.of(3, 4), FeatureSpread.of(0, 2),
                (((Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(40))));*/
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.BASALT_BLOBS);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.BLACKSTONE_BLOBS);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.SMALL_BASALT_COLUMNS);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_BASALT_IRON_DELTA_SHORES);

 /*
 DELTA = register("delta", Feature.DELTA_FEATURE.withConfiguration(new BasaltDeltasFeature(Features.States.LAVA_BLOCK, Features.States.MAGMA_BLOCK,
 SMALL_BASALT_COLUMNS = register("small_basalt_columns", Feature.BASALT_COLUMNS.withConfiguration(new ColumnConfig(FeatureSpread.fixed(1), FeatureSpread.of(1, 3))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(4))));
 LARGE_BASALT_COLUMNS = register("large_basalt_columns", Feature.BASALT_COLUMNS.withConfiguration(new ColumnConfig(FeatureSpread.of(2, 1), FeatureSpread.of(5, 5))).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(2))));
 BASALT_BLOBS = register("basalt_blobs", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(Features.States.NETHERRACK, Features.States.BASALT, FeatureSpread.of(3, 4))).range(128).square().count(75));
 BLACKSTONE_BLOBS = register("blackstone_blobs", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(Features.States.NETHERRACK, Features.States.BLACKSTONE, FeatureSpread.of(3, 4))).range(128).square().count(25));
 GLOWSTONE_EXTRA = register("glowstone_extra", Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.GLOWSTONE.configure(new FeatureSpreadConfig(10))));
 GLOWSTONE = register("glowstone", Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).range(128).square().count(10));
*/


        BiomeDefaultFeatures.addNetherDefaultOres(generation);
    }

    @Override
    protected void configureSpawns(MobSpawnSettings.Builder spawns) {
//        spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSQUITO.get(), 1, 1, 3));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));
    }

    @Override
    public BiomeDictionary.Type[] getBiomeTypes() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.NETHER};
    }
}
