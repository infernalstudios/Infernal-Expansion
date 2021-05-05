package com.nekomaster1000.infernalexp.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class DeltaShoresSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>{
	public DeltaShoresSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232136_1_) {
		super(p_i232136_1_);
	}


    @Override
    public void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int terrainHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(random, chunk, biome, x, z, terrainHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int terrainHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, BlockState topBlock, BlockState middleBlock, BlockState underwaterBlock, int seaLevel) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int depth = -1; // Will be used to know how deep we are in solid blocks so we know when to stop placing middleBlock
        int siltMixThreshold = 63;
        int middleBlockExtraDepth = (int)(noise / 3.0D + 1.0D + random.nextDouble() * 0.25D);

        // Start at top land and loop downward
        for (int y = terrainHeight; y >= seaLevel; --y) {

            // Get the block in the world (Nether will always give Netherrack, Lava, or Air)
            mutable.setPos(x, y, z);
            BlockState currentBlockInWorld = chunk.getBlockState(mutable);

            // Reset the depth counter as we are not in land anymore
            if (currentBlockInWorld.isAir()) {
                depth = -1;
            } else if (currentBlockInWorld.getFluidState().isEmpty() && currentBlockInWorld.getBlock() != Blocks.BEDROCK) {
                // We are in solid land now if fluid check fails. Skip Bedrock as we shouldn't replace that

                // -1 depth means we are switching from air to solid land. Place the surface block now
                if (depth == -1 && y > seaLevel) {
                    // The typical surface of the biome.
                    chunk.setBlockState(mutable, topBlock, false);
                } else if (depth <= 3 + middleBlockExtraDepth) {
                    // Place block only when under surface and down to as deep as the scaledNoise says to go.
                    chunk.setBlockState(mutable, middleBlock, false);
                } else if (y <= siltMixThreshold) {
                    // replaces all underground solid blocks below y = 63 with basalt/silt mix
                    float percentage = ((float) y / 63) - ((float)noise / 6.5f);
                    if (percentage <= 0.10) {
                        chunk.setBlockState(mutable, topBlock, false);
                    } else {
                        chunk.setBlockState(mutable, underwaterBlock, false);
                    }
                } else {
                    // replaces all underground solid blocks above y = 63 with basalt
                    chunk.setBlockState(mutable, underwaterBlock, false);
                }

                // Increment depth so we can start placing middle blocks when moving down next loop as we go deeper
                depth++;
            }
        }
    }
}
