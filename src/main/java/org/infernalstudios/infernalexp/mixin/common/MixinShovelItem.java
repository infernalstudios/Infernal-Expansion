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

package org.infernalstudios.infernalexp.mixin.common;

import org.infernalstudios.infernalexp.blocks.GlowCampfireBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class MixinShovelItem {

    @Inject(at = @At("HEAD"), method = "useOn", cancellable = true, remap = false)
    private void IE_onItemUse(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        Player playerEntity = context.getPlayer();
        if (state.getBlock() instanceof GlowCampfireBlock && state.getValue(GlowCampfireBlock.LIT)) {
            if (!world.isClientSide()) {
                world.levelEvent(null, 1009, pos, 0);
            }
            GlowCampfireBlock.dowse(playerEntity, world, pos, state);
            world.setBlockAndUpdate(pos, state.setValue(GlowCampfireBlock.LIT, false));
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else if (state.getBlock() instanceof NyliumBlock || state.getBlock() == Blocks.SOUL_SOIL) {
            if (state.getBlock() == Blocks.CRIMSON_NYLIUM) {
                state = IEBlocks.CRIMSON_NYLIUM_PATH.get().defaultBlockState();
            } else if (state.getBlock() == Blocks.WARPED_NYLIUM) {
                state = IEBlocks.WARPED_NYLIUM_PATH.get().defaultBlockState();
            } else {
                state = IEBlocks.SOUL_SOIL_PATH.get().defaultBlockState();
            }
            world.playSound(playerEntity, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (playerEntity != null) {
                context.getItemInHand().hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(context.getHand()));
            }
            world.setBlockAndUpdate(pos, state);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}
