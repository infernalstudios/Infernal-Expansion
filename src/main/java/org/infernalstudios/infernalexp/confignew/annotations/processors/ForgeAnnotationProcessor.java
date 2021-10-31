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

package org.infernalstudios.infernalexp.confignew.annotations.processors;

import net.minecraftforge.fml.ModList;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.objectweb.asm.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Annotation processor using Forge {@link net.minecraftforge.forgespi.language.ModFileScanData}. This data is created by Forge whenever we launch the game.
 */
public class ForgeAnnotationProcessor implements IAnnotationProcessor {

    private Set<Data> annotationData = null;

    @Override
    public void findAnnotations() {
        annotationData = new HashSet<>();

        ModList.get().getModFileById(InfernalExpansion.MOD_ID).getFile().getScanResult().getAnnotations().stream()
            // Filter out all annotations that aren't IE Config annotations
            .filter(annotation -> annotationTypes.stream().anyMatch(clazz -> Type.getType(clazz).equals(annotation.getAnnotationType())))
            .forEach(annotation -> {
                try {
                    Class<?> annotationType = Class.forName(annotation.getAnnotationType().getClassName());
                    Class<?> classType = Class.forName(annotation.getClassType().getClassName());

                    Field field = null;

                    if (annotation.getTargetType() == ElementType.FIELD) {
                        field = classType.getDeclaredField(annotation.getMemberName());
                        field.setAccessible(true);
                    }


                    annotationData.add(new Data(annotationType, classType, annotation.getTargetType(), field));

                } catch (ClassNotFoundException | NoSuchFieldException exception) {
                    throw new RuntimeException(exception);
                }
            });
    }

    @Override
    public List<Data> getAnnotationsByType(Class<?> type) {
        if (annotationData == null)
            throw new NullPointerException("Annotation data has not been initialized yet");

        List<Data> filteredAnnotationData = annotationData.stream().filter(annotation -> type == annotation.getAnnotationType()).collect(Collectors.toList());

        // Sort the array alphabetically if that target ElementType is FIELD
        if (Arrays.stream(type.getAnnotation(Target.class).value()).allMatch(elementType -> elementType == ElementType.FIELD)) {
            return sortAnnotations(filteredAnnotationData);
        }

        return filteredAnnotationData;
    }
}
