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

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluids;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import org.infernalstudios.infernalexp.world.gen.features.config.PlantedQuartzFeatureConfig;

import java.util.List;

public class IEConfiguredFeatures {

    public static final RuleTest BASALT = new BlockMatchTest(Blocks.BASALT);
    public static final RuleTest SOUL_SOIL = new BlockMatchTest(Blocks.SOUL_SOIL);

    public static final Holder<ConfiguredFeature<?, ?>> GLOWDUST_LAYER = registerConfiguredFeature("glowdust_layer", IEFeatures.GLOWDUST_LAYER);
    public static final Holder<ConfiguredFeature<?, ?>> GLOWSPIKE = registerConfiguredFeature("glowspike", IEFeatures.GLOWSPIKE, new GlowSpikeFeatureConfig(3, 5, 8, 24, 7, 7, 0.3f, true));
    public static final Holder<ConfiguredFeature<?, ?>> GLOWSPIKE_LARGE = registerConfiguredFeature("glowspike_large", IEFeatures.GLOWSPIKE, new GlowSpikeFeatureConfig(4, 7, 12, 98, 12, 12, 0.2f, false));
    public static final Holder<ConfiguredFeature<?, ?>> DULLTHORN_TREE_PLANTED = registerConfiguredFeature("dullthorn_tree_planted", Feature.HUGE_FUNGUS, new HugeFungusConfiguration(IEBlocks.GLOWDUST_SAND.get().defaultBlockState(), IEBlocks.DULLTHORNS_BLOCK.get().defaultBlockState(), IEBlocks.LUMINOUS_WART_BLOCK.get().defaultBlockState(), IEBlocks.LUMINOUS_WART_BLOCK.get().defaultBlockState(), true));
    public static final Holder<ConfiguredFeature<?, ?>> HANGING_GIANT_BROWN_MUSHROOM = registerConfiguredFeature("hanging_giant_brown_mushroom", IEFeatures.HANGING_GIANT_BROWN_MUSHROOM);
    public static final Holder<ConfiguredFeature<?, ?>> DULLSTONE_DEATH_PIT = registerConfiguredFeature("dullstone_death_pit", IEFeatures.DULLSTONE_DEATH_PIT);
    public static final Holder<ConfiguredFeature<?, ?>> LUMINOUS_FUNGUS = registerConfiguredFeature("luminous_fungus", IEFeatures.LUMINOUS_FUNGUS);
    public static final Holder<ConfiguredFeature<?, ?>> DULLTHORNS = registerConfiguredFeature("dullthorns", IEFeatures.DULLTHORNS);
    public static final Holder<ConfiguredFeature<?, ?>> GSC_SPRING_OPEN = registerConfiguredFeature("gsc_spring_open", Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, Blocks.NETHERRACK, IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get())));
    public static final Holder<ConfiguredFeature<?, ?>> GSC_SPRING_CLOSED = registerConfiguredFeature("gsc_spring_closed", Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 5, 0, HolderSet.direct(Block::builtInRegistryHolder, Blocks.NETHERRACK, IEBlocks.DULLSTONE.get(), IEBlocks.DIMSTONE.get())));
    public static final Holder<ConfiguredFeature<?, ?>> BLACKSTONE_BOULDER = registerConfiguredFeature("blackstone_boulder", IEFeatures.BOULDER, new BlockStateConfiguration(Blocks.BLACKSTONE.defaultBlockState()));
    public static final Holder<ConfiguredFeature<?, ?>> GSC_BLACKSTONE_BLOBS = registerConfiguredFeature("gsc_blackstone_blob", Feature.REPLACE_BLOBS, new ReplaceSphereConfiguration(IEBlocks.DULLSTONE.get().defaultBlockState(), Blocks.BLACKSTONE.defaultBlockState(), UniformInt.of(2, 3)));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_GLOWSILK_COCOON = registerConfiguredFeature("ore_glowsilk_cocoon", Feature.SCATTERED_ORE, new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, IEBlocks.GLOWSILK_COCOON.get().defaultBlockState(), 2));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_BASALT_IRON = registerConfiguredFeature("ore_basalt_iron", Feature.ORE, new OreConfiguration(BASALT, IEBlocks.BASALT_IRON_ORE.get().defaultBlockState(), 8));
    public static final Holder<ConfiguredFeature<?, ?>> PATCH_GLOW_FIRE = registerConfiguredFeature("patch_glow_fire", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(IEBlocks.GLOW_FIRE.get())), List.of(IEBlocks.GLOWDUST_SAND.get())));
    public static final Holder<ConfiguredFeature<?, ?>> BASALTIC_MAGMA = registerConfiguredFeature("basaltic_magma", Feature.ORE, new OreConfiguration(BASALT, IEBlocks.BASALTIC_MAGMA.get().defaultBlockState(), 10));
    public static final Holder<ConfiguredFeature<?, ?>> PATCH_WARPED_CAP = registerConfiguredFeature("patch_warped_cap", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(IEBlocks.WARPED_FUNGUS_CAP.get())), List.of(Blocks.WARPED_NYLIUM)));
    public static final Holder<ConfiguredFeature<?, ?>> PATCH_CRIMSON_CAP = registerConfiguredFeature("patch_crimson_cap", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(IEBlocks.CRIMSON_FUNGUS_CAP.get())), List.of(Blocks.CRIMSON_NYLIUM)));
    public static final Holder<ConfiguredFeature<?, ?>> SHROOMLIGHT_TEAR = registerConfiguredFeature("shroomlight_tear", IEFeatures.SHROOMLIGHT_TEAR);
    public static final Holder<ConfiguredFeature<?, ?>> PATCH_BURIED_BONE = registerConfiguredFeature("patch_buried_bone", Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(IEBlocks.BURIED_BONE.get())), List.of(Blocks.SOUL_SOIL)));
    public static final Holder<ConfiguredFeature<?, ?>> ORE_SOUL_STONE = registerConfiguredFeature("ore_soul_stone", Feature.ORE, new OreConfiguration(SOUL_SOIL, IEBlocks.SOUL_STONE.get().defaultBlockState(), 40));
    public static final Holder<ConfiguredFeature<?, ?>> PATCH_PLANTED_QUARTZ = registerConfiguredFeature("planted_quartz_patch", IEFeatures.PATCH_PLANTED_QUARTZ, new PlantedQuartzFeatureConfig(0.95F));

    private static Holder<ConfiguredFeature<?, ?>> registerConfiguredFeature(String name, Feature<NoneFeatureConfiguration> feature) {
        return registerConfiguredFeature(name, feature, FeatureConfiguration.NONE);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<?, ?>> registerConfiguredFeature(String name, F feature, FC featureConfiguration) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, name);

        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Configured Feature ID: \"" + resourceLocation + "\" is already in the registry!");

        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, resourceLocation, new ConfiguredFeature<>(feature, featureConfiguration));
    }

}
