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

package org.infernalstudios.infernalexp.config.values;

import net.minecraftforge.common.ForgeConfigSpec;
import org.infernalstudios.infernalexp.InfernalExpansion;

public class CachedDoubleValue extends CachedConfigValue<Double> {

    private final double minValue;
    private final double maxValue;
    private final float stepValue;

    public CachedDoubleValue(String translationName, String comment, double defaultValue, double minValue, double maxValue, float stepValue, ForgeConfigSpec.Builder builder) {
        super(translationName, builder
            .comment(comment)
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip." + translationName)
            .defineInRange(translationName, defaultValue, minValue, maxValue)
        );

        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepValue = stepValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public float getStepValue() {
        return stepValue;
    }
}
