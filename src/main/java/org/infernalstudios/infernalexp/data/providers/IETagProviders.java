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

package org.infernalstudios.infernalexp.data.providers;

import java.util.Arrays;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.infernalstudios.infernalexp.data.DataGenDeferredRegister;

public abstract class IETagProviders<T> {

    @SafeVarargs
    public static <E> TagProviderConsumer<E> simple(E... elements) {
        return (provider, tag) -> {
            provider.tag(tag.get()).add(elements);
        };
    }

    @SafeVarargs
    public static <E> TagProviderConsumer<E> simple(ResourceKey<E>... elements) {
        return (provider, tag) -> {
            provider.tag(tag.get()).add(elements);
        };
    }

    public static TagProviderConsumer<Item> items(ItemLike... elements) {
        return simple(Arrays.stream(elements).map(ItemLike::asItem).toArray(Item[]::new));
    }

    @FunctionalInterface
    public interface TagProviderConsumer<T> extends DataGenDeferredRegister.DataProviderConsumer<TagKey<T>, TagsProvider<T>> {}

}
