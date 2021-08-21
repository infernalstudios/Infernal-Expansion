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

package org.infernalstudios.infernalexp.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.infernalstudios.infernalexp.brewing.BrewingHelper;
import org.infernalstudios.infernalexp.mixin.common.IngredientAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class IEBrewingRecipes {

    private static final List<Triple<Potion, Supplier<Ingredient>, Potion>> brewingRecipes = new ArrayList<>();

    public static void registerBrewingRecipes() {
        // Register potions
        addBrewingRecipe(() -> getIngredient(IEItems.MOTH_DUST.get()), IEPotions.LUMINOUS.get(), IEPotions.LONG_LUMINOUS.get(), IEPotions.STRONG_LUMINOUS.get());
        addBrewingRecipe(() -> getIngredient(IEItems.ASCUS_BOMB.get()), IEPotions.INFECTION.get(), IEPotions.LONG_INFECTION.get(), IEPotions.STRONG_INFECTION.get());

        for (Triple<Potion, Supplier<Ingredient>, Potion> recipe : brewingRecipes) {
            BrewingHelper.registerBrewingRecipe(recipe.getLeft(), recipe.getMiddle().get(), recipe.getRight());
        }
    }

    private static void addBrewingRecipe(Supplier<Ingredient> reagent, Potion normalPotion, Potion longPotion, Potion strongPotion) {
        // Allow reagent to be used to make mundane potions
        add(Potions.WATER, reagent, Potions.MUNDANE);

        // Add base potion
        add(Potions.AWKWARD, reagent, normalPotion);

        // Add strong and long variants
        add(normalPotion, () -> getIngredient(Items.GLOWSTONE_DUST), strongPotion);
        add(normalPotion, () -> getIngredient(Items.REDSTONE), longPotion);
    }

    private static void add(Potion from, Supplier<Ingredient> reagent, Potion to) {
        brewingRecipes.add(new ImmutableTriple<>(from, reagent, to));
    }

    private static Ingredient getIngredient(Item ingredient) {
        return IngredientAccessor.createIngredient(Stream.of(new Ingredient.SingleItemList(new ItemStack(ingredient))));
    }

}
