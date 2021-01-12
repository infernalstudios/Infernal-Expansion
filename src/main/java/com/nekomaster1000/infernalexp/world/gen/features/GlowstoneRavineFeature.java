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

import java.util.*;

public class GlowstoneRavineFeature extends Feature<NoFeatureConfig> {
    public GlowstoneRavineFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        BlockPos endPos = pos.add((40 + random.nextInt(20)) * (random.nextInt(2) == 0 ? -1 : 1), 0, (40 + random.nextInt(20)) * (random.nextInt(2) == 0 ? -1 : 1));

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()) != ModBlocks.GLOWDUST_SAND.get().getDefaultState() || world.getBlockState(endPos.down()) == Blocks.LAVA.getDefaultState()) {
            return false;
        } else {
            List<BlockPos> centerLine = ShapeUtil.generateLine(pos, endPos);
            List<BlockPos> sidePoints = new ArrayList<>();

            int segments = random.nextInt(3) + 1;
            System.out.println("Segments: " + segments);

            // Find the side points to create the outline
            for (int i = 1; i <= segments; i++) {
                BlockPos segment = centerLine.get(((centerLine.size() / 3) * i) - 1);

                sidePoints.add(segment.add(1 + random.nextInt(2), 0, 1 + random.nextInt(2)));
                sidePoints.add(segment.add((1 + random.nextInt(2)) * -1, 0, (1 + random.nextInt(2)) * -1));
            }

            Set<BlockPos> points = new HashSet<>();

            // Find outline of ravine
            for (int i = 0; i < segments; i++) {
                if (i == 0) {
                    points.addAll(ShapeUtil.generateLine(pos, sidePoints.get(i)));
                    points.addAll(ShapeUtil.generateLine(pos, sidePoints.get(i + 1)));
                } else if (i == segments - 1) {
                    points.addAll(ShapeUtil.generateLine(sidePoints.get(i), endPos));
                    points.addAll(ShapeUtil.generateLine(sidePoints.get(i + 1), endPos));
                } else {
                    points.addAll(ShapeUtil.generateLine(sidePoints.get(i), sidePoints.get(i + 2)));
                    points.addAll(ShapeUtil.generateLine(sidePoints.get(i + 1), sidePoints.get(i + 3)));
                }
            }

            // Fill in outline
            for (BlockPos centerPoint : centerLine) {
                for (BlockPos sidePoint : sidePoints) {
                    points.addAll(ShapeUtil.generateLine(centerPoint, sidePoint));
                }
            }

            // Replace blocks with air
            for (BlockPos point : points) {
                for (int y = 0; y < 10; y++) {
                    world.setBlockState(point.down(y), Blocks.AIR.getDefaultState(), 2);
                }
            }

            // Place blackstone pillar to find the canyons easier when testing
            for (int i = 0; i < 4; i++) {
                world.setBlockState(pos.up(i), Blocks.BLACKSTONE.getDefaultState(), 2);
            }

            for (BlockPos sidePoint : sidePoints) {
                for (int i = 0; i < 4; i++) {
                    world.setBlockState(sidePoint.up(i), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
                    world.setBlockState(endPos.up(i), Blocks.GOLD_BLOCK.getDefaultState(), 2);
                }
            }

            for (BlockPos centerPoint : centerLine) {
                world.setBlockState(centerPoint, Blocks.EMERALD_BLOCK.getDefaultState(), 2);
            }

            return true;
        }
    }
}
