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

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.confignew.annotation.Configurable;
import org.infernalstudios.infernalexp.confignew.annotation.Max;
import org.infernalstudios.infernalexp.confignew.annotation.Min;
import org.infernalstudios.infernalexp.confignew.annotation.SubCategory;
import org.infernalstudios.infernalexp.confignew.elements.ConfigCategory;
import org.infernalstudios.infernalexp.confignew.elements.ConfigOption;
import org.infernalstudios.infernalexp.confignew.elements.ConfigTree;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IEConfig {

    private static final ConfigTree config = new ConfigTree();

    public static void registerConfig() {
        ForgeConfigSpec.Builder forgeBuilder = new ForgeConfigSpec.Builder();
        ForgeConfigBuilder builder = new ForgeConfigBuilder(forgeBuilder);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, builder.configure(IEConfig::build), "infernalexp/infernalexp-config.toml");
    }

    private static ConfigTree build(IConfigBuilder builder) {
        buildSubCategories();
        buildConfigOptions();
        buildConfigFile(builder);

        return config;
    }

    private static void buildSubCategories() {
        ModFileScanData scanData = ModList.get().getModFileById(InfernalExpansion.MOD_ID).getFile().getScanResult();

        scanData.getAnnotations().stream()
            .filter(annotation -> annotation.getAnnotationType().equals(Type.getType(SubCategory.class)))
            .forEach(annotation -> {
                String classPath = annotation.getClassType().getClassName();
                ConfigCategory category = config.getCategoryByClassPath(classPath);

                category.addChild(new ConfigCategory(
                    (String) annotation.getAnnotationData().get("value"),
                    classPath.substring(classPath.lastIndexOf("/") + 1),
                    category)
                );
            });
    }

    private static void buildConfigOptions() {
        for (ModFileScanData.AnnotationData annotation : findConfigurableAnnotations()) {
            try {
                Class<?> clazz = Class.forName(annotation.getClassType().getClassName());
                Field field = clazz.getDeclaredField(annotation.getMemberName());
                field.setAccessible(true);

                // Get translationName
                String translationName;

                if (annotation.getAnnotationData().containsKey("translationName"))
                    translationName = (String) annotation.getAnnotationData().get("translationName");
                else
                    translationName = field.getName();

                // Get category
                ConfigCategory category;

                if (annotation.getAnnotationData().containsKey("category"))
                    category = config.getCategoryByName((String) annotation.getAnnotationData().get("category"));
                else
                    category = config.getCategoryByClassPath(clazz.getName());

                // Create ConfigOption
                ConfigOption<?> configOption = new ConfigOption<>(
                    translationName,
                    (String) annotation.getAnnotationData().get("description"),
                    field.get(null),
                    field,
                    category
                );

                category.addChild(configOption);

                // Add restrictions
                if (field.isAnnotationPresent(Min.class))
                    configOption.addRestriction("min", field.getDeclaredAnnotation(Min.class).value());

                if (field.isAnnotationPresent(Max.class))
                    configOption.addRestriction("max", field.getDeclaredAnnotation(Max.class).value());

            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private static void buildConfigFile(IConfigBuilder builder) {
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
    }

    public static void updateFields() {
        config.forEachConfigOption(configOption -> {
            try {
                configOption.getField().set(configOption.getValue().getClass().getName(), configOption.getValue());
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private static List<ModFileScanData.AnnotationData> findConfigurableAnnotations() {
        ModFileScanData scanData = ModList.get().getModFileById(InfernalExpansion.MOD_ID).getFile().getScanResult();

        return scanData.getAnnotations().stream()
            .filter(annotation -> annotation.getAnnotationType().equals(Type.getType(Configurable.class)))
            .sorted(Comparator.comparing((ModFileScanData.AnnotationData annotation) -> annotation.getClassType().getClassName())
                .thenComparing(ModFileScanData.AnnotationData::getMemberName))
            .collect(Collectors.toList());
    }
}
