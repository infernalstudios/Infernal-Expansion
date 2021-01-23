package com.nekomaster1000.infernalexp.world.biome.netherbiomes;

import com.nekomaster1000.infernalexp.init.*;
import com.nekomaster1000.infernalexp.world.biome.BiomeHelper;
import com.nekomaster1000.infernalexp.world.biome.ModBiome;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
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
                .setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                .setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 8000, 8, 2.0D))
                .setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
                .setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_BASALT_DELTAS))
                .setParticle(new ParticleEffectAmbience(ModParticleTypes.GLOWSTONE_SPARKLE.get(), 0.005F));
    }

    @Override
    protected Biome.Climate configureClimate() {
        return new Biome.Climate(Biome.RainType.RAIN, 2.0f, Biome.TemperatureModifier.NONE, 0.0f);
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> configureSurfaceBuilder() {
        return BiomeHelper.newConfiguredSurfaceBuilder("glowstone_canyon", new ConfiguredSurfaceBuilder(ModSurfaceBuilders.GLOWSTONE_CANYON_SURFACE_BUILDER, ModSurfaceBuilders.ModSurfaceBuilderConfig.GLOWSTONE_CANYON_CONFIG));
    }

    @Override
    protected void configureGeneration(BiomeGenerationSettings.Builder generation) {
        generation.withStructure(StructureFeatures.RUINED_PORTAL_NETHER);
        generation.withStructure(StructureFeatures.FORTRESS);
        generation.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f);
        //generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA);
        generation.withCarver(GenerationStage.Carving.AIR, ModCarvers.CONFIGURED_GLOWSTONE_RAVINE);
//        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.BLACKSTONE_BOULDER);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.GLOWSPIKE);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.GLOWSPIKELARGE);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.HANGING_GIANT_BROWN_MUSHROOM);
        generation.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, ModConfiguredFeatures.DULLSTONE_DEATH_PIT);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUMINOUS_FUNGUS);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DULLTHORNS);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE);
        //generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,ModConfiguredFeatures.CANYON_BLACKSTONE_ORE);
        //generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,ModConfiguredFeatures.CANYON_CRUMBLING_BLACKSTONE_ORE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BLACKSTONE_BLOBS);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
        generation.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE);

        DefaultBiomeFeatures.withCommonNetherBlocks(generation);
    }

    @Override
    protected void configureSpawns(MobSpawnInfo.Builder spawns) {
        //spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntityTypes.GLOWSQUITO.get(), 1, 1, 3));
        //spawns.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntityTypes.BLINDSIGHT.get(), 100, 1, 1));

        //It doesn't work properly. Glowsquitos don't spawn at all and Blindsights spawn en-masse regardless of if
        // they're set to 1 or 100. Putting spawning for new biomes back in ModEvents for now.
    }
}
