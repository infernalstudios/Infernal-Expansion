package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class BoulderFeature extends Feature<BlockStateFeatureConfig> {

    public BoulderFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get() || world.isAirBlock(pos.down(2))) {
            return false;
        } else {
            int rad = rand.nextInt(2) + 1;
            placeSphere(world, rand, pos.down(Math.floorDiv(rad, 2)), rad, config);
            return true;
        }
    }

    private void placeSphere(ISeedReader world, Random rand, BlockPos pos, int radius, BlockStateFeatureConfig config) {

        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
            for (int y = pos.getY() - (radius+1); y < pos.getY() + (radius+1); y++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    float formula = (float) (Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY() + 1, 2) + Math.pow(z - pos.getZ(), 2));

                    if (formula <= Math.pow(radius, 2)) {
                        world.setBlockState(new BlockPos(x, y, z), config.state, 2 | 16);
                        randomNoise(world, rand, pos, config);
                    }
                }
            }
        }
    }
    private void randomNoise(ISeedReader world, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        int k = rand.nextInt(8);
        switch (k) {
            case 0:
                if (!world.getBlockState(pos.west().down()).isAir()) {
                    world.setBlockState(pos.west(), config.state, 16);
                }
            case 1:
                if (!world.getBlockState(pos.north().down()).isAir()) {
                    world.setBlockState(pos.north(), config.state, 16);
                }
            case 2:
                if (!world.getBlockState(pos.east().down()).isAir()) {
                    world.setBlockState(pos.east(), config.state, 16);
                }
            case 3:
                if (!world.getBlockState(pos.south().down()).isAir()) {
                    world.setBlockState(pos.south(), config.state, 16);
                }
            case 4:
                if (!world.getBlockState(pos.up().down()).isAir()) {
                    world.setBlockState(pos.up(), config.state, 16);
                }
        }
    }
}
