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

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.structures.SimpleNetherStructure;
import org.infernalstudios.infernalexp.world.gen.structures.StriderAltarStructure;

public class IEStructures {

    private static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<StructureFeature<?>> SIMPLE_NETHER_STRUCTURE = STRUCTURES.register("simple_nether_structure", SimpleNetherStructure::new);
    public static final RegistryObject<StructureFeature<?>> STRIDER_ALTAR = STRUCTURES.register("strider_altar", StriderAltarStructure::new);

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Structures Registered!");
    }

}
