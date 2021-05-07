package com.nekomaster1000.infernalexp.events;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.init.IECarvers;
import com.nekomaster1000.infernalexp.init.IEConfiguredFeatures;
import com.nekomaster1000.infernalexp.init.IEConfiguredStructures;
import com.nekomaster1000.infernalexp.init.IEFeatures;
import com.nekomaster1000.infernalexp.init.IEStructures;
import com.nekomaster1000.infernalexp.init.IESurfaceBuilders;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {

	// Register features, surface builders, carvers and structures
	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
		IEFeatures.features.forEach(feature -> event.getRegistry().register(feature));
	}

	@SubscribeEvent
	public static void registerStructures(RegistryEvent.Register<Structure<?>> event) {
		IEStructures.structures.forEach(structure -> event.getRegistry().register(structure));
	}

	@SubscribeEvent
	public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
		IESurfaceBuilders.surfaceBuilders.forEach(surfaceBuilder -> event.getRegistry().register(surfaceBuilder));
	}

	@SubscribeEvent
	public static void registerWorldCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
		IECarvers.carvers.forEach(carver -> event.getRegistry().register(carver));
	}

	@SubscribeEvent
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld world = (ServerWorld) event.getWorld();

			try {
				Method GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");

				ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(world.getChunkProvider().generator));

				if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
			} catch (Exception e) {
				InfernalExpansion.LOGGER.error("Was unable to check if " + world.getDimensionKey().getLocation() + " is using Terraforged's ChunkGenerator");
			}

			if (world.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && world.getDimensionKey().equals(World.OVERWORLD)) return;

			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(world.getChunkProvider().generator.func_235957_b_().func_236195_a_());

			IEStructures.structures.forEach(structure -> tempMap.putIfAbsent(structure, DimensionStructuresSettings.field_236191_b_.get(structure)));
			world.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBiomeLoad(BiomeLoadingEvent event) {
	    if (event.getName() == null) {
	        return;
        }

	    ResourceLocation name = event.getName();
	    RegistryKey<Biome> biome = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, name);

		if (biome == Biomes.CRIMSON_FOREST) {
			event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_GLOWSILK_COCOON);
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.PATCH_CRIMSON_CAP);
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.SHROOMLIGHT_TEAR);
        } else if (biome == Biomes.BASALT_DELTAS) {
			event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_GLOWSILK_COCOON);
			event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_BASALT_IRON_BASALT_DELTA);
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.BASALTIC_MAGMA);
		    event.getGeneration().withStructure(IEConfiguredStructures.STRIDER_ALTAR);
		} else if (biome == Biomes.WARPED_FOREST) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.PATCH_WARPED_CAP);
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.SHROOMLIGHT_TEAR);
		} else if (biome == Biomes.SOUL_SAND_VALLEY) {
			event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_BASALT_IRON_BASALT_DELTA);
            event.getGeneration().withStructure(IEConfiguredStructures.SOUL_SAND_VALLEY_RUIN);
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, IEConfiguredFeatures.PATCH_BURIED_BONE);
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IEConfiguredFeatures.ORE_SOUL_STONE);
        }
	}
}
