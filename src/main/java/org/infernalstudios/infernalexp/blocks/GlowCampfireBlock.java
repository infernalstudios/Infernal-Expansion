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
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
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

public class GlowCampfireBlock extends CampfireBlock {
    public GlowCampfireBlock(int fireDamage, BlockBehaviour.Properties properties) {
        super(false, fireDamage, properties);
    }

    @Deprecated(forRemoval = true, since = "2.5.4")
    public GlowCampfireBlock(boolean spawnParticles, int fireDamage, BlockBehaviour.Properties properties) {
        this(fireDamage, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GlowCampfireBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (world.isClientSide) {
            return state.getValue(LIT) ? createTickerHelper(type, IEBlockEntityTypes.GLOW_CAMPFIRE.get(), GlowCampfireBlock::particleTick) : null;
        } else {
            return state.getValue(LIT) ? createTickerHelper(type, IEBlockEntityTypes.GLOW_CAMPFIRE.get(), CampfireBlockEntity::cookTick) : createTickerHelper(type, IEBlockEntityTypes.GLOW_CAMPFIRE.get(), CampfireBlockEntity::cooldownTick);
        }
    }

    public static void particleTick(Level p_155319_, BlockPos p_155320_, BlockState p_155321_, CampfireBlockEntity p_155322_) {
        RandomSource randomsource = p_155319_.random;
        if (randomsource.nextFloat() < 0.11F) {
            for(int i = 0; i < randomsource.nextInt(2) + 2; ++i) {
                makeParticles(p_155319_, p_155320_);
            }
        }

//        int l = p_155321_.getValue(CampfireBlock.FACING).get2DDataValue();
//
//        for(int j = 0; j < p_155322_.items.size(); ++j) {
//            if (!p_155322_.items.get(j).isEmpty() && randomsource.nextFloat() < 0.2F) {
//                Direction direction = Direction.from2DDataValue(Math.floorMod(j + l, 4));
//                float f = 0.3125F;
//                double d0 = (double)p_155320_.getX() + 0.5D - (double)((float)direction.getStepX() * 0.3125F) + (double)((float)direction.getClockWise().getStepX() * 0.3125F);
//                double d1 = (double)p_155320_.getY() + 0.5D;
//                double d2 = (double)p_155320_.getZ() + 0.5D - (double)((float)direction.getStepZ() * 0.3125F) + (double)((float)direction.getClockWise().getStepZ() * 0.3125F);
//
//                for(int k = 0; k < 4; ++k) {
//                    p_155319_.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
//                }
//            }
//        }

    }

    public static void makeParticles(Level level, BlockPos pos) {
        RandomSource randomsource = level.getRandom();
//        SimpleParticleType simpleparticletype = p_51254_ ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
//        p_51252_.addAlwaysVisibleParticle(simpleparticletype, true, (double)p_51253_.getX() + 0.5D + randomsource.nextDouble() / 3.0D * (double)(randomsource.nextBoolean() ? 1 : -1), (double)p_51253_.getY() + randomsource.nextDouble() + randomsource.nextDouble(), (double)p_51253_.getZ() + 0.5D + randomsource.nextDouble() / 3.0D * (double)(randomsource.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (randomsource.nextBoolean()) {
            level.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), (double)pos.getX() + 0.5D + randomsource.nextDouble() / 4.0D * (double)(randomsource.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.5D + randomsource.nextDouble() / 4.0D * (double)(randomsource.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }
}
