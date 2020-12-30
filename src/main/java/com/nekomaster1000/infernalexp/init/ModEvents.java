package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.entities.ai.AvoidBlockGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraftforge.event.world.BlockEvent;
import com.nekomaster1000.infernalexp.entities.EmbodyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {

        //
        //RUN AWAY!!
        //

        //Piglins fear Warpbeetles and Embodies
        if (event.getEntity() instanceof PiglinEntity || event.getEntity() instanceof HoglinEntity) {
            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                    new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                            WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                    new AvoidEntityGoal<>((CreatureEntity) event.getEntity(),
                            EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
        }


        //
        //ATTACK!!
        //

        //Spiders attack Warp beetles
        if (event.getEntity() instanceof SpiderEntity || event.getEntity() instanceof CaveSpiderEntity) {
            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            WarpbeetleEntity.class, true, false));
        }


        //Skeletons attacks Piglins, Brutes, Embodies & Basalt Giants
        if (event.getEntity() instanceof SkeletonEntity) {

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            PiglinEntity.class, true, false));

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            PiglinBruteEntity.class, true, false));

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(3,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            EmbodyEntity.class, true, false));

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            BasaltGiantEntity.class, true, false));
        }


        //Piglins attack Skeletons & Voline
        if (event.getEntity() instanceof PiglinEntity || event.getEntity() instanceof PiglinBruteEntity) {

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            SkeletonEntity.class, true, false));

            ((CreatureEntity) event.getEntity()).goalSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>((CreatureEntity) event.getEntity(),
                            VolineEntity.class, true, false));
        }


        //Ghasts attack Voline, Embodies, Skeletons & Glowsquitos

        if (event.getEntity() instanceof GhastEntity) {

            ((FlyingEntity) event.getEntity()).targetSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                            VolineEntity.class, true, false));

            ((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
                    new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                            EmbodyEntity.class, true, false));

            ((FlyingEntity) event.getEntity()).targetSelector.addGoal(3,
                    new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                            SkeletonEntity.class, true, false));

            ((FlyingEntity) event.getEntity()).targetSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>((GhastEntity) event.getEntity(),
                            GlowsquitoEntity.class, true, false));
        }


        //...

        // Please add Magma Cubes being scared of Glow Torches/Lanterns/Campfires here. PUSHED TO RELEASE 2

        /*
        if (event.getEntity() instanceof MagmaCubeEntity) {
            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0,
                    new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_TORCH.get(),
                            16.0F));
            ((SlimeEntity) event.getEntity()).goalSelector.addGoal(0, new AvoidBlockGoal((SlimeEntity) event.getEntity(), ModBlocks.GLOW_TORCH.get(), 16.0F));
        }
         */
    }

    //Mob Spawning in pre-existing biomes
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getName().toString().equals("minecraft:nether_wastes")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.VOLINE.get(), 50, 1, 3));


        } else if (event.getName().toString().equals("minecraft:crimson_forest")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.SHROOMLOIN.get(), 5, 1, 3));

            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.VOLINE.get(), 1, 1, 5));


        } else if (event.getName().toString().equals("minecraft:warped_forest")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.WARPBEETLE.get(), 5, 1, 1));

        //    event.getSpawns().withSpawner(EntityClassification.MONSTER,
        //            new MobSpawnInfo.Spawners(ModEntityType.CEROBEETLE.get(), 1, 1, 1));


        } else if (event.getName().toString().equals("minecraft:basalt_deltas")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.BASALT_GIANT.get(), 30, 1, 1));

            //event.getSpawns().withSpawner(EntityClassification.MONSTER,
            //        new MobSpawnInfo.Spawners(ModEntityType.GLOWSQUITO.get(), 1, 5, 10));


        } else if (event.getName().toString().equals("minecraft:soul_sand_valley")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.EMBODY.get(), 60, 1, 5));

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
                Entity entity1 = (Entity)list.get(j);
                if(entity1 instanceof ShroomloinEntity)
                {
                    ShroomloinEntity shroomloinEntity = (ShroomloinEntity) entity1;
                    shroomloinEntity.becomeAngryAt(event.getPlayer());
                }
            }
        }
    }
}
