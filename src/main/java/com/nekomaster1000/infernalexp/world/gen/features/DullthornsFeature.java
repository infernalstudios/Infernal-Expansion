package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class DullthornsFeature extends Feature<NoFeatureConfig> {
    public DullthornsFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        int height = random.nextInt(3) + 1;
        if (!worldIn.isAirBlock(pos) || worldIn.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            for (int i = 0; i < height; i++) {
                if (worldIn.isAirBlock(pos.up(i))) worldIn.setBlockState(pos.up(i), ModBlocks.DULLTHORNS.get().getDefaultState(), 10);
            }
            return true;
        }
    }
}

