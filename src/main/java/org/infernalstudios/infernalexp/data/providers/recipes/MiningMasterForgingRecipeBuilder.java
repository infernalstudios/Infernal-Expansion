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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MiningMasterForgingRecipeBuilder implements RecipeBuilder {

    public static final ResourceLocation TYPE = new ResourceLocation("miningmaster", "forging_recipe");

    private final Item result;
    private final Item catalyst;
    private final List<Item> gems = new ArrayList<>();
    private final List<LeveledEnchantment> enchantments = new ArrayList<>();

    protected MiningMasterForgingRecipeBuilder(Item result, Item catalyst) {
        this.result = result;
        this.catalyst = catalyst;
    }

    public static MiningMasterForgingRecipeBuilder forging(ItemLike result, ItemLike catalyst) {
        return new MiningMasterForgingRecipeBuilder(result.asItem(), catalyst.asItem());
    }

    public @NotNull MiningMasterForgingRecipeBuilder gem(ResourceLocation gem) {
        gems.add(new Item(new Item.Properties()).setRegistryName(gem));
        return this;
    }

    public @NotNull MiningMasterForgingRecipeBuilder gem(ItemLike gem) {
        gems.add(gem.asItem());
        return this;
    }

    public @NotNull MiningMasterForgingRecipeBuilder enchantment(Enchantment enchantment, int level) {
        enchantments.add(new LeveledEnchantment(enchantment, level));
        return this;
    }

    @Override
    public @NotNull MiningMasterForgingRecipeBuilder unlockedBy(@NotNull String name, @NotNull CriterionTriggerInstance criterion) {
        return this;
    }

    @Override
    public @NotNull MiningMasterForgingRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return result;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation location) {
        ResourceLocation id = new ResourceLocation(location.getNamespace(), TYPE.getPath().replace("_recipe", "") + "/" + location.getPath());

        ensureValid(id);
        consumer.accept(new Result(id, result, catalyst, gems, enchantments));
    }

    public static ResourceLocation getRecipeIdForConditionalRecipe(ItemLike item) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item.asItem());
        return new ResourceLocation(location.getNamespace(), TYPE.getPath().replace("_recipe", "") + "/" + location.getPath());
    }

    private void ensureValid(ResourceLocation location) {
        if (gems.isEmpty())
            throw new IllegalStateException("Recipe has no gems " + location);

        if (enchantments.isEmpty())
            throw new IllegalStateException("Recipe has no enchantments " + location);
    }

    private record LeveledEnchantment(Enchantment enchantment, int level) {}

    public record Result(ResourceLocation id, Item result, Item catalyst, List<Item> gems, List<LeveledEnchantment> enchantments) implements FinishedRecipe {

        @Override
        public @NotNull JsonObject serializeRecipe() {
            JsonObject json = new JsonObject();

            json.addProperty("type", TYPE.toString());
            serializeRecipeData(json);

            return json;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            JsonArray gemsArray = new JsonArray();
            for (Item gem : gems) {
                gemsArray.add(serializeItem(gem));
            }
            json.add("gems", gemsArray);

            json.add("catalyst", serializeItem(catalyst));
            json.add("result", serializeItem(result));

            JsonArray enchantmentsArray = new JsonArray();
            for (LeveledEnchantment enchantment : enchantments) {
                JsonObject enchantmentObject = new JsonObject();
                enchantmentObject.addProperty("enchantment", enchantment.enchantment().getRegistryName().toString());
                enchantmentObject.addProperty("lvl", enchantment.level());
                enchantmentsArray.add(enchantmentObject);
            }
            json.add("enchantments", enchantmentsArray);
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
            throw new UnsupportedOperationException("MiningMasterForgingRecipeBuilder$Result#getType should never be called. " +
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
