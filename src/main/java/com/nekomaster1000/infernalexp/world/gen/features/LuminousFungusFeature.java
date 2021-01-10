package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.blocks.LuminousFungusBlock;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.AttachFace;
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
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        int i = 0;
        int amount;
        AttachFace face;

        int pickFacing = random.nextInt(3);

        if (pickFacing == 0) {
            face = AttachFace.CEILING;
            amount = 4;
        } else {
            face = AttachFace.FLOOR;
            amount = 10;
        }

        for (int j = 0; j < 96; j++) {
            BlockState state = ModBlocks.LUMINOUS_FUNGUS.get().getDefaultState().with(LuminousFungusBlock.FACE, face);
            BlockPos blockpos = pos.add(random.nextInt(20) - random.nextInt(40), random.nextInt(4) - random.nextInt(8), random.nextInt(20) - random.nextInt(40));

            if (world.isAirBlock(blockpos) && state.isValidPosition(world, blockpos) && (world.getBlockState(blockpos.up()) == ModBlocks.DULLSTONE.get().getDefaultState() || world.getBlockState(blockpos.down()) == ModBlocks.GLOWDUST_SAND.get().getDefaultState())) {
                world.setBlockState(blockpos, state, 10);
                i++;
            }

            if (i >= amount)
                return true;
        }

        return false;
    }
}

