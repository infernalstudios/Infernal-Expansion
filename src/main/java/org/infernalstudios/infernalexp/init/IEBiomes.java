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

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.biome.IEBiome;
import org.infernalstudios.infernalexp.world.biome.netherbiomes.GlowstoneCanyonBiome;

import java.util.ArrayList;
import java.util.List;

public class IEBiomes {
    private static final List<Pair<ResourceKey<Biome>, Climate.ParameterPoint>> biomeParameters = new ArrayList<>();

    private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, InfernalExpansion.MOD_ID);

    public static ResourceKey<Biome> GLOWSTONE_CANYON = registerBiome("glowstone_canyon", new GlowstoneCanyonBiome());

    private static ResourceKey<Biome> registerBiome(String name, IEBiome biome) {
        ResourceKey<Biome> resourceKey = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(InfernalExpansion.MOD_ID, name));

        biomeParameters.add(Pair.of(resourceKey, biome.getBiomeParameters()));
        BIOMES.register(name, biome::build);

        return resourceKey;
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Biomes Registered!");
    }

    public static List<Pair<ResourceKey<Biome>, Climate.ParameterPoint>> getBiomeParameters() {
        return biomeParameters;
    }
}


