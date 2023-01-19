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

package org.infernalstudios.infernalexp.mixin.common;

import org.infernalstudios.infernalexp.init.IEItems;
import org.infernalstudios.infernalexp.entities.IBucketable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;

@Mixin(MagmaCube.class)
public abstract class MixinMagmaCubeEntity extends Slime implements IBucketable {
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(MagmaCube.class, EntityDataSerializers.BOOLEAN);

    public MixinMagmaCubeEntity(EntityType<? extends Slime> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean isFromBucket) {
        this.entityData.set(FROM_BUCKET, isFromBucket);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player playerIn, @NotNull InteractionHand hand) {
        if (this.isTiny()) {
            return IBucketable.tryBucketEntity(playerIn, hand, this).orElse(super.mobInteract(playerIn, hand));
        } else {
            return super.mobInteract(playerIn, hand);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (reason == MobSpawnType.BUCKET) {
            return spawnDataIn;
        } else {
            this.setSize(0, false);
            return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        }
    }

    @Override
    public void copyToStack(ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag();
        IBucketable.copyToStack(this, stack);

        compound.putInt("Size", this.getSize());
    }

    @Override
    public void copyFromAdditional(CompoundTag compound) {
        IBucketable.copyFromAdditional(this, compound);
        if (compound.contains("Size", 99)) {
            this.setSize(compound.getInt("Size"), false);
        }
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.BUCKET_FILL_LAVA;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(IEItems.MAGMA_CUBE_BUCKET.get());
    }
}
