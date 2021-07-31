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

package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class IEConfiguredStructures {

	public static StructureFeature<?, ?> GLOWSTONE_CANYON_RUIN = registerConfiguredStructure("glowstone_canyon_ruin", IEStructures.GLOWSTONE_CANYON_RUIN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
	public static StructureFeature<?, ?> BASTION_OUTPOST = registerConfiguredStructure("bastion_outpost", IEStructures.BASTION_OUTPOST.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
	public static StructureFeature<?, ?> SOUL_SAND_VALLEY_RUIN = registerConfiguredStructure("soul_sand_valley_ruin", IEStructures.SOUL_SAND_VALLEY_RUIN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static StructureFeature<?, ?> STRIDER_ALTAR = registerConfiguredStructure("strider_altar", IEStructures.STRIDER_ALTAR.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

	public static StructureFeature<?, ?> registerConfiguredStructure(String registryName, StructureFeature<?, ?> structureFeature) {
		ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

		if (WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE.keySet().contains(resourceLocation))
			throw new IllegalStateException("Configured Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

		FlatGenerationSettings.STRUCTURES.put(structureFeature.field_236268_b_.getStructure(), structureFeature);

		return Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, resourceLocation, structureFeature);
	}

}
