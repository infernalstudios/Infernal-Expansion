package com.nekomaster1000.infernalexp.config;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.ClientConfig;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.Miscellaneous;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.MobInteractions;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.MobSpawning;

import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.Nullable;

public final class ConfigHelper {

    //Client
    public static void bakeClient(@Nullable final ModConfig config) {
        //Luminous Effect
        ClientConfig.LUMINOUS_REFRESH_RATE.set(ConfigHolder.CLIENT.luminousRefreshRate.get());
    }

    //Common
    public static void bakeCommon(@Nullable final ModConfig config) {
        //Mob Interactions
        MobInteractions.PIGLIN_FEAR_WARPBEETLE.setBoolean(ConfigHolder.COMMON.piglinFearWarpbeetle.get());
        MobInteractions.PIGLIN_FEAR_EMBODY.setBoolean(ConfigHolder.COMMON.piglinFearEmbody.get());
        MobInteractions.PIGLIN_FEAR_DWARF.setBoolean(ConfigHolder.COMMON.piglinFearDwarf.get());
        MobInteractions.HOGLIN_FEAR_WARPBEETLE.setBoolean(ConfigHolder.COMMON.hoglinFearWarpbeetle.get());
        MobInteractions.HOGLIN_FEAR_EMBODY.setBoolean(ConfigHolder.COMMON.hoglinFearEmbody.get());
        MobInteractions.SPIDER_ATTACK_WARPBEETLE.setBoolean(ConfigHolder.COMMON.spiderAttackWarpbeetle.get());
        MobInteractions.SKELETON_ATTACK_PIGLIN.setBoolean(ConfigHolder.COMMON.skeletonAttackPiglin.get());
        MobInteractions.SKELETON_ATTACK_BRUTE.setBoolean(ConfigHolder.COMMON.skeletonAttackBrute.get());
        MobInteractions.SKELETON_ATTACK_EMBODY.setBoolean(ConfigHolder.COMMON.skeletonAttackEmbody.get());
        MobInteractions.SKELETON_ATTACK_GIANT.setBoolean(ConfigHolder.COMMON.skeletonAttackGiant.get());
        MobInteractions.PIGLIN_ATTACK_SKELETON.setBoolean(ConfigHolder.COMMON.piglinAttackSkeleton.get());
        MobInteractions.PIGLIN_ATTACK_VOLINE.setBoolean(ConfigHolder.COMMON.piglinAttackVoline.get());
        MobInteractions.BRUTE_ATTACK_SKELETON.setBoolean(ConfigHolder.COMMON.bruteAttackSkeleton.get());
        MobInteractions.BRUTE_ATTACK_VOLINE.setBoolean(ConfigHolder.COMMON.bruteAttackVoline.get());
        MobInteractions.GHAST_ATTACK_EMBODY.setBoolean(ConfigHolder.COMMON.ghastAttackEmbody.get());
        MobInteractions.GHAST_ATTACK_VOLINE.setBoolean(ConfigHolder.COMMON.ghastAttackVoline.get());
        MobInteractions.GHAST_ATTACK_SKELETON.setBoolean(ConfigHolder.COMMON.ghastAttackSkeleton.get());
        MobInteractions.GHAST_ATTACK_GLOWSQUITO.setBoolean(ConfigHolder.COMMON.ghastAttackGlowsquito.get());
        MobInteractions.GLOWSQUITO_ATTACK_DWARF.setBoolean(ConfigHolder.COMMON.glowsquitoAttackDwarf.get());
        MobInteractions.GLOWSQUITO_ATTACK_LUMINOUS.setBoolean(ConfigHolder.COMMON.glowsquitoAttackLuminous.get());
        MobInteractions.DWARF_ATTACK_PIGLIN.setBoolean(ConfigHolder.COMMON.dwarfAttackPiglin.get());
        MobInteractions.DWARF_ATTACK_ZOMBIE_PIGLIN.setBoolean(ConfigHolder.COMMON.dwarfAttackZombiePiglin.get());
        MobInteractions.DWARF_ATTACK_PLAYER.setBoolean(ConfigHolder.COMMON.dwarfAttackPlayer.get());
        MobInteractions.BLINDSIGHT_ATTACK_GLOWSQUITO.setBoolean(ConfigHolder.COMMON.blindsightAttackGlowsquito.get());
        MobInteractions.BLINDSIGHT_ATTACK_PLAYER.setBoolean(ConfigHolder.COMMON.blindsightAttackPlayer.get());
        MobInteractions.GIANT_ATTACK_MAGMA_CUBE.setBoolean(ConfigHolder.COMMON.giantAttackMagmaCube.get());
        MobInteractions.EMBODY_ATTACK_PIGLIN.setBoolean(ConfigHolder.COMMON.embodyAttackPiglin.get());
        MobInteractions.EMBODY_ATTACK_PLAYER.setBoolean(ConfigHolder.COMMON.embodyAttackPlayer.get());
        MobInteractions.VOLINE_ATTACK_FIRE_RESISTANCE.setBoolean(ConfigHolder.COMMON.volineAttackFireResistance.get());
        MobInteractions.VOLINE_ATTACK_PLAYER.setBoolean(ConfigHolder.COMMON.volineAttackPlayer.get());
        MobInteractions.VOLINE_ATTACK_MAGMA_CUBE.setBoolean(ConfigHolder.COMMON.volineAttackMagmaCube.get());
        MobInteractions.GLOWSILK_SPEED.setDouble(ConfigHolder.COMMON.glowsilkSpeed.get());

        //Mob Spawnable Biomes
        InfernalExpansionConfig.MobSpawning.VOLINE.setSpawnableBiomes(ConfigHolder.COMMON.volineBiomes.get());
        MobSpawning.WARPBEETLE.setSpawnableBiomes(ConfigHolder.COMMON.warpbeetleBiomes.get());
        InfernalExpansionConfig.MobSpawning.SHROOMLOIN.setSpawnableBiomes(ConfigHolder.COMMON.shroomloinBiomes.get());
        InfernalExpansionConfig.MobSpawning.BASALT_GIANT.setSpawnableBiomes(ConfigHolder.COMMON.basaltGiantBiomes.get());
        InfernalExpansionConfig.MobSpawning.EMBODY.setSpawnableBiomes(ConfigHolder.COMMON.embodyBiomes.get());
        MobSpawning.GLOWSQUITO.setSpawnableBiomes(ConfigHolder.COMMON.glowsquitoBiomes.get());
        InfernalExpansionConfig.MobSpawning.GLOWSILK_MOTH.setSpawnableBiomes(ConfigHolder.COMMON.glowsilkMothBiomes.get());
        MobSpawning.BLINDSIGHT.setSpawnableBiomes(ConfigHolder.COMMON.blindsightBiomes.get());
        MobSpawning.BLACKSTONE_DWARF.setSpawnableBiomes(ConfigHolder.COMMON.blackstoneDwarfBiomes.get());

        //Bonemeal Behaviour
        Miscellaneous.SHROOMLIGHT_GROWABLE.set(ConfigHolder.COMMON.isShroomlightGrowable.get());
        Miscellaneous.SHROOMLIGHT_GROW_CHANCE.set(ConfigHolder.COMMON.shroomlightGrowChance.get());

        //Miscellaneous
        Miscellaneous.FIRE_CHARGE_EXPLOSION.set(ConfigHolder.COMMON.fireChargeExplosion.get());
        Miscellaneous.JERKY_EFFECT_DURATION.set(ConfigHolder.COMMON.jerkyEffectDuration.get());
        Miscellaneous.JERKY_EFFECT_AMPLIFIER.set(ConfigHolder.COMMON.jerkyEffectAmplifier.get());

        Miscellaneous.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.set(ConfigHolder.COMMON.luminousFungusActivateDistance.get());

        Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.set(ConfigHolder.COMMON.luminousFungusGivesEffect.get());
    }

