package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class ModConfiguredFeatures {

    public static final RuleTest BASE_STONE_CANYON = new TagMatchRuleTest(ModTags.Blocks.BASE_STONE_CANYON);
    public static final RuleTest BASE_STONE_SHORES = new TagMatchRuleTest(ModTags.Blocks.BASE_STONE_SHORES);

    public static ConfiguredFeature<?, ?> GLOWSPIKE = registerConfiguredFeature("glowspike",                                                    ModFeatures.GLOWSPIKE.withConfiguration(new GlowSpikeFeatureConfig(3, 5, 8, 24, 7, 7, 0.3f, true)).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> GLOWSPIKELARGE = registerConfiguredFeature("glowspikelarge",                                          ModFeatures.GLOWSPIKE.withConfiguration(new GlowSpikeFeatureConfig(4, 7, 12, 98, 12, 12, 0.2f, false)).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> GLOWSTONE_RAVINE = registerConfiguredFeature("glowstone_ravine",                                      ModFeatures.GLOWSTONE_RAVINE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> HANGING_GIANT_BROWN_MUSHROOM = registerConfiguredFeature("hanging_giant_brown_mushroom",              ModFeatures.HANGING_GIANT_BROWN_MUSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> DULLSTONE_DEATH_PIT = registerConfiguredFeature("dullstone_death_pit",                                ModFeatures.DULLSTONE_DEATH_PIT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(90)));
    public static ConfiguredFeature<?, ?> LUMINOUS_FUNGUS = registerConfiguredFeature("luminous_fungus",                                        ModFeatures.LUMINOUS_FUNGUS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(20)));
    public static ConfiguredFeature<?, ?> DULLTHORNS = registerConfiguredFeature("dullthorns",                                                  ModFeatures.DULLTHORNS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(5))));
    public static ConfiguredFeature<?, ?> BLACKSTONE_BOULDER = registerConfiguredFeature("blackstone_boulder",                                  ModFeatures.BOULDER.withConfiguration(new BlockStateFeatureConfig(Blocks.BLACKSTONE.getDefaultState())).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(1))));
    public static final ConfiguredFeature<?, ?> CANYON_BLACKSTONE_ORE = registerConfiguredFeature("canyon_blackstone_ore",                      Feature.ORE.withConfiguration(new OreFeatureConfig(BASE_STONE_CANYON, Blocks.BLACKSTONE.getDefaultState(), 22)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128))).square().func_242731_b(16));
    public static final ConfiguredFeature<?, ?> CANYON_CRUMBLING_BLACKSTONE_ORE = registerConfiguredFeature("canyon_crumbling_blackstone_ore",  Feature.ORE.withConfiguration(new OreFeatureConfig(BASE_STONE_CANYON, ModBlocks.CRUMBLING_BLACKSTONE.get().getDefaultState(), 22)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128))).square().func_242731_b(12));
    public static final ConfiguredFeature<?, ?> SHORES_RUBBLE_ORE = registerConfiguredFeature("shores_rubble_ore",                              Feature.ORE.withConfiguration(new OreFeatureConfig(BASE_STONE_SHORES, ModBlocks.RUBBLE.get().getDefaultState(), 30)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(0, 0, 128))).square().func_242731_b(24));

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
