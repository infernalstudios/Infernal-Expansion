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

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(MultiNoiseBiomeSource.Preset.class)
public class MixinMultiNoiseBiomeSourcePreset {

    @Shadow
    @Final
    ResourceLocation name;

    @ModifyVariable(method = "biomeSource(Lnet/minecraft/world/level/biome/MultiNoiseBiomeSource$PresetInstance;Z)Lnet/minecraft/world/level/biome/MultiNoiseBiomeSource;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/MultiNoiseBiomeSource$PresetInstance;biomes()Lnet/minecraft/core/Registry;", shift = At.Shift.BY, by = 4), name = "parameterlist", index = 3)
    private Climate.ParameterList<Holder<Biome>> addNetherBiomes(Climate.ParameterList<Holder<Biome>> parameterList) {
        List<Pair<Climate.ParameterPoint, Holder<Biome>>> biomeSource = new ArrayList<>(parameterList.values());

        if (name.equals(new ResourceLocation("nether"))) {
            for (Pair<ResourceLocation, Climate.ParameterPoint> biome : IEBiomes.getBiomeSource()) {
                ForgeRegistries.BIOMES.getHolder(biome.getFirst()).ifPresent(biomeHolder -> biomeSource.add(Pair.of(biome.getSecond(), biomeHolder)));
            }

            return new Climate.ParameterList<>(biomeSource);

        } else {
            return parameterList;
        }
    }

}
