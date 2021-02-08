package com.nekomaster1000.infernalexp.mixin.common;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.nekomaster1000.infernalexp.blocks.GlowFireBlock;
import com.nekomaster1000.infernalexp.init.IEBlocks;

@Mixin(AbstractFireBlock.class)
public class MixinAbstractFireBlock extends Block {
        
        private MixinAbstractFireBlock(AbstractBlock.Properties properties) {
            super(properties);
        }
    
        @Inject(method = "getFireForPlacement", at = @At("HEAD"), cancellable = true)
        private static void getFireForPlacement(IBlockReader reader, BlockPos pos, CallbackInfoReturnable<BlockState> info) {
            if (GlowFireBlock.isGlowFireBase(reader.getBlockState(pos.down()).getBlock())) info.setReturnValue(IEBlocks.GLOW_FIRE.get().getDefaultState());
        }
}