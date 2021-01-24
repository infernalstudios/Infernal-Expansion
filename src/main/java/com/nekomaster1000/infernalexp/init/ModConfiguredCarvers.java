package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class ModConfiguredCarvers {

    public static final ConfiguredCarver<ProbabilityConfig> CONFIGURED_GLOWSTONE_RAVINE = registerConfiguredCarver("glowstone_ravine", ModCarvers.GLOWSTONE_RAVINE.get().func_242761_a(new ProbabilityConfig(0.1f)));

    private static <WC extends ICarverConfig> ConfiguredCarver<WC> registerConfiguredCarver(String registryName, ConfiguredCarver<WC> configuredCarver) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

        if (WorldGenRegistries.CONFIGURED_FEATURE.containsKey(resourceLocation))
            throw new IllegalStateException("Configured Carver ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, resourceLocation, configuredCarver);
    }

}
