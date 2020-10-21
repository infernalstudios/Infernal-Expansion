package com.nekomaster1000.infernalexp.world.biome.netherbiomes;

import com.nekomaster1000.infernalexp.init.ModSurfaceBuilder;
import com.nekomaster1000.infernalexp.world.biome.BiomeHelper;
import com.nekomaster1000.infernalexp.world.biome.BiomeTools;
import com.nekomaster1000.infernalexp.world.biome.ModBiomeBuilder;
import com.nekomaster1000.infernalexp.world.gen.features.ModConfiguredFeatures;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public class GlowstoneCanyonBiome extends ModBiomeBuilder implements BiomeTools {
    static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = BiomeHelper.newConfiguredSurfaceBuilder("glowstone_canyon", new ConfiguredSurfaceBuilder(ModSurfaceBuilder.GLOWSTONE_CANYON_SURFACE_BUILDER, ModSurfaceBuilder.ModSurfaceBuilderConfig.GLOWSTONE_CANYON_CONFIG));
    static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    static final Biome.TemperatureModifier TEMPERATURE_MODIFIER = Biome.TemperatureModifier.NONE;
    static final Biome.Category CATEGORY = Biome.Category.NETHER;
    static final float DEPTH = 0.1F;
    static final float SCALE = 0.2F;
    static final float TEMPERATURE = 2.0F;
    static final float DOWNFALL = 0.0F;
    static final int WATER_COLOR = 13408563;
    static final int WATER_FOG_COLOR = 10053120;
    static final int FOG_COLOR = 10316320;
    static final String PARENT = null;
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);

    public GlowstoneCanyonBiome() {
        super(PRECIPITATION, TEMPERATURE_MODIFIER, TEMPERATURE, DOWNFALL, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR)
                .setFogColor(FOG_COLOR)
                .withSkyColor(BiomeHelper.calcSkyColor(2.0F))
                .setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F))
                .setAmbientSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                .setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 8000, 8, 2.0D))
                .setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
                .setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_NETHER_BASALT_DELTAS))
                .build(),
                GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy()
                );
    }

    static {
        GENERATION_SETTINGS.withStructure(StructureFeatures.field_244134_E);
        GENERATION_SETTINGS.withStructure(StructureFeatures.field_244149_o);
        GENERATION_SETTINGS.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243772_f);
        //GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.DELTA);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.GLOWSPIKE);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUMINOUS_FUNGUS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.BLACKSTONE_BLOBS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE);

        DefaultBiomeFeatures.withCommonNetherBlocks(GENERATION_SETTINGS);

        //SPAWN_SETTINGS.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityType.EMBODY.get(), 1, 1, 3));
    }
}
