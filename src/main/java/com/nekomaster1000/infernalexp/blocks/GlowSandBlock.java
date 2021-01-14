package com.nekomaster1000.infernalexp.blocks;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class GlowSandBlock extends SandBlock {

    public GlowSandBlock(int dustColorIn, Properties properties) {
        super(dustColorIn, properties);
    }


    /**
     * Called whenever a block update occurs in a neighboring block
     * @param state the BlockState of this Block
     * @param worldIn the World this Block is in
     * @param pos the BlockPos of this Block
     * @param blockIn the neighboring Block being updated, before the update
     * @param fromPos the position of the neighboring Block being updated
     */
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        // Check if neighbor changed was above it
        if (pos.up().equals(fromPos)) {
            // Get new state of neighbor
            Block newBlock = worldIn.getBlockState(fromPos).getBlock();

            // Check if Fire, but not Glow Fire
            if (newBlock instanceof AbstractFireBlock && !(newBlock instanceof GlowFireBlock)) {
                // Change to GlowFireBlock
                BlockState newFire = GlowFireBlock.getFireForPlacement(worldIn, fromPos);
                worldIn.destroyBlock(fromPos, false);
                worldIn.setBlockState(fromPos, newFire, 1 | 2); // Cause block update and send change to clients
            }
        }
    }



    /**
     * Override this method to return true if the block should behave like an infinite fire source
     */
    @Override
    public boolean isFireSource(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
        return true;
    }
}
