package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.*;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class ModFeatures {

    public static final Feature<GlowSpikeFeatureConfig> GLOWSPIKE = registerFeature("glowspike", new GlowSpikeFeature(GlowSpikeFeatureConfig.CODEC));
    public static final Feature<NoFeatureConfig> GLOWSTONE_RAVINE = registerFeature("glowstone_ravine", new GlowstoneRavineFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> HANGING_GIANT_BROWN_MUSHROOM = registerFeature("hanging_giant_brown_mushroom", new HangingGiantBrownMushroomFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> LUMINOUS_FUNGUS = registerFeature("luminous_fungus", new LuminousFungusFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> DULLTHORNS = registerFeature("dullthorns", new DullthornsFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<BlockStateFeatureConfig> BOULDER = registerFeature("blackstone_boulder", new BoulderFeature(BlockStateFeatureConfig.field_236455_a_));

    public static <C extends IFeatureConfig, F extends Feature<C>> F registerFeature(String registryName, F feature) {
        return Registry.register(Registry.FEATURE, new ResourceLocation(InfernalExpansion.MOD_ID, registryName), feature);
    }
}