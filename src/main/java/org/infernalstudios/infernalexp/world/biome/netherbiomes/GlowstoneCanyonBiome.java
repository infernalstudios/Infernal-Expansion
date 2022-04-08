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

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.sounds.Musics;
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
import org.infernalstudios.infernalexp.init.IECarvers;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.infernalstudios.infernalexp.init.IEPlacedFeatures;
import org.infernalstudios.infernalexp.init.IESoundEvents;
import org.infernalstudios.infernalexp.world.biome.BiomeHelper;
import org.infernalstudios.infernalexp.world.biome.IEBiome;

public class GlowstoneCanyonBiome extends IEBiome {

    @Override
    protected Biome.BiomeCategory configureCategory() {
        return Biome.BiomeCategory.NETHER;
    }

    @Override
    protected Climate.Parameter configureTemperature() {
        return Climate.Parameter.point(0.7F);
    }

    @Override
    protected Climate.Parameter configureHumidity() {
        return Climate.Parameter.point(-0.2F);
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
    protected void configureAmbience(BiomeSpecialEffects.Builder ambience) {
        ambience
            .waterColor(13408563)
            .waterFogColor(10053120)
            .fogColor(-2916568)
            .skyColor(BiomeHelper.calcSkyColor(2.0f))
//            .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
            .ambientLoopSound(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_LOOP.get())
            .ambientMoodSound(new AmbientMoodSettings(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_MOOD.get(), 2500, 4, 2.0D))
            .ambientAdditionsSound(new AmbientAdditionsSettings(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_ADDITIONS.get(), 0.0111D))
            .backgroundMusic(Musics.createGameMusic(IESoundEvents.MUSIC_NETHER_GLOWSTONE_CANYON.get()))
            .ambientParticle(new AmbientParticleSettings(IEParticleTypes.GLOWSTONE_SPARKLE.get(), 0.005F));
    }

    @Override
    protected Biome.ClimateSettings configureClimate() {
        return new Biome.ClimateSettings(Biome.Precipitation.RAIN, 2.0f, Biome.TemperatureModifier.NONE, 0.0f);
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder generation) {
//        generation.addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER);
//        generation.addStructureStart(StructureFeatures.NETHER_BRIDGE);
//        generation.addStructureStart(IEConfiguredStructures.GLOWSTONE_CANYON_RUIN);
//        generation.addStructureStart(IEConfiguredStructures.BASTION_OUTPOST);
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        generation.addCarver(GenerationStep.Carving.AIR, IECarvers.CONFIGURED_GLOWSTONE_RAVINE);
        generation.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, IEPlacedFeatures.GSC_BLACKSTONE_BLOBS);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEPlacedFeatures.GLOWSPIKE);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEPlacedFeatures.GLOWSPIKE_LARGE);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEPlacedFeatures.HANGING_GIANT_BROWN_MUSHROOM);
        generation.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, IEPlacedFeatures.DULLSTONE_DEATH_PIT);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.LUMINOUS_FUNGUS);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.DULLTHORNS);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, IEPlacedFeatures.GSC_SPRING_OPEN);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, IEPlacedFeatures.GSC_SPRING_CLOSED);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_GLOWSILK_COCOON);
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, IEPlacedFeatures.PATCH_GLOW_FIRE);
        generation.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, IEPlacedFeatures.GLOWDUST_LAYER);

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
