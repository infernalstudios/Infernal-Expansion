/*
 * Copyright 2021 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.init;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.world.gen.surfacebuilders.DeltaShoresSurfaceBuilder;
import org.infernalstudios.infernalexp.world.gen.surfacebuilders.GlowstoneCanyonSurfaceBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import java.util.ArrayList;
import java.util.List;

public class IESurfaceBuilders {

    public static List<SurfaceBuilder<?>> surfaceBuilders = new ArrayList<>();

    // Surface Builders
    public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> GLOWSTONE_CANYON_SURFACE_BUILDER = newSurfaceBuilder("glowstone_canyon", new GlowstoneCanyonSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC));
    public static final SurfaceBuilder<SurfaceBuilderBaseConfiguration> DELTA_SHORES_SURFACE_BUILDER = newSurfaceBuilder("delta_shores", new DeltaShoresSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC));

    // Surface Builder Configs
    public static class ModSurfaceBuilderConfig {
        public static final SurfaceBuilderBaseConfiguration GLOWSTONE_CANYON_CONFIG = new SurfaceBuilderBaseConfiguration(
            IEBlocks.GLOWDUST_SAND.get().defaultBlockState(),
            IEBlocks.GLOWDUST_STONE.get().defaultBlockState(),
            Blocks.GLOWSTONE.defaultBlockState());

        public static final SurfaceBuilderBaseConfiguration DELTA_SHORES_CONFIG = new SurfaceBuilderBaseConfiguration(
            IEBlocks.SILT.get().defaultBlockState(),
            IEBlocks.SILT.get().defaultBlockState(),
            Blocks.BASALT.defaultBlockState()
        );
    }


//    public static void register(IEventBus eventBus) {
//        //SURFACE_BUILDERS.register(eventBus);
//        InfernalExpansion.LOGGER.info("Infernal Expansion: Surface Builders Registered");
//    }

    @SuppressWarnings("deprecation")
    public static SurfaceBuilder<SurfaceBuilderBaseConfiguration> newSurfaceBuilder(String id, SurfaceBuilder<SurfaceBuilderBaseConfiguration> surfaceBuilder) {
        ResourceLocation registryName = new ResourceLocation(InfernalExpansion.MOD_ID, id);

        if (Registry.SURFACE_BUILDER.keySet().contains(registryName))
            throw new IllegalStateException("Surface Builder ID: \"" + registryName.toString() + "\" is already in the registry!");

        surfaceBuilder.setRegistryName(registryName);
        surfaceBuilders.add(surfaceBuilder);

        return surfaceBuilder;
    }
}

