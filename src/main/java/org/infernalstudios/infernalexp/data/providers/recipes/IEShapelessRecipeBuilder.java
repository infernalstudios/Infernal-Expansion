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
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.mixin.common.ShapelessRecipeBuilderAccessor;

import java.util.function.Consumer;

public class IEShapelessRecipeBuilder extends ShapelessRecipeBuilder {

    public IEShapelessRecipeBuilder(ItemLike result, int amount) {
        super(result, amount);
    }

    public static IEShapelessRecipeBuilder shapeless(ItemLike itemLike) {
        return shapeless(itemLike, 1);
    }

    public static IEShapelessRecipeBuilder shapeless(ItemLike itemLike, int count) {
        return new IEShapelessRecipeBuilder(itemLike, count);
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation location) {
        ShapelessRecipeBuilderAccessor accessor = (ShapelessRecipeBuilderAccessor) this;
        ResourceLocation id = new ResourceLocation(location.getNamespace(), "crafting/shapeless/" + location.getPath());

        accessor.invokeEnsureValid(id);
        accessor.getAdvancement()
            .parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(RequirementsStrategy.OR);

        consumer.accept(new ShapelessRecipeBuilder.Result(
            id,
            getResult(),
            accessor.getCount(),
            "",
            accessor.getIngredients(),
            accessor.getAdvancement(),
            new ResourceLocation(location.getNamespace(), "recipes/" + InfernalExpansion.TAB.getRecipeFolderName() + "/" + location.getPath())
        ));
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ItemLike itemLike) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(itemLike.asItem());
        return new ResourceLocation(location.getNamespace(), "crafting/shapeless/" + location.getPath());
    }

}
