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

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import org.infernalstudios.infernalexp.init.IEItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public class MixinItemInHandRenderer {

    @Inject(method = "evaluateWhichHandsToRender", at = @At("HEAD"), cancellable = true)
    private static void IE_selectionUsingItemWhileHoldingGlowsilkBow(LocalPlayer player, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        if (player.isUsingItem()) {
            if (player.getMainHandItem().is(IEItems.GLOWSILK_BOW.get()) || player.getOffhandItem().is(IEItems.GLOWSILK_BOW.get())) {
                cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(player.getUsedItemHand()));
            }
        }
    }

}
