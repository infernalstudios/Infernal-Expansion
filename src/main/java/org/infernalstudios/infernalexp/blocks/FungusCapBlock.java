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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class FungusCapBlock extends Block {

    public FungusCapBlock(Properties properties) {
        super(properties);
    }

    public void fallOn(Level worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            entityIn.causeFallDamage(fallDistance, 0.75F, DamageSource.FALL);
        } else {
            entityIn.causeFallDamage(fallDistance, 0.1F, DamageSource.FALL);
        }
    }

    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(worldIn, entityIn);
        } else {
            bounceEntity(entityIn);
        }
    }

    private void bounceEntity(Entity entity) {
        Vec3 vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 0.5D : 0.3D;
            entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }
    }

}
