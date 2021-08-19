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

import net.minecraft.world.gen.ChunkGenerator;
import org.infernalstudios.infernalexp.config.InfernalExpansionConfig.WorldGeneration;
import org.infernalstudios.infernalexp.util.NoiseChunkGeneratorUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator {

    @Inject(at = @At("HEAD"), method = "<clinit>")
    private static void IE_clinit(CallbackInfo ci) {
        if ((boolean) WorldGeneration.REPLACE_NETHER_BIOME_PROVIDER.get()) {
            NoiseChunkGeneratorUtil.useCustomNetherBiomeProvider();
        }
    }

}
