package com.nekomaster1000.infernalexp.world.gen.features;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class ModFeatures {

    public static final Feature<NoFeatureConfig> GLOWSPIKE = registerFeature("glowspike", new GlowSpikeFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> LUMINOUS_FUNGUS = registerFeature("luminous_fungus", new LuminousFungusFeature(NoFeatureConfig.field_236558_a_));

    public static <C extends IFeatureConfig, F extends Feature<C>> F registerFeature(String registryName, F feature) {
        return Registry.register(Registry.FEATURE, new ResourceLocation(InfernalExpansion.MOD_ID, registryName), feature);
    }
}
