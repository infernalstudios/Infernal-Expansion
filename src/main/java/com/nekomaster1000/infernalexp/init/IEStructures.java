package com.nekomaster1000.infernalexp.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.structures.BastionOutpostStructure;
import com.nekomaster1000.infernalexp.world.gen.structures.GlowstoneCanyonRuinStructure;
import com.nekomaster1000.infernalexp.world.gen.structures.IEStructure;
import com.nekomaster1000.infernalexp.world.gen.structures.SoulSandValleyRuinStructure;
import com.nekomaster1000.infernalexp.world.gen.structures.StriderAltarStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IEStructures {

	public static List<IEStructure<?>> structures = new ArrayList<>();


	public static final IEStructure<NoFeatureConfig> GLOWSTONE_CANYON_RUIN = registerStructure("glowstone_canyon_ruin", new GlowstoneCanyonRuinStructure(NoFeatureConfig.CODEC));
	public static final IEStructure<NoFeatureConfig> BASTION_OUTPOST = registerStructure("bastion_outpost", new BastionOutpostStructure(NoFeatureConfig.CODEC));
	public static final IEStructure<NoFeatureConfig> SOUL_SAND_VALLEY_RUIN = registerStructure("soul_sand_valley_ruin", new SoulSandValleyRuinStructure(NoFeatureConfig.CODEC));
    public static final IEStructure<NoFeatureConfig> STRIDER_ALTAR = registerStructure("strider_altar", new StriderAltarStructure(NoFeatureConfig.CODEC));

	public static <C extends IFeatureConfig, F extends IEStructure<C>> F registerStructure(String registryName, F structure) {
		ResourceLocation resourceLocation = new ResourceLocation(InfernalExpansion.MOD_ID, registryName);

		if (Registry.STRUCTURE_FEATURE.keySet().contains(resourceLocation))
			throw new IllegalStateException("Structure ID: \"" + resourceLocation.toString() + "\" is already in the registry!");

		structure.setRegistryName(resourceLocation);
		structures.add(structure);

		return structure;
	}

	public static void setupStructures() {
		structures.forEach(structure -> setupMapSpacingAndLand(structure, structure.getSeparationSettings(), structure.shouldTransformLand()));
	}

	public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) {
			Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(structure).build();
		}

		DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings).build();

		WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
			Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

			if (structureMap instanceof ImmutableMap) {
				Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);

				tempMap.put(structure, structureSeparationSettings);
				settings.getValue().getStructures().field_236193_d_ = tempMap;

			} else {
				structureMap.put(structure, structureSeparationSettings);
			}
		});
	}

}
