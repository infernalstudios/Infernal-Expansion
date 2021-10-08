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

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import org.infernalstudios.infernalexp.InfernalExpansion;

public class IEConfiguredStructures {

    public static ConfiguredStructureFeature<?, ?> GLOWSTONE_CANYON_RUIN = registerConfiguredStructure("glowstone_canyon_ruin", IEStructures.GLOWSTONE_CANYON_RUIN.configured(FeatureConfiguration.NONE));
    public static ConfiguredStructureFeature<?, ?> BASTION_OUTPOST = registerConfiguredStructure("bastion_outpost", IEStructures.BASTION_OUTPOST.configured(FeatureConfiguration.NONE));
    public static ConfiguredStructureFeature<?, ?> SOUL_SAND_VALLEY_RUIN = registerConfiguredStructure("soul_sand_valley_ruin", IEStructures.SOUL_SAND_VALLEY_RUIN.configured(FeatureConfiguration.NONE));
    public static ConfiguredStructureFeature<?, ?> STRIDER_ALTAR = registerConfiguredStructure("strider_altar", IEStructures.STRIDER_ALTAR.configured(FeatureConfiguration.NONE));

    public static ConfiguredStructureFeature<?, ?> registerConfiguredStructure(String registryName, ConfiguredStructureFeature<?, ?> structureFeature) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Configured Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(structureFeature.feature, structureFeature);

        return Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, resourceLocation, structureFeature);
    }

}
