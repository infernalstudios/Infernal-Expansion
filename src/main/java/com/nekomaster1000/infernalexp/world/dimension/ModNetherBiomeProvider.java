package com.nekomaster1000.infernalexp.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModNetherBiomeProvider extends BiomeProvider {
    public static final Codec<ModNetherBiomeProvider> MOD_NETHER_CODEC = RecordCodecBuilder.create((instance) -> instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((theEndBiomeSource) -> theEndBiomeSource.biomeRegistry), Codec.LONG.fieldOf("seed").stable().forGetter((theEndBiomeSource) -> theEndBiomeSource.seed)).apply(instance, instance.stable(ModNetherBiomeProvider::new)));

    private final Layer biomeLayer;
    private final long seed;
    private final Registry<Biome> biomeRegistry;
    public static List<ResourceLocation> NETHER_BIOMES = new ArrayList<>();

    public ModNetherBiomeProvider(Registry<Biome> registry, long seed) {
        super(biomeList);
        this.seed = seed;
        this.biomeRegistry = registry;
        this.biomeLayer = ModNetherLayerProvider.stackLayers(this.biomeRegistry, seed);
        NETHER_BIOMES = createNetherBiomeList(registry);
    }

    public static List<Biome> biomeList = new ArrayList<>();

    public static List<ResourceLocation> createNetherBiomeList(Registry<Biome> biomeRegistry) {
        List<ResourceLocation> nether_biomes = new ArrayList<>();

        for (Map.Entry<RegistryKey<Biome>, Biome> biomeEntry : biomeRegistry.getEntries()) {
            if (biomeEntry.getValue().getCategory() == Biome.Category.NETHER) {
                ResourceLocation locationKey = biomeEntry.getKey().getLocation();

                if (!nether_biomes.contains(locationKey)) {
                    nether_biomes.add(locationKey);
                }
            }
        }

        nether_biomes.removeIf(Objects::isNull);
        return nether_biomes;
    }

    @Nonnull
    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return MOD_NETHER_CODEC;
    }

    @Nonnull
    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new ModNetherBiomeProvider(biomeRegistry, seed);
    }

    @Nonnull
    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return biomeLayer.func_242936_a(biomeRegistry, x, z);
    }
}
