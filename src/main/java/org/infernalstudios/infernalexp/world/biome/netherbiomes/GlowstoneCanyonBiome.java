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

import net.minecraftforge.common.BiomeDictionary;
import org.infernalstudios.infernalexp.init.IECarvers;
import org.infernalstudios.infernalexp.init.IEConfiguredFeatures;
import org.infernalstudios.infernalexp.init.IEConfiguredStructures;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.infernalstudios.infernalexp.init.IESurfaceBuilders;
import org.infernalstudios.infernalexp.world.biome.BiomeHelper;
import org.infernalstudios.infernalexp.world.biome.ModBiome;

import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.particles.ParticleTypes;
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

public class GlowstoneCanyonBiome extends ModBiome {

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
        return 0.2f;
    }

    @Override
    protected void configureAmbience(BiomeSpecialEffects.Builder ambience) {
        ambience
            .waterColor(13408563)
            .waterFogColor(10053120)
            .fogColor(-2916568)
            .skyColor(BiomeHelper.calcSkyColor(2.0f))
            .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
            .ambientLoopSound(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_LOOP.get())
            .ambientMoodSound(new AmbientMoodSettings(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_MOOD.get(), 2500, 4, 2.0D))
            .ambientAdditionsSound(new AmbientAdditionsSettings(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_ADDITIONS.get(), 0.0111D))
            .backgroundMusic(Musics.createGameMusic(IESoundEvents.MUSIC_NETHER_GLOWSTONE_CANYON.get()))
            .ambientParticle(new AmbientParticleSettings(IEParticleTypes.GLOWSTONE_SPARKLE.get(), 0.005F));
    }

    @Override
    protected Biome.ClimateSettings configureClimate() {
        return new Biome.ClimateSettings(Biome.Precipitation.NONE, 2.0f, Biome.TemperatureModifier.NONE, 0.0f);
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> configureSurfaceBuilder() {
        return BiomeHelper.newConfiguredSurfaceBuilder("glowstone_canyon", new ConfiguredSurfaceBuilder<>(IESurfaceBuilders.GLOWSTONE_CANYON_SURFACE_BUILDER, IESurfaceBuilders.ModSurfaceBuilderConfig.GLOWSTONE_CANYON_CONFIG));
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder generation) {
        generation.addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER);
        generation.addStructureStart(StructureFeatures.NETHER_BRIDGE);
        generation.addStructureStart(IEConfiguredStructures.GLOWSTONE_CANYON_RUIN);
        generation.addStructureStart(IEConfiguredStructures.BASTION_OUTPOST);
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        //generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA);
        generation.addCarver(GenerationStep.Carving.AIR, IECarvers.CONFIGURED_GLOWSTONE_RAVINE);
        generation.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, IEConfiguredFeatures.CANYON_BLACKSTONE_BLOBS);
//        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.BLACKSTONE_BOULDER);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.GLOWSPIKE);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.GLOWSPIKELARGE);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.HANGING_GIANT_BROWN_MUSHROOM);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.DULLSTONE_DEATH_PIT);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.LUMINOUS_FUNGUS);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.DULLTHORNS);
//        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.DULLTHORNS_TREE);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, IEConfiguredFeatures.GSC_SPRING_OPEN);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, IEConfiguredFeatures.GSC_SPRING_CLOSED);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_GLOWSILK_COCOON);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, IEConfiguredFeatures.PATCH_GLOW_FIRE);
        generation.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, IEConfiguredFeatures.GLOWDUST_LAYER);

        BiomeDefaultFeatures.addNetherDefaultOres(generation);
    }

    @Override
    protected void configureSpawns(MobSpawnSettings.Builder spawns) {
//        spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSQUITO.get(), 80, 1, 3));
//        spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(IEEntityTypes.BLINDSIGHT.get(), 10, 1, 1));
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));

        //It doesn't work properly. Glowsquitos don't spawn at all and Blindsights spawn en-masse regardless of if
        // they're set to 1 or 100. Putting spawning for new biomes back in IEEvents for now.
    }

    @Override
    public BiomeDictionary.Type[] getBiomeTypes() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.NETHER, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY};
    }
}
