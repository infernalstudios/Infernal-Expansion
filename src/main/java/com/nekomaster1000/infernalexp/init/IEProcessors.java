package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.world.gen.processors.LootChestProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class IEProcessors {

	public static IStructureProcessorType<LootChestProcessor> LOOT_CHEST_PROCESSOR = () -> LootChestProcessor.CODEC;

	public static void registerProcessors() {
		Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(InfernalExpansion.MOD_ID, "loot_chest_processor"), LOOT_CHEST_PROCESSOR);
	}

}
