package com.nekomaster1000.infernalexp.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class PlantedQuartzFeatureConfig implements IFeatureConfig {
    public static final Codec<PlantedQuartzFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
            Codec.FLOAT.fieldOf("chance_to_fail").forGetter((config) -> config.chanceToFail))
            .apply(builder, PlantedQuartzFeatureConfig::new);
    });

    public final float chanceToFail;

    public PlantedQuartzFeatureConfig(float chanceToFail) {
        this.chanceToFail = chanceToFail;
    }
}
