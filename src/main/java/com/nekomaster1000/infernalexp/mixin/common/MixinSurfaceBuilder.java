package com.nekomaster1000.infernalexp.mixin.common;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import com.nekomaster1000.infernalexp.access.NoiseAccess;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

@Mixin(SurfaceBuilder.class)
public class MixinSurfaceBuilder implements NoiseAccess {

	@Override
	public BlockState modifyNoise(NoiseChunkGenerator chunkGenerator, BlockPos pos, Random random, BlockState chosen, IWorld world, StructureManager structureManager, IChunk chunk) {
		return chosen;
	}

}
