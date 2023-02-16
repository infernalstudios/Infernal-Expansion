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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.infernalstudios.infernalexp.blockentities.GlowCampfireBlockEntity;
import org.infernalstudios.infernalexp.init.IEBlockEntityTypes;
import org.infernalstudios.infernalexp.init.IEParticleTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GlowCampfireBlock extends CampfireBlock {

    public GlowCampfireBlock(int fireDamage, BlockBehaviour.Properties properties) {
        super(false, fireDamage, properties);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GlowCampfireBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (world.isClientSide) {
            return state.getValue(LIT) ? createTickerHelper(type, IEBlockEntityTypes.GLOW_CAMPFIRE.get(), CampfireBlockEntity::particleTick) : null;
        } else {
            return state.getValue(LIT) ? createTickerHelper(type, IEBlockEntityTypes.GLOW_CAMPFIRE.get(), CampfireBlockEntity::cookTick) : createTickerHelper(type, IEBlockEntityTypes.GLOW_CAMPFIRE.get(), CampfireBlockEntity::cooldownTick);
        }
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Random random) {
        super.animateTick(state, level, pos, random);

        if (random.nextInt(2) == 1) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY() + 0.5D;
            double z = (double) pos.getZ() + 0.5D;

            level.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

}
