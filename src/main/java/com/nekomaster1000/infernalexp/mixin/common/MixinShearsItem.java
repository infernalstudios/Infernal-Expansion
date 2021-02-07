package com.nekomaster1000.infernalexp.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.nekomaster1000.infernalexp.init.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

@Mixin(ShearsItem.class)
public class MixinShearsItem {
    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void getDestroySpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> ci) {
        if (state.isIn(ModBlocks.DULLTHORNS.get())) ci.setReturnValue(15.0F);
    }
}