package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowSpikeSmallFeature extends ModFeature {

    private static final int minDiameter = 3;
    private static final int maxDiameter = 5;
    private static final int minHeight = 8;
    private static final int maxHeight = 24;
    private static final int maxXOffset = 7;
    private static final int maxZOffset = 7;

    public GlowSpikeSmallFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        int height = minHeight + random.nextInt(maxHeight - minHeight);
        int diameter = minDiameter + random.nextInt(maxDiameter - minDiameter);
        int xOffset = -maxXOffset + random.nextInt(maxXOffset * 2);
        int zOffset = -maxZOffset + random.nextInt(maxZOffset * 2);

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            for (int i = diameter; i > 0; i -= 2) {
                int[][] points = generateCircle(i);

                for (int[] point : points) {
                    placeLine(world, pos.add(point[0], 0, point[1]), pos.add(xOffset, height, zOffset));
                }
            }

            return true;
        }
    }

    /**
     * Places a line of blocks from the startPos to the endPos. It uses the appropriate blocks to make a {@link GlowSpikeSmallFeature}
     * @param world World blocks are to be placed in
     * @param startPos Start position
     * @param endPos End position
     */
    private void placeLine(ISeedReader world, BlockPos startPos, BlockPos endPos) {
        Vec3d vec1 = new Vec3d(startPos.getX(), startPos.getY(), startPos.getZ());
        Vec3d vec2 = new Vec3d(endPos.getX(), endPos.getY(), endPos.getZ());

        Vec3d diffVec = new Vec3d(vec2.x - vec1.x, vec2.y - vec1.y, vec2.z - vec1.z);
        Vec3d incVec = new Vec3d((int) diffVec.x / diffVec.length(), (int) diffVec.y / diffVec.length(), (int) diffVec.z / diffVec.length());

        int lineLength = (int) diffVec.length();

        for (int i = 0; i <= lineLength; i++) {
            float percentage = (float) i / lineLength;

            if (percentage <= 0.33) {
                world.setBlockState(new BlockPos(vec1.x, vec1.y, vec1.z), Blocks.GLOWSTONE.getDefaultState(), 10);
            } else if (percentage > 0.33 && percentage <= 0.66) {
                world.setBlockState(new BlockPos(vec1.x, vec1.y, vec1.z), ModBlocks.DIMSTONE.get().getDefaultState(), 10);
            } else {
                world.setBlockState(new BlockPos(vec1.x, vec1.y, vec1.z), ModBlocks.DULLSTONE.get().getDefaultState(), 10);
            }
            vec1.add(incVec);
        }
    }

    /**
     * Generates an array of points that creates a rough circle
     * @param diameter Diameter of the generated circle
     * @return Returns an array of int arrays that each have an x, y coordinate
     */
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
}

