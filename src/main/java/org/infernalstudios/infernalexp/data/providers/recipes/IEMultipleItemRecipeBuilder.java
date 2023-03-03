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
import java.util.Optional;
import java.util.function.Consumer;

public class IEMultipleItemRecipeBuilder implements RecipeBuilder {

    public static final ResourceLocation CRUSHING = new ResourceLocation("create", "crushing");
    public static final ResourceLocation PRESSING = new ResourceLocation("create", "pressing");

    private final List<ResultItem> results = new ArrayList<>();
    private final List<Item> ingredients = new ArrayList<>();
    private final int processingTime;
    private final boolean useIngredientAsName;
    private final ResourceLocation type;

    protected IEMultipleItemRecipeBuilder(int processingTime, boolean useIngredientAsName, ResourceLocation type) {
        this.type = type;
        this.useIngredientAsName = useIngredientAsName;
        this.processingTime = processingTime;
    }

    public static IEMultipleItemRecipeBuilder crushing(int processingTime) {
        return new IEMultipleItemRecipeBuilder(processingTime, true, CRUSHING);
    }

    public static IEMultipleItemRecipeBuilder pressing() {
        return new IEMultipleItemRecipeBuilder(-1, false, PRESSING);
    }

    public @NotNull IEMultipleItemRecipeBuilder ingredient(@NotNull ResourceLocation item) {
        ingredients.add(new Item(new Item.Properties()).setRegistryName(item));
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder ingredient(@NotNull ItemLike item) {
        ingredients.add(item.asItem());
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder result(@NotNull ResourceLocation item) {
        results.add(new ResultItem(new Item(new Item.Properties()).setRegistryName(item), Optional.empty(), Optional.empty()));
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder result(@NotNull ItemLike item) {
        results.add(new ResultItem(item.asItem(), Optional.empty(), Optional.empty()));
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder result(@NotNull ResourceLocation item, float chance) {
        results.add(new ResultItem(new Item(new Item.Properties()).setRegistryName(item), Optional.empty(), Optional.of(chance)));
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder result(@NotNull ItemLike item, float chance) {
        results.add(new ResultItem(item.asItem(), Optional.empty(), Optional.of(chance)));
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder result(@NotNull ResourceLocation item, int count) {
        results.add(new ResultItem(new Item(new Item.Properties()).setRegistryName(item), Optional.of(count), Optional.empty()));
        return this;
    }

    public @NotNull IEMultipleItemRecipeBuilder result(@NotNull ItemLike item, int count) {
        results.add(new ResultItem(item.asItem(), Optional.of(count), Optional.empty()));
        return this;
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance criterion) {
        return this;
    }

    @Override
    public @NotNull IEMultipleItemRecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        if (results.isEmpty())
            throw new IllegalStateException("No results for recipe");

        return results.get(0).item();
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer) {
        if (useIngredientAsName) {
            if (ingredients.isEmpty())
                throw new IllegalStateException("No ingredients for recipe to derive name from");

            save(consumer, RecipeBuilder.getDefaultRecipeId(ingredients.get(0)));

        } else {
            if (results.isEmpty())
                throw new IllegalStateException("No results for recipe to derive name from");

            save(consumer, RecipeBuilder.getDefaultRecipeId(results.get(0).item()));
        }
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation location) {
        ResourceLocation id = new ResourceLocation(location.getNamespace(), type.getPath() + "/" + location.getPath());

        ensureValid(id);
        consumer.accept(new IEMultipleItemRecipeBuilder.Result(id, results, ingredients, processingTime, type));
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ItemLike itemLike, ResourceLocation type) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(itemLike.asItem());
        return new ResourceLocation(location.getNamespace(), type.getPath() + "/" + location.getPath());
    }

    private void ensureValid(ResourceLocation location) {
        if (results.isEmpty())
            throw new IllegalStateException("Recipe has no results " + location);
        if (ingredients.isEmpty())
            throw new IllegalStateException("Recipe has no ingredients " + location);
    }

    private record ResultItem(Item item, Optional<Integer> count, Optional<Float> chance) {}

    public record Result(ResourceLocation id, List<ResultItem> results, List<Item> ingredients, int processingTime, ResourceLocation type) implements FinishedRecipe {

        @Override
        public @NotNull JsonObject serializeRecipe() {
            JsonObject json = new JsonObject();

            json.addProperty("type", type.toString());
            serializeRecipeData(json);

            return json;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            JsonArray ingredientsArray = new JsonArray();
            for (Item ingredient : ingredients) {
                JsonObject ingredientObject = new JsonObject();
                ingredientObject.addProperty("item", ingredient.getRegistryName().toString());
                ingredientsArray.add(ingredientObject);
            }
            json.add("ingredients", ingredientsArray);

            JsonArray resultsArray = new JsonArray();
            for (ResultItem result : this.results) {
                JsonObject resultObject = new JsonObject();
                resultObject.addProperty("item", result.item().getRegistryName().toString());
                result.count().ifPresent(count -> resultObject.addProperty("count", count));
                result.chance().ifPresent(chance -> resultObject.addProperty("chance", chance));
                resultsArray.add(resultObject);
            }
            json.add("results", resultsArray);

            if (processingTime > 0)
                json.addProperty("processing_time", processingTime);
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
            throw new UnsupportedOperationException("IEMultipleItemRecipeBuilder$Result#getType should never be called. " +
                "Most likely the underlying implementation of FinishedRecipe has changed, or another mod is doing something very strange.");
        }

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
