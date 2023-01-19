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

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.entities.AscusBombEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AscusBombItem extends Item {
    public AscusBombItem() {
        super(new Item.Properties().stacksTo(16).tab(InfernalExpansion.TAB));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemStack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide) {
            AscusBombEntity ascusBombEntity = new AscusBombEntity(worldIn, playerIn);
            ascusBombEntity.setItem(itemStack);
            ascusBombEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), -20, 0.5f, 1);
            worldIn.addFreshEntity(ascusBombEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));

        if (!playerIn.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, worldIn.isClientSide());
    }
}