    public static void saveToClient() {
        ConfigHolder.CLIENT.luminousRefreshRate.set((int) ClientConfig.LUMINOUS_REFRESH_RATE.getDouble());
    }

    public static void saveToCommon() {
        //Mob Interactions
        ConfigHolder.COMMON.piglinFearWarpbeetle.set(MobInteractions.PIGLIN_FEAR_WARPBEETLE.getBoolean());
        ConfigHolder.COMMON.piglinFearEmbody.set(MobInteractions.PIGLIN_FEAR_EMBODY.getBoolean());
        ConfigHolder.COMMON.piglinFearDwarf.set(MobInteractions.PIGLIN_FEAR_DWARF.getBoolean());
        ConfigHolder.COMMON.hoglinFearWarpbeetle.set(MobInteractions.HOGLIN_FEAR_WARPBEETLE.getBoolean());
        ConfigHolder.COMMON.hoglinFearEmbody.set(MobInteractions.HOGLIN_FEAR_EMBODY.getBoolean());
        ConfigHolder.COMMON.spiderAttackWarpbeetle.set(MobInteractions.SPIDER_ATTACK_WARPBEETLE.getBoolean());
        ConfigHolder.COMMON.skeletonAttackPiglin.set(MobInteractions.SKELETON_ATTACK_PIGLIN.getBoolean());
        ConfigHolder.COMMON.skeletonAttackBrute.set(MobInteractions.SKELETON_ATTACK_BRUTE.getBoolean());
        ConfigHolder.COMMON.skeletonAttackEmbody.set(MobInteractions.SKELETON_ATTACK_EMBODY.getBoolean());
        ConfigHolder.COMMON.skeletonAttackGiant.set(MobInteractions.SKELETON_ATTACK_GIANT.getBoolean());
        ConfigHolder.COMMON.piglinAttackSkeleton.set(MobInteractions.PIGLIN_ATTACK_SKELETON.getBoolean());
        ConfigHolder.COMMON.piglinAttackVoline.set(MobInteractions.PIGLIN_ATTACK_VOLINE.getBoolean());
        ConfigHolder.COMMON.bruteAttackSkeleton.set(MobInteractions.BRUTE_ATTACK_SKELETON.getBoolean());
        ConfigHolder.COMMON.bruteAttackVoline.set(MobInteractions.BRUTE_ATTACK_VOLINE.getBoolean());
        ConfigHolder.COMMON.ghastAttackEmbody.set(MobInteractions.GHAST_ATTACK_EMBODY.getBoolean());
        ConfigHolder.COMMON.ghastAttackVoline.set(MobInteractions.GHAST_ATTACK_VOLINE.getBoolean());
        ConfigHolder.COMMON.ghastAttackSkeleton.set(MobInteractions.GHAST_ATTACK_SKELETON.getBoolean());
        ConfigHolder.COMMON.ghastAttackGlowsquito.set(MobInteractions.GHAST_ATTACK_GLOWSQUITO.getBoolean());
        ConfigHolder.COMMON.glowsquitoAttackDwarf.set(MobInteractions.GLOWSQUITO_ATTACK_DWARF.getBoolean());
        ConfigHolder.COMMON.glowsquitoAttackLuminous.set(MobInteractions.GLOWSQUITO_ATTACK_LUMINOUS.getBoolean());
        ConfigHolder.COMMON.dwarfAttackPiglin.set(MobInteractions.DWARF_ATTACK_PIGLIN.getBoolean());
        ConfigHolder.COMMON.dwarfAttackZombiePiglin.set(MobInteractions.DWARF_ATTACK_ZOMBIE_PIGLIN.getBoolean());
        ConfigHolder.COMMON.dwarfAttackPlayer.set(MobInteractions.DWARF_ATTACK_PLAYER.getBoolean());
        ConfigHolder.COMMON.blindsightAttackGlowsquito.set(MobInteractions.BLINDSIGHT_ATTACK_GLOWSQUITO.getBoolean());
        ConfigHolder.COMMON.blindsightAttackPlayer.set(MobInteractions.BLINDSIGHT_ATTACK_PLAYER.getBoolean());
        ConfigHolder.COMMON.giantAttackMagmaCube.set(MobInteractions.GIANT_ATTACK_MAGMA_CUBE.getBoolean());
        ConfigHolder.COMMON.embodyAttackPiglin.set(MobInteractions.EMBODY_ATTACK_PIGLIN.getBoolean());
        ConfigHolder.COMMON.embodyAttackPlayer.set(MobInteractions.EMBODY_ATTACK_PLAYER.getBoolean());
        ConfigHolder.COMMON.volineAttackFireResistance.set(MobInteractions.VOLINE_ATTACK_FIRE_RESISTANCE.getBoolean());
        ConfigHolder.COMMON.volineAttackPlayer.set(MobInteractions.VOLINE_ATTACK_PLAYER.getBoolean());
        ConfigHolder.COMMON.volineAttackMagmaCube.set(MobInteractions.VOLINE_ATTACK_MAGMA_CUBE.getBoolean());
        ConfigHolder.COMMON.glowsilkSpeed.set(MobInteractions.GLOWSILK_SPEED.getDouble());

        //Mob Spawnable Biomes
        ConfigHolder.COMMON.volineBiomes.set(MobSpawning.VOLINE.getSpawnableBiomes());
        ConfigHolder.COMMON.warpbeetleBiomes.set(InfernalExpansionConfig.MobSpawning.WARPBEETLE.getSpawnableBiomes());
        ConfigHolder.COMMON.shroomloinBiomes.set(InfernalExpansionConfig.MobSpawning.SHROOMLOIN.getSpawnableBiomes());
        ConfigHolder.COMMON.basaltGiantBiomes.set(InfernalExpansionConfig.MobSpawning.BASALT_GIANT.getSpawnableBiomes());
        ConfigHolder.COMMON.embodyBiomes.set(InfernalExpansionConfig.MobSpawning.EMBODY.getSpawnableBiomes());
        ConfigHolder.COMMON.glowsquitoBiomes.set(InfernalExpansionConfig.MobSpawning.GLOWSQUITO.getSpawnableBiomes());
        ConfigHolder.COMMON.glowsilkMothBiomes.set(InfernalExpansionConfig.MobSpawning.GLOWSILK_MOTH.getSpawnableBiomes());
        ConfigHolder.COMMON.blindsightBiomes.set(InfernalExpansionConfig.MobSpawning.BLINDSIGHT.getSpawnableBiomes());
        ConfigHolder.COMMON.blackstoneDwarfBiomes.set(MobSpawning.BLACKSTONE_DWARF.getSpawnableBiomes());

        ConfigHolder.COMMON.isShroomlightGrowable.set(Miscellaneous.SHROOMLIGHT_GROWABLE.getBool());
        ConfigHolder.COMMON.shroomlightGrowChance.set(Miscellaneous.SHROOMLIGHT_GROW_CHANCE.getDouble());

        ConfigHolder.COMMON.fireChargeExplosion.set(Miscellaneous.FIRE_CHARGE_EXPLOSION.getBool());
        ConfigHolder.COMMON.jerkyEffectDuration.set(Miscellaneous.JERKY_EFFECT_DURATION.getInt());
        ConfigHolder.COMMON.jerkyEffectAmplifier.set(Miscellaneous.JERKY_EFFECT_AMPLIFIER.getInt());

        ConfigHolder.COMMON.luminousFungusActivateDistance.set(Miscellaneous.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.getDouble());

        ConfigHolder.COMMON.luminousFungusGivesEffect.set(Miscellaneous.LUMINOUS_FUNGUS_GIVES_EFFECT.getBool());

        ConfigHolder.COMMON_SPEC.save();
    }
}
