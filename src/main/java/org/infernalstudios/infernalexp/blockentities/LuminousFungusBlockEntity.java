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

package org.infernalstudios.infernalexp.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.infernalstudios.infernalexp.blocks.LuminousFungusBlock;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.init.IEBlockEntityTypes;

import java.util.List;

public class LuminousFungusBlockEntity extends BlockEntity {

    public int lightTime = 0;

    public LuminousFungusBlockEntity(BlockPos pos, BlockState state) {
        super(IEBlockEntityTypes.LUMINOUS_FUNGUS.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, LuminousFungusBlockEntity blockEntity) {
        List<Entity> nearbyEntities = world.getEntitiesOfClass(Entity.class,
            new AABB(pos).inflate(InfernalExpansionConfig.Miscellaneous.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.getDouble()));
        Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        nearbyEntities.removeIf((entity) -> {
            Vec3 entityPos = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
            return world.clip(new ClipContext(blockPos, entityPos, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, entity)).getType() != HitResult.Type.MISS;
        });
        boolean shouldLight = false;
        for (Entity entity : nearbyEntities) {
            if (entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) {
                double velX = Math.abs(entity.getX() - entity.xOld);
                double velY = Math.abs(entity.getY() - entity.yOld);
                double velZ = Math.abs(entity.getZ() - entity.zOld);
                if (velX >= (double) 0.003F || velY >= (double) 0.003F || velZ >= (double) 0.003F) {
                    shouldLight = true;
                    break;
                }
            } else if (
                entity.walkDist - entity.walkDistO > 0 ||
                    entity.getDeltaMovement().length() > 0.1D
            ) {
                shouldLight = true;
                break;
            }
        }
        if (blockEntity.lightTime == 0) {
            world.setBlockAndUpdate(blockEntity.worldPosition, state.setValue(LuminousFungusBlock.LIT, shouldLight));
        } else {
            blockEntity.lightTime--;
        }
        if (shouldLight) blockEntity.lightTime = 60;
    }
}
