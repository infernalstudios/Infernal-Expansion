package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.init.IEBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public abstract class IEStructure<F extends IFeatureConfig> extends Structure<F> {

	public IEStructure(Codec<F> codec) {
		super(codec);
	}

	public abstract StructureSeparationSettings getSeparationSettings();

	public abstract boolean shouldTransformLand();

	public static abstract class IEStart<F extends IFeatureConfig> extends StructureStart<F> {

		public IEStart(Structure<F> structure, int chunkX, int chunkY, MutableBoundingBox mutableBoundingBox, int reference, long seed) {
			super(structure, chunkX, chunkY, mutableBoundingBox, reference, seed);
		}

		public int getYPos(ChunkGenerator chunkGenerator, int x, int z) {
			int y = chunkGenerator.getSeaLevel() + this.rand.nextInt(chunkGenerator.getMaxBuildHeight() - 2 - chunkGenerator.getSeaLevel());
			IBlockReader blockColumn = chunkGenerator.func_230348_a_(x, z);

//			for(BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(x, y, z); y > chunkGenerator.getSeaLevel(); --y) {
//				BlockState blockstate = blockColumn.getBlockState(blockpos$mutable);
//				blockpos$mutable.move(Direction.DOWN);
//				BlockState blockstate1 = blockColumn.getBlockState(blockpos$mutable);
//				if (blockstate.isAir() && (blockstate1.isIn(IEBlocks.GLOWDUST_SAND.get()) || blockstate1.isSolidSide(blockColumn, blockpos$mutable, Direction.UP))) {
//					return y;
//				}
//			}

			BlockPos pos = new BlockPos(x, y, z);

			while (y > chunkGenerator.getSeaLevel()) {
				BlockState checkAir = blockColumn.getBlockState(pos.down(y));
				BlockState checkBlock = blockColumn.getBlockState(pos.down(y + 1));

				if (checkAir.isAir() && (checkBlock.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || checkBlock.isSolidSide(blockColumn, pos.down(y), Direction.UP))) {
					return y;
				}

				y--;
			}

			return 0;
		}

	}
}
