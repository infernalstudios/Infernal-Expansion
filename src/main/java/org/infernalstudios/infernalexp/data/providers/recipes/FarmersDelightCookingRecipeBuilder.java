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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FarmersDelightCookingRecipeBuilder implements RecipeBuilder {

    public static final ResourceLocation TYPE = new ResourceLocation("farmersdelight", "cooking");

    private final Item result;
    private final List<Item> ingredients = new ArrayList<>();
    private final Item container;
    private final float experience;
    private final int cookingTime;

    protected FarmersDelightCookingRecipeBuilder(Item result, Item container, float experience, int cookingTime) {
        this.result = result;
        this.container = container;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    public static FarmersDelightCookingRecipeBuilder cooking(ItemLike result, ItemLike container, float experience, int cookingTime) {
        return new FarmersDelightCookingRecipeBuilder(result.asItem(), container.asItem(), experience, cookingTime);
    }

    public @NotNull FarmersDelightCookingRecipeBuilder ingredient(ItemLike ingredient) {
        ingredients.add(ingredient.asItem());
        return this;
    }

    @Override
    public @NotNull FarmersDelightCookingRecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance criterion) {
        return this;
    }

    @Override
    public @NotNull FarmersDelightCookingRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation location) {
        ResourceLocation id = new ResourceLocation(location.getNamespace(), TYPE.getPath() + "/" + location.getPath());

        ensureValid(id);
        consumer.accept(new FarmersDelightCookingRecipeBuilder.Result(id, result, ingredients, container, experience, cookingTime));
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ItemLike itemLike) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(itemLike.asItem());
        return new ResourceLocation(location.getNamespace(), TYPE.getPath() + "/" + location.getPath());
    }

    private void ensureValid(@NotNull ResourceLocation location) {
        if (ingredients.isEmpty())
            throw new IllegalStateException("No ingredients for recipe " + location);
    }

    public record Result(ResourceLocation id, Item result, List<Item> ingredients, Item container, float experience, int cookingTime) implements FinishedRecipe {

        @Override
        public @NotNull JsonObject serializeRecipe() {
            JsonObject json = new JsonObject();

            json.addProperty("type", TYPE.toString());
            serializeRecipeData(json);

            return json;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            JsonArray ingredientsArray = new JsonArray();
            for (Item ingredient : ingredients) {
                ingredientsArray.add(serializeItem(ingredient));
            }
            json.add("ingredients", ingredientsArray);

            json.add("result", serializeItem(result));
            json.add("container", serializeItem(container));
            json.addProperty("experience", experience);
            json.addProperty("cooking_time", cookingTime);
        }

        private JsonObject serializeItem(Item item) {
            JsonObject json = new JsonObject();
            json.addProperty("item", item.getRegistryName().toString());
            return json;
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        /**
         * This method is only ever used in {@link net.minecraft.data.recipes.FinishedRecipe#serializeRecipe()} which we override.
         */
        @Override
        public @NotNull RecipeSerializer<?> getType() {
            throw new UnsupportedOperationException("FarmersDelightCookingRecipeBuilder$Result#getType should never be called. " +
                "Most likely the underlying implementation of FinishedRecipe has changed, or another mod is doing something very strange.");
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }

}
