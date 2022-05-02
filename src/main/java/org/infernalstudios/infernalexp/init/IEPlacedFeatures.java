/*
 * Copyright 2021 Infernal Studios
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
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.infernalstudios.infernalexp.InfernalExpansion;

import java.util.List;

public class IEPlacedFeatures {

    public static final Holder<PlacedFeature> GLOWDUST_LAYER = registerPlacedFeature("glowdust_layer", IEConfiguredFeatures.GLOWDUST_LAYER);
    public static final Holder<PlacedFeature> GLOWSPIKE = registerPlacedFeature("glowspike", IEConfiguredFeatures.GLOWSPIKE, CountPlacement.of(30), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> GLOWSPIKE_LARGE = registerPlacedFeature("glowspike_large", IEConfiguredFeatures.GLOWSPIKE_LARGE, CountPlacement.of(30), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> DULLTHORN_TREE_PLANTED = registerPlacedFeature("dullthorn_tree_planted", IEConfiguredFeatures.DULLTHORN_TREE_PLANTED);
    public static final Holder<PlacedFeature> HANGING_GIANT_BROWN_MUSHROOM = registerPlacedFeature("hanging_giant_brown_mushroom", IEConfiguredFeatures.HANGING_GIANT_BROWN_MUSHROOM, CountPlacement.of(120), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> DULLSTONE_DEATH_PIT = registerPlacedFeature("dullstone_death_pit", IEConfiguredFeatures.DULLSTONE_DEATH_PIT, CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LUMINOUS_FUNGUS = registerPlacedFeature("luminous_fungus", IEConfiguredFeatures.LUMINOUS_FUNGUS, CountPlacement.of(20), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> DULLTHORNS = registerPlacedFeature("dullthorns", IEConfiguredFeatures.DULLTHORNS, CountPlacement.of(10), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> GSC_SPRING_OPEN = registerPlacedFeature("gsc_spring_open", IEConfiguredFeatures.GSC_SPRING_OPEN, CountPlacement.of(4), InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
    public static final Holder<PlacedFeature> GSC_SPRING_CLOSED = registerPlacedFeature("gsc_spring_closed", IEConfiguredFeatures.GSC_SPRING_CLOSED, CountPlacement.of(8), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> BLACKSTONE_BOULDER = registerPlacedFeature("blackstone_boulder", IEConfiguredFeatures.BLACKSTONE_BOULDER, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> GSC_BLACKSTONE_BLOBS = registerPlacedFeature("gsc_blackstone_blob", IEConfiguredFeatures.GSC_BLACKSTONE_BLOBS, CountPlacement.of(8), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ORE_GLOWSILK_COCOON = registerPlacedFeature("ore_glowsilk_cocoon", IEConfiguredFeatures.ORE_GLOWSILK_COCOON, InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ORE_BASALT_IRON_BASALT_DELTA = registerPlacedFeature("ore_basalt_iron_basalt_deltas", IEConfiguredFeatures.ORE_BASALT_IRON, CountPlacement.of(18), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ORE_BASALT_IRON_DELTA_SHORES = registerPlacedFeature("ore_basalt_iron_delta_shores", IEConfiguredFeatures.ORE_BASALT_IRON, CountPlacement.of(8), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PATCH_GLOW_FIRE = registerPlacedFeature("patch_glow_fire", IEConfiguredFeatures.PATCH_GLOW_FIRE, CountPlacement.of(UniformInt.of(0, 5)), InSquarePlacement.spread(), PlacementUtils.RANGE_4_4, BiomeFilter.biome());
    public static final Holder<PlacedFeature> BASALTIC_MAGMA = registerPlacedFeature("basaltic_magma", IEConfiguredFeatures.BASALTIC_MAGMA, CountPlacement.of(8), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PATCH_WARPED_CAP = registerPlacedFeature("patch_warped_cap", IEConfiguredFeatures.PATCH_WARPED_CAP, CountPlacement.of(20), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PATCH_CRIMSON_CAP = registerPlacedFeature("patch_crimson_cap", IEConfiguredFeatures.PATCH_CRIMSON_CAP, CountPlacement.of(20), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> SHROOMLIGHT_TEAR = registerPlacedFeature("shroomlight_tear", IEConfiguredFeatures.SHROOMLIGHT_TEAR, CountPlacement.of(100), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PATCH_BURIED_BONE = registerPlacedFeature("patch_buried_bone", IEConfiguredFeatures.PATCH_BURIED_BONE, CountPlacement.of(5), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ORE_SOUL_STONE = registerPlacedFeature("ore_soul_stone", IEConfiguredFeatures.ORE_SOUL_STONE, CountPlacement.of(45), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PATCH_PLANTED_QUARTZ = registerPlacedFeature("planted_quartz_patch", IEConfiguredFeatures.PATCH_PLANTED_QUARTZ, CountPlacement.of(20), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());

    private static Holder<PlacedFeature> registerPlacedFeature(String name, Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... placementModifiers) {
        return registerPlacedFeature(name, configuredFeature, List.of(placementModifiers));
    }

    private static Holder<PlacedFeature> registerPlacedFeature(String name, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> placementModifiers) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, name);

        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Placed Feature ID: \"" + resourceLocation + "\" is already in the registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, resourceLocation, new PlacedFeature(configuredFeature, placementModifiers));
    }

}
