package com.nekomaster1000.infernalexp.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class GlowSpikeFeatureConfig implements IFeatureConfig {
    public static final Codec<GlowSpikeFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                Codec.INT.fieldOf("min_diameter").forGetter((config) -> config.minDiameter),
                Codec.INT.fieldOf("max_diameter").forGetter((config) -> config.maxDiameter),
                Codec.INT.fieldOf("min_height").forGetter((config) -> config.minHeight),
                Codec.INT.fieldOf("max_height").forGetter((config) -> config.maxHeight),
                Codec.INT.fieldOf("max_x_offset").forGetter((config) -> config.maxXOffset),
                Codec.INT.fieldOf("max_z_offset").forGetter((config) -> config.maxZOffset))
                .apply(builder, GlowSpikeFeatureConfig::new);
    });

    public final int minDiameter;
    public final int maxDiameter;
    public final int minHeight;
    public final int maxHeight;
    public final int maxXOffset;
    public final int maxZOffset;

    public GlowSpikeFeatureConfig(int minDiameter, int maxDiameter, int minHeight, int maxHeight, int maxXOffset, int maxZOffset) {
        this.minDiameter = minDiameter;
        this.maxDiameter = maxDiameter;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.maxXOffset = maxXOffset;
        this.maxZOffset = maxZOffset;
    }
}
