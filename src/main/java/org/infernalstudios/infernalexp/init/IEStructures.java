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
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.structures.SizeCheckingNetherStructure;
import org.infernalstudios.infernalexp.world.gen.structures.StriderAltarStructure;

import java.util.Map;

public class IEStructures {

    public static final Holder<Structure> GLOWSTONE_CANYON_RUIN = registerStructure("glowstone_canyon_ruin", new SizeCheckingNetherStructure(settings(IETags.Biomes.HAS_GLOWSTONE_CANYON_RUIN, TerrainAdjustment.BEARD_THIN), IEStructurePools.GLOWSTONE_CANYON_RUIN, 5));
    public static final Holder<Structure> SOUL_SAND_VALLEY_RUIN = registerStructure("soul_sand_valley_ruin", new SizeCheckingNetherStructure(settings(IETags.Biomes.HAS_SOUL_SAND_VALLEY_RUIN, TerrainAdjustment.BEARD_THIN), IEStructurePools.SOUL_SAND_VALLEY_RUIN, 4));
    public static final Holder<Structure> BASTION_OUTPOST = registerStructure("bastion_outpost", new SizeCheckingNetherStructure(settings(IETags.Biomes.HAS_BASTION_OUTPOST, TerrainAdjustment.BEARD_BOX), IEStructurePools.BASTION_OUTPOST, 8));
    public static final Holder<Structure> STRIDER_ALTAR = registerStructure("strider_altar", new StriderAltarStructure(settings(IETags.Biomes.HAS_STRIDER_ALTAR, TerrainAdjustment.NONE), IEStructurePools.STRIDER_ALTAR));

    private static Holder<Structure> registerStructure(String name, Structure structure) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, name);

        if (BuiltinRegistries.STRUCTURES.keySet().contains(resourceLocation))
            throw new IllegalStateException("Configured Structure ID: \"" + resourceLocation + "\" is already in the registry!");

        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, resourceLocation, structure);
    }

    private static Structure.StructureSettings settings(TagKey<Biome> biomes, TerrainAdjustment terrainAdjustment) {
        return new Structure.StructureSettings(biomes(biomes), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, terrainAdjustment);
    }

    private static HolderSet<Biome> biomes(TagKey<Biome> tag) {
        return BuiltinRegistries.BIOME.getOrCreateTag(tag);
    }

    public static void register() {}

}
