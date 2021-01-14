package com.nekomaster1000.infernalexp.blocks;

import com.nekomaster1000.infernalexp.init.ModBlocks;
import com.nekomaster1000.infernalexp.init.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TrappedGlowSandBlock extends GlowSandBlock {
    public TrappedGlowSandBlock(int dustColorIn, Properties properties) {
        super(dustColorIn, properties);
    }

    private static final int updateRadius = 4;

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) { }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        // Check if the world is the server
        if (!world.isRemote() && entity.getType() != ModEntityTypes.BLINDSIGHT.get()) {

            // Update all trapped blocks within a 3 block range
            for (int x = -updateRadius; x <= updateRadius; x++) {
                for (int y = -updateRadius; y <= updateRadius; y++) {
                    for (int z = -updateRadius; z <= updateRadius; z++) {
                        if (world.getBlockState(pos.add(x, y, z)) == ModBlocks.TRAPPED_GLOWDUST_SAND.get().getDefaultState()) {
                            ((TrappedGlowSandBlock) world.getBlockState(pos.add(x, y, z)).getBlock()).startFalling((ServerWorld) world, pos.add(x, y, z));
                        }
                    }
                }
            }
        }
    }

    public void startFalling(ServerWorld world, BlockPos pos) {
        if ((world.isAirBlock(pos.down()) || canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0)) {
            FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
            this.onStartFalling(fallingblockentity);
            world.addEntity(fallingblockentity);
        }
    }
}
