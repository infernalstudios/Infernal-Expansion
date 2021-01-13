package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.util.ShapeUtil;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SinkHoleFeature extends Feature<BlockStateFeatureConfig> {
    public SinkHoleFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    private static final int minRadius = 2;
    private static final int maxRadius = 4;

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, BlockStateFeatureConfig config) {
        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            int radius = minRadius + random.nextInt(maxRadius - minRadius);
            int depth = pos.getY() - 14 + random.nextInt(8);

            // Check to see if we are on the bottom layer of the biome
            for (int y = 1; y < depth; y++) {
                if (world.getBlockState(pos.down(y)) == Blocks.AIR.getDefaultState()) return false;
            }

            // Build the walls down a few blocks so if the sink hole spawns on a slope it isn't open from the side
            for (BlockPos point : ShapeUtil.generateSolidCircle(radius + 1)) {
                for (int y = 0; y < 3; y++) {
                    world.setBlockState(pos.add(point).down(y + 1), ModBlocks.GLOWDUST_SAND.get().getDefaultState(), 2);
                }
            }

            // Dig down by depth
            for (BlockPos point : ShapeUtil.generateSolidCircle(radius)) {
                world.setBlockState(pos.add(point), config.state, 2);

                for (int y = 1; y < depth; y++) {
                    if (world.getBlockState(pos.add(point).down(y)) != Blocks.LAVA.getDefaultState())
                        world.setBlockState(pos.add(point).down(y), Blocks.AIR.getDefaultState(), 2);
                }
            }

            // Round off the bottom with a sphere
            for (BlockPos point : ShapeUtil.generateSolidSphere(radius)) {
                world.setBlockState(pos.add(point).down(depth + 1), Blocks.AIR.getDefaultState(), 2);
            }

            return true;
        }
    }
}
