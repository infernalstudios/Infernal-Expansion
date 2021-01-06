package com.nekomaster1000.infernalexp.config;

import net.minecraftforge.fml.config.ModConfig;
import sun.security.krb5.Config;

public final class ConfigHelper {

    //Client
    public static void bakeClient(final ModConfig config){

    }

    //Server
    public static void bakeServer(final ModConfig config){
        //Mob Interactions
        InfernalExpansionConfig.piglinFearWarpbeetle = ConfigHolder.SERVER.piglinFearWarpbeetle.get();
        InfernalExpansionConfig.piglinFearEmbody = ConfigHolder.SERVER.piglinFearEmbody.get();
        InfernalExpansionConfig.hoglinFearWarpbeetle = ConfigHolder.SERVER.hoglinFearWarpbeetle.get();
        InfernalExpansionConfig.hoglinFearEmbody = ConfigHolder.SERVER.hoglinFearEmbody.get();
        InfernalExpansionConfig.spiderAttackWarpbeetle = ConfigHolder.SERVER.spiderAttackWarpbeetle.get();
        InfernalExpansionConfig.skeletonAttackPiglin = ConfigHolder.SERVER.skeletonAttackPiglin.get();
        InfernalExpansionConfig.skeletonAttackBrute = ConfigHolder.SERVER.skeletonAttackBrute.get();
        InfernalExpansionConfig.skeletonAttackEmbody = ConfigHolder.SERVER.skeletonAttackEmbody.get();
        InfernalExpansionConfig.skeletonAttackGiant = ConfigHolder.SERVER.skeletonAttackGiant.get();
        InfernalExpansionConfig.piglinAttackSkeleton = ConfigHolder.SERVER.piglinAttackSkeleton.get();
        InfernalExpansionConfig.piglinAttackVoline = ConfigHolder.SERVER.piglinAttackVoline.get();
        InfernalExpansionConfig.bruteAttackSkeleton = ConfigHolder.SERVER.bruteAttackSkeleton.get();
        InfernalExpansionConfig.bruteAttackVoline = ConfigHolder.SERVER.bruteAttackVoline.get();
        InfernalExpansionConfig.ghastAttackEmbody = ConfigHolder.SERVER.ghastAttackEmbody.get();
        InfernalExpansionConfig.ghastAttackVoline = ConfigHolder.SERVER.ghastAttackVoline.get();
        InfernalExpansionConfig.ghastAttackSkeleton = ConfigHolder.SERVER.ghastAttackSkeleton.get();

        //Mob Spawning Booleans
        InfernalExpansionConfig.volineInWastes = ConfigHolder.SERVER.volineInWastes.get();
        InfernalExpansionConfig.shroomloinInCrimson = ConfigHolder.SERVER.shroomloinInCrimson.get();
        InfernalExpansionConfig.volineInCrimson = ConfigHolder.SERVER.volineInCrimson.get();
        InfernalExpansionConfig.warpbeetleInWarped = ConfigHolder.SERVER.warpbeetleInWarped.get();
        InfernalExpansionConfig.giantInDeltas = ConfigHolder.SERVER.giantInDeltas.get();
        InfernalExpansionConfig.embodyInSSV = ConfigHolder.SERVER.embodyInSSV.get();

        //Mob Spawn Rates
        InfernalExpansionConfig.volineWastesRate = ConfigHolder.SERVER.volineWastesRate.get();
        InfernalExpansionConfig.shroomloinCrimsonRate = ConfigHolder.SERVER.shroomloinCrimsonRate.get();
        InfernalExpansionConfig.volineCrimsonRate = ConfigHolder.SERVER.volineCrimsonRate.get();
        InfernalExpansionConfig.warpbeetleWarpedRate = ConfigHolder.SERVER.warpbeetleWarpedRate.get();
        InfernalExpansionConfig.giantDeltasRate = ConfigHolder.SERVER.giantDeltasRate.get();
        InfernalExpansionConfig.embodySSVRate = ConfigHolder.SERVER.embodySSVRate.get();
    }
}
