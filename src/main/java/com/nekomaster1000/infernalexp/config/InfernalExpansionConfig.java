package com.nekomaster1000.infernalexp.config;

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
        PIGLIN_FEAR_WARPBEETLE("piglinFearWarpbeetle"),
        PIGLIN_FEAR_EMBODY("piglinFearEmbody"),
        HOGLIN_FEAR_WARPBEETLE("hoglinFearWarpbeetle"),
        HOGLIN_FEAR_EMBODY("hoglinFearEmbody"),
        SPIDER_ATTACK_WARPBEETLE("spiderAttackWarpbeetle"),
        SKELETON_ATTACK_PIGLIN("skeletonAttackPiglin"),
        SKELETON_ATTACK_BRUTE("skeletonAttackBrute"),
        SKELETON_ATTACK_EMBODY("skeletonAttackEmbody"),
        SKELETON_ATTACK_GIANT("skeletonAttackGiant"),
        PIGLIN_ATTACK_SKELETON("piglinAttackSkeleton"),
        PIGLIN_ATTACK_VOLINE("piglinAttackVoline"),
        BRUTE_ATTACK_SKELETON("bruteAttackSkeleton"),
        BRUTE_ATTACK_VOLINE("bruteAttackVoline"),
        GHAST_ATTACK_EMBODY("ghastAttackEmbody"),
        GHAST_ATTACK_VOLINE("ghastAttackVoline"),
        GHAST_ATTACK_SKELETON("ghastAttackSkeleton");

        private final String translationName;
        private boolean value;

        MobInteractions(String translationName) {
            this.translationName = translationName;
            this.value = false;
        }

        public String getTranslationName() {
            return translationName;
        }

        public boolean get() {
            return value;
        }

        public void set(boolean value) {
            this.value = value;
        }
    }

    //Mob Spawning
    public enum MobSpawning {
        VOLINE_WASTES("volineWastes"),
        SHROOMLOIN_CRIMSON("shroomloinCrimson"),
        VOLINE_CRIMSON("volineCrimson"),
        WARPBEETLE_WARPED("warpbeetleWarped"),
        GIANT_DELTAS("giantDeltas"),
        EMBODY_SSV("embodySSV");

        private final String translationName;
        private boolean enabled;
        private int spawnrate;

        MobSpawning(String translationName) {
            this.translationName = translationName;
            this.enabled = false;
            this.spawnrate = 0;
        }

        public String getTranslationName() {
            return translationName;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getSpawnrate() {
            return spawnrate;
        }

        public void setSpawnrate(int spawnrate) {
            this.spawnrate = spawnrate;
        }
    }

    public enum FloraBehaviour {
        SHROOMLIGHT_GROWABLE("isShroomlightGrowable", false, 0, 0, 0),
        SHROOMLIGHT_GROW_CHANCE("shroomlightGrowChance", true, 0, 1 , 0.01f),
        LUMINOUS_FUNGUS_ACTIVATE_DISTANCE("luminousFungusActivateDistance", true, 0, 100, 1);

        private final String translationName;
        private final boolean isSlider;
        private final double minValue;
        private final double maxValue;
        private final float stepSize;
        private double value;

        FloraBehaviour(String translationName, boolean isSlider, double minValue, double maxValue, float stepSize) {
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

}
