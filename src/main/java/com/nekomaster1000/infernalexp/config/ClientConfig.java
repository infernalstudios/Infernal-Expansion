package com.nekomaster1000.infernalexp.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    ClientConfig(final ForgeConfigSpec.Builder builder){
        builder.push("general");

        //Add Client Configs Here

        builder.pop();
    }
}
