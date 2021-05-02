package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.blocks.ShroomlightFungusBlock;
import com.nekomaster1000.infernalexp.init.IEBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class ShroomlightTearFeature extends Feature<NoFeatureConfig> {

    public ShroomlightTearFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        int i = 0;
        int amount = 10;
        AttachFace face;
        boolean warpedForest;

        if (world.getBiome(pos).getRegistryName().getPath().equals("warped_forest")) {
            face = AttachFace.FLOOR;
            warpedForest = true;

        } else {
            face = AttachFace.CEILING;
            warpedForest = false;
        }

        // Try to place Shroomlight Tear 128 times
        for (int j = 0; j < 64; j++) {
            // Randomize the location of the next luminous fungus to be placed
            BlockState state = IEBlocks.SHROOMLIGHT_FUNGUS.get().getDefaultState().with(ShroomlightFungusBlock.FACE, face);
            BlockPos blockpos = pos.add(random.nextInt(10) - random.nextInt(20), random.nextInt(4) - random.nextInt(8), random.nextInt(10) - random.nextInt(20));

            // If the randomly chosen location is valid, then place the fungus
            if (world.isAirBlock(blockpos) && state.isValidPosition(world, blockpos) && (world.getBlockState(warpedForest ? blockpos.down() : blockpos.up()) == Blocks.SHROOMLIGHT.getDefaultState())) {
                world.setBlockState(blockpos, state, 2);
                i++;
            }


            // If we have placed the max amount of Shroomlight Tear, then return
            if (i >= amount) {
                return true;
            }
        }

        return false;
    }
}
