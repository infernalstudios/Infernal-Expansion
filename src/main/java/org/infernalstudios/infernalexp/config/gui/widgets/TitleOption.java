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

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.infernalstudios.infernalexp.mixin.client.OptionInstanceAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class TitleOption {

    public static OptionInstance<Unit> create(String caption) {
        return new OptionInstance<>(caption, OptionInstance.noTooltip(), (component, unit) -> component, new TitleValueSet(), Unit.INSTANCE, (unit) -> {});
    }

    private record TitleValueSet() implements OptionInstance.ValueSet<Unit> {

        @Override
        public @NotNull Function<OptionInstance<Unit>, AbstractWidget> createButton(@NotNull OptionInstance.TooltipSupplier<Unit> tooltipSupplier, @NotNull Options options, int x, int y, int width) {
            return (optionInstance) -> new Title(x, y, width, 20, ((OptionInstanceAccessor) (Object) optionInstance).getCaption());
        }

        @Override
        public @NotNull Optional<Unit> validateValue(@NotNull Unit value) {
            return Optional.empty();
        }

        @Override
        public @NotNull Codec<Unit> codec() {
            return Codec.EMPTY.codec();
        }

    }

}
