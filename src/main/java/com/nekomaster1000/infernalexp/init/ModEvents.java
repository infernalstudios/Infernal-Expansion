package com.nekomaster1000.infernalexp.init;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraftforge.event.world.BlockEvent;
import com.nekomaster1000.infernalexp.entities.EmbodyEntity;
import net.minecraft.entity.Entity;
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
        }
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


        } else if (event.getName().toString().equals("minecraft:basalt_deltas")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.BASALT_GIANT.get(), 30, 1, 1));

        } else if (event.getName().toString().equals("minecraft:soul_sand_valley")) {
            event.getSpawns().withSpawner(EntityClassification.MONSTER,
                    new MobSpawnInfo.Spawners(ModEntityType.EMBODY.get(), 60, 1, 5));
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
}
