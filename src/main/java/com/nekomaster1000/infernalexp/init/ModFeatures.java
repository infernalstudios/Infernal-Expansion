package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.features.*;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModFeatures {

    public static List<Feature<?>> features = new ArrayList<>();
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, InfernalExpansion.MOD_ID);

    public static final RegistryObject<Feature<GlowSpikeFeatureConfig>> GLOWSPIKE = FEATURES.register("glowspike", () -> new GlowSpikeFeature(GlowSpikeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoFeatureConfig>> HANGING_GIANT_BROWN_MUSHROOM = FEATURES.register("hanging_giant_brown_mushroom", () -> new HangingGiantBrownMushroomFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> LUMINOUS_FUNGUS = FEATURES.register("luminous_fungus", () -> new LuminousFungusFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> DULLTHORNS = FEATURES.register("dullthorns", () -> new DullthornsFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> BOULDER = FEATURES.register("blackstone_boulder", () -> new BoulderFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final RegistryObject<Feature<NoFeatureConfig>> DULLSTONE_DEATH_PIT = FEATURES.register("glowdust_sink_hole", () -> new SinkHoleFeature(NoFeatureConfig.field_236558_a_));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Features Registered!");
    }
}
