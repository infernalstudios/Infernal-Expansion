package com.nekomaster1000.infernalexp.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ModNetherBiomeProvider extends BiomeProvider {
    public static final Codec<ModNetherBiomeProvider> MOD_NETHER_CODEC = RecordCodecBuilder.create((instance) -> instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((theEndBiomeSource) -> theEndBiomeSource.biomeRegistry), Codec.LONG.fieldOf("seed").stable().forGetter((theEndBiomeSource) -> theEndBiomeSource.seed)).apply(instance, instance.stable(ModNetherBiomeProvider::new)));

    private final Layer biomeLayer;
    private final long seed;
    private final Registry<Biome> biomeRegistry;

    public ModNetherBiomeProvider(Registry<Biome> registry, long seed) {
        super(biomeList);
        this.seed = seed;
        this.biomeLayer = ModNetherLayerProvider.stackLayers(seed);
        biomeRegistry = registry;
    }

    public static List<Biome> biomeList = new ArrayList<>();

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
