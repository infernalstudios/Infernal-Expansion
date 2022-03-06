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

import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class GlowstoneCanyonBiome extends ModBiome {

    @Override
    protected Biome.Category configureCategory() {
        return Biome.Category.NETHER;
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
    protected void configureAmbience(BiomeAmbience.Builder ambience) {
        ambience
            .setWaterColor(13408563)
            .setWaterFogColor(10053120)
            .setFogColor(-2916568)
            .withSkyColor(BiomeHelper.calcSkyColor(2.0f))
            .setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F))
            .setAmbientSound(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_LOOP.get())
            .setMoodSound(new MoodSoundAmbience(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_MOOD.get(), 2500, 4, 2.0D))
            .setAdditionsSound(new SoundAdditionsAmbience(IESoundEvents.AMBIENT_GLOWSTONE_CANYON_ADDITIONS.get(), 0.0111D))
            .setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(IESoundEvents.MUSIC_NETHER_GLOWSTONE_CANYON.get()))
            .setParticle(new ParticleEffectAmbience(IEParticleTypes.GLOWSTONE_SPARKLE.get(), 0.005F));
    }

    @Override
    protected Biome.Climate configureClimate() {
        return new Biome.Climate(Biome.RainType.NONE, 2.0f, Biome.TemperatureModifier.NONE, 0.0f);
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> configureSurfaceBuilder() {
        return BiomeHelper.newConfiguredSurfaceBuilder("glowstone_canyon", new ConfiguredSurfaceBuilder(IESurfaceBuilders.GLOWSTONE_CANYON_SURFACE_BUILDER, IESurfaceBuilders.ModSurfaceBuilderConfig.GLOWSTONE_CANYON_CONFIG));
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder generation) {
        generation.withStructure(StructureFeatures.RUINED_PORTAL_NETHER);
        generation.withStructure(StructureFeatures.FORTRESS);
        generation.withStructure(IEConfiguredStructures.GLOWSTONE_CANYON_RUIN);
        generation.withStructure(IEConfiguredStructures.BASTION_OUTPOST);
        generation.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.NETHER_CAVE);
        //generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA);
        generation.withCarver(GenerationStage.Carving.AIR, IECarvers.CONFIGURED_GLOWSTONE_RAVINE);
        generation.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, IEConfiguredFeatures.CANYON_BLACKSTONE_BLOBS);
//        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.BLACKSTONE_BOULDER);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.GLOWSPIKE);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.GLOWSPIKELARGE);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.HANGING_GIANT_BROWN_MUSHROOM);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, IEConfiguredFeatures.DULLSTONE_DEATH_PIT);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.LUMINOUS_FUNGUS);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.DULLTHORNS);
//        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.DULLTHORNS_TREE);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, IEConfiguredFeatures.GSC_SPRING_OPEN);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, IEConfiguredFeatures.GSC_SPRING_CLOSED);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_GLOWSILK_COCOON);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, IEConfiguredFeatures.PATCH_GLOW_FIRE);
        generation.withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, IEConfiguredFeatures.GLOWDUST_LAYER);

        DefaultBiomeFeatures.withCommonNetherBlocks(generation);
    }

    @Override
    protected void configureSpawns(MobSpawnInfo.Builder spawns) {
//        spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(IEEntityTypes.GLOWSQUITO.get(), 80, 1, 3));
//        spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(IEEntityTypes.BLINDSIGHT.get(), 10, 1, 1));
        spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.STRIDER, 60, 1, 2));

        //It doesn't work properly. Glowsquitos don't spawn at all and Blindsights spawn en-masse regardless of if
        // they're set to 1 or 100. Putting spawning for new biomes back in IEEvents for now.
    }

    @Override
    public BiomeDictionary.Type[] getBiomeTypes() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.NETHER, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY};
    }
}
