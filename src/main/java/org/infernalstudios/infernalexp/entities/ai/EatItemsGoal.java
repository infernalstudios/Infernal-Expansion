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

package org.infernalstudios.infernalexp.entities.ai;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;

import java.util.List;
import java.util.Set;

public class EatItemsGoal<T extends Mob> extends Goal {
    protected Path path;
    protected ItemEntity itemInstance;
    protected double eatTime;
    protected double eatDelay;
    protected boolean eating;

    protected final double speed;
    protected final T entityIn;
    protected final Set<Item> eatItems;
    protected final double range;
    protected final PathNavigation navigation;

    public EatItemsGoal(T entityIn, Set<Item> itemsToEat, double range, double speedIn) {
        this.entityIn = entityIn;
        this.eatItems = itemsToEat;
        this.range = range;
        this.speed = speedIn;
        this.navigation = entityIn.getNavigation();
    }

    @Override
    public boolean canUse() {
        if (this.itemInstance == null) {
            List<ItemEntity> list = this.entityIn.level.getEntitiesOfClass(ItemEntity.class, this.entityIn.getBoundingBox().inflate(this.range, 3.0D, this.range));

            for (ItemEntity item : list) {
                if (eatItems.contains(item.getItem().getItem())) {
                    this.path = this.navigation.createPath(item.blockPosition(), 0);
                    this.itemInstance = item;
                    return path != null;
                }
            }
        }

        return false;
    }

    @Override
    public void stop() {
        this.itemInstance = null;
        this.path = null;
        eating = false;
        entityIn.level.broadcastEntityEvent(entityIn, (byte) 8);
    }

    @Override
    public void start() {
        this.navigation.moveTo(this.path, this.speed);
    }

    @Override
    public boolean canContinueToUse() {
        return (!this.navigation.isDone() || this.eatTime > 0) && this.itemInstance.isAlive();
    }

    public void consumeItem() {
        eating = false;
        entityIn.level.broadcastEntityEvent(entityIn, (byte) 8);
        itemInstance.getItem().shrink(1);
    }

    @Override
    public void tick() {
        if (eatDelay <= 0) {
            if (this.entityIn.distanceToSqr(itemInstance) < 2.0D) {
                this.navigation.setSpeedModifier(0.0D);

                if (!eating) {
                    eating = true;
                    entityIn.level.broadcastEntityEvent(entityIn, (byte) 9);
                    entityIn.lookAt(EntityAnchorArgument.Anchor.EYES, itemInstance.position());
                    this.eatTime = 30;

                } else {
                    if (--this.eatTime <= 0) {
                        consumeItem();
                        eatDelay = 20;

                    } else if (eatTime % 3 == 0) {
                        entityIn.lookAt(EntityAnchorArgument.Anchor.EYES, itemInstance.position());
                        entityIn.playSound(SoundEvents.GENERIC_EAT, 0.9F, 1.0F);
                        ((ServerLevel) entityIn.level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, itemInstance.getItem()), entityIn.getRandomX(0.5F) + entityIn.getLookAngle().x / 2.0D, entityIn.getRandomY(), entityIn.getRandomZ(0.5F) + entityIn.getLookAngle().z / 2.0D, 4, 0, 0, 0, 0);
                    }
                }

            } else {
                this.navigation.setSpeedModifier(speed);
            }

        } else {
            eatDelay--;
        }
    }
}
