package com.nekomaster1000.infernalexp.world.gen.features;

import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.util.ShapeUtil;
import com.nekomaster1000.infernalexp.world.gen.features.config.GlowSpikeFeatureConfig;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class GlowSpikeFeature extends Feature<GlowSpikeFeatureConfig> {

    public GlowSpikeFeature(Codec<GlowSpikeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, GlowSpikeFeatureConfig config) {
        // Generate a random height, diameter and offset
        int height = config.minHeight + random.nextInt(config.maxHeight - config.minHeight);
        int diameter = config.minDiameter + random.nextInt(config.maxDiameter - config.minDiameter);
        int xOffset = -config.maxXOffset + random.nextInt(config.maxXOffset * 2);
        int zOffset = -config.maxZOffset + random.nextInt(config.maxZOffset * 2);

        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            List<BlockPos> points = ShapeUtil.generateSolidCircle((float) diameter / 2);

            // Check to see if the glowspike will generate on the ground instead of floating, if not, move one down and try again
            for (BlockPos point : points) {
                BlockPos pointPos = new BlockPos(pos.getX() + point.getX(), pos.getY(), pos.getZ() + point.getZ());

                if (world.getBlockState(pointPos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
                    return generate(world, generator, random, pos.down(), config);
                }
            }

            // Place lines from each point in base to the peak point
            for (BlockPos point : points) {
                placeGlowSpikeLine(world, pos.add(point.getX(), 0, point.getZ()), pos.add(xOffset, height, zOffset), random, config);
            }

            return true;
        }
    }

    private void placeGlowSpikeLine(ISeedReader world, BlockPos startPos, BlockPos endPos, Random random, GlowSpikeFeatureConfig config) {
        List<BlockPos> line = ShapeUtil.generateLine(startPos, endPos);

        for (int i = 0; i < line.size(); i++) {
            BlockPos pos = line.get(i);

            // Skip parts of the spike generating above bedrock
        	if (pos.getY() > 128 || world.getBlockState(pos).equals(Blocks.BEDROCK.getDefaultState())) {
        		continue;
        	}

        	// Finds what percentage of the line has been built and then adds some randomness to it to make for a
            // more gradual change between blocks
            float percentage = (((float) i / line.size()) - config.blockDitheringAmount / 2) + (random.nextFloat() * config.blockDitheringAmount);

        	// Generate blocks from dullstone to dimstone to glowstone, or reversed if darkAtTop is true
            if (percentage <= 0.33) {
                if (config.darkAtTop)
                    world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 2);
                else
                    world.setBlockState(new BlockPos(pos), ModBlocks.DULLSTONE.get().getDefaultState(), 2);
            } else if (percentage > 0.33 && percentage <= 0.66) {
                world.setBlockState(new BlockPos(pos), ModBlocks.DIMSTONE.get().getDefaultState(), 2);
            } else {
                if (config.darkAtTop)
                    world.setBlockState(new BlockPos(pos), ModBlocks.DULLSTONE.get().getDefaultState(), 2);
                else
                    world.setBlockState(new BlockPos(pos), Blocks.GLOWSTONE.getDefaultState(), 2);
            }
        }
    }
}
