package com.nekomaster1000.infernalexp.world.gen.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowSpikeFeature extends Feature<NoFeatureConfig> {

    private final int minRadius = 1;
    private final int maxRadius = 3;
    private final int minHeight = 10;
    private final int maxHeight = 16;

    private PerlinNoiseGenerator noiseGen;

    public GlowSpikeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        SharedSeedRandom seed = new SharedSeedRandom(world.getSeed() + random.nextInt(100));
        noiseGen = new PerlinNoiseGenerator(seed, ImmutableList.of(0));

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            int radius = minRadius + random.nextInt(maxRadius - minRadius);
            int height = minHeight + random.nextInt(maxHeight - minHeight);

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    int distanceFromCenter = MathHelper.abs(x) + MathHelper.abs(z);

                    placeColumn(world, pos.add(x, 0, z), height - (distanceFromCenter * 3));
                }
            }

            return true;
        }
    }

    private void placeLine(ISeedReader world, BlockPos startPos, BlockPos endPos) {
        Vec3d pos1 = new Vec3d(startPos.getX(), startPos.getY(), startPos.getZ());
        Vec3d pos2 = new Vec3d(endPos.getX(), endPos.getY(), endPos.getZ());
    }

    private void placeColumn(ISeedReader world, BlockPos pos, int heightMultiplier) {
        for (int i = 0; i < MathHelper.abs(heightMultiplier); i++) {
            world.setBlockState(pos.up(i), ModBlocks.DIMSTONE.get().getDefaultState(), 10);
        }
    }
}

