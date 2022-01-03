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

import org.infernalstudios.infernalexp.entities.GlowsquitoEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import org.infernalstudios.infernalexp.client.sound.GlowsquitoFlightSound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "handleAddMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;putNonPlayerEntity(ILnet/minecraft/world/entity/Entity;)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void IE_playGlowsquitoSound(ClientboundAddMobPacket packetIn, CallbackInfo ci, LivingEntity livingentity) {
        if (livingentity instanceof GlowsquitoEntity glowsquito) {
            this.minecraft.getSoundManager().queueTickingSound(new GlowsquitoFlightSound(glowsquito));
        }
    }

}
