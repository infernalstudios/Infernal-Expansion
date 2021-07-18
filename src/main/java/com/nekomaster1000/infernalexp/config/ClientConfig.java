package com.nekomaster1000.infernalexp.config;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    final ForgeConfigSpec.IntValue luminousRefreshDelay;
    
    ClientConfig(final ForgeConfigSpec.Builder builder){
        builder.push("general");

        // Luminous Effect
        luminousRefreshDelay = builder
                .comment("Determines how often (in ticks) the luminous effect should update")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.luminousRefreshDelay")
                .defineInRange("luminousRefreshDelay", 2, 1, Integer.MAX_VALUE);

        builder.pop();
    }
}
