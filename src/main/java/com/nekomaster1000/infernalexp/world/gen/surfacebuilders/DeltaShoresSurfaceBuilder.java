package com.nekomaster1000.infernalexp.world.gen.surfacebuilders;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.access.SurfaceBuilderModifyNoise;
import com.nekomaster1000.infernalexp.init.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DeltaShoresSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> implements SurfaceBuilderModifyNoise {
	public DeltaShoresSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232136_1_) {
		super(p_i232136_1_);
	}

	@Override
	public void buildSurface(Random random, IChunk chunk, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
		BlockPos.Mutable pos = new BlockPos.Mutable();

		int xPos = x & 15;
		int zPos = z & 15;

		for (int yPos = 125; yPos >= seaLevel; yPos--) {
			pos.setPos(xPos, yPos, zPos);

			BlockState currentBlockToReplace = chunk.getBlockState(pos);
			BlockState checkForAir = chunk.getBlockState(pos.up());

			if (currentBlockToReplace == Blocks.BASALT.getDefaultState() && checkForAir == Blocks.AIR.getDefaultState()) {
				chunk.setBlockState(pos, config.getTop(), false);

				for (int offset = 1; offset <= 3; offset++) {
					if (chunk.getBlockState(pos.down(offset)) == Blocks.BASALT.getDefaultState()) {
						chunk.setBlockState(pos.down(offset), config.getUnder(), false);
					}
				}

				// Build terrain down to bedrock
				if (yPos <= 63) {
					if (chunk.getBlockState(pos.down(1)) == config.getUnder() && chunk.getBlockState(pos.down(2)) == config.getUnder()) {
						for (int offset = 3; offset <= yPos; offset++) {
							if (chunk.getBlockState(pos.down(offset)).getBlock() != Blocks.AIR && chunk.getBlockState(pos.down(offset)).getBlock() != Blocks.LAVA) {
								float percentage = (((float) offset / yPos) - 0.05f) + (random.nextFloat() * 0.1f);

								if (percentage <= 0.15) {
									chunk.setBlockState(pos.down(offset), ModBlocks.SILT.get().getDefaultState(), false);
								} else {
									chunk.setBlockState(pos.down(offset), Blocks.BASALT.getDefaultState(), false);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public BlockState modifyNoise(NoiseChunkGenerator chunkGenerator, BlockPos pos, Random random, BlockState chosen, IWorld world, StructureManager structureManager, IChunk chunk) {
		if (chosen.getBlock() == Blocks.NETHERRACK) {
			return Blocks.BASALT.getDefaultState();
		}
		return chosen;
	}
}
