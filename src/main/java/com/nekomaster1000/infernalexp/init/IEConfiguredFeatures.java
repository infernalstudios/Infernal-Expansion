package com.nekomaster1000.infernalexp.init;

import com.google.common.collect.ImmutableSet;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BlobReplacementConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.placement.Placement;

public class IEConfiguredFeatures {

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
    public static ConfiguredFeature<?, ?> GLOWCANYON_BLACKSTONE_BLOBS = registerConfiguredFeature("glowcanyon_blackstone_blob", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(IEBlocks.DULLSTONE.get().getDefaultState(), Blocks.BLACKSTONE.getDefaultState(), FeatureSpread.create(2, 3))).range(128).square().count(8));
    public static ConfiguredFeature<?, ?> GLOWCANYON_CRUMBLING_BLACKSTONE_BLOBS = registerConfiguredFeature("glowcanyon_crumbling_blackstone_blob", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(IEBlocks.DULLSTONE.get().getDefaultState(), IEBlocks.CRUMBLING_BLACKSTONE.get().getDefaultState(), FeatureSpread.create(3, 4))).range(128).square().count(2));

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
