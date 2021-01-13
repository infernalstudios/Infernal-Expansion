package com.nekomaster1000.infernalexp.world.dimension;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import javax.annotation.ParametersAreNonnullByDefault;

public enum ModNetherMasterLayer implements IAreaTransformer0 {
    INSTANCE;

    @ParametersAreNonnullByDefault
    @Override
    public int apply(INoiseRandom context, int x, int y) {
        return ModNetherBiomeCollector.getRandomNetherBiomes(context);
    }
}
