package com.nekomaster1000.infernalexp.mixin.common;

import com.nekomaster1000.infernalexp.init.IEBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public class MixinBubbleColumnBlock {

    @Inject(at = @At("HEAD"), method = "isValidPosition", cancellable = true)
    public void IE_isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate_IE = worldIn.getBlockState(pos.down());

        if (blockstate_IE.matchesBlock(IEBlocks.BASALTIC_MAGMA.get())) {
            cir.setReturnValue(true);
        }
    }
}
