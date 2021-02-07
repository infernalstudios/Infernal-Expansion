package com.nekomaster1000.infernalexp.init;

import java.util.ArrayList;
import java.util.List;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.BoulderFeature;
import com.nekomaster1000.infernalexp.world.gen.features.DullthornsFeature;
import com.nekomaster1000.infernalexp.world.gen.features.GlowSpikeFeature;
import com.nekomaster1000.infernalexp.world.gen.features.GlowstoneRavineFeature;
import com.nekomaster1000.infernalexp.world.gen.features.HangingGiantBrownMushroomFeature;
import com.nekomaster1000.infernalexp.world.gen.features.LuminousFungusFeature;
import com.nekomaster1000.infernalexp.world.gen.features.SinkHoleFeature;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class IEFeatures {

    public static List<Feature<?>> features = new ArrayList<>();

    public static final Feature<GlowSpikeFeatureConfig> GLOWSPIKE = registerFeature("glowspike", new GlowSpikeFeature(GlowSpikeFeatureConfig.CODEC));
    public static final Feature<NoFeatureConfig> GLOWSTONE_RAVINE = registerFeature("glowstone_ravine", new GlowstoneRavineFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> HANGING_GIANT_BROWN_MUSHROOM = registerFeature("hanging_giant_brown_mushroom", new HangingGiantBrownMushroomFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> LUMINOUS_FUNGUS = registerFeature("luminous_fungus", new LuminousFungusFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> DULLTHORNS = registerFeature("dullthorns", new DullthornsFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<BlockStateFeatureConfig> BOULDER = registerFeature("blackstone_boulder", new BoulderFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final Feature<NoFeatureConfig> DULLSTONE_DEATH_PIT = registerFeature("glowdust_sink_hole", new SinkHoleFeature(NoFeatureConfig.field_236558_a_));

    public static <C extends IFeatureConfig, F extends Feature<C>> F registerFeature(String registryName, F feature) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (Registry.FEATURE.containsKey(resourceLocation))
            throw new IllegalStateException("Feature ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        feature.setRegistryName(resourceLocation);
        features.add(feature);

        return feature;
    }
}
