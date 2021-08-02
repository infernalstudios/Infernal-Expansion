/*
 * Copyright 2021 Infernal Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.infernalstudios.infernalexp.blocks;

import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class TrappedGlowSandBlock extends GlowSandBlock {
    public TrappedGlowSandBlock(int dustColorIn, Properties properties) {
        super(dustColorIn, properties);
    }

    private static final int updateRadius = 4;
    private int ticksToFall = 10;

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        // Check if the world is the server
        if (!world.isRemote() && entity.getType() != IEEntityTypes.BLINDSIGHT.get()) {

            // Update all trapped blocks within a 3 block range
            for (int x = -updateRadius; x <= updateRadius; x++) {
                for (int y = -updateRadius; y <= updateRadius; y++) {
                    for (int z = -updateRadius; z <= updateRadius; z++) {
                        if (world.getBlockState(pos.add(x, y, z)) == IEBlocks.TRAPPED_GLOWDUST_SAND.get().getDefaultState()) {
                            ((TrappedGlowSandBlock) world.getBlockState(pos.add(x, y, z)).getBlock()).startFalling((ServerWorld) world, pos.add(x, y, z));
                        }
                    }
                }
            }
        }
    }

    public void startFalling(ServerWorld world, BlockPos pos) {
        if ((world.isAirBlock(pos.down()) || canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0)) {

            if (this.ticksToFall == 0) {
                world.playSound(null, pos, SoundEvents.BLOCK_SAND_PLACE, SoundCategory.BLOCKS, 1.5F, world.rand.nextFloat() * 0.1F + 0.9F);

                FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
                this.onStartFalling(fallingblockentity);
                world.addEntity(fallingblockentity);

                this.ticksToFall = 10;
            } else {
                world.spawnParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), pos.getX(), pos.getY(), pos.getZ(), 1, 0.0, 1.0, 0.0, 0.0);
                this.ticksToFall--;
            }
        }
    }
}
