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

import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.infernalexp.InfernalExpansion;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig;
import org.infernalstudios.infernalexp.entities.BasaltGiantEntity;
import org.infernalstudios.infernalexp.entities.BlackstoneDwarfEntity;
import org.infernalstudios.infernalexp.entities.EmbodyEntity;
import org.infernalstudios.infernalexp.entities.GlowsquitoEntity;
import org.infernalstudios.infernalexp.entities.VolineEntity;
import org.infernalstudios.infernalexp.entities.WarpbeetleEntity;
import org.infernalstudios.infernalexp.entities.ai.AvoidBlockGoal;
import org.infernalstudios.infernalexp.init.IETags;

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

}
