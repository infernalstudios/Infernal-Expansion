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

import net.minecraftforge.common.ForgeConfigSpec;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.IEConfig.MobInteractions;
import org.infernalstudios.infernalexp.config.values.CachedBooleanValue;
import org.infernalstudios.infernalexp.config.values.CachedConfigValue;
import org.infernalstudios.infernalexp.config.values.CachedDoubleValue;

import java.util.EnumMap;

public class CommonConfig {

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

    // World Generation
    final ForgeConfigSpec.BooleanValue biomesListIsWhitelist;
    final ForgeConfigSpec.ConfigValue<String> biomesList;
    final ForgeConfigSpec.BooleanValue replaceNetherBiomeProvider; // Dangerous

    //Bonemeal Behaviour
    final ForgeConfigSpec.DoubleValue shroomlightGrowChance;
    final ForgeConfigSpec.BooleanValue isShroomlightGrowable;

    //Miscellaneous
    final ForgeConfigSpec.BooleanValue fireChargeExplosion;
    final ForgeConfigSpec.IntValue jerkyEffectDuration;
    final ForgeConfigSpec.IntValue jerkyEffectAmplifier;
    //    final ForgeConfigSpec.BooleanValue useHogchops;
    final ForgeConfigSpec.BooleanValue useThrowableBricks;

    //Luminous Fungus
    final ForgeConfigSpec.DoubleValue luminousFungusActivateDistance;
    final ForgeConfigSpec.BooleanValue luminousFungusGivesEffect;

    public static final EnumMap<MobInteractions, CachedConfigValue<?>> mobInteractions = new EnumMap<>(MobInteractions.class);

