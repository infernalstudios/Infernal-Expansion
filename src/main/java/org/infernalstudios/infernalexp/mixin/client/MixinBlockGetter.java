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

package org.infernalstudios.infernalexp.mixin.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.infernalstudios.infernalexp.client.DynamicLightingHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockGetter.class)
public interface MixinBlockGetter {

    /**
     * @author SwanX1
     * @reason You can't inject into an interface, this is the only way.
     */
    @Overwrite
    default int getLightEmission(BlockPos pos) {
        if (DynamicLightingHandler.LIGHT_SOURCES.containsKey(pos) && DynamicLightingHandler.LIGHT_SOURCES.get(pos).shouldKeep) {
            return 10;
        }
        return this.getBlockState(pos).getLightEmission((BlockGetter) this, pos);
    }

    @Shadow
    BlockState getBlockState(BlockPos pos);

}
