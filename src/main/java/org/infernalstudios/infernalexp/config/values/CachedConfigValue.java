/*
 * Copyright 2021 Infernal Studios
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

package org.infernalstudios.infernalexp.config.values;

import net.minecraftforge.common.ForgeConfigSpec;

public class CachedConfigValue<T> {

    private final String translationName;
    protected final ForgeConfigSpec.ConfigValue<T> internal;
    protected T cachedValue;
    protected boolean synced;

    public CachedConfigValue(String translationName, ForgeConfigSpec.ConfigValue<T> internal) {
        this.translationName = translationName;
        this.internal = internal;
    }

    public String getTranslationName() {
        return translationName;
    }

    public T get() {
        if (!synced) {
            cachedValue = internal.get();
            synced = true;
        }

        return cachedValue;
    }

    public void set(T value) {
        internal.set(value);
        cachedValue = value;

        synced = true;
    }
}
