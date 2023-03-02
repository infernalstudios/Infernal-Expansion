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

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEItemTags;
import org.infernalstudios.infernalexp.init.IEItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IERecipeProvider extends RecipeProvider {

    public IERecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Basalt
        slab(IEBlocks.BASALT_SLAB.get(), Blocks.BASALT, consumer);
        verticalSlab(IEBlocks.BASALT_VERTICAL_SLAB.get(), IEBlocks.BASALT_SLAB.get(), Blocks.BASALT, consumer);
        stairs(IEBlocks.BASALT_STAIRS.get(), Blocks.BASALT, consumer);
        wall(IEBlocks.BASALT_WALL.get(), Blocks.BASALT, consumer);
        button(IEBlocks.BASALT_BUTTON.get(), Blocks.BASALT, consumer);

        // Polished Basalt
        slab(IEBlocks.POLISHED_BASALT_SLAB.get(), Blocks.POLISHED_BASALT, consumer);
        verticalSlab(IEBlocks.POLISHED_BASALT_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_SLAB.get(), Blocks.POLISHED_BASALT, consumer);
        smallRow(IEBlocks.POLISHED_BASALT_PRESSURE_PLATE.get(), 1, Blocks.POLISHED_BASALT, consumer);

        // Polished Basalt Tiles
        IEShapedRecipeBuilder.shaped(IEBlocks.POLISHED_BASALT_TILES.get(), 5)
            .define('#', IEBlocks.BASALT_BRICKS.get())
            .pattern(" # ").pattern("###").pattern(" # ")
            .unlockedBy(getHasName(IEBlocks.BASALT_BRICKS.get()), has(IEBlocks.BASALT_BRICKS.get()))
            .save(consumer);
        stonecutting(IEBlocks.POLISHED_BASALT_TILES.get(), 1, IEBlocks.BASALT_BRICKS.get(), consumer);
        slab(IEBlocks.POLISHED_BASALT_TILES_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES.get(), consumer);
        verticalSlab(IEBlocks.POLISHED_BASALT_TILES_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES.get(), consumer);

        // Basalt Bricks
        smallSquare(IEBlocks.BASALT_BRICKS.get(), 4, Blocks.POLISHED_BASALT, consumer);
        stonecutting(IEBlocks.BASALT_BRICKS.get(), 1, Blocks.POLISHED_BASALT, consumer);
        slab(IEBlocks.BASALT_BRICK_SLAB.get(), IEBlocks.BASALT_BRICKS.get(), consumer);
        verticalSlab(IEBlocks.BASALT_BRICK_VERTICAL_SLAB.get(), IEBlocks.BASALT_BRICK_SLAB.get(), IEBlocks.BASALT_BRICKS.get(), consumer);
        stairs(IEBlocks.BASALT_BRICK_STAIRS.get(), IEBlocks.BASALT_BRICKS.get(), consumer);
        wall(IEBlocks.BASALT_BRICK_WALL.get(), IEBlocks.BASALT_BRICKS.get(), consumer);
        chiseled(IEBlocks.CHISELED_BASALT_BRICKS.get(), IEBlocks.BASALT_BRICK_SLAB.get(), IEBlocks.BASALT_BRICKS.get(), consumer);
        specialChiseled(IEBlocks.MAGMATIC_CHISELED_BASALT_BRICKS.get(), IEBlocks.BASALT_BRICK_SLAB.get(), IEBlocks.BASALTIC_MAGMA.get(), consumer);
        smelting(IEBlocks.CRACKED_BASALT_BRICKS.get(), IEBlocks.BASALT_BRICKS.get(), 0.1F, 200, consumer);

        // Cobbled Basalt
        IEShapelessRecipeBuilder.shapeless(IEBlocks.BASALT_COBBLED.get(), 2)
            .requires(Blocks.BASALT, 2)
            .unlockedBy(getHasName(Blocks.BASALT), has(Blocks.BASALT))
            .save(consumer);
        slab(IEBlocks.BASALT_COBBLED_SLAB.get(), IEBlocks.BASALT_COBBLED.get(), consumer);
        verticalSlab(IEBlocks.BASALT_COBBLED_VERTICAL_SLAB.get(), IEBlocks.BASALT_COBBLED_SLAB.get(), IEBlocks.BASALT_COBBLED.get(), consumer);

        // Smooth Glowstone
        smelting(IEBlocks.SMOOTH_GLOWSTONE.get(), Blocks.GLOWSTONE, 0.1F, 200, consumer);
        slab(IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), IEBlocks.SMOOTH_GLOWSTONE.get(), consumer);
        verticalSlab(IEBlocks.SMOOTH_GLOWSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), IEBlocks.SMOOTH_GLOWSTONE.get(), consumer);
        stairs(IEBlocks.SMOOTH_GLOWSTONE_STAIRS.get(), IEBlocks.SMOOTH_GLOWSTONE.get(), consumer);
        button(IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(), IEBlocks.SMOOTH_GLOWSTONE.get(), consumer);
        IEShapedRecipeBuilder.shaped(IEBlocks.SMOOTH_GLOWSTONE_PRESSURE_PLATE.get())
            .define('#', IEBlocks.SMOOTH_GLOWSTONE.get()).define('X', IEBlocks.SMOOTH_DULLSTONE.get())
            .pattern("#X")
            .unlockedBy(getHasName(IEBlocks.SMOOTH_GLOWSTONE.get()), has(IEBlocks.SMOOTH_GLOWSTONE.get()))
            .save(consumer);

        // Glowstone Bricks
        smallSquare(IEBlocks.GLOWSTONE_BRICKS.get(), 4, IEBlocks.SMOOTH_GLOWSTONE.get(), consumer);
        stonecutting(IEBlocks.GLOWSTONE_BRICKS.get(), 1, Blocks.GLOWSTONE, consumer);
        slab(IEBlocks.GLOWSTONE_BRICK_SLAB.get(), IEBlocks.GLOWSTONE_BRICKS.get(), consumer);
        verticalSlab(IEBlocks.GLOWSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.GLOWSTONE_BRICK_SLAB.get(), IEBlocks.GLOWSTONE_BRICKS.get(), consumer);
        stairs(IEBlocks.GLOWSTONE_BRICK_STAIRS.get(), IEBlocks.GLOWSTONE_BRICKS.get(), consumer);
        wall(IEBlocks.GLOWSTONE_BRICK_WALL.get(), IEBlocks.GLOWSTONE_BRICKS.get(), consumer);
        chiseled(IEBlocks.CHISELED_GLOWSTONE_BRICKS.get(), IEBlocks.GLOWSTONE_BRICK_SLAB.get(), IEBlocks.GLOWSTONE_BRICKS.get(), consumer);
        smelting(IEBlocks.CRACKED_GLOWSTONE_BRICKS.get(), IEBlocks.GLOWSTONE_BRICKS.get(), 0.1F, 200, consumer);

        // Smooth Dimstone
        smelting(IEBlocks.SMOOTH_DIMSTONE.get(), IEBlocks.DIMSTONE.get(), 0.1F, 200, consumer);
        slab(IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE.get(), consumer);
        verticalSlab(IEBlocks.SMOOTH_DIMSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE.get(), consumer);
        stairs(IEBlocks.SMOOTH_DIMSTONE_STAIRS.get(), IEBlocks.SMOOTH_DIMSTONE.get(), consumer);
        button(IEBlocks.SMOOTH_DIMSTONE_BUTTON.get(), IEBlocks.SMOOTH_DIMSTONE.get(), consumer);

        // Dimstone Bricks
        IEShapedRecipeBuilder.shaped(IEBlocks.DIMSTONE.get())
            .define('X', Items.GLOWSTONE_DUST).define('O', IEItems.DULLROCKS.get())
            .pattern("XO").pattern("OX")
            .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
            .save(consumer);
        smallSquare(IEBlocks.DIMSTONE_BRICKS.get(), 4, IEBlocks.SMOOTH_DIMSTONE.get(), consumer);
        stonecutting(IEBlocks.DIMSTONE_BRICKS.get(), 1, IEBlocks.DIMSTONE.get(), consumer);
        slab(IEBlocks.DIMSTONE_BRICK_SLAB.get(), IEBlocks.DIMSTONE_BRICKS.get(), consumer);
        verticalSlab(IEBlocks.DIMSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DIMSTONE_BRICK_SLAB.get(), IEBlocks.DIMSTONE_BRICKS.get(), consumer);
        stairs(IEBlocks.DIMSTONE_BRICK_STAIRS.get(), IEBlocks.DIMSTONE_BRICKS.get(), consumer);
        wall(IEBlocks.DIMSTONE_BRICK_WALL.get(), IEBlocks.DIMSTONE_BRICKS.get(), consumer);
        chiseled(IEBlocks.CHISELED_DIMSTONE_BRICKS.get(), IEBlocks.DIMSTONE_BRICK_SLAB.get(), IEBlocks.DIMSTONE_BRICKS.get(), consumer);
        smelting(IEBlocks.CRACKED_DIMSTONE_BRICKS.get(), IEBlocks.DIMSTONE_BRICKS.get(), 0.1F, 200, consumer);

        // Smooth Dullstone
        smelting(IEBlocks.SMOOTH_DULLSTONE.get(), IEBlocks.DULLSTONE.get(), 0.1F, 200, consumer);
        slab(IEBlocks.SMOOTH_DULLSTONE_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE.get(), consumer);
        verticalSlab(IEBlocks.SMOOTH_DULLSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE.get(), consumer);
        stairs(IEBlocks.SMOOTH_DULLSTONE_STAIRS.get(), IEBlocks.SMOOTH_DULLSTONE.get(), consumer);
        button(IEBlocks.SMOOTH_DULLSTONE_BUTTON.get(), IEBlocks.SMOOTH_DULLSTONE.get(), consumer);

        // Dullstone Bricks
        smallSquare(IEBlocks.DULLSTONE.get(), 4, IEItems.DULLROCKS.get(), consumer);
        smallSquare(IEBlocks.DULLSTONE_BRICKS.get(), 4, IEBlocks.DULLSTONE.get(), consumer);
        stonecutting(IEBlocks.DULLSTONE_BRICKS.get(), 1, IEBlocks.DULLSTONE.get(), consumer);
        slab(IEBlocks.DULLSTONE_BRICK_SLAB.get(), IEBlocks.DULLSTONE_BRICKS.get(), consumer);
        verticalSlab(IEBlocks.DULLSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DULLSTONE_BRICK_SLAB.get(), IEBlocks.DULLSTONE_BRICKS.get(), consumer);
        stairs(IEBlocks.DULLSTONE_BRICK_STAIRS.get(), IEBlocks.DULLSTONE_BRICKS.get(), consumer);
        wall(IEBlocks.DULLSTONE_BRICK_WALL.get(), IEBlocks.DULLSTONE_BRICKS.get(), consumer);
        chiseled(IEBlocks.CHISELED_DULLSTONE_BRICKS.get(), IEBlocks.DULLSTONE_BRICK_SLAB.get(), IEBlocks.DULLSTONE_BRICKS.get(), consumer);
        smelting(IEBlocks.CRACKED_DULLSTONE_BRICKS.get(), IEBlocks.DULLSTONE_BRICKS.get(), 0.1F, 200, consumer);

        // Glowdust
        largeSquare(IEBlocks.GLOWDUST.get(), 4, Items.GLOWSTONE_DUST, consumer);
        IEShapedRecipeBuilder.shaped(IEBlocks.TRAPPED_GLOWDUST_SAND.get())
            .define('X', Items.GLOWSTONE_DUST).define('O', Blocks.GRAVEL)
            .pattern("XOX").pattern("OXO").pattern("XOX")
            .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Items.GLOWSTONE_DUST))
            .save(consumer);

        // Glowdust Stone
        largeSquare(IEBlocks.GLOWDUST_STONE.get(), 4, IEBlocks.GLOWDUST_SAND.get(), consumer);
        slab(IEBlocks.GLOWDUST_STONE_SLAB.get(), IEBlocks.GLOWDUST_STONE.get(), consumer);
        verticalSlab(IEBlocks.GLOWDUST_STONE_VERTICAL_SLAB.get(), IEBlocks.GLOWDUST_STONE_SLAB.get(), IEBlocks.GLOWDUST_STONE.get(), consumer);
        stairs(IEBlocks.GLOWDUST_STONE_STAIRS.get(), IEBlocks.GLOWDUST_STONE.get(), consumer);
        wall(IEBlocks.GLOWDUST_STONE_WALL.get(), IEBlocks.GLOWDUST_STONE.get(), consumer);

        // Glowdust Stone Bricks
        smallSquare(IEBlocks.GLOWDUST_STONE_BRICKS.get(), 4, IEBlocks.GLOWDUST_STONE.get(), consumer);
        slab(IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), consumer);
        verticalSlab(IEBlocks.GLOWDUST_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), consumer);
        stairs(IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), consumer);
        wall(IEBlocks.GLOWDUST_STONE_BRICK_WALL.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), consumer);
        chiseled(IEBlocks.CHISELED_GLOWDUST_STONE_BRICKS.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), consumer);
        smelting(IEBlocks.CRACKED_GLOWDUST_STONE_BRICKS.get(), IEBlocks.GLOWDUST_STONE_BRICKS.get(), 0.1F, 200, consumer);

        // Soul Sand
        slab(IEBlocks.SOUL_SAND_SLAB.get(), Blocks.SOUL_SAND, consumer);
        verticalSlab(IEBlocks.SOUL_SAND_VERTICAL_SLAB.get(), IEBlocks.SOUL_SAND_SLAB.get(), Blocks.SOUL_SAND, consumer);
        stairs(IEBlocks.SOUL_SAND_STAIRS.get(), Blocks.SOUL_SAND, consumer);

        // Soul Soil
        slab(IEBlocks.SOUL_SOIL_SLAB.get(), Blocks.SOUL_SOIL, consumer);
        verticalSlab(IEBlocks.SOUL_SOIL_VERTICAL_SLAB.get(), IEBlocks.SOUL_SOIL_SLAB.get(), Blocks.SOUL_SOIL, consumer);
        stairs(IEBlocks.SOUL_SOIL_STAIRS.get(), Blocks.SOUL_SOIL, consumer);

        // Soul Slate
        smelting(IEBlocks.SOUL_SLATE.get(), Blocks.SOUL_SOIL, 0.1F, 200, consumer);
        slab(IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_SLATE.get(), consumer);
        verticalSlab(IEBlocks.SOUL_SLATE_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_SLATE.get(), consumer);
        stairs(IEBlocks.SOUL_SLATE_STAIRS.get(), IEBlocks.SOUL_SLATE.get(), consumer);
        button(IEBlocks.SOUL_SLATE_BUTTON.get(), IEBlocks.SOUL_SLATE.get(), consumer);
        smallRow(IEBlocks.SOUL_SLATE_PRESSURE_PLATE.get(), 1, IEBlocks.SOUL_SLATE.get(), consumer);

        // Soul Slate Bricks
        smallSquare(IEBlocks.SOUL_SLATE_BRICKS.get(), 4, IEBlocks.SOUL_SLATE.get(), consumer);
        stonecutting(IEBlocks.SOUL_SLATE_BRICKS.get(), 1, IEBlocks.SOUL_SLATE.get(), consumer);
        slab(IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), 2, IEBlocks.SOUL_SLATE.get(), consumer);
        verticalSlab(IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), consumer);
        ConditionalRecipe.builder()
            .addCondition(new QuarkFlagCondition("vertical_slabs"))
            .addRecipe(c -> IESingleItemRecipeBuilder.stonecutting(IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), 2, IEBlocks.SOUL_SLATE.get()).unlockedBy(getHasName(IEBlocks.SOUL_SLATE.get()), has(IEBlocks.SOUL_SLATE.get())).save(c, from(IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE.get())))
            .build(consumer, IESingleItemRecipeBuilder.getRecipeIdForConditionalRecipe(from(IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE.get()), RecipeSerializer.STONECUTTER));
        stairs(IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.SOUL_SLATE_BRICK_STAIRS.get(), 1, IEBlocks.SOUL_SLATE.get(), consumer);
        wall(IEBlocks.SOUL_SLATE_BRICK_WALL.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.SOUL_SLATE_BRICK_WALL.get(), 1, IEBlocks.SOUL_SLATE.get(), consumer);
        chiseled(IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.CHISELED_SOUL_SLATE_BRICKS.get(), 1, IEBlocks.SOUL_SLATE.get(), consumer);
        specialChiseled(IEBlocks.CHARGED_CHISELED_SOUL_SLATE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICK_SLAB.get(), IEItems.SOUL_SALT_CLUMP.get(), consumer);
        smelting(IEBlocks.CRACKED_SOUL_SLATE_BRICKS.get(), IEBlocks.SOUL_SLATE_BRICKS.get(), 0.1F, 200, consumer);

        // Soul Stone
        smelting(IEBlocks.SOUL_STONE.get(), Blocks.SOUL_SAND, 0.1F, 200, consumer);
        slab(IEBlocks.SOUL_STONE_SLAB.get(), IEBlocks.SOUL_STONE.get(), consumer);
        verticalSlab(IEBlocks.SOUL_STONE_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE_SLAB.get(), IEBlocks.SOUL_STONE.get(), consumer);
        stairs(IEBlocks.SOUL_STONE_STAIRS.get(), IEBlocks.SOUL_STONE.get(), consumer);

        // Soul Stone Bricks
        smallSquare(IEBlocks.SOUL_STONE_BRICKS.get(), 4, IEBlocks.SOUL_STONE.get(), consumer);
        stonecutting(IEBlocks.SOUL_STONE_BRICKS.get(), 1, IEBlocks.SOUL_STONE.get(), consumer);
        slab(IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEBlocks.SOUL_STONE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.SOUL_STONE_BRICK_SLAB.get(), 2, IEBlocks.SOUL_STONE.get(), consumer);
        verticalSlab(IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEBlocks.SOUL_STONE_BRICKS.get(), consumer);
        ConditionalRecipe.builder()
            .addCondition(new QuarkFlagCondition("vertical_slabs"))
            .addRecipe(c -> IESingleItemRecipeBuilder.stonecutting(IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), 2, IEBlocks.SOUL_STONE.get()).unlockedBy(getHasName(IEBlocks.SOUL_STONE.get()), has(IEBlocks.SOUL_STONE.get())).save(c, from(IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE.get())))
            .build(consumer, IESingleItemRecipeBuilder.getRecipeIdForConditionalRecipe(from(IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE.get()), RecipeSerializer.STONECUTTER));
        stairs(IEBlocks.SOUL_STONE_BRICK_STAIRS.get(), IEBlocks.SOUL_STONE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.SOUL_STONE_BRICK_STAIRS.get(), 1, IEBlocks.SOUL_STONE.get(), consumer);
        wall(IEBlocks.SOUL_STONE_BRICK_WALL.get(), IEBlocks.SOUL_STONE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.SOUL_STONE_BRICK_WALL.get(), 1, IEBlocks.SOUL_STONE.get(), consumer);
        chiseled(IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEBlocks.SOUL_STONE_BRICKS.get(), consumer);
        stonecuttingFrom(IEBlocks.CHISELED_SOUL_STONE_BRICKS.get(), 1, IEBlocks.SOUL_STONE.get(), consumer);
        specialChiseled(IEBlocks.CHARGED_CHISELED_SOUL_STONE_BRICKS.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(), IEItems.SOUL_SALT_CLUMP.get(), consumer);
        smelting(IEBlocks.CRACKED_SOUL_STONE_BRICKS.get(), IEBlocks.SOUL_STONE_BRICKS.get(), 0.1F, 200, consumer);

        // Dullthorns
        smallSquare(IEBlocks.DULLTHORNS_BLOCK.get(), 4, IEBlocks.DULLTHORNS.get(), consumer);
        IEShapelessRecipeBuilder.shapeless(Items.STICK, 4)
            .requires(IEBlocks.DULLTHORNS.get())
            .unlockedBy(getHasName(IEBlocks.DULLTHORNS.get()), has(IEBlocks.DULLTHORNS.get()))
            .save(consumer, from(Items.STICK, IEBlocks.DULLTHORNS.get()));

        // Glass
        smelting(IEBlocks.GLOW_GLASS.get(), IEBlocks.GLOWDUST_SAND.get(), 0.1F, 200, consumer);
        pane(IEBlocks.GLOW_GLASS_PANE.get(), IEBlocks.GLOW_GLASS.get(), consumer);
        IEShapedRecipeBuilder.shaped(IEBlocks.QUARTZ_GLASS.get(), 2)
            .define('#', Blocks.GLASS).define('X', Items.QUARTZ)
            .pattern(" # ").pattern("#X#").pattern(" # ")
            .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
            .save(consumer);
        pane(IEBlocks.QUARTZ_GLASS_PANE.get(), IEBlocks.QUARTZ_GLASS.get(), consumer);
        IECookingRecipeBuilder.smelting(Blocks.GLASS, IEBlocks.SILT.get(), 0.1F, 200)
            .unlockedBy(getHasName(IEBlocks.SILT.get()), has(IEBlocks.SILT.get()))
            .save(consumer, from(Blocks.GLASS, IEBlocks.SILT.get()));

        // Glow Items
        IEShapedRecipeBuilder.shaped(IEBlocks.GLOW_CAMPFIRE.get())
            .define('#', ItemTags.LOGS).define('X', Items.STICK).define('O', IEItems.GLOWCOAL.get())
            .pattern(" X ").pattern("XOX").pattern("###")
            .unlockedBy(getHasName(IEItems.GLOWCOAL.get()), has(IEItems.GLOWCOAL.get()))
            .save(consumer);
        IEShapedRecipeBuilder.shaped(IEBlocks.GLOW_LANTERN.get())
            .define('#', Items.IRON_NUGGET).define('X', IEItems.GLOWCOAL.get())
            .pattern("###").pattern("#X#").pattern("###")
            .unlockedBy(getHasName(IEItems.GLOWCOAL.get()), has(IEItems.GLOWCOAL.get()))
            .save(consumer);
        IEShapedRecipeBuilder.shaped(IEBlocks.GLOW_TORCH.get())
            .define('#', Items.STICK).define('X', IEItems.GLOWCOAL.get())
            .pattern("X").pattern("#")
            .unlockedBy(getHasName(IEItems.GLOWCOAL.get()), has(IEItems.GLOWCOAL.get()))
            .save(consumer);

        // Blindsight Tongue Stew
        IEShapelessRecipeBuilder.shapeless(IEItems.BLINDSIGHT_TONGUE_STEW.get())
            .requires(IEItems.BLINDSIGHT_TONGUE.get()).requires(Blocks.CRIMSON_FUNGUS).requires(IEBlocks.LUMINOUS_FUNGUS.get()).requires(Items.WARPED_FUNGUS).requires(Items.BOWL)
            .unlockedBy(getHasName(IEItems.BLINDSIGHT_TONGUE.get()), has(IEItems.BLINDSIGHT_TONGUE.get()))
            .save(consumer);

        // Blindsight Tongue Whip
        IEShapedRecipeBuilder.shaped(IEItems.BLINDSIGHT_TONGUE_WHIP.get())
            .define('T', IEItems.BLINDSIGHT_TONGUE.get()).define('M', IEItems.MOLTEN_GOLD_CLUSTER.get()).define('S', Items.STICK)
            .pattern(" T ").pattern("MMM").pattern(" S ")
            .unlockedBy(getHasName(IEItems.BLINDSIGHT_TONGUE.get()), has(IEItems.BLINDSIGHT_TONGUE.get()))
            .save(consumer);

        // Crimson Fungus
        smallSquare(IEBlocks.CRIMSON_FUNGUS_CAP.get(), 1, Blocks.CRIMSON_FUNGUS, consumer);
        smallRow(IEBlocks.CRIMSON_NYLIUM_CARPET.get(), 3, Blocks.CRIMSON_NYLIUM, consumer);
        fungus(Blocks.CRIMSON_FUNGUS, IEBlocks.CRIMSON_FUNGUS_CAP.get(), consumer);

        // Luminous Fungus
        smallSquare(IEBlocks.LUMINOUS_FUNGUS_CAP.get(), 1, IEBlocks.LUMINOUS_FUNGUS.get(), consumer);
        fungus(IEBlocks.LUMINOUS_FUNGUS.get(), IEBlocks.LUMINOUS_FUNGUS_CAP.get(), consumer);

        // Warped Fungus
        smallSquare(IEBlocks.WARPED_FUNGUS_CAP.get(), 1, Blocks.WARPED_FUNGUS, consumer);
        smallRow(IEBlocks.WARPED_NYLIUM_CARPET.get(), 3, Blocks.WARPED_NYLIUM, consumer);
        fungus(Blocks.WARPED_FUNGUS, IEBlocks.WARPED_FUNGUS_CAP.get(), consumer);

        // Cured Jerky
        IEShapedRecipeBuilder.shaped(IEItems.CURED_JERKY.get(), 2)
            .define('#', IEItems.SOUL_SALT_CLUMP.get()).define('X', Items.ROTTEN_FLESH)
            .pattern(" # ").pattern("#X#").pattern(" # ")
            .unlockedBy(getHasName(IEItems.SOUL_SALT_CLUMP.get()), has(IEItems.SOUL_SALT_CLUMP.get()))
            .save(consumer);

        // Cooked Hogchop
        campfireCooking(IEItems.COOKED_HOGCHOP.get(), IEItems.RAW_HOGCHOP.get(), 0.35F, 600, consumer);
        smelting(IEItems.COOKED_HOGCHOP.get(), IEItems.RAW_HOGCHOP.get(), 0.35F, 200, consumer);
        smoking(IEItems.COOKED_HOGCHOP.get(), IEItems.RAW_HOGCHOP.get(), 0.35F, 100, consumer);

        // Glowsilk Bow
        IEShapedRecipeBuilder.shaped(IEItems.GLOWSILK_BOW.get())
            .define('#', Items.STICK).define('X', IEItems.GLOWSILK.get())
            .pattern(" X#").pattern("X #").pattern(" X#")
            .unlockedBy(getHasName(IEItems.GLOWSILK.get()), has(IEItems.GLOWSILK.get()))
            .save(consumer);
        smithing(IEItems.GLOWSILK_BOW.get(), Items.BOW, IEBlocks.GLOWSILK_COCOON.get(), consumer);

        // Infernal Painting
        IEShapedRecipeBuilder.shaped(IEItems.INFERNAL_PAINTING.get())
            .define('#', Items.BLAZE_ROD).define('X', Items.STICK).define('O', ItemTags.WOOL)
            .pattern("#X#").pattern("XOX").pattern("#X#")
            .unlockedBy(getHasName(Items.BLAZE_ROD), has(Items.BLAZE_ROD))
            .save(consumer);

        // Spirit Eye
        IEShapedRecipeBuilder.shaped(IEItems.SPIRIT_EYE.get())
            .define('#', IEItems.SOUL_SALT_CLUMP.get()).define('X', Items.SPIDER_EYE)
            .pattern(" # ").pattern("#X#").pattern(" # ")
            .unlockedBy(getHasName(IEItems.SOUL_SALT_CLUMP.get()), has(IEItems.SOUL_SALT_CLUMP.get()))
            .save(consumer);

        // Blast Furnace
        IEShapedRecipeBuilder.shaped(Blocks.BLAST_FURNACE)
            .define('#', IEItemTags.SMOOTH_STONES).define('X', Blocks.FURNACE).define('I', Items.IRON_INGOT)
            .pattern("III").pattern("IXI").pattern("###")
            .unlockedBy(getHasName(IEItemTags.SMOOTH_STONES), has(IEItemTags.SMOOTH_STONES))
            .save(consumer, from(Blocks.BLAST_FURNACE, IEItemTags.SMOOTH_STONES));

        // Gold Ingot
        IEShapedRecipeBuilder.shaped(Items.GOLD_INGOT, 3)
            .define('#', IEItems.MOLTEN_GOLD_CLUSTER.get())
            .pattern("###").pattern("###").pattern("###")
            .unlockedBy(getHasName(IEItems.MOLTEN_GOLD_CLUSTER.get()), has(IEItems.MOLTEN_GOLD_CLUSTER.get()))
            .save(consumer, from(Items.GOLD_INGOT, IEItems.MOLTEN_GOLD_CLUSTER.get()));
        IEShapelessRecipeBuilder.shapeless(Items.GOLD_INGOT, 1)
            .requires(IEItems.MOLTEN_GOLD_CLUSTER.get(), 3)
            .unlockedBy(getHasName(IEItems.MOLTEN_GOLD_CLUSTER.get()), has(IEItems.MOLTEN_GOLD_CLUSTER.get()))
            .save(consumer, extend(from(Items.GOLD_INGOT, IEItems.MOLTEN_GOLD_CLUSTER.get()), "_1"));
        IEShapelessRecipeBuilder.shapeless(Items.GOLD_INGOT, 2)
            .requires(IEItems.MOLTEN_GOLD_CLUSTER.get(), 6)
            .unlockedBy(getHasName(IEItems.MOLTEN_GOLD_CLUSTER.get()), has(IEItems.MOLTEN_GOLD_CLUSTER.get()))
            .save(consumer, extend(from(Items.GOLD_INGOT, IEItems.MOLTEN_GOLD_CLUSTER.get()), "_2"));

        // Iron Ingot
        blasting(Items.IRON_INGOT, IEBlocks.BASALT_IRON_ORE.get(), 0.7F, 100, consumer);
        smelting(Items.IRON_INGOT, IEBlocks.BASALT_IRON_ORE.get(), 0.7F, 200, consumer);

        // Paper
        IEShapedRecipeBuilder.shaped(Items.PAPER)
            .define('#', IEItemTags.MUSHROOMS)
            .pattern("###")
            .unlockedBy(getHasName(IEItemTags.MUSHROOMS), has(IEItemTags.MUSHROOMS))
            .save(consumer, from(Items.PAPER, IEItemTags.MUSHROOMS));

        // Shroomlight
        largeSquare(Blocks.SHROOMLIGHT, 1, IEBlocks.SHROOMLIGHT_FUNGUS.get(), consumer);

        // Torch
        IEShapedRecipeBuilder.shaped(Blocks.TORCH, 12)
            .define('#', Items.STICK).define('X', Items.FIRE_CHARGE)
            .pattern("X").pattern("#")
            .unlockedBy(getHasName(Items.FIRE_CHARGE), has(Items.FIRE_CHARGE))
            .save(consumer);
    }

    private void slab(ItemLike slab, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(slab, 6).define('#', fullBlock).pattern("###").unlockedBy(getHasName(fullBlock), has(fullBlock)).save(consumer);
        stonecutting(slab, 2, fullBlock, consumer);
    }

    private void verticalSlab(ItemLike verticalSlab, ItemLike slab, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder()
            .addCondition(new QuarkFlagCondition("vertical_slabs"))
            .addRecipe(c -> IEShapelessRecipeBuilder.shapeless(slab).requires(verticalSlab).unlockedBy(getHasName(fullBlock), has(fullBlock)).save(c, extend(RecipeBuilder.getDefaultRecipeId(slab), "_from_vertical_slab")))
            .build(consumer, extend(IEShapelessRecipeBuilder.getRecipeIdForConditionalRecipe(slab), "_from_vertical_slab"));

        ConditionalRecipe.builder()
            .addCondition(new QuarkFlagCondition("vertical_slabs"))
            .addRecipe(IEShapedRecipeBuilder.shaped(verticalSlab, 3).define('#', slab).pattern("#").pattern("#").pattern("#").unlockedBy(getHasName(fullBlock), has(fullBlock))::save)
            .build(consumer, IEShapedRecipeBuilder.getRecipeIdForConditionalRecipe(verticalSlab));

        ConditionalRecipe.builder()
            .addCondition(new QuarkFlagCondition("vertical_slabs"))
            .addRecipe(IESingleItemRecipeBuilder.stonecutting(verticalSlab, 2, fullBlock).unlockedBy(getHasName(fullBlock), has(fullBlock))::save)
            .build(consumer, IESingleItemRecipeBuilder.getRecipeIdForConditionalRecipe(verticalSlab, RecipeSerializer.STONECUTTER));
    }

    private void stairs(ItemLike stairs, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(stairs, 4).define('#', fullBlock).pattern("#  ").pattern("## ").pattern("###").unlockedBy(getHasName(fullBlock), has(fullBlock)).save(consumer);
        stonecutting(stairs, 1, fullBlock, consumer);
    }

    private void wall(ItemLike wall, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(wall, 6).define('#', fullBlock).pattern("###").pattern("###").unlockedBy(getHasName(fullBlock), has(fullBlock)).save(consumer);
        stonecutting(wall, 1, fullBlock, consumer);
    }

    private void pane(ItemLike pane, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(pane, 16).define('#', fullBlock).pattern("###").pattern("###").unlockedBy(getHasName(fullBlock), has(fullBlock)).save(consumer);
    }

    private void button(ItemLike button, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        IEShapelessRecipeBuilder.shapeless(button).requires(fullBlock).unlockedBy(getHasName(fullBlock), has(fullBlock)).save(consumer);
    }

    private void chiseled(ItemLike chiseledBlock, ItemLike slab, ItemLike fullBlock, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(chiseledBlock).define('#', slab).pattern("#").pattern("#").unlockedBy(getHasName(fullBlock), has(fullBlock)).save(consumer);
        stonecutting(chiseledBlock, 1, fullBlock, consumer);
    }

    private void specialChiseled(ItemLike chargedChiseledBlock, ItemLike chiseledSlab, ItemLike catalyst, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(chargedChiseledBlock)
            .define('#', chiseledSlab).define('X', catalyst)
            .pattern("#").pattern("X").pattern("#")
            .unlockedBy(getHasName(chiseledSlab), has(chiseledSlab))
            .save(consumer);
    }

    private void fungus(ItemLike fungus, ItemLike fungusCap, Consumer<FinishedRecipe> consumer) {
        IEShapelessRecipeBuilder.shapeless(fungus, 4).requires(fungusCap).unlockedBy(getHasName(fungusCap), has(fungusCap)).save(consumer);
    }

    private void smallSquare(ItemLike result, int count, ItemLike ingredient, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(result, count).define('#', ingredient).pattern("##").pattern("##").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void largeSquare(ItemLike result, int count, ItemLike ingredient, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(result, count).define('#', ingredient).pattern("###").pattern("###").pattern("###").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void smallRow(ItemLike result, int count, ItemLike ingredient, Consumer<FinishedRecipe> consumer) {
        IEShapedRecipeBuilder.shaped(result, count).define('#', ingredient).pattern("##").unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void blasting(ItemLike result, ItemLike ingredient, float experience, int cookingTime, Consumer<FinishedRecipe> consumer) {
        IECookingRecipeBuilder.blasting(result, ingredient, experience, cookingTime).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void campfireCooking(ItemLike result, ItemLike ingredient, float experience, int cookingTime, Consumer<FinishedRecipe> consumer) {
        IECookingRecipeBuilder.campfireCooking(result, ingredient, experience, cookingTime).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void smelting(ItemLike result, ItemLike ingredient, float experience, int cookingTime, Consumer<FinishedRecipe> consumer) {
        IECookingRecipeBuilder.smelting(result, ingredient, experience, cookingTime).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void smoking(ItemLike result, ItemLike ingredient, float experience, int cookingTime, Consumer<FinishedRecipe> consumer) {
        IECookingRecipeBuilder.smoking(result, ingredient, experience, cookingTime).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void smithing(ItemLike result, ItemLike base, ItemLike addition, Consumer<FinishedRecipe> consumer) {
        IEUpgradeRecipeBuilder.smithing(result, base, addition).unlockedBy(getHasName(addition), has(addition)).save(consumer);
    }

    private void stonecutting(ItemLike result, int count, ItemLike ingredient, Consumer<FinishedRecipe> consumer) {
        IESingleItemRecipeBuilder.stonecutting(result, count, ingredient).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer);
    }

    private void stonecuttingFrom(ItemLike result, int count, ItemLike ingredient, Consumer<FinishedRecipe> consumer) {
        IESingleItemRecipeBuilder.stonecutting(result, count, ingredient).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, from(result, ingredient));
    }

    private static ResourceLocation extend(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    private static ResourceLocation from(ItemLike result, ItemLike from) {
        return extend(RecipeBuilder.getDefaultRecipeId(result), "_from_" + name(from));
    }

    private static ResourceLocation from(ItemLike result, TagKey<?> from) {
        return extend(RecipeBuilder.getDefaultRecipeId(result), "_from_" + name(from));
    }

    private static String getHasName(TagKey<?> tag) {
        return "has_" + name(tag);
    }

    private static String name(ItemLike itemLike) {
        return itemLike.asItem().getRegistryName().getPath();
    }

    private static String name(TagKey<?> tag) {
        return tag.location().getPath();
    }

}
