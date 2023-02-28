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

import net.minecraft.data.DataProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.infernalexp.InfernalExpansion;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DataGenDeferredRegister<T extends IForgeRegistryEntry<T>, P extends DataProvider> {

    private final DeferredRegister<T> register;
    private final DataProviderCollection<T, P> dataProviders = new DataProviderCollection<>();

    public DataGenDeferredRegister(IForgeRegistry<T> registry) {
        register = DeferredRegister.create(registry, InfernalExpansion.MOD_ID);
    }

    public <S extends T> RegistryObject<S> register(String name, Supplier<? extends S> supplier, @Nullable DataProviderCollection.DataProviderConsumer<T, P> provider) {
        RegistryObject<S> registryObject = register.register(name, supplier);
        dataProviders.addProvider(registryObject, provider);

        return registryObject;
    }

    public void register(IEventBus eventBus) {
        register.register(eventBus);
    }

    public DeferredRegister<T> getRegister() {
        return register;
    }

    public DataProviderCollection<T, P> getDataProviders() {
        return dataProviders;
    }

}
