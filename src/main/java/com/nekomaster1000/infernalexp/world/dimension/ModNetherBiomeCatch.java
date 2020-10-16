package com.nekomaster1000.infernalexp.world.dimension;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public class ModNetherBiomeCatch {
    public static final ForgeRegistry<Biome> BIOME_REGISTRY = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES);

    public static ArrayList<String> netherBiomeList = new ArrayList<>();
    public static ArrayList<Integer> netherBiomeIDS = new ArrayList<>();
    private static final List<String> vanillaNetherBiomes = Arrays.asList("minecraft:basalt_deltas", "minecraft:crimson_forest", "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:warped_forest");

    public static void netherBiomeCollection() {
        netherBiomeList.addAll(vanillaNetherBiomes);

        if (netherBiomeList.size() > 0) {
            for (String netherBiome : netherBiomeList) {
                final Biome biome = WorldGenRegistries.BIOME.getOptional(new ResourceLocation(netherBiome)).orElse(BIOME_REGISTRY.getValue(Biomes.NETHER_WASTES.getLocation()));

                if (biome == null) {
                    InfernalExpansion.LOGGER.error("Illegal registry name");
                } else {
                    ModNetherBiomeProvider.biomeList.add(biome);
                    netherBiomeIDS.add(WorldGenRegistries.BIOME.getId(biome));
                }
            }
        }
    }

    public static int getRandomNetherBiomes(INoiseRandom rand) {
        return netherBiomeIDS.get(rand.random(netherBiomeIDS.size()));
    }
}
