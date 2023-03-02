/*
 * Copyright 2023 Infernal Studios
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

package org.infernalstudios.infernalexp.data.providers.recipes;

import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.mixin.common.SimpleCookingRecipeBuilderAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IECookingRecipeBuilder extends SimpleCookingRecipeBuilder {

    protected IECookingRecipeBuilder(ItemLike result, Ingredient ingredient, float experience, int cookingTime, SimpleCookingSerializer<?> serializer) {
        super(result, ingredient, experience, cookingTime, serializer);
    }

    public static IECookingRecipeBuilder cooking(ItemLike result, ItemLike ingredient, float experience, int cookingTime, SimpleCookingSerializer<?> serializer) {
        return new IECookingRecipeBuilder(result, Ingredient.of(ingredient), experience, cookingTime, serializer);
    }

    public static IECookingRecipeBuilder campfireCooking(ItemLike result, ItemLike ingredient, float experience, int cookingTime) {
        return cooking(result, ingredient, experience, cookingTime, SimpleCookingSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static IECookingRecipeBuilder blasting(ItemLike result, ItemLike ingredient, float experience, int cookingTime) {
        return cooking(result, ingredient, experience, cookingTime, SimpleCookingSerializer.BLASTING_RECIPE);
    }

    public static IECookingRecipeBuilder smelting(ItemLike result, ItemLike ingredient, float experience, int cookingTime) {
        return cooking(result, ingredient, experience, cookingTime, SimpleCookingSerializer.SMELTING_RECIPE);
    }

    public static IECookingRecipeBuilder smoking(ItemLike result, ItemLike ingredient, float experience, int cookingTime) {
        return cooking(result, ingredient, experience, cookingTime, SimpleCookingSerializer.SMOKING_RECIPE);
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation location) {
        SimpleCookingRecipeBuilderAccessor accessor = (SimpleCookingRecipeBuilderAccessor) this;
        ResourceLocation id = new ResourceLocation(location.getNamespace(), accessor.getSerializer().getRegistryName().getPath() + "/" + location.getPath());

        accessor.invokeEnsureValid(id);
        accessor.getAdvancement()
            .parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(RequirementsStrategy.OR);

        consumer.accept(new SimpleCookingRecipeBuilder.Result(
            id,
            "",
            accessor.getIngredient(),
            getResult(),
            accessor.getExperience(),
            accessor.getCookingTime(),
            accessor.getAdvancement(),
            new ResourceLocation(location.getNamespace(), "recipes/" + InfernalExpansion.TAB.getRecipeFolderName() + "/" + location.getPath()),
            accessor.getSerializer()
        ));
    }

}
