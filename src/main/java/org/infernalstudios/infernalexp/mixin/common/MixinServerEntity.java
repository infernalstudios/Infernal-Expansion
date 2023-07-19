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

import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import org.infernalstudios.infernalexp.entities.GlowsilkArrowEntity;
import org.infernalstudios.infernalexp.network.GlowsilkArrowVelocityPacket;
import org.infernalstudios.infernalexp.network.IENetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(ServerEntity.class)
public class MixinServerEntity {

    @Shadow
    @Final
    private Entity entity;

    @Redirect(method = "sendChanges", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 2))
    private void IE_updateArrowVelocitySendChanges(Consumer<Object> instance, Object t) {
        if (entity instanceof GlowsilkArrowEntity glowsilkArrow) {
            instance.accept(IENetworkHandler.INSTANCE.toVanillaPacket(new GlowsilkArrowVelocityPacket(glowsilkArrow, false), NetworkDirection.PLAY_TO_CLIENT));
        } else {
            instance.accept(t);
        }
    }

    @Redirect(method = "sendChanges", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 3))
    private void IE_updateArrowVelocityFullSendChanges(Consumer<Object> instance, Object t) {
        if (entity instanceof GlowsilkArrowEntity glowsilkArrow && t instanceof ClientboundMoveEntityPacket.PosRot) {
            instance.accept(IENetworkHandler.INSTANCE.toVanillaPacket(new GlowsilkArrowVelocityPacket(glowsilkArrow, true), NetworkDirection.PLAY_TO_CLIENT));
        } else {
            instance.accept(t);
        }
    }

    @Redirect(method = "sendPairingData", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 3))
    private void IE_updateArrowVelocitySendPairingData(Consumer<Object> instance, Object t) {
        if (entity instanceof GlowsilkArrowEntity glowsilkArrow) {
            instance.accept(IENetworkHandler.INSTANCE.toVanillaPacket(new GlowsilkArrowVelocityPacket(glowsilkArrow, false), NetworkDirection.PLAY_TO_CLIENT));
        } else {
            instance.accept(t);
        }
    }

}
