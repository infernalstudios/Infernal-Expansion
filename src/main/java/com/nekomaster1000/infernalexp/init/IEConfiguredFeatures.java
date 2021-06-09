package com.nekomaster1000.infernalexp.init;

import com.google.common.collect.ImmutableSet;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlobReplacementConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

public class IEConfiguredFeatures {

    public static final RuleTest BASALT = new BlockMatchRuleTest(Blocks.BASALT);
    public static final RuleTest SOUL_SOIL = new BlockMatchRuleTest(Blocks.SOUL_SOIL);

    public static ConfiguredFeature<?, ?> GLOWDUST_LAYER = registerConfiguredFeature("glowdust_layer", IEFeatures.GLOWDUST_LAYER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static ConfiguredFeature<?, ?> GLOWSPIKE = registerConfiguredFeature("glowspike", IEFeatures.GLOWSPIKE.withConfiguration(new GlowSpikeFeatureConfig(3, 5, 8, 24, 7, 7, 0.3f, true)).withPlacement(Features.Placements.PATCH_PLACEMENT.count(30)));
    public static ConfiguredFeature<?, ?> GLOWSPIKELARGE = registerConfiguredFeature("glowspikelarge", IEFeatures.GLOWSPIKE.withConfiguration(new GlowSpikeFeatureConfig(4, 7, 12, 98, 12, 12, 0.2f, false)).withPlacement(Features.Placements.PATCH_PLACEMENT.count(30)));
    public static ConfiguredFeature<?, ?> DULLTHORN_TREE_PLANTED = registerConfiguredFeature("dullthorn_tree_planted", Feature.HUGE_FUNGUS.withConfiguration(new HugeFungusConfig(IEBlocks.GLOWDUST_SAND.get().getDefaultState(), IEBlocks.DULLTHORNS_BLOCK.get().getDefaultState(), IEBlocks.LUMINOUS_WART_BLOCK.get().getDefaultState(), IEBlocks.LUMINOUS_WART_BLOCK.get().getDefaultState(), true)));
    public static ConfiguredFeature<?, ?> GLOWSTONE_RAVINE = registerConfiguredFeature("glowstone_ravine", IEFeatures.GLOWSTONE_RAVINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.count(120)));
    public static ConfiguredFeature<?, ?> HANGING_GIANT_BROWN_MUSHROOM = registerConfiguredFeature("hanging_giant_brown_mushroom", IEFeatures.HANGING_GIANT_BROWN_MUSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.count(120)));
    public static ConfiguredFeature<?, ?> DULLSTONE_DEATH_PIT = registerConfiguredFeature("dullstone_death_pit", IEFeatures.DULLSTONE_DEATH_PIT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.count(90)));
    public static ConfiguredFeature<?, ?> LUMINOUS_FUNGUS = registerConfiguredFeature("luminous_fungus", IEFeatures.LUMINOUS_FUNGUS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.count(20)));
    public static ConfiguredFeature<?, ?> DULLTHORNS = registerConfiguredFeature("dullthorns", IEFeatures.DULLTHORNS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(5))));
    public static ConfiguredFeature<?, ?> GSC_SPRING_OPEN = registerConfiguredFeature("gsc_spring_open", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.LAVA.getDefaultState(), false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get()))).withPlacement(Features.Placements.SPRING_PLACEMENT).square().count(4));
    public static ConfiguredFeature<?, ?> GSC_SPRING_CLOSED = registerConfiguredFeature("gsc_spring_closed", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.LAVA.getDefaultState(), false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK, IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get()))).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().count(8));
    public static ConfiguredFeature<?, ?> BLACKSTONE_BOULDER = registerConfiguredFeature("blackstone_boulder", IEFeatures.BOULDER.withConfiguration(new BlockStateFeatureConfig(Blocks.BLACKSTONE.getDefaultState())).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
    public static ConfiguredFeature<?, ?> CANYON_BLACKSTONE_BLOBS = registerConfiguredFeature("glowcanyon_blackstone_blob", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(IEBlocks.DULLSTONE.get().getDefaultState(), Blocks.BLACKSTONE.getDefaultState(), FeatureSpread.create(2, 3))).range(128).square().count(8));
    public static ConfiguredFeature<?, ?> ORE_GLOWSILK_COCOON = registerConfiguredFeature("ore_glowsilk_cocoon", Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER, IEBlocks.GLOWSILK_COCOON.get().getDefaultState(), 2)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(20, 10))).square());
    public static ConfiguredFeature<?, ?> ORE_BASALT_IRON_BASALT_DELTA = registerConfiguredFeature("ore_basalt_iron_basalt_deltas", Feature.ORE.withConfiguration(new OreFeatureConfig(BASALT, IEBlocks.BASALT_IRON_ORE.get().getDefaultState(), 8)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().count(18));
    public static ConfiguredFeature<?, ?> ORE_BASALT_IRON_DELTA_SHORES = registerConfiguredFeature("ore_basalt_iron_delta_shores", Feature.ORE.withConfiguration(new OreFeatureConfig(BASALT, IEBlocks.BASALT_IRON_ORE.get().getDefaultState(), 8)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().count(8));
    public static ConfiguredFeature<?, ?> PATCH_GLOW_FIRE = registerConfiguredFeature("patch_glow_fire", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(IEBlocks.GLOW_FIRE.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(IEBlocks.GLOWDUST_SAND.get())).preventProjection().build()).withPlacement(Features.Placements.FIRE_PLACEMENT));
    public static ConfiguredFeature<?, ?> BASALTIC_MAGMA = registerConfiguredFeature("basaltic_magma", Feature.ORE.withConfiguration(new OreFeatureConfig(BASALT, IEBlocks.BASALTIC_MAGMA.get().getDefaultState(),10)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().count(8));
    public static ConfiguredFeature<?, ?> PATCH_WARPED_CAP = registerConfiguredFeature("patch_warped_cap", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(IEBlocks.WARPED_FUNGUS_CAP.get().getDefaultState()), new SimpleBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.WARPED_NYLIUM.getBlock())).preventProjection().build()).range(128).chance(16));
    public static ConfiguredFeature<?, ?> PATCH_CRIMSON_CAP = registerConfiguredFeature("patch_crimson_cap", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(IEBlocks.CRIMSON_FUNGUS_CAP.get().getDefaultState()), new SimpleBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.WARPED_NYLIUM.getBlock())).preventProjection().build()).range(128).chance(16));
    public static ConfiguredFeature<?, ?> SHROOMLIGHT_TEAR = registerConfiguredFeature("shroomlight_tear", IEFeatures.SHROOMLIGHT_TEAR.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).range(128).square().count(100).chance(1));
    public static ConfiguredFeature<?, ?> PATCH_BURIED_BONE = registerConfiguredFeature("patch_buried_bone", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(IEBlocks.BURIED_BONE.get().getDefaultState()), new SimpleBlockPlacer())).tries(128).whitelist(ImmutableSet.of(Blocks.SOUL_SOIL.getBlock())).preventProjection().build()).range(128).count(8).chance(3));
    public static ConfiguredFeature<?, ?> ORE_SOUL_STONE = registerConfiguredFeature("ore_soul_stone", Feature.ORE.withConfiguration(new OreFeatureConfig(SOUL_SOIL, IEBlocks.SOUL_STONE.get().getDefaultState(), 40)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().count(45));


    //public static final ConfiguredFeature<?, ?> PATCH_CRIMSON_ROOTS = register("patch_crimson_roots", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.CRIMSON_ROOTS), new SimpleBlockPlacer())).tries(64).preventProjection().build()).range(128));


    //public static final ConfiguredFeature<?, ?> PATCH_PUMPKIN = register("patch_pumpkin", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.PUMPKIN), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Features.States.GRASS_BLOCK.getBlock())).preventProjection().build()).withPlacement(Features.Placements.PATCH_PLACEMENT).chance(32));


    //    public static final ConfiguredFeature<?, ?> RANDOM_GLOWSTONE_CANYON_PLANT = registerConfiguredFeature("rand_glowstone_canyon", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
//            LUMINOUS_FUNGUS.withChance(0.5F)),
//            LUMINOUS_FUNGUS)).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(15))));

    public static ConfiguredFeature<?, ?> registerConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature) {
		ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

		if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(resourceLocation))
			throw new IllegalStateException("Configured Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resourceLocation, configuredFeature);
	}

}
