package com.nekomaster1000.infernalexp.world.gen.surfacebuilders;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class GlowstoneCanyonSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public GlowstoneCanyonSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232136_1_) {
        super(p_i232136_1_);
    }

    @Override
    public void buildSurface(Random random, IChunk chunk, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        BlockPos.Mutable pos = new BlockPos.Mutable();

        int xPos = x & 15;
        int zPos = z & 15;

        for (int yPos = 125; yPos >= seaLevel; yPos--) {
            pos.setPos(xPos, yPos, zPos);

            BlockState currentBlockToReplace = chunk.getBlockState(pos);
            BlockState checkForAir = chunk.getBlockState(pos.up());

            // Replace netherrack with correct blocks for the glowstone canyon
            if (currentBlockToReplace == Blocks.NETHERRACK.getDefaultState() && checkForAir != Blocks.AIR.getDefaultState()) {
                chunk.setBlockState(pos, ModBlocks.DULLSTONE.get().getDefaultState(), false);
            } else if (currentBlockToReplace == Blocks.NETHERRACK.getDefaultState()) {
                chunk.setBlockState(pos, config.getTop(), false);

                // Checks to see if it should place a glowdust layer
                glowdustLayerCheck: for (int xCheck = -1; xCheck <= 1; xCheck++) {
                    for (int zCheck = -1; zCheck <= 1; zCheck++) {
                        if (chunk.getBlockState(pos.add(xCheck, 1, zCheck)) == ModBlocks.GLOWDUST_SAND.get().getDefaultState()) {
                            chunk.setBlockState(pos.up(), ModBlocks.GLOWDUST.get().getDefaultState(), false);
                            break glowdustLayerCheck;
                        }
                    }
                }

                // Replace netherrack with glowdust rock/stone
                for (int offset = 1; offset <= 3; offset++) {
                    if (chunk.getBlockState(pos.down(offset)) == Blocks.NETHERRACK.getDefaultState()) {
                        chunk.setBlockState(pos.down(offset), config.getUnder(), false);
                    }
                }

                // Build terrain down to bedrock
                if (yPos <= 63) {
                    if (chunk.getBlockState(pos.down(1)) == config.getUnder() && chunk.getBlockState(pos.down(2)) == config.getUnder()) {
                        for (int offset = 3; offset <= yPos; offset++) {
                            float percentage = (((float) offset / yPos) - 0.05f) + (random.nextFloat() * 0.1f);

                            if (percentage <= 0.15 && random.nextInt(10) == 1) {
                                chunk.setBlockState(pos.down(offset), ModBlocks.DIMSTONE.get().getDefaultState(), false);
                            } else if (percentage <= 0.15) {
                                chunk.setBlockState(pos.down(offset), ModBlocks.GLOWDUST_STONE.get().getDefaultState(), false);
                            } else {
                                chunk.setBlockState(pos.down(offset), ModBlocks.DULLSTONE.get().getDefaultState(), false);
                            }
                        }
                    }
                }
            }
        }

        // Add specs of dimstone to low areas of dullstone, this needs to be refactored because putting this code here is stupid.
        // It should probably go where all the netherrack is replaced with dullstone or where the terrain is built down.
        for (int yPos = 50; yPos > 0; yPos--) {
            pos.setPos(xPos, yPos, zPos);

            if (chunk.getBlockState(pos) == ModBlocks.DULLSTONE.get().getDefaultState()) {
                if (random.nextInt(50) == 1) {
                    chunk.setBlockState(pos, ModBlocks.DIMSTONE.get().getDefaultState(), false);
                }
//                I tried to make patches of dimstone. It looked like shit, specs look way better
//                else if (chunk.getBlockState(pos.east()) == ModBlocks.DIMSTONE.get().getDefaultState() && random.nextInt(8) <= 6 ||
//                        chunk.getBlockState(pos.west()) == ModBlocks.DIMSTONE.get().getDefaultState() && random.nextInt(8) <= 6 ||
//                        chunk.getBlockState(pos.north()) == ModBlocks.DIMSTONE.get().getDefaultState() && random.nextInt(8) <= 6 ||
//                        chunk.getBlockState(pos.south()) == ModBlocks.DIMSTONE.get().getDefaultState() && random.nextInt(8) <= 6 ||
//                        chunk.getBlockState(pos.up()) == ModBlocks.DIMSTONE.get().getDefaultState() && random.nextInt(8) == 1 ||
//                        chunk.getBlockState(pos.down()) == ModBlocks.DIMSTONE.get().getDefaultState() && random.nextInt(8) == 1) {
//                    chunk.setBlockState(pos, ModBlocks.DIMSTONE.get().getDefaultState(), false);
//                }
            }
        }
    }
}
