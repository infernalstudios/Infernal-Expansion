package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.DeltaShoresSurfaceBuilder;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.GlowstoneCanyonSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModSurfaceBuilder {

    //public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, InfernalExpansion.MOD_ID);

    // Surface Builders
    public static final SurfaceBuilder<SurfaceBuilderConfig> GLOWSTONE_CANYON_SURFACE_BUILDER = newSurfaceBuilder("glowstone_canyon", new GlowstoneCanyonSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    public static final SurfaceBuilder<SurfaceBuilderConfig> DELTA_SHORES_SURFACE_BUILDER = newSurfaceBuilder("delta_shores", new DeltaShoresSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));

    // Surface Builder Configs
    public static class ModSurfaceBuilderConfig {
         public static final SurfaceBuilderConfig GLOWSTONE_CANYON_CONFIG = new SurfaceBuilderConfig(
                ModBlocks.GLOWDUST_SAND.get().getDefaultState(),
                ModBlocks.GLOWDUST_STONE.get().getDefaultState(),
                Blocks.GLOWSTONE.getDefaultState());

         public static final SurfaceBuilderConfig DELTA_SHORES_CONFIG = new SurfaceBuilderConfig(
                ModBlocks.SILT.get().getDefaultState(),
                ModBlocks.RUBBLE.get().getDefaultState(),
                Blocks.BASALT.getDefaultState()
        );
    }


    public static void register(IEventBus eventBus) {
        //SURFACE_BUILDERS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Surface Builders Registered");
    }

    public static SurfaceBuilder<SurfaceBuilderConfig> newSurfaceBuilder(String id, SurfaceBuilder<SurfaceBuilderConfig> surfaceBuilder) {
        Registry.register(Registry.SURFACE_BUILDER, new ResourceLocation(InfernalExpansion.MOD_ID, id), surfaceBuilder);
        return surfaceBuilder;
    }
}

