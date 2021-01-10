package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.config.ConfigHelper;
import com.nekomaster1000.infernalexp.config.ConfigHolder;
import com.nekomaster1000.infernalexp.config.InfernalExpansionConfig;
import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.entities.ai.AvoidBlockGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    //Called When Config is Changed
    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event){
        final ModConfig config = event.getConfig();
        //Recalculates what the configs should be when changed
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            ConfigHelper.bakeClient(config);
        } else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
            ConfigHelper.bakeServer(config);
        }
    }


    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {

        //
        //RUN AWAY!!
        //

        //Piglins fear Warpbeetles and Embodies
        if (event.getEntity() instanceof PiglinEntity){
            if(InfernalExpansionConfig.piglinFearWarpbeetle) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if(InfernalExpansionConfig.piglinFearEmbody) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
            }
        }

        if(event.getEntity() instanceof HoglinEntity){
            if(InfernalExpansionConfig.hoglinFearWarpbeetle) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if(InfernalExpansionConfig.hoglinFearEmbody) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                        new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                                EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
            }
        }


        //
        //ATTACK!!
        //

        //Spiders attack Warp beetles
        if (event.getEntity() instanceof SpiderEntity && InfernalExpansionConfig.spiderAttackWarpbeetle) {
            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            WarpbeetleEntity.class, true, false));

           }


        //Skeletons attacks Piglins, Brutes, Embodies & Basalt Giants
        if (event.getEntity() instanceof SkeletonEntity) {

            if(InfernalExpansionConfig.skeletonAttackPiglin) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                PiglinEntity.class, true, false));
            }
            if(InfernalExpansionConfig.skeletonAttackBrute) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                PiglinBruteEntity.class, true, false));
            }
            if(InfernalExpansionConfig.skeletonAttackEmbody) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                EmbodyEntity.class, true, false));
            }
            if(InfernalExpansionConfig.skeletonAttackGiant) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                BasaltGiantEntity.class, true, false));
            }
        }


        //Piglins attack Skeletons & Voline
        if (event.getEntity() instanceof PiglinEntity) {
            if(InfernalExpansionConfig.piglinAttackSkeleton) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                SkeletonEntity.class, true, false));
            }
            if(InfernalExpansionConfig.piglinAttackVoline) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                VolineEntity.class, true, false));
            }
        }

        if(event.getEntity() instanceof PiglinBruteEntity){
            if(InfernalExpansionConfig.bruteAttackSkeleton) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                SkeletonEntity.class, true, false));
            }
            if(InfernalExpansionConfig.bruteAttackVoline) {
                ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                                VolineEntity.class, true, false));
            }
        }


        //Ghasts attack Voline, Embodies, Skeletons
        if (event.getEntity() instanceof GhastEntity) {
            
            ((FlyingEntity) event.getEntity()).targetSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                            GlowsquitoEntity.class, true, false));

            if(InfernalExpansionConfig.ghastAttackVoline) {
                ((FlyingEntity) event.getEntity()).targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                                VolineEntity.class, true, false));
            }
            if(InfernalExpansionConfig.ghastAttackEmbody) {
                ((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                                EmbodyEntity.class, true, false));
            }
            if(InfernalExpansionConfig.ghastAttackSkeleton) {
                ((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                                SkeletonEntity.class, true, false));
            }
        }

        if (event.getEntity() instanceof MagmaCubeEntity) {

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_TORCH.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_WALL_TORCH.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_LANTERN.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_CAMPFIRE.get(),
                            16.0F));

            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_FIRE.get(),
                            16.0F));
        }

    }

    //Mob Spawning in pre-existing biomes
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getName().toString().equals("minecraft:nether_wastes")) {

            if(InfernalExpansionConfig.volineInWastes) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(ModEntityType.VOLINE.get(), InfernalExpansionConfig.volineWastesRate, 1, 3));
            }


        } else if (event.getName().toString().equals("minecraft:crimson_forest")) {

            if(InfernalExpansionConfig.shroomloinInCrimson) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(ModEntityType.SHROOMLOIN.get(), InfernalExpansionConfig.shroomloinCrimsonRate, 1, 3));
            }

            if(InfernalExpansionConfig.volineInCrimson) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(ModEntityType.VOLINE.get(), InfernalExpansionConfig.volineCrimsonRate, 1, 5));
            }

        } else if (event.getName().toString().equals("minecraft:warped_forest")) {

        //    event.getSpawns().withSpawner(EntityClassification.MONSTER,
        //            new MobSpawnInfo.Spawners(ModEntityType.CEROBEETLE.get(), 1, 1, 1));

            if(InfernalExpansionConfig.warpbeetleInWarped) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(ModEntityType.WARPBEETLE.get(), InfernalExpansionConfig.warpbeetleWarpedRate, 1, 1));
            }

        } else if (event.getName().toString().equals("minecraft:basalt_deltas")) {

            if(InfernalExpansionConfig.giantInDeltas) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(ModEntityType.BASALT_GIANT.get(), InfernalExpansionConfig.giantDeltasRate, 1, 1));
            }

            //event.getSpawns().withSpawner(EntityClassification.MONSTER,
            //        new MobSpawnInfo.Spawners(ModEntityType.GLOWSQUITO.get(), 1, 5, 10));


        } else if (event.getName().toString().equals("minecraft:soul_sand_valley")) {

            if(InfernalExpansionConfig.embodyInSSV) {
                event.getSpawns().withSpawner(EntityClassification.MONSTER,
                        new MobSpawnInfo.Spawners(ModEntityType.EMBODY.get(), InfernalExpansionConfig.embodySSVRate, 1, 5));
            }

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.SKELETAL_PIGLIN.get(), 10, 1, 1));


            //Mob Spawning in new biomes

        } else if (event.getName().toString().equals("infernalexp:glowstone_canyon")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.GLOWSQUITO.get(), 100, 1, 1));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.SKELETAL_PIGLIN.get(), 20, 1, 1));

            //event.getSpawns().withSpawner(EntityClassification.MONSTER,
            //        new MobSpawnInfo.Spawners(EntityType.GHAST, 20, 1, 1));
            // Not spawning for some reason?

        } else if (event.getName().toString().equals("infernalexp:delta_shores")) {

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.BASALT_GIANT.get(), 5, 1, 1));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.SKELETAL_PIGLIN.get(), 10, 1, 3));

        }
    }

    //Blocks being broken
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        if(event.getState().equals(Blocks.CRIMSON_FUNGUS.getDefaultState())  || event.getState().equals(Blocks.NETHER_WART_BLOCK.getDefaultState())) {
            List<?> list = event.getPlayer().world.getEntitiesWithinAABB(ShroomloinEntity.class,
                    event.getPlayer().getBoundingBox().grow(32.0D));
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity)list.get(j);
                if(entity instanceof ShroomloinEntity)
                {
                    ShroomloinEntity shroomloinEntity = (ShroomloinEntity) entity;
                    shroomloinEntity.becomeAngryAt(event.getPlayer());
                }
            }
        }
    }

    // Register features and surface builders
    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        ModFeatures.features.forEach(feature -> event.getRegistry().register(feature));
    }

    @SubscribeEvent
    public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
        ModSurfaceBuilders.surfaceBuilders.forEach(surfaceBuilder -> event.getRegistry().register(surfaceBuilder));
    }
}
