package com.nekomaster1000.infernalexp.config;

import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.FloraBehaviour;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.MobInteractions;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.MobSpawning;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig.ClientConfig;
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
        MobInteractions.GLOWSILK_SPEED.setDouble(ConfigHolder.COMMON.glowsilkSpeed.get());

        //Mob Spawning Booleans
        MobSpawning.VOLINE_WASTES.setEnabled(ConfigHolder.COMMON.volineInWastes.get());
        MobSpawning.SHROOMLOIN_CRIMSON.setEnabled(ConfigHolder.COMMON.shroomloinInCrimson.get());
        MobSpawning.VOLINE_CRIMSON.setEnabled(ConfigHolder.COMMON.volineInCrimson.get());
        MobSpawning.WARPBEETLE_WARPED.setEnabled(ConfigHolder.COMMON.warpbeetleInWarped.get());
        MobSpawning.GIANT_DELTAS.setEnabled(ConfigHolder.COMMON.giantInDeltas.get());
        MobSpawning.EMBODY_SSV.setEnabled(ConfigHolder.COMMON.embodyInSSV.get());
        MobSpawning.GLOWSILK_GSC.setEnabled(ConfigHolder.COMMON.glowsilkInGSC.get());
        MobSpawning.GLOWSILK_DELTAS.setEnabled(ConfigHolder.COMMON.glowsilkInDeltas.get());
        MobSpawning.GLOWSILK_CRIMSON.setEnabled(ConfigHolder.COMMON.glowsilkInCrimson.get());

        //Mob Spawn Rates
        MobSpawning.VOLINE_WASTES.setSpawnrate(ConfigHolder.COMMON.volineWastesRate.get());
        MobSpawning.SHROOMLOIN_CRIMSON.setSpawnrate(ConfigHolder.COMMON.shroomloinCrimsonRate.get());
        MobSpawning.VOLINE_CRIMSON.setSpawnrate(ConfigHolder.COMMON.volineCrimsonRate.get());
        MobSpawning.WARPBEETLE_WARPED.setSpawnrate(ConfigHolder.COMMON.warpbeetleWarpedRate.get());
        MobSpawning.GIANT_DELTAS.setSpawnrate(ConfigHolder.COMMON.giantDeltasRate.get());
        MobSpawning.EMBODY_SSV.setSpawnrate(ConfigHolder.COMMON.embodySSVRate.get());
        MobSpawning.GLOWSILK_GSC.setSpawnrate(ConfigHolder.COMMON.glowsilkGSCRate.get());
        MobSpawning.GLOWSILK_DELTAS.setSpawnrate(ConfigHolder.COMMON.glowsilkDeltasRate.get());
        MobSpawning.GLOWSILK_CRIMSON.setSpawnrate(ConfigHolder.COMMON.glowsilkCrimsonRate.get());

        //Bonemeal Behaviour
        FloraBehaviour.SHROOMLIGHT_GROWABLE.set(ConfigHolder.COMMON.isShroomlightGrowable.get());
        FloraBehaviour.SHROOMLIGHT_GROW_CHANCE.set(ConfigHolder.COMMON.shroomlightGrowChance.get());

        FloraBehaviour.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.set(ConfigHolder.COMMON.luminousFungusActivateDistance.get());
    }

    public static void saveToClient() {
        ConfigHolder.CLIENT.luminousRefreshRate.set((int) ClientConfig.LUMINOUS_REFRESH_RATE.getDouble());
    }

    public static void saveToCommon() {
        //Mob Interactions
        ConfigHolder.COMMON.piglinFearWarpbeetle.set(MobInteractions.PIGLIN_FEAR_WARPBEETLE.getBoolean());
        ConfigHolder.COMMON.piglinFearEmbody.set(MobInteractions.PIGLIN_FEAR_EMBODY.getBoolean());
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
        ConfigHolder.COMMON.glowsilkSpeed.set(MobInteractions.GLOWSILK_SPEED.getDouble());

        //Mob Spawning Booleans
        ConfigHolder.COMMON.volineInWastes.set(MobSpawning.VOLINE_WASTES.isEnabled());
        ConfigHolder.COMMON.shroomloinInCrimson.set(MobSpawning.SHROOMLOIN_CRIMSON.isEnabled());
        ConfigHolder.COMMON.volineInCrimson.set(MobSpawning.VOLINE_CRIMSON.isEnabled());
        ConfigHolder.COMMON.warpbeetleInWarped.set(MobSpawning.WARPBEETLE_WARPED.isEnabled());
        ConfigHolder.COMMON.giantInDeltas.set(MobSpawning.GIANT_DELTAS.isEnabled());
        ConfigHolder.COMMON.embodyInSSV.set(MobSpawning.EMBODY_SSV.isEnabled());
		ConfigHolder.COMMON.glowsilkInGSC.set(MobSpawning.GLOWSILK_GSC.isEnabled());
		ConfigHolder.COMMON.glowsilkInDeltas.set(MobSpawning.GLOWSILK_DELTAS.isEnabled());
		ConfigHolder.COMMON.glowsilkInCrimson.set(MobSpawning.GLOWSILK_CRIMSON.isEnabled());

        //Mob Spawn Rates
        ConfigHolder.COMMON.volineWastesRate.set(MobSpawning.VOLINE_WASTES.getSpawnrate());
        ConfigHolder.COMMON.shroomloinCrimsonRate.set(MobSpawning.SHROOMLOIN_CRIMSON.getSpawnrate());
        ConfigHolder.COMMON.volineCrimsonRate.set(MobSpawning.VOLINE_CRIMSON.getSpawnrate());
        ConfigHolder.COMMON.warpbeetleWarpedRate.set(MobSpawning.WARPBEETLE_WARPED.getSpawnrate());
        ConfigHolder.COMMON.giantDeltasRate.set(MobSpawning.GIANT_DELTAS.getSpawnrate());
        ConfigHolder.COMMON.embodySSVRate.set(MobSpawning.EMBODY_SSV.getSpawnrate());
		ConfigHolder.COMMON.glowsilkGSCRate.set(MobSpawning.GLOWSILK_GSC.getSpawnrate());
		ConfigHolder.COMMON.glowsilkDeltasRate.set(MobSpawning.GLOWSILK_DELTAS.getSpawnrate());
		ConfigHolder.COMMON.glowsilkCrimsonRate.set(MobSpawning.GLOWSILK_CRIMSON.getSpawnrate());

        ConfigHolder.COMMON.isShroomlightGrowable.set(FloraBehaviour.SHROOMLIGHT_GROWABLE.getBool());
        ConfigHolder.COMMON.shroomlightGrowChance.set(FloraBehaviour.SHROOMLIGHT_GROW_CHANCE.getDouble());

        ConfigHolder.COMMON.luminousFungusActivateDistance.set(FloraBehaviour.LUMINOUS_FUNGUS_ACTIVATE_DISTANCE.getDouble());

        ConfigHolder.COMMON_SPEC.save();
    }
}
