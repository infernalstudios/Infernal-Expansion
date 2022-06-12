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

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.structures.SizeCheckingNetherStructure;
import org.infernalstudios.infernalexp.world.gen.structures.StriderAltarStructure;

public class IEStructureTypes {

    public static final StructureType<SizeCheckingNetherStructure> SIZE_CHECKING_NETHER_STRUCTURE = registerStructureType("simple_nether_structure", SizeCheckingNetherStructure.CODEC);
    public static final StructureType<StriderAltarStructure> STRIDER_ALTAR = registerStructureType("strider_altar", StriderAltarStructure.CODEC);

    private static <S extends Structure> StructureType<S> registerStructureType(String registryName, Codec<S> codec) {
        return Registry.register(Registry.STRUCTURE_TYPES, new ResourceLocation(InfernalExpansion.MOD_ID, registryName), () -> codec);
    }

    public static void register() {}

}
