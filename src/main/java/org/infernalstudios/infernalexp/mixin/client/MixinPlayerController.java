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

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import org.infernalstudios.infernalexp.events.RightClickBlockAfterActionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerController.class)
public class MixinPlayerController {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameType;isCreative()Z"), method = "func_217292_a")
    private void IE_fireRightClickBlockAfterActionEventClient(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockRayTraceResult hitVec, CallbackInfoReturnable<ActionResultType> cir) {
        MinecraftForge.EVENT_BUS.post(new RightClickBlockAfterActionEvent(player, hand, hitVec.getPos(), hitVec));
    }

}
