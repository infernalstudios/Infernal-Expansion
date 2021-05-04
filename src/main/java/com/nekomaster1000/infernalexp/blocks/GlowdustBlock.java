package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.IEBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItemUseContext;

import javax.annotation.Nullable;

public class GlowdustBlock extends SnowBlock {

	public GlowdustBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		if (blockstate.matchesBlock(this)) {
			int i = blockstate.get(LAYERS);
			if (i < 7) {
				return blockstate.with(LAYERS, Math.min(8, i + 1));
			} else {
				return IEBlocks.GLOWDUST_SAND.get().getDefaultState();
			}
		} else {
			return super.getStateForPlacement(context);
		}
    }
}
