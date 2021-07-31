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

package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.access.FireTypeAccess;
import com.nekomaster1000.infernalexp.access.FireTypeAccess.KnownFireTypes;
import com.nekomaster1000.infernalexp.blocks.GlowFireBlock;
import com.nekomaster1000.infernalexp.init.IEBlocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraftforge.fml.ModList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public abstract class MixinAbstractFireBlock extends Block {

	private MixinAbstractFireBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Inject(method = "getFireForPlacement", at = @At("HEAD"), cancellable = true)
	private static void IE_getFireForPlacement(IBlockReader reader, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
		if (GlowFireBlock.isGlowFireBase(reader.getBlockState(pos.down()).getBlock())) {
			info.setReturnValue(IEBlocks.GLOW_FIRE.get().getDefaultState());
		}
	}

	@Inject(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;forceFireTicks(I)V"))
	private void IE_setCustomFires(BlockState state, World worldIn, BlockPos pos, Entity entityIn, CallbackInfo info) {
		FireTypeAccess access = ((FireTypeAccess) entityIn);
		if (state.matchesBlock(Blocks.SOUL_FIRE)) {
			access.setFireType(KnownFireTypes.SOUL_FIRE);
		} else if (state.matchesBlock(IEBlocks.GLOW_FIRE.get())) {
			access.setFireType(KnownFireTypes.GLOW_FIRE);
		} else {
			access.setFireType(KnownFireTypes.FIRE);
		}

		if (ModList.get().isLoaded("endergetic")) {
			if (state.getBlock().getRegistryName().equals(new ResourceLocation("endergetic", "ender_fire")) && state.getBlock() instanceof AbstractFireBlock) {
				access.setFireType(KnownFireTypes.ENDER_FIRE);
			}
		}

	}
}
