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
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.mixin.common.SingleItemRecipeBuilderAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IESingleItemRecipeBuilder extends SingleItemRecipeBuilder {

    public IESingleItemRecipeBuilder(ItemLike result, int count, ItemLike ingredient, RecipeSerializer<?> serializer) {
        super(serializer, Ingredient.of(ingredient), result, count);
    }

    public static IESingleItemRecipeBuilder stonecutting(ItemLike result, int count, ItemLike ingredient) {
        return new IESingleItemRecipeBuilder(result, count, ingredient, RecipeSerializer.STONECUTTER);
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation location) {
        SingleItemRecipeBuilderAccessor accessor = (SingleItemRecipeBuilderAccessor) this;
        ResourceLocation id = new ResourceLocation(location.getNamespace(), accessor.getSerializer().getRegistryName().getPath() + "/" + location.getPath());

        accessor.invokeEnsureValid(id);
        accessor.getAdvancement()
            .parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(RequirementsStrategy.OR);

        consumer.accept(new SingleItemRecipeBuilder.Result(
            id,
            accessor.getSerializer(),
            "",
            accessor.getIngredient(),
            getResult(),
            accessor.getCount(),
            accessor.getAdvancement(),
            new ResourceLocation(location.getNamespace(), "recipes/" + InfernalExpansion.TAB.getRecipeFolderName() + "/" + location.getPath())
        ));
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ResourceLocation location, RecipeSerializer<?> serializer) {
        return new ResourceLocation(location.getNamespace(), serializer.getRegistryName().getPath() + "/" + location.getPath());
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ItemLike itemLike, RecipeSerializer<?> serializer) {
        return getRecipeIdForConditionalRecipe(ForgeRegistries.ITEMS.getKey(itemLike.asItem()), serializer);
    }

}
