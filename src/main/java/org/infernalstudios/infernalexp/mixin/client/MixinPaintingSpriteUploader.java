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

package org.infernalstudios.infernalexp.mixin.client;

import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import org.infernalstudios.infernalexp.client.entity.render.InfernalPaintingRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(PaintingTextureManager.class)
public class MixinPaintingSpriteUploader {

    @Shadow
    @Final
    private static ResourceLocation BACK_SPRITE_LOCATION;

    @Inject(method = "getResourcesToLoad", at = @At("RETURN"), cancellable = true, remap = false)
    private void getResourcesToLoad(CallbackInfoReturnable<Stream<ResourceLocation>> cir) {
        cir.setReturnValue(Stream.concat(Registry.MOTIVE.keySet().stream(), Stream.of(BACK_SPRITE_LOCATION, InfernalPaintingRenderer.BACK_TEXTURE_ATLAS_LOCATION)));
    }

}
