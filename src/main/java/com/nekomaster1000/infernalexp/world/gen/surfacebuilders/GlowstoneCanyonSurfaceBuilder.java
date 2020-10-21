package com.nekomaster1000.infernalexp.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class GlowstoneCanyonSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public GlowstoneCanyonSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232136_1_) {
        super(p_i232136_1_);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        BlockPos.Mutable block = new BlockPos.Mutable();

        int xPos = x & 15;
        int zPos = z & 15;

        for (int yPos = 125; yPos >= seaLevel; yPos--) {
            block.setPos(xPos, yPos, zPos);

            BlockState currentBlockToReplace = chunkIn.getBlockState(block);
            BlockState checkForAir = chunkIn.getBlockState(block.up());

            if (currentBlockToReplace == Blocks.NETHERRACK.getDefaultState() && checkForAir != Blocks.AIR.getDefaultState()) {
                chunkIn.setBlockState(block, ModBlocks.DULLSTONE.get().getDefaultState(), false);
            } else if (currentBlockToReplace == Blocks.NETHERRACK.getDefaultState()) {
                chunkIn.setBlockState(block, config.getTop(), false);

                for (int offset = 1; offset <= 3; offset++) {
                    if (chunkIn.getBlockState(block.down(offset)) == Blocks.NETHERRACK.getDefaultState()) {
                        chunkIn.setBlockState(block.down(offset), config.getUnder(), false);
                    }
                }

                if (yPos <= 63) {
                    for (int offset = 4; offset <= yPos; offset++) {
                        chunkIn.setBlockState(block.down(offset), ModBlocks.DULLSTONE.get().getDefaultState(), false);
                    }
                }
            }
        }
    }
}
