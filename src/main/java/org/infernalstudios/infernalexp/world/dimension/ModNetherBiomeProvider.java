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

package org.infernalstudios.infernalexp.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.util.WorldSeedHolder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.LazyAreaContext;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.layer.Layer;
import net.minecraft.world.level.newbiome.layer.ZoomLayer;

import javax.annotation.Nonnull;
import java.util.function.LongFunction;

public class ModNetherBiomeProvider extends BiomeSource {

    public static void registerBiomeProvider() {
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(InfernalExpansion.MOD_ID, "biome_provider"), MOD_NETHER_CODEC);
    }

    public static final Codec<ModNetherBiomeProvider> MOD_NETHER_CODEC =
        RecordCodecBuilder.create((instance) -> instance.group(
                Codec.LONG.fieldOf("seed").orElseGet(WorldSeedHolder::getSeed).forGetter((biomeSource) -> biomeSource.seed),
                RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((biomeProvider) -> biomeProvider.biomeRegistry),
                Codec.intRange(1, 20).fieldOf("biome_size").orElse(1).forGetter((biomeSource) -> biomeSource.biomeSize))
            .apply(instance, ModNetherBiomeProvider::new));

    public final long seed;
    private final int biomeSize;
    private final Layer biomeLayer;
    protected final Registry<Biome> biomeRegistry;

    public ModNetherBiomeProvider(long seed, Registry<Biome> biomeRegistry, int size) {
        super(ModNetherBiomeCollector.netherBiomeCollection(biomeRegistry).stream().map((registryKey) -> () -> (Biome) biomeRegistry.get(registryKey)));

        this.seed = seed;
        this.biomeLayer = buildWorldProcedure(seed, size, biomeRegistry);
        this.biomeRegistry = biomeRegistry;
        this.biomeSize = size;
    }

    public static Layer buildWorldProcedure(long seed, int biomeSize, Registry<Biome> biomeRegistry) {
        AreaFactory<LazyArea> layerFactory = build((salt) -> new LazyAreaContext(25, seed, salt),
            biomeSize,
            seed,
            biomeRegistry);
        return new Layer(layerFactory);
    }

    public static <T extends Area, C extends BigContext<T>> AreaFactory<T> build(LongFunction<C> contextFactory, int biomeSize, long seed, Registry<Biome> biomeRegistry) {
        AreaFactory<T> layerFactory = (new ModNetherMasterLayer(seed, biomeRegistry)).run(contextFactory.apply(200L));

        for (int currentExtraZoom = 0; currentExtraZoom < biomeSize; currentExtraZoom++) {
            if ((currentExtraZoom + 2) % 3 != 0) {
                layerFactory = ZoomLayer.NORMAL.run(contextFactory.apply(2001L + currentExtraZoom), layerFactory);
            } else {
                layerFactory = ZoomLayer.FUZZY.run(contextFactory.apply(2000L + (currentExtraZoom * 31)), layerFactory);
            }
        }

        return layerFactory;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        int k = (this.biomeLayer).area.get(x, z);
        Biome biome = this.biomeRegistry.byId(k);
        if (biome == null) {
            //fallback to builtin registry if dynamic registry doesnt have biome
            if (SharedConstants.IS_RUNNING_IN_IDE) {
                throw Util.pauseInIde(new IllegalStateException("Unknown biome id: " + k));
            } else {
                return this.biomeRegistry.get(Biomes.byId(0));
            }
        } else {
            return biome;
        }
    }

    @Nonnull
    @Override
    protected Codec<? extends BiomeSource> codec() {
        return MOD_NETHER_CODEC;
    }

    @Nonnull
    @Override
    public BiomeSource withSeed(long seed) {
        return new ModNetherBiomeProvider(seed, this.biomeRegistry, this.biomeSize);
    }
}
