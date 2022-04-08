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

package org.infernalstudios.infernalexp.world.gen.structures;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.infernalstudios.infernalexp.InfernalExpansion;

import java.util.function.Function;

public class PoolUtil {

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacy(String name, Holder<StructureProcessorList> processorList) {
        return StructurePoolElement.legacy(InfernalExpansion.MOD_ID + ":" + name, processorList);
    }

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacy(String name) {
        return StructurePoolElement.legacy(InfernalExpansion.MOD_ID + ":" + name);
    }

}
