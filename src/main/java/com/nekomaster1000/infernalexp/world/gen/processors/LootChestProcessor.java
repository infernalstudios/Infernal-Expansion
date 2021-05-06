package com.nekomaster1000.infernalexp.world.gen.processors;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.init.IEProcessors;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

public class LootChestProcessor extends StructureProcessor {

	/*
	To use this processor, add a structure block in place of the chest, make sure the structure block is in data mode
	Then for "Custom Data Tag Name" put in without the brackets chest-(direction facing. east, west, south, north)-(resource location of loot table)
	Make sure to add the dashes in between, as that is what separates the data
	 */

	public static final LootChestProcessor INSTANCE = new LootChestProcessor();
	public static final Codec<LootChestProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@ParametersAreNonnullByDefault
	@Nullable
	@Override
	public Template.BlockInfo process(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfoRelative, Template.BlockInfo structureBlockInfoGlobal, PlacementSettings placementSettings, @Nullable Template template) {
		BlockState blockState = structureBlockInfoGlobal.state;

		if (blockState.matchesBlock(Blocks.STRUCTURE_BLOCK)) {
			String[] nbtData = structureBlockInfoGlobal.nbt.getString("metadata").split("-");

			if (nbtData[0].equals("chest")) {
				ChestTileEntity tileEntity = new ChestTileEntity();
				tileEntity.setLootTable(new ResourceLocation(nbtData[2]), new Random().nextLong());

				return new Template.BlockInfo(structureBlockInfoGlobal.pos, Blocks.CHEST.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, Objects.requireNonNull(Direction.byName(nbtData[1]))), tileEntity.serializeNBT());
			}
		}

		return structureBlockInfoGlobal;
	}

	@Override
	protected IStructureProcessorType<?> getType() {
		return IEProcessors.LOOT_CHEST_PROCESSOR;
	}

}
