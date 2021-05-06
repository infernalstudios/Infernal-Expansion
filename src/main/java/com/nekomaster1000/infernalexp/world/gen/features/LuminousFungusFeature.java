package com.nekomaster1000.infernalexp.world.gen.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import com.nekomaster1000.infernalexp.init.IEBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class LuminousFungusFeature extends Feature<NoFeatureConfig> {
    public LuminousFungusFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        int i = 0;
        int amount;
        AttachFace face;

        int pickFacing = random.nextInt(3);

        // Pick whether the fungus will spawn on the ceiling or on the floor and set the amount to spawn appropriately
        if (pickFacing == 0) {
            face = AttachFace.CEILING;
            amount = 4;
        } else {
            face = AttachFace.FLOOR;
            amount = 10;
        }

        // Try to place luminous fungus 128 times
        for (int j = 0; j < 128; j++) {
            // Randomize the location of the next luminous fungus to be placed
            BlockState state = IEBlocks.LUMINOUS_FUNGUS.get().getDefaultState().with(LuminousFungusBlock.FACE, face);
            BlockPos blockpos = pos.add(random.nextInt(10) - random.nextInt(20), random.nextInt(4) - random.nextInt(8), random.nextInt(10) - random.nextInt(20));

            // If the randomly chosen location is valid, then place the fungus
            if (world.isAirBlock(blockpos) && state.isValidPosition(world, blockpos) && (world.getBlockState(blockpos.up()) == IEBlocks.DULLSTONE.get().getDefaultState() || world.getBlockState(blockpos.down()) == IEBlocks.GLOWDUST_SAND.get().getDefaultState())) {
                world.setBlockState(blockpos, state, 2);
                i++;
            }

            // If we have placed the max amount of luminous fungus, then return
            if (i >= amount) {
                return true;
            }
        }

        return false;
    }
}