    CommonConfig(final ForgeConfigSpec.Builder builder) {
        //Mob Interactions
        builder.push("Mob Interactions");

        mobInteractions.put(MobInteractions.PIGLIN_FEAR_WARPBEETLE, new CachedBooleanValue("piglinFearWarpbeetle",
            "Determines if Piglins will run away from Warpbeetles", true, builder));
        mobInteractions.put(MobInteractions.PIGLIN_FEAR_EMBODY, new CachedBooleanValue("piglinFearEmbody",
            "Determines if Piglins will run away from Embodies", true, builder));
        mobInteractions.put(MobInteractions.PIGLIN_FEAR_DWARF, new CachedBooleanValue("piglinFearDwarf",
            "Determines if Piglins will run away from Blackstone Dwarves", true, builder));
        mobInteractions.put(MobInteractions.HOGLIN_FEAR_EMBODY, new CachedBooleanValue("hoglinFearWarpbeetle",
            "Determines if Hoglins will run away from Embodies", true, builder));
        mobInteractions.put(MobInteractions.HOGLIN_FEAR_WARPBEETLE, new CachedBooleanValue("hoglinFearEmbody",
            "Determines if Hoglins will run away from Warpbeetles", true, builder));
        mobInteractions.put(MobInteractions.SPIDER_ATTACK_WARPBEETLE, new CachedBooleanValue("spiderAttackWarpbeetle",
            "Determines if Spiders and Warpbeetles will fight", true, builder));
        mobInteractions.put(MobInteractions.SKELETON_ATTACK_PIGLIN, new CachedBooleanValue("skeletonAttackPiglin",
            "Determines if Skeletons will attack Piglins", true, builder));
        mobInteractions.put(MobInteractions.SKELETON_ATTACK_BRUTE, new CachedBooleanValue("skeletonAttackBrute",
            "Determines if Skeletons will attack Piglin Brutes", true, builder));
        mobInteractions.put(MobInteractions.SKELETON_ATTACK_EMBODY, new CachedBooleanValue("skeletonAttackEmbody",
            "Determines if Skeletons and Embodies will fight", true, builder));
        mobInteractions.put(MobInteractions.SKELETON_ATTACK_GIANT, new CachedBooleanValue("skeletonAttackGiant",
            "Determines if Skeletons and Basalt Giants will fight", true, builder));
        mobInteractions.put(MobInteractions.PIGLIN_ATTACK_SKELETON, new CachedBooleanValue("piglinAttackSkeleton",
            "Determines if Piglins will attack Skeletons", true, builder));
        mobInteractions.put(MobInteractions.PIGLIN_ATTACK_VOLINE, new CachedBooleanValue("piglinAttackVoline",
            "Determines if Piglins will attack Voline\"", true, builder));
        mobInteractions.put(MobInteractions.BRUTE_ATTACK_SKELETON, new CachedBooleanValue("bruteAttackSkeleton",
            "Determines if Piglin Brutes will attack Skeletons", true, builder));
        mobInteractions.put(MobInteractions.BRUTE_ATTACK_VOLINE, new CachedBooleanValue("bruteAttackVoline",
            "Determines if Piglin Brutes will attack Voline", true, builder));
        mobInteractions.put(MobInteractions.GHAST_ATTACK_EMBODY, new CachedBooleanValue("ghastAttackEmbody",
            "Determines if Ghasts will shoot at Embodies", false, builder));
        mobInteractions.put(MobInteractions.GHAST_ATTACK_VOLINE, new CachedBooleanValue("ghastAttackVoline",
            "Determines if Ghasts will shoot at Voline", false, builder));
        mobInteractions.put(MobInteractions.GHAST_ATTACK_SKELETON, new CachedBooleanValue("ghastAttackSkeleton",
            "Determines if Ghasts will shoot at Skeletons", false, builder));
        mobInteractions.put(MobInteractions.GHAST_ATTACK_GLOWSQUITO, new CachedBooleanValue("ghastAttackGlowsquito",
            "Determines if Ghasts will shoot at Glowsquitos\"", false, builder));
        mobInteractions.put(MobInteractions.GLOWSQUITO_ATTACK_DWARF, new CachedBooleanValue("glowsquitoAttackDwarf",
            "Determines if Glowsquitos and Blackstone Dwarves will fight", true, builder));
        mobInteractions.put(MobInteractions.GLOWSQUITO_ATTACK_LUMINOUS, new CachedBooleanValue("glowsquitoAttackLuminous",
            "Determines if Glowsquitos will attack Entities with the Luminance Effect", true, builder));
        mobInteractions.put(MobInteractions.DWARF_ATTACK_PIGLIN, new CachedBooleanValue("dwarfAttackPiglin",
            "Determines if Blackstone Dwarves will attack Piglins", true, builder));
        mobInteractions.put(MobInteractions.DWARF_ATTACK_ZOMBIE_PIGLIN, new CachedBooleanValue("dwarfAttackZombiePiglin",
            "Determines if Blackstone Dwarves will attack Zombified Piglins", true, builder));
        mobInteractions.put(MobInteractions.DWARF_ATTACK_PLAYER, new CachedBooleanValue("dwarfAttackPlayer",
            "Determines if Blackstone Dwarves will attack Players", true, builder));
        mobInteractions.put(MobInteractions.BLINDSIGHT_ATTACK_GLOWSQUITO, new CachedBooleanValue("blindsightAttackGlowsquito",
            "Determines if Blindsights will attack Glowsquitos", true, builder));
        mobInteractions.put(MobInteractions.BLINDSIGHT_ATTACK_PLAYER, new CachedBooleanValue("blindsightAttackPlayer",
            "Determines if Blindsights will attack Players", true, builder));
        mobInteractions.put(MobInteractions.GIANT_ATTACK_MAGMA_CUBE, new CachedBooleanValue("giantAttackMagmaCube",
            "Determines if Basalt Giants will attack Magma Cubes", true, builder));
        mobInteractions.put(MobInteractions.EMBODY_ATTACK_PIGLIN, new CachedBooleanValue("embodyAttackPiglin",
            "Determines if Embodies will attack Piglins", true, builder));
        mobInteractions.put(MobInteractions.EMBODY_ATTACK_PLAYER, new CachedBooleanValue("embodyAttackPlayer",
            "Determines if Embodies will attack Players", true, builder));
        mobInteractions.put(MobInteractions.VOLINE_ATTACK_FIRE_RESISTANCE, new CachedBooleanValue("volineAttackFireResistance",
            "Determines if Voline will attack Entities with Fire Resistance", true, builder));
        mobInteractions.put(MobInteractions.VOLINE_ATTACK_PLAYER, new CachedBooleanValue("volineAttackPlayer",
            "Determines if Voline will attack Players", true, builder));
        mobInteractions.put(MobInteractions.VOLINE_ATTACK_MAGMA_CUBE, new CachedBooleanValue("volineAttackMagmaCube",
            "Determines if Voline will attack small Magma Cubes", true, builder));
        mobInteractions.put(MobInteractions.USE_HOGCHOPS, new CachedBooleanValue("useHogchops",
            "Determines if Hogchops will replace Porkchops in Hoglin Drops", true, builder));
        mobInteractions.put(MobInteractions.GLOWSILK_SPEED, new CachedDoubleValue("glowsilkSpeed",
            "Determines the speed at which Glowsilk Moths fly", 1.3D, 0.0D, 10.0D, 0.2F, builder));

        builder.pop();

        //Mob Spawning
        builder.push("Mob Spawning");

        //Spawnable biomes
        builder.push("Spawnable Biomes");

        volineBiomes = builder
            .comment("Determines what biomes Volines will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.voline.biomes")
            .define("volineBiomes", "minecraft:nether_wastes, minecraft:crimson_forest, byg:magma_wastes");

        warpbeetleBiomes = builder
            .comment("Determines what biomes Warpbeetles will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.warpbeetle.biomes")
            .define("warpbeetleBiomes", "minecraft:warped_forest, byg:warped_desert");

        shroomloinBiomes = builder
            .comment("Determines what biomes Shroomloins will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.shroomloin.biomes")
            .define("shroomloinBiomes", "minecraft:crimson_forest, byg:crimson_gardens, byg:glowstone_gardens, byg:embur_bog, byg:sythian_torrids");

        basaltGiantBiomes = builder
            .comment("Determines what biomes Basalt Giants will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.basalt_giant.biomes")
            .define("basaltGiantBiomes", "minecraft:basalt_deltas, infernalexp:delta_shores, byg:magma_wastes");

        embodyBiomes = builder
            .comment("Determines what biomes Embodies will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.embody.biomes")
            .define("embodyBiomes", "minecraft:soul_sand_valley, byg:warped_desert, byg:wailing_garth, byg:withering_woods");

        glowsquitoBiomes = builder
            .comment("Determines what biomes Glowsquitos will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsquito.biomes")
            .define("glowsquitoBiomes", "infernalexp:glowstone_canyon, byg:glowstone_gardens");

        glowsilkMothBiomes = builder
            .comment("Determines what biomes Glowsilk Moths will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.glowsilk_moth.biomes")
            .define("glowsilkMothBiomes", "minecraft:crimson_forest, minecraft:basalt_deltas, infernalexp:glowstone_canyon, byg:glowstone_gardens");

        blindsightBiomes = builder
            .comment("Determines what biomes Blindsights will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.blindsight.biomes")
            .define("blindsightBiomes", "infernalexp:glowstone_canyon, byg:glowstone_gardens");

        blackstoneDwarfBiomes = builder
            .comment("Determines what biomes Blackstone Dwarfs will spawn in")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.blackstone_dwarf.biomes")
            .define("blackstoneDwarfBiomes", "infernalexp:glowstone_canyon, byg:magma_wastes");

        builder.pop();

        builder.pop();

        // World Generation
        builder.push("World Generation");

        biomesListIsWhitelist = builder
            .comment("Should the biome list below act as a whitelist (True/On), or a blacklist (False/Off).")
            .comment("CHANGING THIS REQUIRES A GAME RESTART")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.biomesListIsWhitelist")
            .define("biomesListIsWhitelist", false);

        biomesList = builder
            .comment("List of biomes to either whitelist or blacklist from nether generation. Split biomes with a comma. To include all nether biomes from all loaded mods leave this blank.")
            .comment("CHANGING THIS REQUIRES A GAME RESTART")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.biomesList")
            .define("biomesList", "");

        builder.push("Dangerous Configs");

        replaceNetherBiomeProvider = builder
            .comment("Replaces existing worlds' vanilla nether biome provider with ours.\nTHIS CAN BREAK YOUR WORLD, DO NOT TURN ON IF YOU DON'T KNOW WHAT YOU'RE DOING")
            .translation(InfernalExpansion.MOD_ID + ".config.replaceNetherBiomeProvider")
            .define("replaceNetherBiomeProvider", false);

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

        useThrowableBricks = builder
            .comment("Determines if bricks are throwable or not")
            .translation(InfernalExpansion.MOD_ID + ".config.tooltip.useThrowableBricks")
            .define("useThrowableBricks", true);

        builder.pop();
    }
}
