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

import org.infernalstudios.infernalexp.init.IEItems;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/item/UseAction;"), method = "func_241741_a_(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/renderer/entity/model/BipedModel$ArmPose;", cancellable = true)
    private static void renderWhipInfernalExpansion(AbstractClientPlayerEntity playerEntity, Hand hand, CallbackInfoReturnable<BipedModel.ArmPose> cir) {
        if (playerEntity.getHeldItem(hand).isItemEqual(IEItems.BLINDSIGHT_TONGUE_WHIP.get().getDefaultInstance()) || playerEntity.getHeldItem(hand).isItemEqual(IEItems.KINETIC_TONGUE_WHIP.get().getDefaultInstance())) {
            cir.setReturnValue(BipedModel.ArmPose.THROW_SPEAR);
        }
    }
}
