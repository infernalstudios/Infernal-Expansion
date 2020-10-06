package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.surfacebuilders.GlowstoneCanyonSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSurfaceBuilder {

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, InfernalExpansion.MOD_ID);

    public static final SurfaceBuilderConfig GLOWSTONE_CANYON_CONFIG = new SurfaceBuilderConfig(
            ModBlocks.GLOWDUST_SAND.get().getDefaultState(),
            ModBlocks.GLOWDUST_SANDSTONE.get().getDefaultState(),
            Blocks.GLOWSTONE.getDefaultState()
    );
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GLOWSTONE_CANYON = SURFACE_BUILDERS.register("glowstone_canyon", () -> new GlowstoneCanyonSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
}

