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

package org.infernalstudios.infernalexp.confignew;

import org.infernalstudios.infernalexp.confignew.annotations.Configurable;
import org.infernalstudios.infernalexp.confignew.annotations.Max;
import org.infernalstudios.infernalexp.confignew.annotations.Min;
import org.infernalstudios.infernalexp.confignew.annotations.SubCategory;
import org.infernalstudios.infernalexp.confignew.annotations.processors.IAnnotationProcessor;
import org.infernalstudios.infernalexp.confignew.builders.IConfigBuilder;
import org.infernalstudios.infernalexp.confignew.elements.ConfigCategory;
import org.infernalstudios.infernalexp.confignew.elements.ConfigOption;
import org.infernalstudios.infernalexp.confignew.elements.ConfigTree;

public class IEConfig {

    private final ConfigTree config = new ConfigTree();
    private final IAnnotationProcessor annotationProcessor;
    private final IConfigBuilder configBuilder;

    public IEConfig(IAnnotationProcessor annotationProcessor, IConfigBuilder configBuilder) {
        this.annotationProcessor = annotationProcessor;
        this.configBuilder = configBuilder;

        this.init();
    }

    protected void init() {
        annotationProcessor.findAnnotations();

        buildSubCategories();
        buildConfigOptions();

        configBuilder.init(this);
    }

    protected void buildSubCategories() {
        for (IAnnotationProcessor.Data annotationData : annotationProcessor.getAnnotationsByType(SubCategory.class)) {
            ConfigCategory category = config.getCategoryByClassPath(annotationData.getClassType().getName());

            category.addChild(new ConfigCategory(
                    annotationData.getClassType().getAnnotation(SubCategory.class).value(),
                    annotationData.getClassType().getSimpleName(),
                    category
                )
            );
        }
    }

    protected void buildConfigOptions() {
        for (IAnnotationProcessor.Data annotationData : annotationProcessor.getAnnotationsByType(Configurable.class)) {
            if (annotationData.getField() == null)
                throw new RuntimeException("@Configurable annotation is on type other than ElementType.FIELD");

            try {
                annotationData.getField().setAccessible(true);
                Configurable annotation = annotationData.getField().getAnnotation(Configurable.class);

                // Get translation name
                String translationName;

                if (!annotation.translationName().equals(""))
                    translationName = annotation.translationName();
                else
                    translationName = annotationData.getField().getName();

                // Get category
                ConfigCategory category;

                if (!annotation.category().equals(""))
                    category = config.getCategoryByName(annotation.category());
                else
                    category = config.getCategoryByClassPath(annotationData.getClassType().getName());

                ConfigOption<?> configOption = new ConfigOption<>(
                    translationName,
                    annotation.description(),
                    annotationData.getField().get(null),
                    annotationData.getField(),
                    category
                );

                category.addChild(configOption);

                // Add restrictions
                if (annotationData.getField().isAnnotationPresent(Min.class))
                    configOption.addRestriction("min", annotationData.getField().getDeclaredAnnotation(Min.class).value());

                if (annotationData.getField().isAnnotationPresent(Max.class))
                    configOption.addRestriction("max", annotationData.getField().getDeclaredAnnotation(Max.class).value());

            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public ConfigTree buildConfigFile(IConfigBuilder builder) {
        config.getChildren().forEach(category -> {
            builder.push(category.getDisplayName());

            category.getChildren().forEach(configElement -> {
                if (configElement instanceof ConfigCategory) {
                    ConfigCategory subCategory = (ConfigCategory) configElement;

                    builder.push(subCategory.getDisplayName());

                    subCategory.getChildren().forEach(subConfigElement -> {
                        if (subConfigElement instanceof ConfigOption) {
                            builder.define((ConfigOption<?>) subConfigElement);
                        }
                    });

                    builder.pop();

                } else if (configElement instanceof ConfigOption) {
                    builder.define((ConfigOption<?>) configElement);
                }
            });

            builder.pop();
        });

        return config;
    }

    public void updateFields() {
        config.forEachConfigOption(configOption -> {
            try {
                configOption.getField().set(configOption.getValue().getClass().getName(), configOption.getValue());
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public ConfigTree getConfig() {
        return config;
    }
}
