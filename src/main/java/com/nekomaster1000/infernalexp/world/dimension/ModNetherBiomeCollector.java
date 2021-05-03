package com.nekomaster1000.infernalexp.world.dimension;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ModNetherBiomeCollector {
    public static final ForgeRegistry<Biome> biomeRegistry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);

    public static List<RegistryKey<Biome>> netherBiomeList = new ArrayList<>();

    public static List<RegistryKey<Biome>> netherBiomeCollection(Registry<Biome> biomeRegistry) {

        for (Map.Entry<RegistryKey<Biome>, Biome> entry : biomeRegistry.getEntries()) {
            if (entry.getValue().getCategory() == Biome.Category.NETHER && !entry.getKey().getLocation().getNamespace().equals("ultra_amplified_dimension")) {
                if (!netherBiomeList.contains(entry.getKey())) {
                    netherBiomeList.add(entry.getKey());
                }
            }
        }

        netherBiomeList.sort(Comparator.comparing(key -> key.getLocation().toString()));
        return netherBiomeList;
    }

    public static int getRandomNetherBiomes(INoiseRandom random) {
        return biomeRegistry.getID(netherBiomeList.get(random.random(netherBiomeList.size())).getLocation());
    }
}
