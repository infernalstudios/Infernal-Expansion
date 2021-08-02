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

import org.infernalstudios.infernalexp.entities.BlindsightEntity;
import org.infernalstudios.infernalexp.init.IEEffects;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DullthornsBlockBlock extends Block {

    public DullthornsBlockBlock(Properties properties) {
        super(properties);

    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isRemote()) {
            if (entityIn instanceof LivingEntity && entityIn.isAlive() && !(entityIn instanceof BlindsightEntity)) {
                LivingEntity livingEntity = (LivingEntity) entityIn;
                livingEntity.addPotionEffect(new EffectInstance(IEEffects.LUMINOUS.get(), 300, 0));
            }
            entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

}
