package com.nekomaster1000.infernalexp.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

import javax.annotation.Nonnull;

public class ModNetherBiomeProvider extends BiomeProvider {
    public static final Codec<ModNetherBiomeProvider> MOD_NETHER_CODEC = RecordCodecBuilder.create((instance) -> instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((netherProvider) -> netherProvider.biomeRegistry), Codec.LONG.fieldOf("seed").stable().forGetter((netherProvider) -> netherProvider.seed)).apply(instance, instance.stable(ModNetherBiomeProvider::new)));

    private final Layer biomeLayer;
    private final Registry<Biome> biomeRegistry;
    private final long seed;

    public ModNetherBiomeProvider(Registry<Biome> registry, long seed) {
        super(ModNetherBiomeCollector.getBiomeSet());
        this.seed = seed;
        this.biomeLayer = ModNetherLayerProvider.stackLayers(seed);
        this.biomeRegistry = registry;
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
