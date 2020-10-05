package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.biome.GlowstoneCanyonBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes
{
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Biome> GLOWSTONE_CANYON = BIOMES.register("glowstone_canyon", () -> new GlowstoneCanyonBiome());

    public static void register(IEventBus modEventBus)
    {
        BIOMES.register(modEventBus);
    }

    public static void registerBiomes() {

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(GLOWSTONE_CANYON.get(), 30));
        
        BiomeDictionary.addTypes(GLOWSTONE_CANYON.get(), BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.NETHER);

    }

    private static void registerBiome(Biome biome, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }

    public static void setupBiomes() {
    }
}


