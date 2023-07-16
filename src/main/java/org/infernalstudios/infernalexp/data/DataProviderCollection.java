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

package org.infernalstudios.infernalexp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import net.minecraft.data.DataProvider;

public class DataProviderCollection<T, P extends DataProvider> {

    private final List<DataProviderPair<T, P>> collection = new ArrayList<>();

    public void addProvider(Supplier<? extends T> object, DataGenDeferredRegister.DataProviderConsumer<T, P> provider) {
        if (object != null && provider != null)
            collection.add(new DataProviderPair<>(object, provider));
    }

    public void forEach(BiConsumer<DataGenDeferredRegister.DataProviderConsumer<T, P>, Supplier<? extends T>> action) {
        for (DataProviderPair<T, P> providerPair : collection) {
            action.accept(providerPair.dataProvider(), providerPair.object());
        }
    }

    private record DataProviderPair<T, P extends DataProvider>(
        Supplier<? extends T> object,
        DataGenDeferredRegister.DataProviderConsumer<T, P> dataProvider
    ) {}

}
