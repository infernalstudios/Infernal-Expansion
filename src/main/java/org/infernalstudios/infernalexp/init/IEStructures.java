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
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.structures.SimpleNetherStructure;
import org.infernalstudios.infernalexp.world.gen.structures.StriderAltarStructure;

import java.util.ArrayList;
import java.util.List;

public class IEStructures {

    public static final List<StructureFeature<?>> structures = new ArrayList<>();

    public static final StructureFeature<JigsawConfiguration> SIMPLE_NETHER_STRUCTURE = registerStructure("simple_nether_structure", new SimpleNetherStructure());
    public static final StructureFeature<?> STRIDER_ALTAR = registerStructure("strider_altar", new StriderAltarStructure());

    private static StructureFeature<JigsawConfiguration> registerStructure(String registryName, StructureFeature<JigsawConfiguration> structure) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (ForgeRegistries.STRUCTURE_FEATURES.getKeys().contains(resourceLocation))
            throw new IllegalStateException("Feature ID: \"" + resourceLocation + "\" is already in the registry!");

        structure.setRegistryName(resourceLocation);
        structures.add(structure);

        return structure;
    }

}
