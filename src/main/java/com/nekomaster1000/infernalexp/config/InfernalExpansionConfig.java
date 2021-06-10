package com.nekomaster1000.infernalexp.config;

import javax.annotation.Nonnull;
import java.util.Arrays;

public final class InfernalExpansionConfig {

    //Client

    //Luminous Effect
    public enum ClientConfig {
        LUMINOUS_REFRESH_RATE("luminousRefreshRate", true, 1, 20, 1);

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

    //Mob Interactions
    //Create enum and set values to false. These will get overridden by ConfigHelper to the actual config values
    public enum MobInteractions{
        PIGLIN_FEAR_WARPBEETLE("piglinFearWarpbeetle", false),
        PIGLIN_FEAR_EMBODY("piglinFearEmbody", false),
        PIGLIN_FEAR_DWARF("piglinFearDwarf", false),
        HOGLIN_FEAR_WARPBEETLE("hoglinFearWarpbeetle", false),
        HOGLIN_FEAR_EMBODY("hoglinFearEmbody", false),
        SPIDER_ATTACK_WARPBEETLE("spiderAttackWarpbeetle", false),
        SKELETON_ATTACK_PIGLIN("skeletonAttackPiglin", false),
        SKELETON_ATTACK_BRUTE("skeletonAttackBrute", false),
        SKELETON_ATTACK_EMBODY("skeletonAttackEmbody", false),
        SKELETON_ATTACK_GIANT("skeletonAttackGiant", false),
        PIGLIN_ATTACK_SKELETON("piglinAttackSkeleton", false),
        PIGLIN_ATTACK_VOLINE("piglinAttackVoline", false),
        BRUTE_ATTACK_SKELETON("bruteAttackSkeleton", false),
        BRUTE_ATTACK_VOLINE("bruteAttackVoline", false),
        GHAST_ATTACK_EMBODY("ghastAttackEmbody", false),
        GHAST_ATTACK_VOLINE("ghastAttackVoline", false),
        GHAST_ATTACK_SKELETON("ghastAttackSkeleton", false),
        GHAST_ATTACK_GLOWSQUITO("ghastAttackGlowsquito", false),
        GLOWSQUITO_ATTACK_DWARF("glowsquitoAttackDwarf", false),
        GLOWSQUITO_ATTACK_LUMINOUS("glowsquitoAttackLuminous", false),
        DWARF_ATTACK_PIGLIN("dwarfAttackPiglin", false),
        DWARF_ATTACK_ZOMBIE_PIGLIN("dwarfAttackZombiePiglin", false),
        DWARF_ATTACK_PLAYER("dwarfAttackPlayer", false),
        BLINDSIGHT_ATTACK_GLOWSQUITO("blindsightAttackGlowsquito", false),
        BLINDSIGHT_ATTACK_PLAYER("blindsightAttackPlayer", false),
        GIANT_ATTACK_MAGMA_CUBE("giantAttackMagmaCube", false),
        EMBODY_ATTACK_PIGLIN("embodyAttackPiglin", false),
        EMBODY_ATTACK_PLAYER("embodyAttackPlayer", false),
        VOLINE_ATTACK_FIRE_RESISTANCE("volineAttackFireResistance", false),
        VOLINE_ATTACK_PLAYER("volineAttackPlayer", false),
        VOLINE_ATTACK_MAGMA_CUBE("volineAttackMagmaCube", false),
        GLOWSILK_SPEED("glowsilkSpeed", true);

        private final String translationName;
		private final boolean isSlider;
        private boolean booleanValue;
        private double doubleValue;

        MobInteractions(String translationName, boolean isSlider) {
            this.translationName = translationName;
            this.isSlider = isSlider;
            this.booleanValue = false;
            this.doubleValue = 0.0D;
        }

        public String getTranslationName() {
            return translationName;
        }

        public boolean getBoolean() {
            return booleanValue;
        }

        public void setBoolean(boolean value) {
            this.booleanValue = value;
        }

        public double getDouble() {
            return doubleValue;
        }

        public void setDouble(double value) {
            this.doubleValue = value;
        }

        public boolean isSlider() {
            return isSlider;
        }
    }

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
            return MobSpawning.valueOf(name.toUpperCase());
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
        SHROOMLIGHT_GROW_CHANCE("shroomlightGrowChance", true, 0, 1 , 0.01f),
        LUMINOUS_FUNGUS_ACTIVATE_DISTANCE("luminousFungusActivateDistance", true, 0, 100, 1),
        FIRE_CHARGE_EXPLOSION("fireChargeExplosion", false, 0, 1, 1),
        JERKY_EFFECT_DURATION("jerkyEffectDuration", true, 1, 120, 1),
        JERKY_EFFECT_AMPLIFIER("jerkyEffectAmplifier", true, 0, 2, 1),
        LUMINOUS_FUNGUS_GIVES_EFFECT("luminousFungusGivesEffect", false, 0, 0, 0);

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
