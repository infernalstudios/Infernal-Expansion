package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.Placement;

public class ModConfiguredFeatures {

    public static ConfiguredFeature<?, ?> GLOWSPIKE = registerConfiguredFeature("glowspike", ModFeatures.GLOWSPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT.func_242731_b(120)));
    public static ConfiguredFeature<?, ?> LUMINOUS_FUNGUS = registerConfiguredFeature("luminous_fungus", ModFeatures.LUMINOUS_FUNGUS.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(5))));

//    public static final ConfiguredFeature<?, ?> RANDOM_GLOWSTONE_CANYON_PLANT = registerConfiguredFeature("rand_glowstone_canyon", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
//            LUMINOUS_FUNGUS.withChance(0.5F)),
//            LUMINOUS_FUNGUS)).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(15))));

    public static ConfiguredFeature<?, ?> registerConfiguredFeature(String registryName, ConfiguredFeature<?, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(InfernalExpansion.MOD_ID, registryName), configuredFeature);
    }

}
