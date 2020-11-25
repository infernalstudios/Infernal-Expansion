package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.util.ShapeUtil;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class HangingCrimsonFungusFeature extends Feature<NoFeatureConfig> {
    public HangingCrimsonFungusFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    private static final int minSize = 3;
    private static final int maxSize = 7;

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!world.isAirBlock(pos) || world.getBlockState(pos.up()) != ModBlocks.DULLSTONE.get().getDefaultState()) {
            return false;
        } else {
            int size = minSize + random.nextInt(maxSize - minSize);

            for (int y = 0; y <= size; y++) {
                world.setBlockState(pos.down(y), Blocks.CRIMSON_HYPHAE.getDefaultState(), 10);
            }

            ArrayList<BlockPos> points = ShapeUtil.generateSolidCircle((float) (size / 2) + 1);

            for (BlockPos point : points) {
                world.setBlockState(pos.add(point.getX(), point.getY() - size, point.getZ()), Blocks.NETHER_WART_BLOCK.getDefaultState(), 10);
            }

            return true;
        }
    }
}
