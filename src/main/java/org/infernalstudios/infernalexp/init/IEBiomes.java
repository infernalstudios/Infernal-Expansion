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
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.biome.IEBiome;
import org.infernalstudios.infernalexp.world.biome.netherbiomes.GlowstoneCanyonBiome;

import java.util.ArrayList;
import java.util.List;

public class IEBiomes {
    private static final List<Pair<ResourceLocation, Climate.ParameterPoint>> biomeSource = new ArrayList<>();
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, InfernalExpansion.MOD_ID);

    public static RegistryObject<Biome> GLOWSTONE_CANYON = registerNetherBiome("glowstone_canyon", new GlowstoneCanyonBiome());
    //public static final RegistryObject<Biome> DELTA_SHORES = registerNetherBiome("delta_shores", () -> new DeltaShoresSubBiome().build());

    private static RegistryObject<Biome> registerNetherBiome(String name, IEBiome biome) {
        BiomeDictionary.addTypes(ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(InfernalExpansion.MOD_ID, name)), biome.getBiomeTypes());

        biomeSource.add(Pair.of(new ResourceLocation(InfernalExpansion.MOD_ID, name), biome.getBiomeParameters()));

        return BIOMES.register(name, biome::build);
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);

        InfernalExpansion.LOGGER.info("Infernal Expansion: Biomes Registered!");
    }

    public static List<Pair<ResourceLocation, Climate.ParameterPoint>> getBiomeSource() {
        return biomeSource;
    }
}


