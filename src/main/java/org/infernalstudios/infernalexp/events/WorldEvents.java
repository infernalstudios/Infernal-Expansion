/*
 * Copyright 2022 Infernal Studios
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

package org.infernalstudios.infernalexp.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.init.IECarvers;
import org.infernalstudios.infernalexp.init.IEFeatures;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {

    @SubscribeEvent
    public static void registerWorldGen(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.FEATURES, helper -> IEFeatures.features.forEach(helper::register));
        event.register(ForgeRegistries.Keys.WORLD_CARVERS, helper -> IECarvers.carvers.forEach(helper::register));
    }

    // TODO: Use the new biome modifiers
    //    @SubscribeEvent(priority = EventPriority.HIGH)
    //    public void onBiomeLoad(BiomeLoadingEvent event) {
    //        if (event.getName() == null) {
    //            return;
    //        }
    //
    //        ResourceLocation name = event.getName();
    //        ResourceKey<Biome> biome = ResourceKey.create(Registry.BIOME_REGISTRY, name);
    //
    //        if (biome == Biomes.CRIMSON_FOREST) {
    //            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_GLOWSILK_COCOON);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.PATCH_CRIMSON_CAP);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.SHROOMLIGHT_TEAR);
    //        } else if (biome == Biomes.BASALT_DELTAS) {
    //            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_GLOWSILK_COCOON);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_BASALT_IRON_BASALT_DELTA);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.BASALTIC_MAGMA);
    //        } else if (biome == Biomes.WARPED_FOREST) {
    //            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.PATCH_WARPED_CAP);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.SHROOMLIGHT_TEAR);
    //        } else if (biome == Biomes.SOUL_SAND_VALLEY) {
    //            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_BASALT_IRON_BASALT_DELTA);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.PATCH_BURIED_BONE);
    //            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, IEPlacedFeatures.ORE_SOUL_STONE);
    //        } else if (biome == Biomes.NETHER_WASTES) {
    //            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, IEPlacedFeatures.PATCH_PLANTED_QUARTZ);
    //        }
    //    }
}
