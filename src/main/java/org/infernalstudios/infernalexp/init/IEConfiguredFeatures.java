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

package org.infernalstudios.infernalexp.init;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluids;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import org.infernalstudios.infernalexp.world.gen.features.config.PlantedQuartzFeatureConfig;

public class IEConfiguredFeatures {

    public static final RuleTest BASALT = new BlockMatchTest(Blocks.BASALT);
    public static final RuleTest SOUL_SOIL = new BlockMatchTest(Blocks.SOUL_SOIL);

    public static ConfiguredFeature<?, ?> GLOWDUST_LAYER = registerConfiguredFeature("glowdust_layer", IEFeatures.GLOWDUST_LAYER.configured(FeatureConfiguration.NONE));
    public static ConfiguredFeature<?, ?> GLOWSPIKE = registerConfiguredFeature("glowspike", IEFeatures.GLOWSPIKE.configured(new GlowSpikeFeatureConfig(3, 5, 8, 24, 7, 7, 0.3f, true)).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(30)));
    public static ConfiguredFeature<?, ?> GLOWSPIKELARGE = registerConfiguredFeature("glowspikelarge", IEFeatures.GLOWSPIKE.configured(new GlowSpikeFeatureConfig(4, 7, 12, 98, 12, 12, 0.2f, false)).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(30)));
    public static ConfiguredFeature<?, ?> DULLTHORN_TREE_PLANTED = registerConfiguredFeature("dullthorn_tree_planted", Feature.HUGE_FUNGUS.configured(new HugeFungusConfiguration(IEBlocks.GLOWDUST_SAND.get().defaultBlockState(), IEBlocks.DULLTHORNS_BLOCK.get().defaultBlockState(), IEBlocks.LUMINOUS_WART_BLOCK.get().defaultBlockState(), IEBlocks.LUMINOUS_WART_BLOCK.get().defaultBlockState(), true)));
    public static ConfiguredFeature<?, ?> HANGING_GIANT_BROWN_MUSHROOM = registerConfiguredFeature("hanging_giant_brown_mushroom", IEFeatures.HANGING_GIANT_BROWN_MUSHROOM.configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(120)));
    public static ConfiguredFeature<?, ?> DULLSTONE_DEATH_PIT = registerConfiguredFeature("dullstone_death_pit", IEFeatures.DULLSTONE_DEATH_PIT.configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(90)));
    public static ConfiguredFeature<?, ?> LUMINOUS_FUNGUS = registerConfiguredFeature("luminous_fungus", IEFeatures.LUMINOUS_FUNGUS.configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(20)));
    public static ConfiguredFeature<?, ?> DULLTHORNS = registerConfiguredFeature("dullthorns", IEFeatures.DULLTHORNS.configured(FeatureConfiguration.NONE).decorated(FeatureDecorator.COUNT_MULTILAYER.configured(new CountConfiguration(5))));
    public static ConfiguredFeature<?, ?> GSC_SPRING_OPEN = registerConfiguredFeature("gsc_spring_open", Feature.SPRING.configured(new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get()))).range(Features.Decorators.RANGE_4_4).squared().count(4));
    public static ConfiguredFeature<?, ?> GSC_SPRING_CLOSED = registerConfiguredFeature("gsc_spring_closed", Feature.SPRING.configured(new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK, IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get()))).range(Features.Decorators.RANGE_10_10).squared().count(8));
    public static ConfiguredFeature<?, ?> BLACKSTONE_BOULDER = registerConfiguredFeature("blackstone_boulder", IEFeatures.BOULDER.configured(new BlockStateConfiguration(Blocks.BLACKSTONE.defaultBlockState())).decorated(FeatureDecorator.COUNT_MULTILAYER.configured(new CountConfiguration(1))));
    public static ConfiguredFeature<?, ?> CANYON_BLACKSTONE_BLOBS = registerConfiguredFeature("glowcanyon_blackstone_blob", Feature.REPLACE_BLOBS.configured(new ReplaceSphereConfiguration(IEBlocks.DULLSTONE.get().defaultBlockState(), Blocks.BLACKSTONE.defaultBlockState(), UniformInt.of(2, 3))).range(Features.Decorators.FULL_RANGE).squared().count(8));
    public static ConfiguredFeature<?, ?> ORE_GLOWSILK_COCOON = registerConfiguredFeature("ore_glowsilk_cocoon", Feature.SCATTERED_ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES, IEBlocks.GLOWSILK_COCOON.get().defaultBlockState(), 2)).range(Features.Decorators.RANGE_8_8).squared());
    public static ConfiguredFeature<?, ?> ORE_BASALT_IRON_BASALT_DELTA = registerConfiguredFeature("ore_basalt_iron_basalt_deltas", Feature.ORE.configured(new OreConfiguration(BASALT, IEBlocks.BASALT_IRON_ORE.get().defaultBlockState(), 8)).range(Features.Decorators.RANGE_10_10).squared().count(18));
    public static ConfiguredFeature<?, ?> ORE_BASALT_IRON_DELTA_SHORES = registerConfiguredFeature("ore_basalt_iron_delta_shores", Feature.ORE.configured(new OreConfiguration(BASALT, IEBlocks.BASALT_IRON_ORE.get().defaultBlockState(), 8)).range(Features.Decorators.RANGE_10_10).squared().count(8));
    public static ConfiguredFeature<?, ?> PATCH_GLOW_FIRE = registerConfiguredFeature("patch_glow_fire", Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(IEBlocks.GLOW_FIRE.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(IEBlocks.GLOWDUST_SAND.get())).noProjection().build()).decorated(Features.Decorators.FIRE));
    public static ConfiguredFeature<?, ?> BASALTIC_MAGMA = registerConfiguredFeature("basaltic_magma", Feature.ORE.configured(new OreConfiguration(BASALT, IEBlocks.BASALTIC_MAGMA.get().defaultBlockState(), 10)).range(Features.Decorators.RANGE_10_10).squared().count(8));
    public static ConfiguredFeature<?, ?> PATCH_WARPED_CAP = registerConfiguredFeature("patch_warped_cap", Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(IEBlocks.WARPED_FUNGUS_CAP.get().defaultBlockState()), new SimpleBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.WARPED_NYLIUM)).noProjection().build()));
    public static ConfiguredFeature<?, ?> PATCH_CRIMSON_CAP = registerConfiguredFeature("patch_crimson_cap", Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(IEBlocks.CRIMSON_FUNGUS_CAP.get().defaultBlockState()), new SimpleBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Blocks.WARPED_NYLIUM)).noProjection().build()));
    public static ConfiguredFeature<?, ?> SHROOMLIGHT_TEAR = registerConfiguredFeature("shroomlight_tear", IEFeatures.SHROOMLIGHT_TEAR.configured(FeatureConfiguration.NONE).range(Features.Decorators.FULL_RANGE).squared().count(100));
    public static ConfiguredFeature<?, ?> PATCH_BURIED_BONE = registerConfiguredFeature("patch_buried_bone", Feature.RANDOM_PATCH.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(IEBlocks.BURIED_BONE.get().defaultBlockState()), new SimpleBlockPlacer())).tries(128).whitelist(ImmutableSet.of(Blocks.SOUL_SOIL)).noProjection().build()));
    public static ConfiguredFeature<?, ?> ORE_SOUL_STONE = registerConfiguredFeature("ore_soul_stone", Feature.ORE.configured(new OreConfiguration(SOUL_SOIL, IEBlocks.SOUL_STONE.get().defaultBlockState(), 40)).range(Features.Decorators.RANGE_10_10).squared().count(45));
    public static ConfiguredFeature<?, ?> PATCH_PLANTED_QUARTZ = registerConfiguredFeature("planted_quartz_patch", IEFeatures.PATCH_PLANTED_QUARTZ.configured(new PlantedQuartzFeatureConfig(0.95F)).decorated(Features.Decorators.HEIGHTMAP_DOUBLE_SQUARE.count(10)));

    //public static final ConfiguredFeature<?, ?> PATCH_CRIMSON_ROOTS = register("patch_crimson_roots", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.CRIMSON_ROOTS), new SimpleBlockPlacer())).tries(64).preventProjection().build()).range(128));


    //public static final ConfiguredFeature<?, ?> PATCH_PUMPKIN = register("patch_pumpkin", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.PUMPKIN), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Features.States.GRASS_BLOCK.getBlock())).preventProjection().build()).withPlacement(Features.Placements.PATCH_PLACEMENT).chance(32));


    //    public static final ConfiguredFeature<?, ?> RANDOM_GLOWSTONE_CANYON_PLANT = registerConfiguredFeature("rand_glowstone_canyon", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
//            LUMINOUS_FUNGUS.withChance(0.5F)),
//            LUMINOUS_FUNGUS)).withPlacement(Placement.COUNT_MULTILAYER.configure(new FeatureSpreadConfig(15))));

    public static ConfiguredFeature<?, ?> registerConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Configured Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, resourceLocation, configuredFeature);
    }

}
