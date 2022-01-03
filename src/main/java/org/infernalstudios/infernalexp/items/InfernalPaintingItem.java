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

package org.infernalstudios.infernalexp.items;

import org.infernalstudios.infernalexp.entities.InfernalPaintingEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.HangingEntityItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class InfernalPaintingItem extends HangingEntityItem {

    public InfernalPaintingItem(Properties properties) {
        super(EntityType.PAINTING, properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos blockPos = context.getPos().offset(context.getFace());

        if (context.getPlayer() == null || !this.canPlace(context.getPlayer(), context.getFace(), context.getItem(), blockPos)) {
            return ActionResultType.FAIL;
        } else {
            InfernalPaintingEntity paintingEntity = new InfernalPaintingEntity(context.getWorld(), blockPos, context.getFace());

            CompoundNBT compoundNBT = context.getItem().getTag();
            if (compoundNBT != null) {
                EntityType.applyItemNBT(context.getWorld(), context.getPlayer(), paintingEntity, compoundNBT);
            }

            if (paintingEntity.onValidSurface()) {
                if (!context.getWorld().isRemote()) {
                    paintingEntity.playPlaceSound();
                    context.getWorld().addEntity(paintingEntity);
                }

                context.getItem().shrink(1);
                return ActionResultType.func_233537_a_(context.getWorld().isRemote());
            } else {
                return ActionResultType.CONSUME;
            }
        }
    }
}
