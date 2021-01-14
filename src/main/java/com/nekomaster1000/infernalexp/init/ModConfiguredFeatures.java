package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;

public class ModConfiguredFeatures {

    public static ConfiguredFeature<?, ?> GLOWSPIKE = registerConfiguredFeature("glowspike", ModFeatures.GLOWSPIKE.withConfiguration(new GlowSpikeFeatureConfig(3, 5, 8, 24, 7, 7, 0.3f, true)).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> GLOWSPIKELARGE = registerConfiguredFeature("glowspikelarge", ModFeatures.GLOWSPIKE.withConfiguration(new GlowSpikeFeatureConfig(4, 7, 12, 98, 12, 12, 0.2f, false)).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> GLOWSTONE_RAVINE = registerConfiguredFeature("glowstone_ravine", ModFeatures.GLOWSTONE_RAVINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(60)));
    public static ConfiguredFeature<?, ?> HANGING_GIANT_BROWN_MUSHROOM = registerConfiguredFeature("hanging_giant_brown_mushroom", ModFeatures.HANGING_GIANT_BROWN_MUSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> GLOWDUST_SINK_HOLE = registerConfiguredFeature("glowdust_sink_hole", ModFeatures.GLOWDUST_SINK_HOLE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(40)));
    public static ConfiguredFeature<?, ?> LUMINOUS_FUNGUS = registerConfiguredFeature("luminous_fungus", ModFeatures.LUMINOUS_FUNGUS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(30)));
    public static ConfiguredFeature<?, ?> DULLTHORNS = registerConfiguredFeature("dullthorns", ModFeatures.DULLTHORNS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(5))));
    public static ConfiguredFeature<?, ?> BLACKSTONE_BOULDER = registerConfiguredFeature("blackstone_boulder", ModFeatures.BOULDER.withConfiguration(new BlockStateFeatureConfig(Blocks.BLACKSTONE.getDefaultState())).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
    public static ConfiguredFeature<?, ?> GLOWCANYON_BLACKSTONE_BLOBS = registerConfiguredFeature("glowcanyon_blackstone_blob", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(ModBlocks.DULLSTONE.get().getDefaultState(), Blocks.BLACKSTONE.getDefaultState(), FeatureSpread.func_242253_a(2, 3))).range(128).square().func_242731_b(8));
    public static ConfiguredFeature<?, ?> GLOWCANYON_CRUMBLING_BLACKSTONE_BLOBS = registerConfiguredFeature("glowcanyon_crumbling_blackstone_blob", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(ModBlocks.DULLSTONE.get().getDefaultState(), ModBlocks.CRUMBLING_BLACKSTONE.get().getDefaultState(), FeatureSpread.func_242253_a(3, 4))).range(128).square().func_242731_b(2));

    //    public static final ConfiguredFeature<?, ?> RANDOM_GLOWSTONE_CANYON_PLANT = registerConfiguredFeature("rand_glowstone_canyon", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
//            LUMINOUS_FUNGUS.withChance(0.5F)),
//            LUMINOUS_FUNGUS)).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(15))));

    public static ConfiguredFeature<?, ?> registerConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (WorldGenRegistries.CONFIGURED_FEATURE.containsKey(resourceLocation))
            throw new IllegalStateException("Configured Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resourceLocation, configuredFeature);
    }

}
