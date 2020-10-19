package com.nekomaster1000.infernalexp.world.gen;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import com.nekomaster1000.infernalexp.entities.*;
import com.nekomaster1000.infernalexp.init.ModEntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

//import net.minecraft.entity.monster.MonsterEntity;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModEntitySpawns {

    @SubscribeEvent
    public static void spawnEntities(FMLLoadCompleteEvent event) {

        for (Biome biome : ForgeRegistries.BIOMES) {

            GlobalEntityTypeAttributes.put(ModEntityType.GLOWSQUITO.get(), GlowsquitoEntity.setCustomAttributes().create());

            GlobalEntityTypeAttributes.put(ModEntityType.VOLINE.get(), VolineEntity.setCustomAttributes().create());
            //static{EntitySpawnPlacementRegistry.register(EntityType.ZOMBIE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,MobEntity::canSpawnOn);}

            GlobalEntityTypeAttributes.put(ModEntityType.PYRNO.get(), PyrnoEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.WARPBEETLE.get(), WarpbeetleEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.CEROBEETLE.get(), CerobeetleEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.EMBODY.get(), EmbodyEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.BASALT_GIANT.get(), BasaltGiantEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.BASALT_TITAN.get(), BasaltTitanEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityType.SKELETAL_PIGLIN.get(), SkeletalPiglinEntity.setCustomAttributes().create());

            //Biomes.NETHER_WASTES.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntityType.VOLINE.get(), 1, 1, 3));
            //Biomes.WARPED_FOREST.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntityType.WARPBEETLE.get(), 2, 1, 1));
            //Biomes.SOUL_SAND_VALLEY.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntityType.EMBODY.get(), 2, 1, 5));
            //ModBiomes.GLOWSTONE_CANYON.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(ModEntityType.GLOWSQUITO.get(), 2, 1, 5));

            }
        }
    }
