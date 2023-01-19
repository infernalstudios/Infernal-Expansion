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

import org.infernalstudios.infernalexp.init.IEParticleTypes;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class GlowTorchBlock extends TorchBlock {

    public GlowTorchBlock(Properties properties) {
        super(properties, null);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@NotNull BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        if (rand.nextInt(2) == 1) {
            worldIn.addParticle(IEParticleTypes.GLOWSTONE_SPARKLE.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
