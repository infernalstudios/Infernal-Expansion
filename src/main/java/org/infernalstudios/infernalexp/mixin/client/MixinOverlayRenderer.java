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

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OverlayRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.infernalstudios.infernalexp.access.FireTypeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(OverlayRenderer.class)
public class MixinOverlayRenderer {

    @ModifyVariable(method = "renderFire", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/renderer/model/RenderMaterial;getSprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0), name = "textureatlassprite")
    private static TextureAtlasSprite IE_renderCustomFiresOverlay(TextureAtlasSprite original, Minecraft minecraftIn, MatrixStack matrixStackIn) {
        return ((FireTypeAccess) minecraftIn.player).getFireType().getSprite1().getSprite();
    }

}
