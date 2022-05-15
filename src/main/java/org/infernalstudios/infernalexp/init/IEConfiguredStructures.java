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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import org.infernalstudios.infernalexp.InfernalExpansion;

public class IEConfiguredStructures {

    public static final Holder<ConfiguredStructureFeature<?, ?>> GLOWSTONE_CANYON_RUIN = registerConfiguredStructure("glowstone_canyon_ruin", IEStructures.SIMPLE_NETHER_STRUCTURE.configured(new JigsawConfiguration(IEStructurePools.GLOWSTONE_CANYON_RUIN, 1), IETags.Biomes.HAS_GLOWSTONE_CANYON_RUIN, true));
    public static final Holder<ConfiguredStructureFeature<?, ?>> SOUL_SAND_VALLEY_RUIN = registerConfiguredStructure("soul_sand_valley_ruin", IEStructures.SIMPLE_NETHER_STRUCTURE.configured(new JigsawConfiguration(IEStructurePools.SOUL_SAND_VALLEY_RUIN, 1), IETags.Biomes.HAS_SOUL_SAND_VALLEY_RUIN, true));
    public static final Holder<ConfiguredStructureFeature<?, ?>> BASTION_OUTPOST = registerConfiguredStructure("bastion_outpost", IEStructures.SIMPLE_NETHER_STRUCTURE.configured(new JigsawConfiguration(IEStructurePools.BASTION_OUTPOST, 1), IETags.Biomes.HAS_BASTION_OUTPOST, true));
    public static final Holder<ConfiguredStructureFeature<?, ?>> STRIDER_ALTAR = registerConfiguredStructure("strider_altar", IEStructures.STRIDER_ALTAR.configured(new JigsawConfiguration(IEStructurePools.STRIDER_ALTAR, 1), IETags.Biomes.HAS_STRIDER_ALTAR, false));

    private static <SC extends FeatureConfiguration, S extends StructureFeature<SC>> Holder<ConfiguredStructureFeature<?, ?>> registerConfiguredStructure(String name, ConfiguredStructureFeature<SC, S> configuredStructure) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, name);

        if (BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.keySet().contains(resourceLocation))

            throw new IllegalStateException("Configured Structure ID: \"" + resourceLocation + "\" is already in the registry!");

        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, resourceLocation, configuredStructure);
    }

    public static void register() {
    }

}
