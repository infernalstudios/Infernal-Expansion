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
