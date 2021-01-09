package com.nekomaster1000.infernalexp.world.dimension;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import javax.annotation.ParametersAreNonnullByDefault;

public class ModNetherMasterLayer implements IAreaTransformer0 {
    private final Registry<Biome> biomeRegistry;

    public ModNetherMasterLayer(Registry<Biome> biomeRegistry) {
        this.biomeRegistry = biomeRegistry;
    }

    @ParametersAreNonnullByDefault
    @Override
    public int apply(INoiseRandom random, int x, int y) {
        return getRandomNetherBiomes(this.biomeRegistry, random);
    }

    public static int getRandomNetherBiomes(Registry<Biome> biomeRegistry, INoiseRandom random) {
        return biomeRegistry.getId(biomeRegistry.getOptional(ModNetherBiomeProvider.NETHER_BIOMES.get(random.random(ModNetherBiomeProvider.NETHER_BIOMES.size()))).orElseThrow(RuntimeException::new));
    }
}
