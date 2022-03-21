/*
 * Copyright 2022 Infernal Studios
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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEEntityTypes;
import org.infernalstudios.infernalexp.init.IEParticleTypes;

import java.util.Random;

public class TrappedGlowSandBlock extends GlowSandBlock {
    public TrappedGlowSandBlock(int dustColorIn, Properties properties) {
        super(dustColorIn, properties);
    }

    private static final int updateRadius = 4;
    private int ticksToFall = 10;

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        // Check if the world is the server
        if (!world.isClientSide() && entity.getType() != IEEntityTypes.BLINDSIGHT.get()) {

            // Update all trapped blocks within a 3 block range
            for (int x = -updateRadius; x <= updateRadius; x++) {
                for (int y = -updateRadius; y <= updateRadius; y++) {
                    for (int z = -updateRadius; z <= updateRadius; z++) {
                        if (world.getBlockState(pos.offset(x, y, z)) == IEBlocks.TRAPPED_GLOWDUST_SAND.get().defaultBlockState()) {
                            ((TrappedGlowSandBlock) world.getBlockState(pos.offset(x, y, z)).getBlock()).startFalling((ServerLevel) world, pos.offset(x, y, z));
                        }
                    }
                }
            }
        }
    }

    public void startFalling(ServerLevel world, BlockPos pos) {
        if ((world.isEmptyBlock(pos.below()) || isFree(world.getBlockState(pos.below())) && pos.getY() >= 0)) {

            if (this.ticksToFall == 0) {
                world.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.5F, world.random.nextFloat() * 0.1F + 0.9F);

                FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(world, pos, world.getBlockState(pos));
                this.falling(fallingblockentity);
                world.addFreshEntity(fallingblockentity);

                this.ticksToFall = 10;
            } else {
                world.sendParticles(IEParticleTypes.GLOWSTONE_SPARKLE.get(), pos.getX(), pos.getY(), pos.getZ(), 1, 0.0, 1.0, 0.0, 0.0);
                this.ticksToFall--;
            }
        }
    }
}
