package com.nekomaster1000.infernalexp.config;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    //Mob Interactions
    final ForgeConfigSpec.BooleanValue piglinFearWarpbeetle;
    final ForgeConfigSpec.BooleanValue piglinFearEmbody;
    final ForgeConfigSpec.BooleanValue piglinFearDwarf;
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
    final ForgeConfigSpec.BooleanValue ghastAttackGlowsquito;
    final ForgeConfigSpec.BooleanValue glowsquitoAttackDwarf;
    final ForgeConfigSpec.BooleanValue glowsquitoAttackLuminous;
    final ForgeConfigSpec.BooleanValue dwarfAttackPiglin;
    final ForgeConfigSpec.BooleanValue dwarfAttackZombiePiglin;
    final ForgeConfigSpec.BooleanValue dwarfAttackPlayer;
    final ForgeConfigSpec.BooleanValue blindsightAttackGlowsquito;
    final ForgeConfigSpec.BooleanValue blindsightAttackPlayer;
    final ForgeConfigSpec.BooleanValue giantAttackMagmaCube;
    final ForgeConfigSpec.BooleanValue embodyAttackPiglin;
    final ForgeConfigSpec.BooleanValue embodyAttackPlayer;
    final ForgeConfigSpec.BooleanValue volineAttackFireResistance;
    final ForgeConfigSpec.BooleanValue volineAttackPlayer;
    final ForgeConfigSpec.BooleanValue volineAttackMagmaCube;
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
    
    //Bonemeal Behaviour
    final ForgeConfigSpec.DoubleValue shroomlightGrowChance;
    final ForgeConfigSpec.BooleanValue isShroomlightGrowable;

    //Miscellaneous
    final ForgeConfigSpec.BooleanValue fireChargeExplosion;
    final ForgeConfigSpec.IntValue jerkyEffectDuration;
    final ForgeConfigSpec.IntValue jerkyEffectAmplifier;
    
    //Luminous Fungus
    final ForgeConfigSpec.DoubleValue luminousFungusActivateDistance;
    final ForgeConfigSpec.BooleanValue luminousFungusGivesEffect;

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

        piglinFearDwarf = builder
            .comment("Determines if Piglins will run away from Blackstone Dwarves")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.piglinFearDwarf")
            .define("piglinFearDwarf", true);

        hoglinFearWarpbeetle = builder
                .comment("Determines if Hoglins will run away from Warpbeetles")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.hoglinFearWarpbeetle")
                .define("hoglinFearWarpbeetle", true);

        hoglinFearEmbody = builder
                .comment("Determines if Hoglins will run away from Embodies")
                .translation(InfernalExpansion.MOD_ID + ".config.tooltip.hoglinFearEmbody")
                .define("hoglinFearEmbody", true);

        spiderAttackWarpbeetle = builder
                .comment("Determines if Spiders and Warpbeetles will fight")
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

        ghastAttackGlowsquito = builder
            .comment("Determines if Ghasts will shoot at Glowsquitos")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.ghastAttackGlowsquito")
            .define("ghastAttackGlowsquito", false);

        glowsquitoAttackDwarf = builder
            .comment("Determines if Glowsquitos and Blackstone Dwarves will fight")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsquitoAttackDwarf")
            .define("glowsquitoAttackDwarf", true);

        glowsquitoAttackLuminous = builder
            .comment("Determines if Glowsquitos will attack Entities with the Luminous Effect")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsquitoAttackLuminous")
            .define("glowsquitoAttackLuminous", true);

        dwarfAttackPiglin = builder
            .comment("Determines if Blackstone Dwarves will attack Piglins")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.dwarfAttackPiglin")
            .define("dwarfAttackPiglin", true);

        dwarfAttackZombiePiglin = builder
            .comment("Determines if Blackstone Dwarves will attack Zombified Piglins")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.dwarfAttackZombiePiglin")
            .define("dwarfAttackZombiePiglin", true);

        dwarfAttackPlayer = builder
            .comment("Determines if Blackstone Dwarves will attack Players")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.dwarfAttackPlayer")
            .define("dwarfAttackPlayer", true);

        blindsightAttackGlowsquito = builder
            .comment("Determines if Blindsights will attack Glowsquitos")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.blindsightAttackGlowsquito")
            .define("blindsightAttackGlowsquito", true);

        blindsightAttackPlayer = builder
            .comment("Determines if Blindsights will attack Players")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.blindsightAttackPlayer")
            .define("blindsightAttackPlayer", true);

        giantAttackMagmaCube = builder
            .comment("Determines if Basalt Giants will attack Magma Cubes")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.giantAttackMagmaCube")
            .define("giantAttackMagmaCube", true);

        embodyAttackPiglin = builder
            .comment("Determines if Embodies will attack Piglins")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.embodyAttackPiglin")
            .define("embodyAttackPiglin", true);

        embodyAttackPlayer = builder
            .comment("Determines if Embodies will attack Players")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.embodyAttackPlayer")
            .define("embodyAttackPlayer", true);

        volineAttackFireResistance = builder
            .comment("Determines if Voline will attack Entities with Fire Resistance")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineAttackFireResistance")
            .define("volineAttackFireResistance", true);

        volineAttackPlayer = builder
            .comment("Determines if Voline will attack Players")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineAttackPlayer")
            .define("volineAttackPlayer", true);

        volineAttackMagmaCube = builder
            .comment("Determines if Voline will attack small Magma Cubes")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.volineAttackMagmaCube")
            .define("volineAttackMagmaCube", true);

        glowsilkSpeed = builder
            .comment("Determines the speed at which Glowsilk Moths fly")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilkSpeed")
            .defineInRange("glowsilkSpeed", 1.3D, 0.0D, Double.MAX_VALUE);

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

        builder.push("Miscellaneous");

        fireChargeExplosion = builder
            .comment("Determines if thrown fire charges will explode on impact")
            .translation(InfernalExpansion.MOD_ID + ".config.fireChargeExplosion")
            .define("fireChargeExplosion", true);

        jerkyEffectDuration = builder
            .comment("Determines the duration in seconds of the effect that Cured Jerky gives")
            .translation(InfernalExpansion.MOD_ID + ".config.jerkyEffectDuration")
            .defineInRange("jerkyEffectDuration", 8, 0, Integer.MAX_VALUE);

        jerkyEffectAmplifier = builder
            .comment("Determines the amplifier of the effect that Cured Jerky gives")
            .translation(InfernalExpansion.MOD_ID + ".config.jerkyEffectAmplifier")
            .defineInRange("jerkyEffectAmplifier", 1, 0, 2);
        
        builder.push("Luminous Fungus");
        
        luminousFungusActivateDistance = builder
                .comment("Determines the distance an entity has to be from a luminous fungus for it to activate (larger values have performance impact)")
                .translation(InfernalExpansion.MOD_ID + ".config.luminousFungusActivateDistance")
                .defineInRange("luminousFungusActivateDistance", 4.0D, 0.0D, Double.MAX_VALUE);

        luminousFungusGivesEffect = builder
            .comment("Determines whether Luminous Fungus gives the Luminance effect on collision with an entity")
            .translation(InfernalExpansion.MOD_ID + ".config.luminousFungusActivateDistance")
            .define("luminousFungusGivesEffect", true);

        builder.pop();
    }
}
