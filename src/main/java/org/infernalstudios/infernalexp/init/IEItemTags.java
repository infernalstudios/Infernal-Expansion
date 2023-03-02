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

    public static final TagKey<Item> SMOOTH_STONES = tag("smooth_stones", IETagProviders.items(
        Blocks.SMOOTH_STONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_QUARTZ,
        IEBlocks.SMOOTH_GLOWSTONE.get(), IEBlocks.SMOOTH_DIMSTONE.get(), IEBlocks.SMOOTH_DULLSTONE.get()
    ));

    private static TagKey<Item> tag(String name, IETagProviders.TagProviderConsumer<Item> tagProvider) {
        TagKey<Item> tag = ItemTags.create(new ResourceLocation(InfernalExpansion.MOD_ID, name));
        TAGS.addProvider(() -> tag, tagProvider);
        return tag;
    }

}
