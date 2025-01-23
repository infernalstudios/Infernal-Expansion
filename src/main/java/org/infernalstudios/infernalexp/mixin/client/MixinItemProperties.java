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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(targets = "net.minecraft.client.renderer.item.ItemProperties$1")
public class MixinItemProperties {

    @WrapOperation(
        method = "unclampedCall",
        at = @At(
            value = "INVOKE",
            target = "java/lang/Math.random()D"
        )
    )
    private double IE_daytimeInGSC(Operation<Double> original, @Local(index = 2, argsOnly = true) ClientLevel clientWorld, @Local(index = 5) Entity entity) {
        Optional<ResourceKey<Biome>> biomeKey = clientWorld.getBiome(entity.blockPosition()).unwrapKey();

        if (biomeKey.isPresent() && biomeKey.get().equals(IEBiomes.GLOWSTONE_CANYON)) {
            return Mth.nextDouble(entity.random, 0.95, 1.05) % 1;
        }
        return original.call();
    }
}
