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

package org.infernalstudios.infernalexp.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.infernalstudios.infernalexp.entities.IBucketable;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class EntityBucketItem extends BucketItem {
    private final Supplier<? extends EntityType<?>> entityTypeSupplier;
    private final Supplier<SoundEvent> emptyingSoundSupplier;

    public EntityBucketItem(Supplier<EntityType<?>> entityType, Fluid fluid, Supplier<SoundEvent> emptyingSound, Properties builder) {
        super(fluid, builder);
        this.entityTypeSupplier = entityType;
        this.emptyingSoundSupplier = emptyingSound;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, Level world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerLevel) {
            this.placeEntity((ServerLevel) world, stack, pos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable Player player, LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, this.emptyingSoundSupplier.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeEntity(ServerLevel worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityTypeSupplier.get().spawn(worldIn, stack, null, pos, MobSpawnType.BUCKET, true, true);
        if (entity instanceof IBucketable) {
            IBucketable bucketable = (IBucketable) entity;
            bucketable.copyFromAdditional(stack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        InteractionResultHolder<ItemStack> actionResult = super.use(worldIn, playerIn, handIn);
        ItemStack heldItem = playerIn.getItemInHand(handIn);
        BlockHitResult rayTraceResult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (rayTraceResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(heldItem);
        } else if (!(worldIn instanceof ServerLevel)) {
            return InteractionResultHolder.success(heldItem);
        } else {
            BlockPos pos = rayTraceResult.getBlockPos();
            if (!(worldIn.getBlockState(pos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(heldItem);
            } else {
                return actionResult;
            }
        }
    }
}
