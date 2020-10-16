package com.nekomaster1000.infernalexp.world.biome;

import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.HashMap;

public interface BiomeTools {

    default Biome getRiver() {
        return WorldGenRegistries.BIOME.getOrThrow(Biomes.RIVER);
    }

    default HashMap<Biome, Integer> getHills() {
        return new HashMap<>();
    }

    default HashMap<Biome, Integer> getEdges() {
        return new HashMap<>();
    }

    default HashMap<Biome, Integer> getBeaches() {
        return new HashMap<>();
    }

    default HashMap<Biome, Integer> getMutations() {
        return new HashMap<>();
    }

}
