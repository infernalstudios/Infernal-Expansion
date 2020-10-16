//package com.nekomaster1000.infernalexp.world.biome;
//
//import com.google.common.collect.ImmutableList;
//import com.nekomaster1000.infernalexp.init.ModBlocks;
//import net.minecraft.block.Blocks;
//import net.minecraft.client.audio.BackgroundMusicTracks;
//import net.minecraft.particles.ParticleTypes;
//import net.minecraft.util.SoundEvents;
//import net.minecraft.world.biome.*;
//import net.minecraft.world.biome.DefaultBiomeFeatures;
//import net.minecraft.world.gen.GenerationStage;
//import net.minecraft.world.gen.carver.WorldCarver;
//import net.minecraft.world.gen.feature.*;
//import net.minecraft.world.gen.placement.*;
//import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
//import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
//
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeAmbience;
//import net.minecraft.world.biome.MoodSoundAmbience;
//
//public class GlowstoneCanyonBiome extends Biome
//{
//    public GlowstoneCanyonBiome()
//    {
//        super(new Builder()
//                .surfaceBuilder(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(
//                        ModBlocks.GLOWDUST_SAND.get().getDefaultState(),
//                        ModBlocks.GLOWDUST_SANDSTONE.get().getDefaultState(),
//                        Blocks.GLOWSTONE.getDefaultState()
//                ))
//                .precipitation(RainType.NONE)
//                .category(Category.NETHER)
//                .depth(0.1F)
//                .scale(0.2F)
//                .temperature(2.0F)
//                .downfall(0.0F)
//                .func_235097_a_(new BiomeAmbience.Builder()
//                        .setWaterColor(13060890)
//                        .setWaterFogColor(11546126)
//                        .setFogColor(16106038)
//                        .setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F))
//                        .setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
//                        .setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
//                        .setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
//                        .setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_BASALT_DELTAS))
//                        .build())
//                .parent(null)
//                .func_235098_a_(ImmutableList.of(new Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F)))
//        );
//        this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_NETHER);
//        this.addCarver(GenerationStage.Carving.AIR, createCarver(WorldCarver.field_236240_b_, new ProbabilityConfig(0.2F)));
//        this.func_235063_a_(DefaultBiomeFeatures.FORTRESS);
//
//        //this.addFeature(GenerationStage.Decoration.LAKES,            Feature.LAKE.withConfiguration(DefaultBiomeFeatures.LAVA).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
//        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236286_Q_.withConfiguration(DefaultBiomeFeatures.BASALT_DELTA).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(5))));
//        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(40, 8, 16, 256))));
//        //this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236285_P_.withConfiguration(DefaultBiomeFeatures.field_235140_aR_).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(4))));
//        //this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.field_236285_P_.withConfiguration(DefaultBiomeFeatures.field_235141_aS_).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(2))));
//        //this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,Feature.field_236287_R_.withConfiguration(DefaultBiomeFeatures.field_235142_aT_).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(75, 0, 0, 128))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.withConfiguration(DefaultBiomeFeatures.field_235143_aU_).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(25, 0, 0, 128))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.NETHER_LAVA).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(32, 4, 16, 128))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.NETHER_FIRE).withPlacement(Placement.field_236960_A_.configure(new FrequencyConfig(10))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.NETHER_SOUL_FIRE).withPlacement(Placement.field_236960_A_.configure(new FrequencyConfig(10))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.LIGHT_GEM_CHANCE.configure(new FrequencyConfig(30))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(40, 0, 0, 128))));
//       //this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.5F, 0, 0, 128))));
//       //this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.5F, 0, 0, 128))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK,Blocks.MAGMA_BLOCK.getDefaultState(), 11)).withPlacement(Placement.MAGMA.configure(new FrequencyConfig(4))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.ENCLOSED_NETHER_SPRING_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(32, 10, 20, 128))));
//        //DefaultBiomeFeatures.func_235190_a_(this, 20, 32);
//        DefaultBiomeFeatures.func_235193_at_(this);
//
//
//        //addSpawn(EntityClassification.CREATURE, new SpawnListEntry(ModEntityType.GLOWSQUITO.get(), 20, 2, 5));
//    }
//}