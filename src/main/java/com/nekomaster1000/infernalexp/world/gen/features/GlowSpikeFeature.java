package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowSpikeFeature extends Feature<NoFeatureConfig> {

    private static final int minDiameter = 3;
    private static final int maxDiameter = 9;
    private static final int minHeight = 12;
    private static final int maxHeight = 24;

    public GlowSpikeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        int height = minHeight + random.nextInt(maxHeight - minHeight);
        int diameter = minDiameter + random.nextInt(maxDiameter - minDiameter);
        int xOffset = -7 + random.nextInt(14);
        int zOffset = -7 + random.nextInt(14);

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            int[][] points = generateCircle(diameter);

            for (int[] point : points) {
                placeLine(world, pos.add(point[0], 0, point[1]), pos.add(xOffset, height, zOffset));
            }

            return true;
        }
    }

    private void placeLine(ISeedReader world, BlockPos startPos, BlockPos endPos) {
        Vec3d vec1 = new Vec3d(startPos.getX(), startPos.getY(), startPos.getZ());
        Vec3d vec2 = new Vec3d(endPos.getX(), endPos.getY(), endPos.getZ());

        Vec3d diffVec = new Vec3d(vec2.x - vec1.x, vec2.y - vec1.y, vec2.z - vec1.z);
        Vec3d incVec = new Vec3d((int) diffVec.x / diffVec.length(), (int) diffVec.y / diffVec.length(), (int) diffVec.z / diffVec.length());

        for (int i = 0; i <= (int) diffVec.length(); i++) {
            world.setBlockState(new BlockPos(vec1.x, vec1.y, vec1.z), ModBlocks.DIMSTONE.get().getDefaultState(), 10);
            vec1.add(incVec);
        }
    }

    private int[][] generateCircle(int diameter) {
        int pointsToCalculate = diameter * 4;
        int[][] points = new int[pointsToCalculate][2];
        float degreeIncrement = (float) 360 / pointsToCalculate;

        for (int i = 0; i < pointsToCalculate; i++) {
            float degree = degreeIncrement * i;

            points[i][0] = MathHelper.floor(MathHelper.cos(degree) * (diameter / 2.0f));
            points[i][1] = MathHelper.floor(MathHelper.sin(degree) * (diameter / 2.0f));
        }

        return points;
    }

    private void placeColumn(ISeedReader world, BlockPos pos, int heightMultiplier) {
        for (int i = 0; i < MathHelper.abs(heightMultiplier); i++) {
            world.setBlockState(pos.up(i), ModBlocks.DIMSTONE.get().getDefaultState(), 10);
        }
    }
}

