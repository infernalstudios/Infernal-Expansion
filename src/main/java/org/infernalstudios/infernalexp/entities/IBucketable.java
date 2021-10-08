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

package org.infernalstudios.infernalexp.entities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface IBucketable {
    boolean isFromBucket();

    void setFromBucket(boolean isFromBucket);

    void copyToStack(ItemStack stack);

    void copyFromAdditional(CompoundTag compound);

    ItemStack getBucketItem();

    SoundEvent getBucketedSound();

    static void copyToStack(Mob entity, ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag();
        if (entity.hasCustomName()) {
            stack.setHoverName(entity.getCustomName());
        }

        if (entity.isNoAi()) {
            compound.putBoolean("NoAI", entity.isNoAi());
        }

        if (entity.isSilent()) {
            compound.putBoolean("Silent", entity.isSilent());
        }

        if (entity.isNoGravity()) {
            compound.putBoolean("NoGravity", entity.isNoGravity());
        }

        if (entity.isCurrentlyGlowing()) {
            compound.putBoolean("Glowing", entity.isCurrentlyGlowing());
        }

        if (entity.isInvulnerable()) {
            compound.putBoolean("Invulnerable", entity.isInvulnerable());
        }

        compound.putFloat("Health", entity.getHealth());
    }

    static void copyFromAdditional(Mob entity, CompoundTag compound) {
        if (compound.contains("NoAI")) {
            entity.setNoAi(compound.getBoolean("NoAI"));
        }

        if (compound.contains("Silent")) {
            entity.setSilent(compound.getBoolean("Silent"));
        }

        if (compound.contains("NoGravity")) {
            entity.setNoGravity(compound.getBoolean("NoGravity"));
        }

        if (compound.contains("Glowing")) {
            entity.setGlowingTag(compound.getBoolean("Glowing"));
        }

        if (compound.contains("Invulnerable")) {
            entity.setInvulnerable(compound.getBoolean("Invulnerable"));
        }

        if (compound.contains("Health", 99)) {
            entity.setHealth(compound.getFloat("Health"));
        }
    }

    static <T extends LivingEntity & IBucketable> Optional<InteractionResult> tryBucketEntity(Player player, InteractionHand hand, T entity) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() == Items.LAVA_BUCKET && entity.isAlive()) {
            entity.playSound(entity.getBucketedSound(), 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack bucketItem = entity.getBucketItem();
            entity.copyToStack(bucketItem);
            Level world = entity.level;
            if (!world.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucketItem);
            }

            if (heldItem.isEmpty()) {
                player.setItemInHand(hand, bucketItem);
            } else if (!player.getInventory().add(bucketItem)) {
                player.drop(bucketItem, false);
            }

            entity.remove(Entity.RemovalReason.DISCARDED);

            return Optional.of(InteractionResult.sidedSuccess(world.isClientSide));
        } else {
            return Optional.empty();
        }
    }
}
