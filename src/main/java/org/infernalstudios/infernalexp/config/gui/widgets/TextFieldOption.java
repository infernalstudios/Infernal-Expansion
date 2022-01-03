/*
 * Copyright 2022 Infernal Studios
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

package org.infernalstudios.infernalexp.config.gui.widgets;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class TextFieldOption extends AbstractOption {

    private final ITextComponent tooltip;
    private final Function<GameSettings, String> getter;
    private final BiConsumer<GameSettings, String> setter;

    public TextFieldOption(String translationKey, @Nullable ITextComponent tooltip, Function<GameSettings, String> getter, BiConsumer<GameSettings, String> setter) {
        super(translationKey);
        this.tooltip = tooltip;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Widget createWidget(GameSettings options, int xIn, int yIn, int widthIn) {
        if (tooltip != null) {
            this.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(tooltip, 200));
        }

        return new IETextFieldWidget(options, xIn, yIn, widthIn, getBaseMessageTranslation(), this);
    }

    public void set(GameSettings options, String textIn) {
        setter.accept(options, textIn);
    }

    public String get(GameSettings options) {
        return getter.apply(options);
    }

}
