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

package com.nekomaster1000.infernalexp.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.nekomaster1000.infernalexp.client.sound.GlowsquitoFlightSound;
import com.nekomaster1000.infernalexp.entities.GlowsquitoEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.server.SSpawnMobPacket;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(ClientPlayNetHandler.class)
public class MixinClientPlayNetHandler {

	@Shadow
	private Minecraft client;

	@Inject(method = "handleSpawnMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;addEntity(ILnet/minecraft/entity/Entity;)V", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void IE_playGlowsquitoSound(SSpawnMobPacket packetIn, CallbackInfo ci, double d0, double d1, double d2, float f, float f1, LivingEntity livingentity) {
		if (livingentity instanceof GlowsquitoEntity) {
			this.client.getSoundHandler().playOnNextTick(new GlowsquitoFlightSound((GlowsquitoEntity) livingentity));
		}
	}

}
