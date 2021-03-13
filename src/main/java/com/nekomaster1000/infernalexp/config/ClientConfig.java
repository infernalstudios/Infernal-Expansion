package com.nekomaster1000.infernalexp.config;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    final ForgeConfigSpec.IntValue luminousRefreshRate;
    
    ClientConfig(final ForgeConfigSpec.Builder builder){
        builder.push("general");

        // Luminous Effect
        luminousRefreshRate = builder
                .comment("Determines how much (in ticks) the luminous effect should update")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.luminousRefreshRate")
                .defineInRange("luminousRefreshRate", 2, 1, Integer.MAX_VALUE);

        builder.pop();
    }
}
