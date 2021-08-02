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

package com.nekomaster1000.infernalexp.world.dimension;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class ModNetherMasterLayer implements IAreaTransformer0 {

    private final Registry<Biome> dynamicRegistry;

    public ModNetherMasterLayer(long seed, Registry<Biome> dynamicRegistry) {
        this.dynamicRegistry = dynamicRegistry;
    }

    @Override
    public int apply(INoiseRandom context, int x, int y) {
        return ModNetherBiomeCollector.getRandomNetherBiomes(context, this.dynamicRegistry);
    }
}
