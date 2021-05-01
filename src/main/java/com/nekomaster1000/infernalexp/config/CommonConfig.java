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
    final ForgeConfigSpec.DoubleValue glowsilkSpeed;

    //Mob Spawnable Biomes
    final ForgeConfigSpec.ConfigValue<String> volineBiomes;
    final ForgeConfigSpec.ConfigValue<String> warpbeetleBiomes;
    final ForgeConfigSpec.ConfigValue<String> shroomloinBiomes;
    final ForgeConfigSpec.ConfigValue<String> basaltGiantBiomes;
    final ForgeConfigSpec.ConfigValue<String> embodyBiomes;
    final ForgeConfigSpec.ConfigValue<String> glowsquitoBiomes;
    final ForgeConfigSpec.ConfigValue<String> glowsilkMothBiomes;
    final ForgeConfigSpec.ConfigValue<String> blindsightBiomes;
    final ForgeConfigSpec.ConfigValue<String> blackstoneDwarfBiomes;
    final ForgeConfigSpec.ConfigValue<String> skeletalPiglinBiomes;
    
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

        glowsilkSpeed = builder
            .comment("Determines the speed at which Glowsilk Moths fly")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkSpeed")
            .defineInRange("glowsilkSpeed", 3.0D, 0.0D, Double.MAX_VALUE);

        builder.pop();

        //Mob Spawning
        builder.push("Mob Spawning");

        //Spawnable biomes
        builder.push("Spawnable Biomes");

        volineBiomes = builder
            .comment("Determines what biomes Volines will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.voline.biomes")
            .define("volineBiomes", "minecraft:nether_wastes, minecraft:crimson_forest");

        warpbeetleBiomes = builder
            .comment("Determines what biomes Warpbeetles will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.warpbeetle.biomes")
            .define("warpbeetleBiomes", "minecraft:warped_forest");

        shroomloinBiomes = builder
            .comment("Determines what biomes Shroomloins will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.shroomloin.biomes")
            .define("shroomloinBiomes", "minecraft:crimson_forest");

        basaltGiantBiomes = builder
            .comment("Determines what biomes Basalt Giants will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.basalt_giant.biomes")
            .define("basaltGiantBiomes", "minecraft:basalt_deltas, infernalexp:delta_shores");

        embodyBiomes = builder
            .comment("Determines what biomes Embodies will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.embody.biomes")
            .define("embodyBiomes", "minecraft:soul_sand_valley");

        glowsquitoBiomes = builder
            .comment("Determines what biomes Glowsquitos will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsquito.biomes")
            .define("glowsquitoBiomes", "infernalexp:glowstone_canyon");

        glowsilkMothBiomes = builder
            .comment("Determines what biomes Glowsilk Moths will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilk_moth.biomes")
            .define("glowsilkMothBiomes", "minecraft:crimson_forest, minecraft:basalt_deltas, infernalexp:glowstone_canyon");

        blindsightBiomes = builder
            .comment("Determines what biomes Blindsights will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.blindsight.biomes")
            .define("blindsightBiomes", "infernalexp:glowstone_canyon");

        blackstoneDwarfBiomes = builder
            .comment("Determines what biomes Blackstone Dwarfs will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.blackstone_dwarf.biomes")
            .define("blackstoneDwarfBiomes", "infernalexp:glowstone_canyon");

        skeletalPiglinBiomes = builder
            .comment("Determines what biomes Skeletal Piglins will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.skeletal_piglin.biomes")
            .define("skeletalPiglinBiomes", "minecraft:soul_sand_valley, infernalexp:glowstone_canyon, infernalexp:delta_shores");

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
