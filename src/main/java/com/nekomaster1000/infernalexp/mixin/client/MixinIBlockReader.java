package com.nekomaster1000.infernalexp.mixin.client;

import com.nekomaster1000.infernalexp.client.DynamicLightingHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@Mixin(IBlockReader.class)
public interface MixinIBlockReader {
    @Overwrite
    default int getLightValue(BlockPos pos) {
        if (DynamicLightingHandler.LIGHT_SOURCES.containsKey(pos) && DynamicLightingHandler.LIGHT_SOURCES.get(pos).shouldKeep)
            return 15;
        return this.getBlockState(pos).getLightValue((IBlockReader) (Object) this, pos);
    }

    @Shadow
    BlockState getBlockState(BlockPos pos);

}