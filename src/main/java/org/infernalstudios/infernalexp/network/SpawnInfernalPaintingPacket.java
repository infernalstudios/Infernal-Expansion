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

package org.infernalstudios.infernalexp.network;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.infernalexp.entities.InfernalPaintingEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class SpawnInfernalPaintingPacket {

    private final int entityID;
    private final UUID uniqueID;
    private final BlockPos pos;
    private final Direction facing;
    private final String title;

    public SpawnInfernalPaintingPacket(InfernalPaintingEntity painting) {
        this.entityID = painting.getId();
        this.uniqueID = painting.getUUID();
        this.pos = painting.getPos();
        this.facing = painting.getDirection();
        this.title = ForgeRegistries.PAINTING_TYPES.getKey(painting.motive).toString();
    }

    public SpawnInfernalPaintingPacket(int entityID, UUID uniqueID, BlockPos pos, Direction facing, String title) {
        this.entityID = entityID;
        this.uniqueID = uniqueID;
        this.pos = pos;
        this.facing = facing;
        this.title = title;
    }

    public static void encode(SpawnInfernalPaintingPacket message, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeVarInt(message.entityID);
        packetBuffer.writeUUID(message.uniqueID);
        packetBuffer.writeBlockPos(message.pos);
        packetBuffer.writeByte(message.facing.get2DDataValue());
        packetBuffer.writeUtf(message.title);
    }

    public static SpawnInfernalPaintingPacket decode(FriendlyByteBuf packetBuffer) {
        return new SpawnInfernalPaintingPacket(
            packetBuffer.readVarInt(),
            packetBuffer.readUUID(),
            packetBuffer.readBlockPos(),
            Direction.from2DDataValue(packetBuffer.readByte()),
            packetBuffer.readUtf()
        );
    }

    public static void handle(SpawnInfernalPaintingPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Optional<Level> world = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());

            InfernalPaintingEntity paintingEntity = new InfernalPaintingEntity(world.orElse(null), message.pos, message.facing, ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(message.title)));
            paintingEntity.setId(message.entityID);
            paintingEntity.setUUID(message.uniqueID);

            world.filter(ClientLevel.class::isInstance).ifPresent(w -> ((ClientLevel) w).putNonPlayerEntity(message.entityID, paintingEntity));
        });

        ctx.get().setPacketHandled(true);
    }
}
