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

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.mixin.client.OptionInstanceAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class TextFieldOption {

    public static OptionInstance<String> create(String caption, String tooltip, String initialValue, Consumer<String> setter) {
        return new OptionInstance<>(caption, OptionInstance.cachedConstantTooltip(Component.translatable(tooltip)), (component, string) -> component, new TextFieldValueSet(), initialValue, setter);
    }

    private record TextFieldValueSet() implements OptionInstance.ValueSet<String> {

        @Override
        public @NotNull Function<OptionInstance<String>, AbstractWidget> createButton(@NotNull OptionInstance.TooltipSupplier<String> tooltipSupplier, @NotNull Options options, int x, int y, int width) {
            return (optionInstance) -> new TextField(optionInstance, x, y, width, ((OptionInstanceAccessor) (Object) optionInstance).getCaption(), tooltipSupplier);
        }

        @Override
        public @NotNull Optional<String> validateValue(@NotNull String value) {
            return Optional.of(value);
        }

        @Override
        public @NotNull Codec<String> codec() {
            return Codec.STRING.stable();
        }

    }

}
