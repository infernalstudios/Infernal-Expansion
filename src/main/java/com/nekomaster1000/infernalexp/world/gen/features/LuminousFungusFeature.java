package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class LuminousFungusFeature extends Feature<NoFeatureConfig> {
    public LuminousFungusFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader worldIn, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        if (!worldIn.isAirBlock(pos) || worldIn.getBlockState(pos.down()).getBlock() != ModBlocks.GLOWDUST_SAND.get()) {
            return false;
        } else {
            worldIn.setBlockState(pos, ModBlocks.LUMINOUS_FUNGUS.get().getDefaultState(), 10);
            return true;
        }
    }
}

