package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.DeltaShoresSurfaceBuilder;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.GlowstoneCanyonSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.ArrayList;
import java.util.List;

public class IESurfaceBuilders {

	public static List<SurfaceBuilder<?>> surfaceBuilders = new ArrayList<>();

	// Surface Builders
	public static final SurfaceBuilder<SurfaceBuilderConfig> GLOWSTONE_CANYON_SURFACE_BUILDER = newSurfaceBuilder("glowstone_canyon", new GlowstoneCanyonSurfaceBuilder(SurfaceBuilderConfig.CODEC));
	public static final SurfaceBuilder<SurfaceBuilderConfig> DELTA_SHORES_SURFACE_BUILDER = newSurfaceBuilder("delta_shores", new DeltaShoresSurfaceBuilder(SurfaceBuilderConfig.CODEC));

	// Surface Builder Configs
	public static class ModSurfaceBuilderConfig {
		public static final SurfaceBuilderConfig GLOWSTONE_CANYON_CONFIG = new SurfaceBuilderConfig(
            IEBlocks.GLOWDUST_SAND.get().getDefaultState(),
            IEBlocks.GLOWDUST_STONE.get().getDefaultState(),
            Blocks.GLOWSTONE.getDefaultState());

		public static final SurfaceBuilderConfig DELTA_SHORES_CONFIG = new SurfaceBuilderConfig(
            IEBlocks.SILT.get().getDefaultState(),
            IEBlocks.SILT.get().getDefaultState(),
            Blocks.BASALT.getDefaultState()
        );
    }


//    public static void register(IEventBus eventBus) {
//        //SURFACE_BUILDERS.register(eventBus);
//        InfernalExpansion.LOGGER.info("Infernal Expansion: Surface Builders Registered");
//    }

    public static SurfaceBuilder<SurfaceBuilderConfig> newSurfaceBuilder(String id, SurfaceBuilder<SurfaceBuilderConfig> surfaceBuilder) {
        ResourceLocation registryName = new ResourceLocation(InfernalExpansion.MOD_ID, id);

        if (Registry.SURFACE_BUILDER.keySet().contains(registryName))
			throw new IllegalStateException("Surface Builder ID: \"" + registryName.toString() + "\" is already in the registry!");

        surfaceBuilder.setRegistryName(registryName);
        surfaceBuilders.add(surfaceBuilder);

        return surfaceBuilder;
    }
}

