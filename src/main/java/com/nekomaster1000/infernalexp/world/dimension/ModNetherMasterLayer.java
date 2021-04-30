package com.nekomaster1000.infernalexp.world.dimension;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class ModNetherMasterLayer implements IAreaTransformer0 {

    private final Registry<Biome> dynamicRegistry;

    public ModNetherMasterLayer(long seed, Registry<Biome> dynamicRegistry){
        this.dynamicRegistry = dynamicRegistry;
    }

    @Override
    public int apply(INoiseRandom context, int x, int y) {
        return ModNetherBiomeCollector.getRandomNetherBiomes(context);
    }
}
