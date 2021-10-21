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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.structures.BastionOutpostStructure;
import org.infernalstudios.infernalexp.world.gen.structures.GlowstoneCanyonRuinStructure;
import org.infernalstudios.infernalexp.world.gen.structures.IEStructure;
import org.infernalstudios.infernalexp.world.gen.structures.SoulSandValleyRuinStructure;
import org.infernalstudios.infernalexp.world.gen.structures.StriderAltarStructure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IEStructures {

    public static List<IEStructure<?>> structures = new ArrayList<>();


    public static final IEStructure<NoneFeatureConfiguration> GLOWSTONE_CANYON_RUIN = registerStructure("glowstone_canyon_ruin", new GlowstoneCanyonRuinStructure(NoneFeatureConfiguration.CODEC));
    public static final IEStructure<NoneFeatureConfiguration> BASTION_OUTPOST = registerStructure("bastion_outpost", new BastionOutpostStructure(NoneFeatureConfiguration.CODEC));
    public static final IEStructure<NoneFeatureConfiguration> SOUL_SAND_VALLEY_RUIN = registerStructure("soul_sand_valley_ruin", new SoulSandValleyRuinStructure(NoneFeatureConfiguration.CODEC));
    public static final IEStructure<NoneFeatureConfiguration> STRIDER_ALTAR = registerStructure("strider_altar", new StriderAltarStructure(NoneFeatureConfiguration.CODEC));

    @SuppressWarnings("deprecation")
    public static <C extends FeatureConfiguration, F extends IEStructure<C>> F registerStructure(String registryName, F structure) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (Registry.STRUCTURE_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Structure ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        structure.setRegistryName(resourceLocation);
        structures.add(structure);

        return structure;
    }

    public static void setupStructures() {
        structures.forEach(structure -> setupMapSpacingAndLand(structure, structure.getSeparationSettings(), structure.shouldTransformLand()));
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure, StructureFeatureConfiguration structureSeparationSettings, boolean transformSurroundingLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(structure).build();
        }

        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder().putAll(StructureSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            if (structureMap instanceof ImmutableMap) {
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);

                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;

            } else {
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }

}
