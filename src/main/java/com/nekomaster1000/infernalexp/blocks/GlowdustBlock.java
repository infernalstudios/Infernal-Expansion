package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

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
