package com.nekomaster1000.infernalexp.blocks;

import javax.annotation.Nullable;

import com.nekomaster1000.infernalexp.init.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItemUseContext;

public class GlowdustBlock extends SnowBlock {

    public GlowdustBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        if (blockstate.isIn(this)) {
            int i = blockstate.get(LAYERS);
            if (i < 7) {
                return blockstate.with(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
            }
            else {
                return ModBlocks.GLOWDUST_SAND.get().getDefaultState();
            }
        } else {
            return super.getStateForPlacement(context);
        }
    }
}
