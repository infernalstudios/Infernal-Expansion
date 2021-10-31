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

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ConfigCategory implements IConfigElement {

    private final String translationName;
    private final String[] keywords;

    protected IConfigElement parent;
    protected final List<IConfigElement> children = new ArrayList<>();

    public ConfigCategory(String translationName, String keyword, IConfigElement parent) {
        this(translationName, keyword == null ? null : new String[]{keyword}, parent);
    }

    public ConfigCategory(String translationName, String[] keywords, IConfigElement parent) {
        this.translationName = translationName;
        this.keywords = keywords;
        this.parent = parent;
    }

    @Override
    public String getTranslationName() {
        return translationName;
    }

    public String getDisplayName() {
        return StringUtils.capitalize(String.join(" ", StringUtils.splitByCharacterTypeCamelCase(translationName)));
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Nullable
    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public IConfigElement getParent() {
        return parent;
    }

    @Override
    public List<IConfigElement> getChildren() {
        return children;
    }

    public void addChild(IConfigElement child) {
        children.add(child);
    }

    public void addChildren(List<IConfigElement> children) {
        this.children.addAll(children);
    }
}
