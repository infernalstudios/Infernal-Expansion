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

import com.google.common.collect.ImmutableSet;
import org.infernalstudios.infernalexp.confignew.annotations.Configurable;
import org.infernalstudios.infernalexp.confignew.annotations.Max;
import org.infernalstudios.infernalexp.confignew.annotations.Min;
import org.infernalstudios.infernalexp.confignew.annotations.SubCategory;

import javax.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Defines an annotation processor that will search through mod classes to find IE Config annotations. The annotation processor will also sort and filter through the annotations.
 */
public interface IAnnotationProcessor {

    // Array of IE Config annotations
    Set<Class<?>> annotationTypes = ImmutableSet.of(Configurable.class, Max.class, Min.class, SubCategory.class);

    /**
     * Find all IE Config annotations in the mod's classes. This should be called before all other methods to initialize the annotation data
     */
    void findAnnotations();


    /**
     * @param type Type of annotation to filter by
     * @return List of annotations in the mod's classes that are of the specified type
     */
    List<Data> getAnnotationsByType(Class<?> type);

    /**
     * Sorts a list of annotations alphabetically based on their class name and field name
     *
     * @param annotationData The list of annotations to sort. It should only consist of annotations with {@link java.lang.annotation.ElementType#FIELD}
     * @return The sorted list of annotations
     */
    default List<Data> sortAnnotations(List<Data> annotationData) {
        return annotationData.stream()
            .sorted(Comparator.comparing((Data annotation) -> annotation.getClassType().getTypeName())
                .thenComparing(annotation -> annotation.getField() != null ? annotation.getField().getName() : ""))
            .collect(Collectors.toList());
    }

    class Data {

        private final Class<?> annotationType;
        private final Class<?> classType;
        private final ElementType elementType;
        private final Field field;

        public Data(Class<?> annotationType, Class<?> classType, ElementType elementType, @Nullable Field field) {
            this.annotationType = annotationType;
            this.classType = classType;
            this.elementType = elementType;
            this.field = field;
        }

        public Class<?> getAnnotationType() {
            return annotationType;
        }

        public Class<?> getClassType() {
            return classType;
        }

        public ElementType getElementType() {
            return elementType;
        }

        public Field getField() {
            return field;
        }
    }

}
