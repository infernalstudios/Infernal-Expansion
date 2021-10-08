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

package org.infernalstudios.infernalexp.items;

import org.infernalstudios.infernalexp.entities.InfernalPaintingEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

import net.minecraft.world.item.Item.Properties;

public class InfernalPaintingItem extends HangingEntityItem {

    public InfernalPaintingItem(Properties properties) {
        super(EntityType.PAINTING, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos().relative(context.getClickedFace());

        if (context.getPlayer() == null || !this.mayPlace(context.getPlayer(), context.getClickedFace(), context.getItemInHand(), blockPos)) {
            return InteractionResult.FAIL;
        } else {
            InfernalPaintingEntity paintingEntity = new InfernalPaintingEntity(context.getLevel(), blockPos, context.getClickedFace());

            CompoundTag compoundNBT = context.getItemInHand().getTag();
            if (compoundNBT != null) {
                EntityType.updateCustomEntityTag(context.getLevel(), context.getPlayer(), paintingEntity, compoundNBT);
            }

            if (paintingEntity.survives()) {
                if (!context.getLevel().isClientSide()) {
                    paintingEntity.playPlacementSound();
                    context.getLevel().addFreshEntity(paintingEntity);
                }

                context.getItemInHand().shrink(1);
                return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }
}
