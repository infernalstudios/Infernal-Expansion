package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.DeltaShoresSurfaceBuilder;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.GlowstoneCanyonSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModSurfaceBuilders {

    public static List<SurfaceBuilder<?>> surfaceBuilders = new ArrayList<>();
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, InfernalExpansion.MOD_ID);

    // Surface Builders
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GLOWSTONE_CANYON_SURFACE_BUILDER = SURFACE_BUILDERS.register("glowstone_canyon", () -> new GlowstoneCanyonSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DELTA_SHORES_SURFACE_BUILDER = SURFACE_BUILDERS.register("delta_shores", () -> new DeltaShoresSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));

    // Surface Builder Configs
    public static class ModSurfaceBuilderConfig {
         public static final SurfaceBuilderConfig GLOWSTONE_CANYON_CONFIG = new SurfaceBuilderConfig(
                ModBlocks.GLOWDUST_SAND.get().getDefaultState(),
                ModBlocks.GLOWDUST_STONE.get().getDefaultState(),
                Blocks.GLOWSTONE.getDefaultState());

         public static final SurfaceBuilderConfig DELTA_SHORES_CONFIG = new SurfaceBuilderConfig(
                ModBlocks.SILT.get().getDefaultState(),
                ModBlocks.SILT.get().getDefaultState(),
                Blocks.BASALT.getDefaultState()
        );
    }


//    public static void register(IEventBus eventBus) {
//        //SURFACE_BUILDERS.register(eventBus);
//        InfernalExpansion.LOGGER.info("Infernal Expansion: Surface Builders Registered");
//    }

    public static void register(IEventBus eventBus) {
        SURFACE_BUILDERS.register(eventBus);
        InfernalExpansion.LOGGER.info("Infernal Expansion: Surface Builders Registered!");
    }
}

