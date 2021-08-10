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

package org.infernalstudios.infernalexp.config;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Locale;

public final class InfernalExpansionConfig {

    //Client

    //Luminous Effect
    public enum ClientConfig {
        LUMINOUS_REFRESH_DELAY("luminousRefreshDelay", true, 1, 20, 1);

        private final String translationName;
        private final boolean isSlider;
        private final double minValue;
        private final double maxValue;
        private final float stepSize;
        private double value;

        ClientConfig(String translationName, boolean isSlider, double minValue, double maxValue, float stepSize) {
            this.translationName = translationName;
            this.isSlider = isSlider;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.stepSize = stepSize;
            this.value = 0;
        }

        public String getTranslationName() {
            return translationName;
        }

        public double getMinValue() {
            return minValue;
        }

        public double getMaxValue() {
            return maxValue;
        }

        public float getStepSize() {
            return stepSize;
        }

        public void set(double value) {
            this.value = value;
        }

        public void set(boolean value) {
            this.value = value ? 1 : 0;
        }

        public double getDouble() {
            return value;
        }

        public boolean getBool() {
            return value > 0;
        }

        public boolean isSlider() {
            return isSlider;
        }
    }

    //Common

    public enum MobSpawning {
        VOLINE("voline"),
        WARPBEETLE("warpbeetle"),
        SHROOMLOIN("shroomloin"),
        BASALT_GIANT("basalt_giant"),
        EMBODY("embody"),
        GLOWSQUITO("glowsquito"),
        GLOWSILK_MOTH("glowsilk_moth"),
        BLINDSIGHT("blindsight"),
        BLACKSTONE_DWARF("blackstone_dwarf");

        private final String translationName;
        private String spawnableBiomes;

        MobSpawning(String translationName) {
            this.translationName = translationName;
            this.spawnableBiomes = "";
        }

        public String getTranslationName() {
            return translationName;
        }

        public void setSpawnableBiomes(String spawnableBiomes) {
            this.spawnableBiomes = spawnableBiomes;
        }

        public String getSpawnableBiomes() {
            return spawnableBiomes;
        }

        /**
         * @param name name of the enum constant to get
         * @return the enum constant
         */
        public static MobSpawning getByName(@Nonnull String name) {
            return MobSpawning.valueOf(name.toUpperCase(Locale.ROOT));
        }

        /**
         * @param name name of the enum constant to check if it exists in the enum
         * @return true if enum contains enum constant
         */
        public static boolean contains(@Nonnull String name) {
            return Arrays.stream(MobSpawning.values()).anyMatch(entity -> entity.getTranslationName().equals(name));
        }
    }

    public enum Miscellaneous {
        SHROOMLIGHT_GROWABLE("isShroomlightGrowable", false, 0, 0, 0),
        SHROOMLIGHT_GROW_CHANCE("shroomlightGrowChance", true, 0, 1, 0.01f),
        LUMINOUS_FUNGUS_ACTIVATE_DISTANCE("luminousFungusActivateDistance", true, 0, 100, 1),
        FIRE_CHARGE_EXPLOSION("fireChargeExplosion", false, 0, 1, 1),
        JERKY_EFFECT_DURATION("jerkyEffectDuration", true, 1, 120, 1),
        JERKY_EFFECT_AMPLIFIER("jerkyEffectAmplifier", true, 0, 2, 1),
        LUMINOUS_FUNGUS_GIVES_EFFECT("luminousFungusGivesEffect", false, 0, 0, 0),
        USE_THROWABLE_BRICKS("useThrowableBricks", false, 0, 0,0);

        private final String translationName;
        private final boolean isSlider;
        private final double minValue;
        private final double maxValue;
        private final float stepSize;
        private double value;

        Miscellaneous(String translationName, boolean isSlider, double minValue, double maxValue, float stepSize) {
            this.translationName = translationName;
            this.isSlider = isSlider;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.stepSize = stepSize;
            this.value = 0;
        }

        public String getTranslationName() {
            return translationName;
        }

        public double getMinValue() {
            return minValue;
        }

        public double getMaxValue() {
            return maxValue;
        }

        public float getStepSize() {
            return stepSize;
        }

        public void set(double value) {
            this.value = value;
        }

        public void set(boolean value) {
            this.value = value ? 1 : 0;
        }

        public double getDouble() {
            return value;
        }

        public int getInt() {
            return (int) value;
        }

        public boolean getBool() {
            return value > 0;
        }

        public boolean isSlider() {
            return isSlider;
        }
    }

}
