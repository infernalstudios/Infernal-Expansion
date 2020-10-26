package com.nekomaster1000.infernalexp.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public abstract class ModFeature extends Feature<NoFeatureConfig> {
    public ModFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean func_241855_a(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config) {
        return place(world, chunk, random, pos, config);
    }

    /**
     * This method is called whenever the game tries to generate the feature
     * @param world World to place feature in
     * @param chunk Chunk the feature is placed in
     * @param random Random generator
     * @param pos BlockPos that the feature gets placed at
     * @param config Feature config class
     * @return Returns true if the feature was placed successfully, false otherwise
     */
    public abstract boolean place(ISeedReader world, ChunkGenerator chunk, Random random, BlockPos pos, NoFeatureConfig config);
}
