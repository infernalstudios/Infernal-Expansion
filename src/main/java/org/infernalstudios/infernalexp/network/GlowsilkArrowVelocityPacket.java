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

package org.infernalstudios.infernalexp.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.network.NetworkEvent;
import org.infernalstudios.infernalexp.entities.GlowsilkArrowEntity;

import java.util.Optional;
import java.util.function.Supplier;

public class GlowsilkArrowVelocityPacket {

    private final boolean movement;
    private final int entityId;
    private final double velocityX;
    private final double velocityY;
    private final double velocityZ;
    private final float xRotation;
    private final float yRotation;
    private final boolean onGround;

    private GlowsilkArrowVelocityPacket(int entityId, double velocityX, double velocityY, double velocityZ) {
        this(false, entityId, velocityX, velocityY, velocityZ, 0F, 0F, false);
    }

    private GlowsilkArrowVelocityPacket(int entityId, double velocityX, double velocityY, double velocityZ, float xRotation, float yRotation, boolean onGround) {
        this(true, entityId, velocityX, velocityY, velocityZ, xRotation, yRotation, onGround);
    }

    private GlowsilkArrowVelocityPacket(boolean movement, int entityId, double velocityX, double velocityY, double velocityZ, float xRotation, float yRotation, boolean onGround) {
        this.movement = movement;
        this.entityId = entityId;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.onGround = onGround;
    }

    public GlowsilkArrowVelocityPacket(GlowsilkArrowEntity entity, boolean movement) {
        this(movement, entity.getId(), entity.getDeltaMovement().x(), entity.getDeltaMovement().y(), entity.getDeltaMovement().z(), entity.getXRot(), entity.getYRot(), entity.isOnGround());
    }

    public static void encode(GlowsilkArrowVelocityPacket message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.movement);
        buffer.writeVarInt(message.entityId);
        buffer.writeDouble(message.velocityX);
        buffer.writeDouble(message.velocityY);
        buffer.writeDouble(message.velocityZ);

        if (message.movement) {
            buffer.writeFloat(message.xRotation);
            buffer.writeFloat(message.yRotation);
            buffer.writeBoolean(message.onGround);
        }
    }

    public static GlowsilkArrowVelocityPacket decode(FriendlyByteBuf buffer) {
        boolean movement = buffer.readBoolean();

        if (movement) {
            return new GlowsilkArrowVelocityPacket(
                buffer.readVarInt(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readBoolean()
            );
        } else {
            return new GlowsilkArrowVelocityPacket(
                buffer.readVarInt(),
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble()
            );
        }
    }

    public static void handle(GlowsilkArrowVelocityPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Optional<Level> clientLevel = LogicalSidedProvider.CLIENTWORLD.get(context.get().getDirection().getReceptionSide());

            clientLevel.ifPresent(level -> {
                Entity entity = level.getEntity(message.entityId);

                if (entity != null) {
                    entity.lerpMotion(message.velocityX, message.velocityY, message.velocityZ);

                    if (message.movement) {
                        entity.setXRot(message.xRotation);
                        entity.setYRot(message.yRotation);
                        entity.setOnGround(message.onGround);
                    }
                }
            });
        });

        context.get().setPacketHandled(true);
    }

}
