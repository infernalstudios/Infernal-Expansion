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

import org.infernalstudios.infernalexp.util.WorldSeedHolder;

import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(DimensionGeneratorSettings.class)
public class MixinDimensionGeneratorSettings {
    /**
     * World seed for worldgen when not specified by JSON by Haven King
     * https://github.com/Hephaestus-Dev/seedy-behavior/blob/master/src/main/java/dev/hephaestus/seedy/mixin/world/gen/GeneratorOptionsMixin.java
     */

    @Inject(method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V",
        at = @At(value = "RETURN"))
    private void IE_giveUsRandomSeeds(long seed, boolean generateFeatures, boolean bonusChest, SimpleRegistry<Dimension> registry, Optional<String> s, CallbackInfo ci) {
        WorldSeedHolder.setSeed(seed);
    }
}
