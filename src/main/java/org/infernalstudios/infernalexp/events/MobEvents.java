/*
 * Copyright 2022 Infernal Studios
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

package org.infernalstudios.infernalexp.events;

import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.data.SpawnrateManager;
import org.infernalstudios.infernalexp.entities.BasaltGiantEntity;
import org.infernalstudios.infernalexp.entities.BlackstoneDwarfEntity;
import org.infernalstudios.infernalexp.entities.EmbodyEntity;
import org.infernalstudios.infernalexp.entities.GlowsquitoEntity;
import org.infernalstudios.infernalexp.entities.VolineEntity;
import org.infernalstudios.infernalexp.entities.WarpbeetleEntity;
import org.infernalstudios.infernalexp.entities.ai.AvoidBlockGoal;
import org.infernalstudios.infernalexp.init.IETags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ResourceLocationException;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = InfernalExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {

        //
        //RUN AWAY!!
        //

        //Piglins fear Warpbeetles and Embodies
        if (event.getEntity() instanceof Piglin entity) {
            if (InfernalExpansionConfig.MobInteractions.PIGLIN_FEAR_WARPBEETLE.getBoolean()) {
                entity.goalSelector.addGoal(4,
                        new AvoidEntityGoal<>(entity, WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if (InfernalExpansionConfig.MobInteractions.PIGLIN_FEAR_EMBODY.getBoolean()) {
                entity.goalSelector.addGoal(4, new AvoidEntityGoal<>(entity, EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if (InfernalExpansionConfig.MobInteractions.PIGLIN_FEAR_DWARF.getBoolean()) {
                entity.goalSelector.addGoal(4,
                        new AvoidEntityGoal<>(entity, BlackstoneDwarfEntity.class, 16.0F, 1.2D, 1.2D));
            }
        }

        if (event.getEntity() instanceof Hoglin entity) {
            if (InfernalExpansionConfig.MobInteractions.HOGLIN_FEAR_WARPBEETLE.getBoolean()) {
                entity.goalSelector.addGoal(4,
                        new AvoidEntityGoal<>(entity, WarpbeetleEntity.class, 16.0F, 1.2D, 1.2D));
            }
            if (InfernalExpansionConfig.MobInteractions.HOGLIN_FEAR_EMBODY.getBoolean()) {
                entity.goalSelector.addGoal(4, new AvoidEntityGoal<>(entity, EmbodyEntity.class, 16.0F, 1.2D, 1.2D));
            }
        }

        //
        // ATTACK!!
        //

        // Spiders attack Warp beetles
        if (event.getEntity() instanceof Spider entity
                && InfernalExpansionConfig.MobInteractions.SPIDER_ATTACK_WARPBEETLE.getBoolean()) {
            entity.targetSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>(entity, WarpbeetleEntity.class, true, false));
        }

        // Skeletons attacks Piglins, Brutes, Embodies & Basalt Giants
        if (event.getEntity() instanceof Skeleton entity) {
            if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_PIGLIN.getBoolean()) {
                entity.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(entity, Piglin.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_BRUTE.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, PiglinBrute.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_EMBODY.getBoolean()) {
                entity.targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>(entity, EmbodyEntity.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.SKELETON_ATTACK_GIANT.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, BasaltGiantEntity.class, true, false));
            }
        }

        // Piglins attack Skeletons & Voline
        if (event.getEntity() instanceof Piglin entity) {
            if (InfernalExpansionConfig.MobInteractions.PIGLIN_ATTACK_SKELETON.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, Skeleton.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.PIGLIN_ATTACK_VOLINE.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, VolineEntity.class, true, false));
            }
        }

        if (event.getEntity() instanceof PiglinBrute entity) {
            if (InfernalExpansionConfig.MobInteractions.BRUTE_ATTACK_SKELETON.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, Skeleton.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.BRUTE_ATTACK_VOLINE.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, VolineEntity.class, true, false));
            }
        }

        // Ghasts attack Voline, Embodies, Skeletons
        if (event.getEntity() instanceof Ghast entity) {
            if (InfernalExpansionConfig.MobInteractions.GHAST_ATTACK_GLOWSQUITO.getBoolean()) {
                entity.targetSelector.addGoal(4,
                        new NearestAttackableTargetGoal<>(entity, GlowsquitoEntity.class, true, false));
            }

            if (InfernalExpansionConfig.MobInteractions.GHAST_ATTACK_EMBODY.getBoolean()) {
                entity.targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>(entity, EmbodyEntity.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.GHAST_ATTACK_VOLINE.getBoolean()) {
                entity.targetSelector.addGoal(2,
                        new NearestAttackableTargetGoal<>(entity, VolineEntity.class, true, false));
            }
            if (InfernalExpansionConfig.MobInteractions.GHAST_ATTACK_SKELETON.getBoolean()) {
                entity.targetSelector.addGoal(3,
                        new NearestAttackableTargetGoal<>(entity, Skeleton.class, true, false));
            }
        }

        if (event.getEntity() instanceof MagmaCube entity) {
            entity.goalSelector.addGoal(0, new AvoidBlockGoal(entity, IETags.Blocks.MAGMA_CUBE_AVOID_BLOCKS, 8));
        }
    }

    private void addEntityToSpawner(BiomeLoadingEvent event, EntityType<?> entityType, SpawnrateManager.SpawnInfo spawnInfo) {
        // Add our entity to the spawner
        event.getSpawns().addSpawn(entityType.getCategory(),
            new MobSpawnSettings.SpawnerData(entityType, spawnInfo.spawnRate(), spawnInfo.minCount(), spawnInfo.maxCount()));

        // Change spawn costs
        if (spawnInfo.energyBudget() != 0 && spawnInfo.charge() != 0) {
            event.getSpawns().addMobCharge(entityType, spawnInfo.charge(), spawnInfo.energyBudget());
        } else if (spawnInfo.energyBudget() != 0 || spawnInfo.charge() != 0) {
            InfernalExpansion.LOGGER.error("EntityType {} has incomplete spawn charge data. When editing spawn costs, make sure to set both \"energy_budget\" and \"charge\"", entityType.toString());
        }
    }

    //Mob Spawning in pre-existing biomes
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {

        if (event.getCategory() != Biome.BiomeCategory.NETHER) {
            return;
        }

        MiscEvents.getSpawnrateManager().forEach((entity, value) -> {
            // Get the biomes the entity is allowed to spawn in from InfernalExpansionConfig
            List<String> spawnableBiomes = Arrays.asList(InfernalExpansionConfig.MobSpawning.getByName(entity.split(":")[1]).getSpawnableBiomes().replace(" ", "").split(","));

            // Check if the current biome getting loaded is in the spawnable biomes, if not, return
            if (!spawnableBiomes.contains(event.getName().toString())) {
                return;
            }

            // Get the entity type from the name
            EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity));

            // Check if the entity type exists
            if (entityType == null) {
                throw new ResourceLocationException("Invalid EntityType resource location " + entity);
            }

            // Find either the default spawn info or the spawn info for a specific biome from SpawnrateManager
            if (value.containsKey(event.getName().toString()) && spawnableBiomes.contains(event.getName().toString())) {
                addEntityToSpawner(event, entityType, value.get(event.getName().toString()));
            } else if (value.containsKey("default") && spawnableBiomes.contains(event.getName().toString())) {
                addEntityToSpawner(event, entityType, value.get("default"));
            } else {
                InfernalExpansion.LOGGER.error("{} doesn't have a default spawn entry", entity);
            }
        });
    }

}
