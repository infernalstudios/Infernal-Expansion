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

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import org.infernalstudios.infernalexp.init.IEBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

@Mixin(targets = "net.minecraft.client.renderer.item.ItemProperties$static$1")
public class MixinItemProperties {

    @ModifyVariable(method = "unclampedCall", at = @At(value = "STORE", ordinal = 1 /* this ordinal is when its set to Math.random(), the second time d0 is set to something */), ordinal = 0 /* this ordinal means the first double variable */)
    private double IE_daytimeInGSC(double in, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity) {
        ClientLevel clientWorld = world;
        if (entity == null) {
            return in;
        }
        if (world == null && entity.level instanceof ClientLevel) {
            clientWorld = (ClientLevel) entity.getCommandSenderWorld();
        }

        Optional<ResourceKey<Biome>> biomeKey = clientWorld.getBiome(entity.blockPosition()).unwrapKey();

        if (biomeKey.isPresent() && biomeKey.get().equals(IEBiomes.GLOWSTONE_CANYON)) {
            return Mth.nextDouble(new Random(), 0.95, 1.05) % 1;
        }
        return in;
    }
}
