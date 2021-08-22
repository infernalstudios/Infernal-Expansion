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

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConfigTree implements IConfigElement {

    private final List<ConfigCategory> categories = ImmutableList.of(
        new ConfigCategory("mobs", "entities", this),
        new ConfigCategory("worldGeneration", "world", this),
        new ConfigCategory("miscellaneous", new String[]{}, this),
        new ConfigCategory("client", "client", this)
    );

    public ConfigCategory getCategoryByClassPath(String classPath) {
        for (ConfigCategory category : categories) {

            // First check sub categories for match
            for (IConfigElement child : category.getChildren()) {
                if (child instanceof ConfigCategory) {
                    ConfigCategory subCategory = (ConfigCategory) child;

                    if (subCategory.getKeywords() != null && Arrays.stream(subCategory.getKeywords()).anyMatch(classPath::contains))
                        return subCategory;
                }
            }

            // Then check main category for match
            if (category.getKeywords() != null && Arrays.stream(category.getKeywords()).anyMatch(classPath::contains))
                return category;
        }

        // If no categories match, return the misc category
        return getCategoryByName("miscellaneous");
    }

    public ConfigCategory getCategoryByName(String name) {
        for (ConfigCategory category : categories) {
            if (category.getTranslationName().equals(name))
                return category;
        }

        throw new IllegalArgumentException(name + " is not a valid config category!");
    }

    public void forEachConfigOption(Consumer<ConfigOption<?>> action) {
        getChildren().forEach(category -> category.getChildren().forEach(configElement -> {

            if (configElement instanceof ConfigCategory) {
                ((ConfigCategory) configElement).getChildren().forEach(subConfigElement -> {
                    if (subConfigElement instanceof ConfigOption) {
                        action.accept((ConfigOption<?>) subConfigElement);
                    }
                });

            } else if (configElement instanceof ConfigOption) {
                action.accept((ConfigOption<?>) configElement);
            }
        }));
    }

    @Override
    public String getTranslationName() {
        return "topLevel";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public IConfigElement getParent() {
        return null;
    }

    @Override
    public List<ConfigCategory> getChildren() {
        return categories;
    }


}
