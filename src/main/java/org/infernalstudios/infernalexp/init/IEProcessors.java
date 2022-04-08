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

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.processors.LootChestProcessor;

public class IEProcessors {

    public static final StructureProcessorType<LootChestProcessor> LOOT_CHEST = registerProcessor("loot_chest", LootChestProcessor.CODEC);

    public static final Holder<StructureProcessorList> LOOT_CHEST_LIST = registerProcessorList("loot_chest", ImmutableList.of(new LootChestProcessor()));

    private static <P extends StructureProcessor> StructureProcessorType<P> registerProcessor(String name, Codec<P> processorCodec) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(InfernalExpansion.MOD_ID, name), () -> processorCodec);
    }

    private static Holder<StructureProcessorList> registerProcessorList(String name, ImmutableList<StructureProcessor> processors) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, name);

        if (BuiltinRegistries.PROCESSOR_LIST.keySet().contains(resourceLocation))
            throw new IllegalStateException("Processor List ID: \"" + resourceLocation + "\" is already in the registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, resourceLocation, new StructureProcessorList(processors));
    }

    public static void bootstrap() {
    }
}
