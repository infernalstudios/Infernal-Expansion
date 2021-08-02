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

package com.nekomaster1000.infernalexp.brewing;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class IEBrewingRecipe implements IBrewingRecipe {
    private final ItemStack input;
    private final ItemStack ingredient;
    private final ItemStack output;


    public IEBrewingRecipe(ItemStack input, ItemStack ingredient, ItemStack output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return input.isItemEqual(this.input) &&
            PotionUtils.getPotionFromItem(input).equals(PotionUtils.getPotionFromItem(this.input));
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.isItemEqual(this.ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return this.isInput(input) && this.isIngredient(ingredient) ?
            this.output.copy() :
            ItemStack.EMPTY;
    }
}
