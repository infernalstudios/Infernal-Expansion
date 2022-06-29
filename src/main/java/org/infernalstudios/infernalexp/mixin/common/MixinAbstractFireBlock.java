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

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.infernalstudios.infernalexp.access.FireTypeAccess;
import org.infernalstudios.infernalexp.api.FireType;
import org.infernalstudios.infernalexp.blocks.GlowFireBlock;
import org.infernalstudios.infernalexp.init.IEBlocks;
import org.infernalstudios.infernalexp.init.IEFireTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class MixinAbstractFireBlock extends Block {

    private MixinAbstractFireBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "getState", at = @At("HEAD"), cancellable = true)
    private static void IE_getFireForPlacement(BlockGetter reader, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
        if (GlowFireBlock.isGlowFireBase(reader.getBlockState(pos.below()))) {
            info.setReturnValue(IEBlocks.GLOW_FIRE.get().defaultBlockState());
        }
    }

    @Inject(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemainingFireTicks(I)V"))
    private void IE_setCustomFires(BlockState state, Level worldIn, BlockPos pos, Entity entityIn, CallbackInfo info) {
        FireType.getFireTypes().forEach(fireType -> {
            if (fireType != IEFireTypes.FIRE && ModList.get().isLoaded(fireType.getName().getNamespace())) {
                if (state.getBlock().getRegistryName().equals(fireType.getBlock()) && state.getBlock() instanceof BaseFireBlock) {
                    ((FireTypeAccess) entityIn).setFireType(fireType);
                }
            }
        });
    }
}
