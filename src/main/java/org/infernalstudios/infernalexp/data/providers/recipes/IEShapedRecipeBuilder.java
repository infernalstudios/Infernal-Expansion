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
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.mixin.common.ShapedRecipeBuilderAccessor;

import java.util.function.Consumer;

public class IEShapedRecipeBuilder extends ShapedRecipeBuilder {

    public IEShapedRecipeBuilder(ItemLike result, int amount) {
        super(result, amount);
    }

    public static IEShapedRecipeBuilder shaped(ItemLike itemLike) {
        return shaped(itemLike, 1);
    }

    public static IEShapedRecipeBuilder shaped(ItemLike itemLike, int count) {
        return new IEShapedRecipeBuilder(itemLike, count);
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation location) {
        ShapedRecipeBuilderAccessor accessor = (ShapedRecipeBuilderAccessor) this;
        ResourceLocation id = new ResourceLocation(location.getNamespace(), "crafting/shaped/" + location.getPath());

        accessor.invokeEnsureValid(id);
        accessor.getAdvancement()
            .parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(RequirementsStrategy.OR);

        consumer.accept(new ShapedRecipeBuilder.Result(
            id,
            getResult(),
            accessor.getCount(),
            "",
            accessor.getRows(),
            accessor.getKey(),
            accessor.getAdvancement(),
            new ResourceLocation(location.getNamespace(), "recipes/" + InfernalExpansion.TAB.getRecipeFolderName() + "/" + location.getPath())
        ));
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ItemLike itemLike) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(itemLike.asItem());
        return new ResourceLocation(location.getNamespace(), "crafting/shaped/" + location.getPath());
    }

}
