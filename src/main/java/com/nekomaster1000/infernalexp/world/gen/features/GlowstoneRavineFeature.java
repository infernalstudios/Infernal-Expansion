package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class GlowstoneRavineFeature extends Feature<NoFeatureConfig> {
    public GlowstoneRavineFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!world.isAirBlock(pos) || world.getBlockState(pos.down()) != ModBlocks.GLOWDUST_SAND.get().getDefaultState()) {
            return false;
        } else {
            for (int x = 0; x <= 15; x++) {
                for (int y = 0; y <= 12; y++) {
                    for (int z = 0; z <= 3; z++) {
                        world.setBlockState(pos.add(x, y, z), Blocks.AIR.getDefaultState(), 10);
                    }
                }
            }

            return true;
        }
    }
}
