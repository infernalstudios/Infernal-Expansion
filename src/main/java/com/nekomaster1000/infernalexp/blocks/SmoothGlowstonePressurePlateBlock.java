package com.nekomaster1000.infernalexp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

public class SmoothGlowstonePressurePlateBlock extends PressurePlateBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public SmoothGlowstonePressurePlateBlock(Sensitivity sensitivityIn, Properties propertiesIn) {
		super(sensitivityIn, propertiesIn);
		this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, false).with(LIT, false));
	}

	@Override
	protected BlockState setRedstoneStrength(BlockState state, int strength) {
		return state.with(POWERED, strength > 0).with(LIT, strength > 0);
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		builder.add(POWERED);
	}
}
