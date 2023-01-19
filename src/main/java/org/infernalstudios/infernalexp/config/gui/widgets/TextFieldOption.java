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

import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class TextFieldOption extends Option {

    private final Function<Minecraft, List<FormattedCharSequence>> tooltipSupplier;
    private final Function<Options, String> getter;
    private final BiConsumer<Options, String> setter;

    public TextFieldOption(String translationKey, Function<Options, String> getter, BiConsumer<Options, String> setter, Function<Minecraft, List<FormattedCharSequence>> tooltipSupplier) {
        super(translationKey);
        this.tooltipSupplier = tooltipSupplier;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public @NotNull AbstractWidget createButton(@NotNull Options options, int xIn, int yIn, int widthIn) {
        return new TextField(options, xIn, yIn, widthIn, getCaption(), this, tooltipSupplier.apply(Minecraft.getInstance()));
    }

    public void set(Options options, String textIn) {
        setter.accept(options, textIn);
    }

    public String get(Options options) {
        return getter.apply(options);
    }
}
