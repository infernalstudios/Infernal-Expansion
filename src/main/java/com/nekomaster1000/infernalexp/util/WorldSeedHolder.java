package com.nekomaster1000.infernalexp.util;

public class WorldSeedHolder {
    private static long SEED = 0;

    public static long getSeed() {
        return SEED;
    }

    public static void setSeed(long seed) {
        SEED = seed;
    }
}
