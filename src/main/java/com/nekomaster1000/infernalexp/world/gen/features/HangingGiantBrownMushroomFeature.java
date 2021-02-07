package com.nekomaster1000.infernalexp.world.gen.features;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.IEBlocks;
import com.nekomaster1000.infernalexp.util.ShapeUtil;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class HangingGiantBrownMushroomFeature extends Feature<NoFeatureConfig> {
    public HangingGiantBrownMushroomFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int minSize = 3;
    private static final int maxSize = 7;

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!world.isAirBlock(pos) || world.getBlockState(pos.up()) != IEBlocks.DULLSTONE.get().getDefaultState()) {
            return false;
        } else {
            // Generate size between minSize and maxSize
            int size = minSize + random.nextInt(maxSize - minSize);

            // Generate stem
            for (int y = 0; y <= size; y++) {
                world.setBlockState(pos.down(y), Blocks.MUSHROOM_STEM.getDefaultState(), 2);
            }

            // Generate mushroom cap
            for (BlockPos point : ShapeUtil.generateSolidCircle((float) (size / 2) + 1)) {
                world.setBlockState(pos.add(point.getX(), point.getY() - size, point.getZ()), Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState(), 2);
            }

            return true;
        }
    }
}
