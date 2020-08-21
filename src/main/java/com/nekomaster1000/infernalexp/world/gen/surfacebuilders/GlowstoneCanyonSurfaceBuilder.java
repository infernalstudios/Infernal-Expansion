package com.nekomaster1000.infernalexp.world.gen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.ValleySurfaceBuilder;

public class GlowstoneCanyonSurfaceBuilder extends ValleySurfaceBuilder {
    private static final BlockState field_237163_a_ = ModBlocks.GLOWDUST_SAND.get().getDefaultState();
    private static final BlockState field_237164_b_ = ModBlocks.GLOWDUST_SANDSTONE.get().getDefaultState();
    private static final BlockState field_237165_c_ = Blocks.GLOWSTONE.getDefaultState();
    private static final ImmutableList<BlockState> field_237166_d_ = ImmutableList.of(field_237163_a_, field_237164_b_);
    private static final ImmutableList<BlockState> field_237167_e_ = ImmutableList.of(field_237163_a_);

    public GlowstoneCanyonSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232130_1_) {
        super(p_i232130_1_);
    }

    protected ImmutableList<BlockState> func_230387_a_() {
        return field_237166_d_;
    }

    protected ImmutableList<BlockState> func_230388_b_() {
        return field_237167_e_;
    }

    protected BlockState func_230389_c_() {
        return field_237165_c_;
    }
}
