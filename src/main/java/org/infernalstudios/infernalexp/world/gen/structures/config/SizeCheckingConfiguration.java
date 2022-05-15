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

package org.infernalstudios.infernalexp.world.gen.structures.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class SizeCheckingConfiguration extends JigsawConfiguration {

    public static final Codec<SizeCheckingConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(
                StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(SizeCheckingConfiguration::startPool),
                Codec.intRange(0, 7).fieldOf("size").forGetter(SizeCheckingConfiguration::maxDepth),
                Codec.INT.fieldOf("size_to_check").forGetter(SizeCheckingConfiguration::sizeToCheck))
            .apply(instance, SizeCheckingConfiguration::new));

    private final int sizeToCheck;

    public SizeCheckingConfiguration(Holder<StructureTemplatePool> startPool, int maxDepth, int sizeToCheck) {
        super(startPool, maxDepth);
        this.sizeToCheck = sizeToCheck;
    }

    public int sizeToCheck() {
        return this.sizeToCheck;
    }

}
