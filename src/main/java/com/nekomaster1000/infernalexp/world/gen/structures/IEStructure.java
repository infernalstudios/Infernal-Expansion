package com.nekomaster1000.infernalexp.world.gen.structures;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
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

    // Gets the y-value for where the structure will spawn
    public static int getYPos(ChunkGenerator chunkGenerator, int x, int z, SharedSeedRandom random) {
        // Gets a random value between sea level and the max build height and grabs its column
        int y = chunkGenerator.getSeaLevel() + random.nextInt(chunkGenerator.getMaxBuildHeight() - 2 - chunkGenerator.getSeaLevel());
        IBlockReader blockColumn = chunkGenerator.func_230348_a_(x, z);

        BlockPos pos = new BlockPos(x, y, z);

        // Proceeds downwards through the column until it finds a space where there is a solid block with air above it and returns that height
        while (y > chunkGenerator.getSeaLevel()) {
            BlockState checkAir = blockColumn.getBlockState(pos.down(y));
            BlockState checkBlock = blockColumn.getBlockState(pos.down(y + 1));

            if (checkAir.isAir() && (checkBlock.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || checkBlock.isSolidSide(blockColumn, pos.down(y), Direction.UP))) {
                return y;
            }

            y--;
        }

        // Returns 0 if no valid space is found
        return 0;
    }

    public static abstract class IEStart<F extends IFeatureConfig> extends StructureStart<F> {

        public IEStart(Structure<F> structure, int chunkX, int chunkY, MutableBoundingBox mutableBoundingBox, int reference, long seed) {
            super(structure, chunkX, chunkY, mutableBoundingBox, reference, seed);
        }

        // Gets the y-value for where the structure will spawn
        public static int getYPos(ChunkGenerator chunkGenerator, int x, int z, SharedSeedRandom random) {
            // Gets a random value between sea level and the max build height and grabs its column
            int y = chunkGenerator.getSeaLevel() + random.nextInt(chunkGenerator.getMaxBuildHeight() - 2 - chunkGenerator.getSeaLevel());
            IBlockReader blockColumn = chunkGenerator.func_230348_a_(x, z);

            BlockPos pos = new BlockPos(x, y, z);

            // Proceeds downwards through the column until it finds a space where there is a solid block with air above it and returns that height
            while (y > chunkGenerator.getSeaLevel()) {
                BlockState checkAir = blockColumn.getBlockState(pos.down(y));
                BlockState checkBlock = blockColumn.getBlockState(pos.down(y + 1));

                if (checkAir.isAir() && (checkBlock.matchesBlock(IEBlocks.GLOWDUST_SAND.get()) || checkBlock.isSolidSide(blockColumn, pos.down(y), Direction.UP))) {
                    return y;
                }

                y--;
            }

            // Returns 0 if no valid space is found
            return 0;
        }
    }
}
