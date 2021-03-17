package com.nekomaster1000.infernalexp.config;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    //Mob Interactions
    final ForgeConfigSpec.BooleanValue piglinFearWarpbeetle;
    final ForgeConfigSpec.BooleanValue piglinFearEmbody;
    final ForgeConfigSpec.BooleanValue hoglinFearWarpbeetle;
    final ForgeConfigSpec.BooleanValue hoglinFearEmbody;
    final ForgeConfigSpec.BooleanValue spiderAttackWarpbeetle;
    final ForgeConfigSpec.BooleanValue skeletonAttackPiglin;
    final ForgeConfigSpec.BooleanValue skeletonAttackBrute;
    final ForgeConfigSpec.BooleanValue skeletonAttackEmbody;
    final ForgeConfigSpec.BooleanValue skeletonAttackGiant;
    final ForgeConfigSpec.BooleanValue piglinAttackSkeleton;
    final ForgeConfigSpec.BooleanValue piglinAttackVoline;
    final ForgeConfigSpec.BooleanValue bruteAttackSkeleton;
    final ForgeConfigSpec.BooleanValue bruteAttackVoline;
    final ForgeConfigSpec.BooleanValue ghastAttackEmbody;
    final ForgeConfigSpec.BooleanValue ghastAttackVoline;
    final ForgeConfigSpec.BooleanValue ghastAttackSkeleton;

    //Mob Spawning Booleans
    final ForgeConfigSpec.BooleanValue volineInWastes;
    final ForgeConfigSpec.BooleanValue shroomloinInCrimson;
    final ForgeConfigSpec.BooleanValue volineInCrimson;
    final ForgeConfigSpec.BooleanValue warpbeetleInWarped;
    final ForgeConfigSpec.BooleanValue giantInDeltas;
    final ForgeConfigSpec.BooleanValue embodyInSSV;
    final ForgeConfigSpec.BooleanValue glowsilkInGSC;
    final ForgeConfigSpec.BooleanValue glowsilkInDeltas;
    final ForgeConfigSpec.BooleanValue glowsilkInCrimson;

    //Mob Spawn Rates
    final ForgeConfigSpec.IntValue volineWastesRate;
    final ForgeConfigSpec.IntValue shroomloinCrimsonRate;
    final ForgeConfigSpec.IntValue volineCrimsonRate;
    final ForgeConfigSpec.IntValue warpbeetleWarpedRate;
    final ForgeConfigSpec.IntValue giantDeltasRate;
    final ForgeConfigSpec.IntValue embodySSVRate;
    final ForgeConfigSpec.IntValue glowsilkGSCRate;
    final ForgeConfigSpec.IntValue glowsilkDeltasRate;
    final ForgeConfigSpec.IntValue glowsilkCrimsonRate;
    
    //Bonemeal Behaviour
    final ForgeConfigSpec.DoubleValue shroomlightGrowChance;
    final ForgeConfigSpec.BooleanValue isShroomlightGrowable;
    
    //Luminous Fungus
    final ForgeConfigSpec.DoubleValue luminousFungusActivateDistance;

    CommonConfig(final ForgeConfigSpec.Builder builder){
        //Mob Interactions
        builder.push("Mob Interactions");

        piglinFearWarpbeetle = builder
                .comment("Determines if Piglins will run away from Warpbeetles")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.piglinFearWarpbeetle")
                .define("piglinFearWarpbeetle", true);

        piglinFearEmbody = builder
                .comment("Determines if Piglins will run away from Embodies")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.piglinFearEmbody")
                .define("piglinFearEmbody", true);

        hoglinFearWarpbeetle = builder
                .comment("Determines if Hoglins will run away from Warpbeetles")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.hoglinFearWarpbeetle")
                .define("hoglinFearWarpbeetle", true);

        hoglinFearEmbody = builder
                .comment("Determines if Hoglins will run away from Embodies")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.hoglinFearEmbody")
                .define("hoglinFearEmbody", true);

        spiderAttackWarpbeetle = builder
                .comment("Determines if Spiders will attack Warpbeetles")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.spiderAttackWarpbeetle")
                .define("spiderAttackWarpbeetle", true);

        skeletonAttackPiglin = builder
                .comment("Determines if Skeletons will attack Piglins")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.skeletonAttackPiglin")
                .define("skeletonAttackPiglin", true);

        skeletonAttackBrute = builder
                .comment("Determines if Skeletons will attack Piglin Brutes")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.skeletonAttackBrute")
                .define("skeletonAttackBrute", true);

        skeletonAttackEmbody = builder
                .comment("Determines if Skeletons and Embodies will fight")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.skeletonAttackEmbody")
                .define("skeletonAttackEmbody", true);

        skeletonAttackGiant = builder
                .comment("Determines if Skeletons and Basalt Giants will fight")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.skeletonAttackGiant")
                .define("skeletonAttackGiant", true);

        piglinAttackSkeleton = builder
                .comment("Determines if Piglins will attack Skeletons")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.piglinAttackSkeleton")
                .define("piglinAttackSkeleton", true);

        piglinAttackVoline = builder
                .comment("Determines if Piglins will attack Volines")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.piglinAttackVoline")
                .define("piglinAttackVoline", true);

        bruteAttackSkeleton = builder
                .comment("Determines if Piglin Brutes will attack Skeletons")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.bruteAttackSkeleton")
                .define("bruteAttackSkeleton", true);

        bruteAttackVoline = builder
                .comment("Determines if Piglin Brutes will attack Volines")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.bruteAttackVoline")
                .define("bruteAttackVoline", true);

        ghastAttackEmbody = builder
                .comment("Determines if Ghasts will shoot at Embodies")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.ghastAttackEmbody")
                .define("ghastAttackEmbody", false);

        ghastAttackVoline = builder
                .comment("Determines if Ghasts will shoot at Volines")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.ghastAttackVoline")
                .define("ghastAttackVoline", false);

        ghastAttackSkeleton = builder
                .comment("Determines if Ghasts will shoot at Skeletons")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.ghastAttackSkeleton")
                .define("ghastAttackSkeleton", false);

        builder.pop();

        //Mob Spawning
        builder.push("Mob Spawning");

        //Spawn Booleans
        builder.push("Toggle Spawns");

        volineInWastes = builder
                .comment("Determines if Volines will spawn in Nether Wastes")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineWastes.enable")
                .define("volineInWastes", true);

        shroomloinInCrimson = builder
                .comment("Determines if Shroomloins will spawn in the Crimson Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.shroomloinCrimson.enable")
                .define("shroomloinInCrimson", true);

        volineInCrimson = builder
                .comment("Determines if Volines will spawn in Crimson Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineCrimson.enable")
                .define("volineInCrimson", true);

        warpbeetleInWarped = builder
                .comment("Determines if Warpbeetles will spawn in Warped Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.warpbeetleWarped.enable")
                .define("warpbeetleInWarped", true);

        giantInDeltas = builder
                .comment("Determines if Basalt Giants will spawn in Basalt Deltas")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.giantDeltas.enable")
                .define("giantInDeltas", true);

        embodyInSSV = builder
                .comment("Determines if Embodies will spawn in the Soul Sand Valleys")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.embodySSV.enable")
                .define("embodyInSSV", true);

        glowsilkInGSC = builder
                .comment("Determines if Glowsilk Moths will spawn in Glowstone Canyons")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkGSC.enable")
                .define("glowsilkInGSC", true);

        glowsilkInDeltas = builder
                .comment("Determines if Glowsilk Moths will spawn in Basalt Deltas")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkDeltas.enable")
                .define("glowsilkInDeltas", true);

        glowsilkInCrimson = builder
                .comment("Determines if Glowsilk Moths will spawn in Crimson Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkCrimson.enable")
                .define("glowsilkCrimson", true);

        builder.pop();

      //Spawn Rates
        builder.push("Spawn Rates");

        volineWastesRate = builder
                .comment("Determines the rate at which Volines spawn in the Nether Wastes")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineWastes.spawnrate")
                .defineInRange("volineWastesRate", 50, 0, Integer.MAX_VALUE);

        shroomloinCrimsonRate = builder
                .comment("Determines the rate at which Shroomloins spawn in the Crimson Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.shroomloinCrimson.spawnrate")
                .defineInRange("shroomloinCrimsonRate", 5, 0, Integer.MAX_VALUE);

        volineCrimsonRate = builder
                .comment("Determines the rate at which Volines spawn in the Crimson Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineCrimson.spawnrate")
                .defineInRange("volineCrimsonRate", 1, 0, Integer.MAX_VALUE);

        warpbeetleWarpedRate = builder
                .comment("Determines the rate at which Warpbeetles spawn in the Warped Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.warpbeetleWarped.spawnrate")
                .defineInRange("warpbeetleWarpedRate", 1, 0, Integer.MAX_VALUE);

        giantDeltasRate = builder
                .comment("Determines the rate at which Basalt Giants spawn in the Basalt Deltas")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.giantDeltas.spawnrate")
                .defineInRange("giantDeltasRate", 30, 0, Integer.MAX_VALUE);

        embodySSVRate = builder
                .comment("Determines the rate at which Embodies spawn in the Soul Sand Valleys")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.embodySSV.spawnrate")
                .defineInRange("embodySSVRate", 60, 0, Integer.MAX_VALUE);

        glowsilkGSCRate = builder
                .comment("Determines the rate at which Glowsilk Moths spawn in the Glowstone Canyon")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkGSC.spawnrate")
                .defineInRange("glowsilkGSCRate", 1, 0, Integer.MAX_VALUE);

        glowsilkDeltasRate = builder
                .comment("Determines the rate at which Glowsilk Moths spawn in the Basalt Deltas")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkDeltas.spawnrate")
                .defineInRange("glowsilkDeltasRate", 2, 0, Integer.MAX_VALUE);

        glowsilkCrimsonRate = builder
                .comment("Determines the rate at which Glowsilk Moths spawn in the Crimson Forests")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkCrimson.spawnrate")
                .defineInRange("glowsilkCrimsonRate", 2, 0, Integer.MAX_VALUE);

        builder.pop();
        
        builder.pop();

      //Bonemeal Behaviour
        builder.push("Bonemeal Behaviour");
        
        isShroomlightGrowable = builder
                .comment("Determines if a shroomlight tear will grow when a shroomlight is bonemealed (overrides shroomlightGrowChance)")
                .translation(InfernalExpansion.MOD_ID + ".config.isShroomlightGrowable")
                .define("isShroomlightGrowable", true);
        
        shroomlightGrowChance = builder
                .comment("Determines the chance a shroomlight tear will grow when a shroomlight is bonemealed")
                .translation(InfernalExpansion.MOD_ID + ".config.shroomlightGrowChance")
                .defineInRange("shroomlightGrowChance", 1.0D, 0.0D, 1.0D);

        builder.pop();
        
        builder.push("Luminous Fungus");
        
        luminousFungusActivateDistance = builder
                .comment("Determines the distance an entity has to be from a luminous fungus for it to activate (larger values have performance impact)")
                .translation(InfernalExpansion.MOD_ID + ".config.luminousFungusActivateDistance")
                .defineInRange("luminousFungusActivateDistance", 4.0D, 0.0D, Double.MAX_VALUE);

        builder.pop();
    }
}
