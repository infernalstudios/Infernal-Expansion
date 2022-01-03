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

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.biome.ModBiome;
import org.infernalstudios.infernalexp.world.biome.netherbiomes.GlowstoneCanyonBiome;

public class IEBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Biome> GLOWSTONE_CANYON = registerNetherBiome("glowstone_canyon", new GlowstoneCanyonBiome());
    //public static final RegistryObject<Biome> DELTA_SHORES = registerNetherBiome("delta_shores", () -> new DeltaShoresSubBiome().build());

    private static RegistryObject<Biome> registerNetherBiome(String name, ModBiome biome) {
        BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(InfernalExpansion.MOD_ID, name)), biome.getBiomeTypes());

        return BIOMES.register(name, biome::build);
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Biomes Registered!");
    }
}


