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
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.infernalstudios.infernalexp.InfernalExpansion;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class DataGenDeferredRegister<T extends IForgeRegistryEntry<T>, D extends DataProvider, L extends Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>> {

    private final DeferredRegister<T> register;
    private final List<Pair<Supplier<? extends T>, DataProviderConsumer<T, D>>> dataProviders = new ArrayList<>();
    private final List<Pair<Supplier<? extends T>, LootProviderConsumer<T, L>>> lootProviders = new ArrayList<>();

    public DataGenDeferredRegister(IForgeRegistry<T> registry) {
        register = DeferredRegister.create(registry, InfernalExpansion.MOD_ID);
    }

    public <S extends T> RegistryObject<S> register(String name, Supplier<? extends S> supplier, @Nullable DataProviderConsumer<T, D> dataProvider, @Nullable LootProviderConsumer<T, L> lootProvider) {
        RegistryObject<S> registryObject = register.register(name, supplier);
        if (registryObject != null && dataProvider != null)
            dataProviders.add(Pair.of(registryObject, dataProvider));

        if (registryObject != null && lootProvider != null)
            lootProviders.add(Pair.of(registryObject, lootProvider));

        return registryObject;
    }

    public void register(IEventBus eventBus) {
        register.register(eventBus);
    }

    public DeferredRegister<T> getRegister() {
        return register;
    }

    public List<Pair<Supplier<? extends T>, DataProviderConsumer<T, D>>> getDataProviders() {
        return dataProviders;
    }

    public List<Pair<Supplier<? extends T>, LootProviderConsumer<T, L>>> getLootProviders() {
        return lootProviders;
    }

    @FunctionalInterface
    public interface DataProviderConsumer<T, P extends DataProvider> {
        void accept(P dataProvider, Supplier<? extends T> object);
    }

    public interface NoneDataProvider extends DataProvider {}

    @FunctionalInterface
    public interface LootProviderConsumer<T, L extends Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>> {
        void accept(L loot, Supplier<? extends T> object);
    }

    public interface NoneLootProvider extends Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {}

}
