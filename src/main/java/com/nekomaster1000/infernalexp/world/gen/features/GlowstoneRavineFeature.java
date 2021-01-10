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

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GlowstoneRavineFeature extends Feature<NoFeatureConfig> {
    public GlowstoneRavineFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        BlockPos endPos = pos.add((40 + random.nextInt(20)) * (random.nextInt(2) == 0 ? -1 : 1), 0, (40 + random.nextInt(20)) * (random.nextInt(2) == 0 ? -1 : 1));

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()) != ModBlocks.GLOWDUST_SAND.get().getDefaultState() || world.getBlockState(endPos.down()) == Blocks.AIR.getDefaultState() || world.getBlockState(endPos.down()) == Blocks.LAVA.getDefaultState()) {
            return false;
        } else {
            List<BlockPos> centerLine = ShapeUtil.generateLine(pos, endPos);

            BlockPos halfwayPoint = centerLine.get(centerLine.size() / 2);
            BlockPos sidePoint1 = halfwayPoint.add((2 + random.nextInt(3)) * (random.nextInt(2) == 0 ? -1 : 1), 0, (2 + random.nextInt(3)) * (random.nextInt(2) == 0 ? -1 : 1));
            BlockPos sidePoint2 = halfwayPoint.add((2 + random.nextInt(3)) * (random.nextInt(2) == 0 ? -1 : 1), 0, (2 + random.nextInt(3)) * (random.nextInt(2) == 0 ? -1 : 1));

            Set<BlockPos> points = new HashSet<>();

            // Find outline of ravine
            points.addAll(ShapeUtil.generateLine(pos, sidePoint1));
            points.addAll(ShapeUtil.generateLine(pos, sidePoint2));
            points.addAll(ShapeUtil.generateLine(sidePoint1, endPos));
            points.addAll(ShapeUtil.generateLine(sidePoint2, endPos));

            // Fill in outline
            for (BlockPos point : centerLine) {
                points.addAll(ShapeUtil.generateLine(point, sidePoint1));
                points.addAll(ShapeUtil.generateLine(point, sidePoint2));
            }

            // Replace blocks with air
            for (BlockPos point : points) {
                for (int y = 0; y < 30; y++) {
                    world.setBlockState(point.down(y), Blocks.AIR.getDefaultState(), 10);
                }
            }

            // Place blackstone pillar to find the canyons easier when testing
            for (int i = 0; i < 4; i++) {
                world.setBlockState(pos.up(i), Blocks.BLACKSTONE.getDefaultState(), 10);
            }

            return true;
        }
    }
}
