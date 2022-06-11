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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.features.BoulderFeature;
import org.infernalstudios.infernalexp.world.gen.features.DullthornsFeature;
import org.infernalstudios.infernalexp.world.gen.features.GlowLayerFeature;
import org.infernalstudios.infernalexp.world.gen.features.GlowSpikeFeature;
import org.infernalstudios.infernalexp.world.gen.features.HangingGiantBrownMushroomFeature;
import org.infernalstudios.infernalexp.world.gen.features.LuminousFungusFeature;
import org.infernalstudios.infernalexp.world.gen.features.PlantedQuartzFeature;
import org.infernalstudios.infernalexp.world.gen.features.ShroomlightTearFeature;
import org.infernalstudios.infernalexp.world.gen.features.SinkHoleFeature;
import org.infernalstudios.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import org.infernalstudios.infernalexp.world.gen.features.config.PlantedQuartzFeatureConfig;

import java.util.HashMap;
import java.util.Map;

public class IEFeatures {

    public static Map<ResourceLocation, Feature<?>> features = new HashMap<>();

    public static final Feature<NoneFeatureConfiguration> GLOWDUST_LAYER = registerFeature("glowdust_layer", new GlowLayerFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<GlowSpikeFeatureConfig> GLOWSPIKE = registerFeature("glowspike", new GlowSpikeFeature(GlowSpikeFeatureConfig.CODEC));
    public static final Feature<NoneFeatureConfiguration> HANGING_GIANT_BROWN_MUSHROOM = registerFeature("hanging_giant_brown_mushroom", new HangingGiantBrownMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> LUMINOUS_FUNGUS = registerFeature("luminous_fungus", new LuminousFungusFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> DULLTHORNS = registerFeature("dullthorns", new DullthornsFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<BlockStateConfiguration> BOULDER = registerFeature("blackstone_boulder", new BoulderFeature(BlockStateConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> DULLSTONE_DEATH_PIT = registerFeature("glowdust_sink_hole", new SinkHoleFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> SHROOMLIGHT_TEAR = registerFeature("shroomlight_tear", new ShroomlightTearFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<PlantedQuartzFeatureConfig> PATCH_PLANTED_QUARTZ = registerFeature("planted_quartz_patch", new PlantedQuartzFeature(PlantedQuartzFeatureConfig.CODEC));

    public static <C extends FeatureConfiguration, F extends Feature<C>> F registerFeature(String registryName, F feature) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (ForgeRegistries.FEATURES.getKeys().contains(resourceLocation))
            throw new IllegalStateException("Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        features.put(resourceLocation, feature);

        return feature;
    }
}
