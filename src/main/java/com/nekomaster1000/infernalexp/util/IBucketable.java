package com.nekomaster1000.infernalexp.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.Optional;

public interface IBucketable {
    boolean isFromBucket();

    void setFromBucket(boolean isFromBucket);

    void copyToStack(ItemStack stack);

    void copyFromAdditional(CompoundNBT compound);

    ItemStack getBucketItem();

    SoundEvent getBucketedSound();

    static void copyToStack(MobEntity entity, ItemStack stack) {
        CompoundNBT compound = stack.getOrCreateTag();
        if (entity.hasCustomName()) {
            stack.setDisplayName(entity.getCustomName());
        }

        if (entity.isAIDisabled()) {
            compound.putBoolean("NoAI", entity.isAIDisabled());
        }

        if (entity.isSilent()) {
            compound.putBoolean("Silent", entity.isSilent());
        }

        if (entity.hasNoGravity()) {
            compound.putBoolean("NoGravity", entity.hasNoGravity());
        }

        if (entity.isGlowing()) {
            compound.putBoolean("Glowing", entity.isGlowing());
        }

        if (entity.isInvulnerable()) {
            compound.putBoolean("Invulnerable", entity.isInvulnerable());
        }

        compound.putFloat("Health", entity.getHealth());
    }

    static void copyFromAdditional(MobEntity entity, CompoundNBT compound) {
        if (compound.contains("NoAI")) {
            entity.setNoAI(compound.getBoolean("NoAI"));
        }

        if (compound.contains("Silent")) {
            entity.setSilent(compound.getBoolean("Silent"));
        }

        if (compound.contains("NoGravity")) {
            entity.setNoGravity(compound.getBoolean("NoGravity"));
        }

        if (compound.contains("Glowing")) {
            entity.setGlowing(compound.getBoolean("Glowing"));
        }

        if (compound.contains("Invulnerable")) {
            entity.setInvulnerable(compound.getBoolean("Invulnerable"));
        }

        if (compound.contains("Health", 99)) {
            entity.setHealth(compound.getFloat("Health"));
        }
    }

    static <T extends LivingEntity & IBucketable> Optional<ActionResultType> tryBucketEntity(PlayerEntity player, Hand hand, T entity) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() == Items.LAVA_BUCKET && entity.isAlive()) {
            entity.playSound(entity.getBucketedSound(), 1.0F, 1.0F);
            heldItem.shrink(1);
            ItemStack bucketItem = entity.getBucketItem();
            entity.copyToStack(bucketItem);
            World world = entity.world;
            if (!world.isRemote) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)player, bucketItem);
            }

            if (heldItem.isEmpty()) {
                player.setHeldItem(hand, bucketItem);
            } else if (!player.inventory.addItemStackToInventory(bucketItem)) {
                player.dropItem(bucketItem, false);
            }

            entity.remove();
            return Optional.of(ActionResultType.func_233537_a_(world.isRemote));
        } else {
            return Optional.empty();
        }
    }
}
