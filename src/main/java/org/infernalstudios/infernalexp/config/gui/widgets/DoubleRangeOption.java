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

package org.infernalstudios.infernalexp.config.gui.widgets;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record DoubleRangeOption(double minInclusive, double maxInclusive, float step) implements OptionInstance.SliderableValueSet<Double> {

    @Override
    public double toSliderValue(@NotNull Double value) {
        return Mth.map(value, this.minInclusive(), this.maxInclusive(), 0.0D, 1.0D);
    }

    @Override
    public @NotNull Double fromSliderValue(double value) {
        return (double) (Math.round(Mth.map(value, 0.0D, 1.0D, this.minInclusive(), this.maxInclusive()) / this.step()) * this.step());
    }

    @Override
    public @NotNull Optional<Double> validateValue(@NotNull Double value) {
        return value >= this.minInclusive() && value <= this.maxInclusive() ? Optional.of(value) : Optional.empty();
    }

    @Override
    public @NotNull Codec<Double> codec() {
        return Codec.doubleRange(this.minInclusive(), this.maxInclusive() + 1.0D);
    }

}
