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
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.mixin.common.UpgradeRecipeBuilderAccessor;

import java.util.function.Consumer;

public class IEUpgradeRecipeBuilder extends UpgradeRecipeBuilder {

    public IEUpgradeRecipeBuilder(ItemLike result, ItemLike base, ItemLike addition, RecipeSerializer<?> serializer) {
        super(serializer, Ingredient.of(base), Ingredient.of(addition), result.asItem());
    }

    public static IEUpgradeRecipeBuilder smithing(ItemLike result, ItemLike base, ItemLike addition) {
        return new IEUpgradeRecipeBuilder(result, base, addition, RecipeSerializer.SMITHING);
    }

    public IEUpgradeRecipeBuilder unlockedBy(String p_126390_, CriterionTriggerInstance p_126391_) {
        ((UpgradeRecipeBuilderAccessor) this).getAdvancement().addCriterion(p_126390_, p_126391_);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        this.save(consumer, ((UpgradeRecipeBuilderAccessor) this).getResult().getRegistryName());
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation location) {
        UpgradeRecipeBuilderAccessor accessor = (UpgradeRecipeBuilderAccessor) this;
        ResourceLocation id = new ResourceLocation(location.getNamespace(), accessor.getSerializer().getRegistryName().getPath() + "/" + location.getPath());

        accessor.invokeEnsureValid(id);
        accessor.getAdvancement()
            .parent(new ResourceLocation("recipes/root"))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(RequirementsStrategy.OR);

        consumer.accept(new UpgradeRecipeBuilder.Result(
            id,
            accessor.getSerializer(),
            accessor.getBase(),
            accessor.getAddition(),
            accessor.getResult(),
            accessor.getAdvancement(),
            new ResourceLocation(location.getNamespace(), "recipes/" + InfernalExpansion.TAB.getRecipeFolderName() + "/" + location.getPath())
        ));
    }

}
