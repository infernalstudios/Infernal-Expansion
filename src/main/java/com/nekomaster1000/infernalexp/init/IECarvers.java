package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.carvers.GlowstoneRavineCarver;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.ArrayList;
import java.util.List;

public class IECarvers {

	public static List<WorldCarver<ProbabilityConfig>> carvers = new ArrayList<>();

	// Carvers
	public static final WorldCarver<ProbabilityConfig> GLOWSTONE_RAVINE = registerWorldCarver("glowstone_ravine", new GlowstoneRavineCarver(ProbabilityConfig.CODEC));

	// Configured Carvers
	public static final ConfiguredCarver<ProbabilityConfig> CONFIGURED_GLOWSTONE_RAVINE = registerConfiguredCarver("glowstone_ravine", GLOWSTONE_RAVINE.func_242761_a(new ProbabilityConfig(0.1f)));

    private static WorldCarver<ProbabilityConfig> registerWorldCarver(String registryName, WorldCarver<ProbabilityConfig> carver) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

		if (Registry.CARVER.keySet().contains(resourceLocation))
			throw new IllegalStateException("World Carver ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        carver.setRegistryName(resourceLocation);
        carvers.add(carver);

        return carver;
    }

    private static <WC extends ICarverConfig> ConfiguredCarver<WC> registerConfiguredCarver(String registryName, ConfiguredCarver<WC> configuredCarver) {
        ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

		if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(resourceLocation))
			throw new IllegalStateException("Configured Carver ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, resourceLocation, configuredCarver);
    }

}
