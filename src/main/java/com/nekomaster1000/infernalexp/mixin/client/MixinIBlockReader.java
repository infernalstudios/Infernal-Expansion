package com.nekomaster1000.infernalexp.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.nekomaster1000.infernalexp.client.DynamicLightingHandler;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

@Mixin(value = IBlockReader.class, priority = 200)
public interface MixinIBlockReader {

    /**
     * @reason Unknown
     * @author Unknown
     * @param pos
     * @return lightlevel for given blockPos.
     */

	@Overwrite
	default int getLightValue(BlockPos pos) {
		if (DynamicLightingHandler.LIGHT_SOURCES.containsKey(pos) && DynamicLightingHandler.LIGHT_SOURCES.get(pos).shouldKeep) {
			return 10;
		}
		return this.getBlockState(pos).getLightValue((IBlockReader) this, pos);
	}

	@Shadow
	BlockState getBlockState(BlockPos pos);

}
