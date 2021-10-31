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

package org.infernalstudios.infernalexp.confignew.elements;

import net.minecraftforge.common.ForgeConfigSpec;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigOption<T> implements IConfigElement {

    private final String translationName;
    private final String description;

    private ForgeConfigSpec.ConfigValue<T> value;
    private final T defaultValue;
    private final Map<String, Object> restrictions = new HashMap<>();

    private final Field field;
    private final IConfigElement parent;

    public ConfigOption(String translationName, String description, T defaultValue, Field field, IConfigElement parent) {
        this.translationName = translationName;
        this.description = description;
        this.defaultValue = defaultValue;
        this.field = field;
        this.parent = parent;
    }

    @Override
    public String getTranslationName() {
        return translationName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void addConfigValue(ForgeConfigSpec.ConfigValue<T> value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value.set(value);
    }

    public T getValue() {
        return value.get();
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void addRestriction(String name, Object value) {
        restrictions.put(name, value);
    }

    public Object getRestriction(String name) {
        if (restrictions.containsKey(name))
            return restrictions.get(name);

        return null;
    }

    public Field getField() {
        return field;
    }

    @Override
    public IConfigElement getParent() {
        return parent;
    }

    @Override
    public List<? extends IConfigElement> getChildren() {
        return null;
    }
}
