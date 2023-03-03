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

package org.infernalstudios.infernalexp.init;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.data.DataProviderCollection;
import org.infernalstudios.infernalexp.data.providers.IETagProviders;

public class IEItemTags {

    public static final DataProviderCollection<TagKey<Item>, TagsProvider<Item>> TAGS = new DataProviderCollection<>();

    public static final TagKey<Item> GLOWSILK_REPAIR_BLACKLIST = tag("glowsilk_repair_blacklist", IETagProviders.items());

    public static final TagKey<Item> MUSHROOMS = tag("mushrooms", IETagProviders.items(
        Items.RED_MUSHROOM, Items.BROWN_MUSHROOM
    ));

    public static final TagKey<Item> FUNGUS = tag("fungus", (provider, tag) -> {
        provider.tag(tag.get()).addTag(IEItemTags.MUSHROOMS)
            .add(Items.CRIMSON_FUNGUS, Items.WARPED_FUNGUS, IEBlocks.LUMINOUS_FUNGUS.get().asItem());
    });

    public static final TagKey<Item> SMOOTH_STONES = tag("smooth_stones", IETagProviders.items(
        Blocks.SMOOTH_STONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_QUARTZ,
        IEBlocks.SMOOTH_GLOWSTONE.get(), IEBlocks.SMOOTH_DIMSTONE.get(), IEBlocks.SMOOTH_DULLSTONE.get()
    ));

    static {
        overrideVanilla("buttons", IETagProviders.items(
            IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(), IEBlocks.SMOOTH_DIMSTONE_BUTTON.get(), IEBlocks.SMOOTH_GLOWSTONE_BUTTON.get(),
            IEBlocks.BASALT_BUTTON.get(), IEBlocks.SOUL_SLATE_BUTTON.get()
        ));
        overrideVanilla("coals", IETagProviders.items(Items.FIRE_CHARGE));
        overrideVanilla("slabs", IETagProviders.items(
            IEBlocks.SMOOTH_GLOWSTONE_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE_SLAB.get(),
            IEBlocks.GLOWSTONE_BRICK_SLAB.get(), IEBlocks.DIMSTONE_BRICK_SLAB.get(), IEBlocks.DULLSTONE_BRICK_SLAB.get(),
            IEBlocks.GLOWDUST_STONE_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICK_SLAB.get(), IEBlocks.BASALT_SLAB.get(),
            IEBlocks.BASALT_COBBLED_SLAB.get(), IEBlocks.POLISHED_BASALT_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES_SLAB.get(),
            IEBlocks.BASALT_BRICK_SLAB.get(), IEBlocks.SOUL_SAND_SLAB.get(), IEBlocks.SOUL_SOIL_SLAB.get(),
            IEBlocks.SOUL_STONE_SLAB.get(), IEBlocks.SOUL_SLATE_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_SLAB.get(),
            IEBlocks.SOUL_SLATE_BRICK_SLAB.get()
        ));
        overrideVanilla("stairs", IETagProviders.items(
            IEBlocks.SMOOTH_GLOWSTONE_STAIRS.get(), IEBlocks.SMOOTH_DIMSTONE_STAIRS.get(), IEBlocks.SMOOTH_DULLSTONE_STAIRS.get(),
            IEBlocks.GLOWSTONE_BRICK_STAIRS.get(), IEBlocks.DIMSTONE_BRICK_STAIRS.get(), IEBlocks.DULLSTONE_BRICK_STAIRS.get(),
            IEBlocks.GLOWDUST_STONE_STAIRS.get(), IEBlocks.GLOWDUST_STONE_BRICK_STAIRS.get(), IEBlocks.BASALT_STAIRS.get(),
            IEBlocks.BASALT_BRICK_STAIRS.get(), IEBlocks.SOUL_SAND_STAIRS.get(), IEBlocks.SOUL_SOIL_STAIRS.get(),
            IEBlocks.SOUL_STONE_STAIRS.get(), IEBlocks.SOUL_SLATE_STAIRS.get(), IEBlocks.SOUL_STONE_BRICK_STAIRS.get(),
            IEBlocks.SOUL_SLATE_BRICK_STAIRS.get()
        ));
        overrideVanilla("walls", IETagProviders.items(
            IEBlocks.GLOWSTONE_BRICK_WALL.get(), IEBlocks.DIMSTONE_BRICK_WALL.get(), IEBlocks.DULLSTONE_BRICK_WALL.get(),
            IEBlocks.GLOWDUST_STONE_WALL.get(), IEBlocks.GLOWDUST_STONE_BRICK_WALL.get(), IEBlocks.BASALT_WALL.get(),
            IEBlocks.BASALT_BRICK_WALL.get(), IEBlocks.SOUL_STONE_WALL.get(), IEBlocks.SOUL_STONE_BRICK_WALL.get(),
            IEBlocks.SOUL_SLATE_BRICK_WALL.get()
        ));

        override("forge", "ores/iron", IETagProviders.items(IEBlocks.BASALT_IRON_ORE.get().asItem()));
        override("miningmaster", "catalysts", IETagProviders.items(IEItems.BLINDSIGHT_TONGUE_WHIP.get()));
        override("quark", "vertical_slab", IETagProviders.items(
            IEBlocks.SMOOTH_GLOWSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DIMSTONE_VERTICAL_SLAB.get(), IEBlocks.SMOOTH_DULLSTONE_VERTICAL_SLAB.get(),
            IEBlocks.GLOWSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DIMSTONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.DULLSTONE_BRICK_VERTICAL_SLAB.get(),
            IEBlocks.GLOWDUST_STONE_VERTICAL_SLAB.get(), IEBlocks.GLOWDUST_STONE_BRICK_VERTICAL_SLAB.get(), IEBlocks.BASALT_COBBLED_VERTICAL_SLAB.get(),
            IEBlocks.BASALT_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_VERTICAL_SLAB.get(), IEBlocks.POLISHED_BASALT_TILES_VERTICAL_SLAB.get(),
            IEBlocks.BASALT_BRICK_VERTICAL_SLAB.get(), IEBlocks.SOUL_SAND_VERTICAL_SLAB.get(), IEBlocks.SOUL_SOIL_VERTICAL_SLAB.get(),
            IEBlocks.SOUL_STONE_VERTICAL_SLAB.get(), IEBlocks.SOUL_SLATE_VERTICAL_SLAB.get(), IEBlocks.SOUL_STONE_BRICK_VERTICAL_SLAB.get(),
            IEBlocks.SOUL_SLATE_BRICK_VERTICAL_SLAB.get()
        ));
    }

    private static TagKey<Item> tag(String name, IETagProviders.TagProviderConsumer<Item> tagProvider) {
        TagKey<Item> tag = ItemTags.create(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        TAGS.addProvider(() -> tag, tagProvider);
        return tag;
    }

    private static void override(String namespace, String name, IETagProviders.TagProviderConsumer<Item> tagProvider) {
        TAGS.addProvider(() -> ItemTags.create(new ResourceLocation(namespace, name)), tagProvider);
    }

    private static void overrideVanilla(String name, IETagProviders.TagProviderConsumer<Item> tagProvider) {
        override("minecraft", name, tagProvider);
    }

}
