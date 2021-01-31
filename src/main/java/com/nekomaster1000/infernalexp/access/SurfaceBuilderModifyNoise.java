package com.nekomaster1000.infernalexp.access;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

public interface SurfaceBuilderModifyNoise {
	default BlockState modifyNoise(NoiseChunkGenerator chunkGenerator, BlockPos pos, Random random, BlockState chosen, IWorld world, StructureManager structureManager, IChunk chunk) {
		return chosen;
	}
}
