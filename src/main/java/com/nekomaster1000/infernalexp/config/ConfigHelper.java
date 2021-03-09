package com.nekomaster1000.infernalexp.config;

import net.minecraftforge.fml.config.ModConfig;

public final class ConfigHelper {

    //Client
    public static void bakeClient(final ModConfig config){
        //Luminous Effect
        InfernalExpansionConfig.luminousRefreshRate = ConfigHolder.CLIENT.luminousRefreshRate.get();
    }

    //Common
    public static void bakeCommon(final ModConfig config){
        //Mob Interactions
        InfernalExpansionConfig.piglinFearWarpbeetle = ConfigHolder.COMMON.piglinFearWarpbeetle.get();
        InfernalExpansionConfig.piglinFearEmbody = ConfigHolder.COMMON.piglinFearEmbody.get();
        InfernalExpansionConfig.hoglinFearWarpbeetle = ConfigHolder.COMMON.hoglinFearWarpbeetle.get();
        InfernalExpansionConfig.hoglinFearEmbody = ConfigHolder.COMMON.hoglinFearEmbody.get();
        InfernalExpansionConfig.spiderAttackWarpbeetle = ConfigHolder.COMMON.spiderAttackWarpbeetle.get();
        InfernalExpansionConfig.skeletonAttackPiglin = ConfigHolder.COMMON.skeletonAttackPiglin.get();
        InfernalExpansionConfig.skeletonAttackBrute = ConfigHolder.COMMON.skeletonAttackBrute.get();
        InfernalExpansionConfig.skeletonAttackEmbody = ConfigHolder.COMMON.skeletonAttackEmbody.get();
        InfernalExpansionConfig.skeletonAttackGiant = ConfigHolder.COMMON.skeletonAttackGiant.get();
        InfernalExpansionConfig.piglinAttackSkeleton = ConfigHolder.COMMON.piglinAttackSkeleton.get();
        InfernalExpansionConfig.piglinAttackVoline = ConfigHolder.COMMON.piglinAttackVoline.get();
        InfernalExpansionConfig.bruteAttackSkeleton = ConfigHolder.COMMON.bruteAttackSkeleton.get();
        InfernalExpansionConfig.bruteAttackVoline = ConfigHolder.COMMON.bruteAttackVoline.get();
        InfernalExpansionConfig.ghastAttackEmbody = ConfigHolder.COMMON.ghastAttackEmbody.get();
        InfernalExpansionConfig.ghastAttackVoline = ConfigHolder.COMMON.ghastAttackVoline.get();
        InfernalExpansionConfig.ghastAttackSkeleton = ConfigHolder.COMMON.ghastAttackSkeleton.get();

        //Mob Spawning Booleans
        InfernalExpansionConfig.volineInWastes = ConfigHolder.COMMON.volineInWastes.get();
        InfernalExpansionConfig.shroomloinInCrimson = ConfigHolder.COMMON.shroomloinInCrimson.get();
        InfernalExpansionConfig.volineInCrimson = ConfigHolder.COMMON.volineInCrimson.get();
        InfernalExpansionConfig.warpbeetleInWarped = ConfigHolder.COMMON.warpbeetleInWarped.get();
        InfernalExpansionConfig.giantInDeltas = ConfigHolder.COMMON.giantInDeltas.get();
        InfernalExpansionConfig.embodyInSSV = ConfigHolder.COMMON.embodyInSSV.get();

        //Mob Spawn Rates
        InfernalExpansionConfig.volineWastesRate = ConfigHolder.COMMON.volineWastesRate.get();
        InfernalExpansionConfig.shroomloinCrimsonRate = ConfigHolder.COMMON.shroomloinCrimsonRate.get();
        InfernalExpansionConfig.volineCrimsonRate = ConfigHolder.COMMON.volineCrimsonRate.get();
        InfernalExpansionConfig.warpbeetleWarpedRate = ConfigHolder.COMMON.warpbeetleWarpedRate.get();
        InfernalExpansionConfig.giantDeltasRate = ConfigHolder.COMMON.giantDeltasRate.get();
        InfernalExpansionConfig.embodySSVRate = ConfigHolder.COMMON.embodySSVRate.get();
        
        //Bonemeal Behaviour
        InfernalExpansionConfig.isShroomlightGrowable = ConfigHolder.COMMON.isShroomlightGrowable.get();
        InfernalExpansionConfig.shroomlightGrowChance = ConfigHolder.COMMON.shroomlightGrowChance.get();
        
        InfernalExpansionConfig.luminousFungusActivateDistance = ConfigHolder.COMMON.luminousFungusActivateDistance.get();
        
    }
}
