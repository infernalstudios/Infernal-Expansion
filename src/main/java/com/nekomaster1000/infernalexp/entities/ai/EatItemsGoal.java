/*
 * Copyright 2021 Infernal Studios
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

package com.nekomaster1000.infernalexp.entities.ai;

import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Set;

public class EatItemsGoal<T extends MobEntity> extends Goal {
    protected Path path;
    protected ItemEntity itemInstance;
    protected double eatTime;
    protected double eatDelay;
    protected boolean eating;

    protected final double speed;
    protected final T entityIn;
    protected final Set<Item> eatItems;
    protected final double range;
    protected final PathNavigator navigation;

    public EatItemsGoal(T entityIn, Set<Item> itemsToEat, double range, double speedIn){
        this.entityIn = entityIn;
        this.eatItems = itemsToEat;
        this.range = range;
        this.speed = speedIn;
        this.navigation = entityIn.getNavigator();
    }

    @Override
    public boolean shouldExecute() {
        if (this.itemInstance == null) {
            List<ItemEntity> list = this.entityIn.world.getEntitiesWithinAABB(ItemEntity.class, this.entityIn.getBoundingBox().grow(this.range, 3.0D, this.range));

            for (ItemEntity item : list) {
                if (eatItems.contains(item.getItem().getItem())) {
                    this.path = this.navigation.getPathToPos(item.getPosition(), 0);
					this.itemInstance = item;
                    return path != null;
                }
            }
        }

        return false;
    }

    @Override
    public void resetTask() {
        this.itemInstance = null;
        this.path = null;
        eating = false;
        entityIn.world.setEntityState(entityIn, (byte) 8);
    }

    @Override
    public void startExecuting() {
        this.navigation.setPath(this.path, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (!this.navigation.noPath() || this.eatTime > 0) && this.itemInstance.isAlive();
    }

    public void consumeItem() {
        eating = false;
        entityIn.world.setEntityState(entityIn, (byte) 8);
        itemInstance.getItem().shrink(1);
    }

    @Override
    public void tick() {
        if (eatDelay <= 0) {
            if (this.entityIn.getDistanceSq(itemInstance) < 2.0D) {
                this.navigation.setSpeed(0.0D);

                if (!eating) {
                    eating = true;
                    entityIn.world.setEntityState(entityIn, (byte) 9);
                    entityIn.lookAt(EntityAnchorArgument.Type.EYES, itemInstance.getPositionVec());
                    this.eatTime = 30;

                } else {
                    if (--this.eatTime <= 0) {
                        consumeItem();
                        eatDelay = 20;

                    } else if (eatTime % 3 == 0) {
                        entityIn.lookAt(EntityAnchorArgument.Type.EYES, itemInstance.getPositionVec());
                        entityIn.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.9F, 1.0F);
                        ((ServerWorld) entityIn.world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, itemInstance.getItem()), entityIn.getPosXRandom(0.5F) + entityIn.getLookVec().x / 2.0D, entityIn.getPosYRandom(), entityIn.getPosZRandom(0.5F) + entityIn.getLookVec().z / 2.0D, 4, 0, 0, 0, 0);
                    }
                }

            } else {
                this.navigation.setSpeed(speed);
            }

        } else {
            eatDelay--;
        }
    }
}
