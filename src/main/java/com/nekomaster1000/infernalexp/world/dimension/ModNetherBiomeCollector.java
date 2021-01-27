package com.nekomaster1000.infernalexp.world.dimension;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class ModNetherBiomeCollector {
    public static final ForgeRegistry<Biome> biomeRegistry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);

    public static List<Biome> netherBiomeList = new ArrayList<>();

    public static void netherBiomeCollection() {
        for (Biome biome : biomeRegistry.getValues()) {
            if (biome.getCategory() == Biome.Category.NETHER) {
                if (!netherBiomeList.contains(biome)) {
                    netherBiomeList.add(biome);
                }
            }
        }
    }

    public static int getRandomNetherBiomes(INoiseRandom random) {
        return biomeRegistry.getID(netherBiomeList.get(random.random(netherBiomeList.size())));
    }

    public static List<Biome> getBiomeSet() {
        return netherBiomeList;
    }
}
