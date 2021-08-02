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

package com.nekomaster1000.infernalexp.network;

import com.nekomaster1000.infernalexp.entities.InfernalPaintingEntity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

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
        this.entityID = painting.getEntityId();
        this.uniqueID = painting.getUniqueID();
        this.pos = painting.getHangingPosition();
        this.facing = painting.getHorizontalFacing();
        this.title = ForgeRegistries.PAINTING_TYPES.getKey(painting.art).toString();
    }

    public SpawnInfernalPaintingPacket(int entityID, UUID uniqueID, BlockPos pos, Direction facing, String title) {
        this.entityID = entityID;
        this.uniqueID = uniqueID;
        this.pos = pos;
        this.facing = facing;
        this.title = title;
    }

    public static void encode(SpawnInfernalPaintingPacket message, PacketBuffer packetBuffer) {
        packetBuffer.writeVarInt(message.entityID);
        packetBuffer.writeUniqueId(message.uniqueID);
        packetBuffer.writeBlockPos(message.pos);
        packetBuffer.writeByte(message.facing.getHorizontalIndex());
        packetBuffer.writeString(message.title);
    }

    public static SpawnInfernalPaintingPacket decode(PacketBuffer packetBuffer) {
        return new SpawnInfernalPaintingPacket(
            packetBuffer.readVarInt(),
            packetBuffer.readUniqueId(),
            packetBuffer.readBlockPos(),
            Direction.byHorizontalIndex(packetBuffer.readByte()),
            packetBuffer.readString()
        );
    }

    public static void handle(SpawnInfernalPaintingPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Optional<World> world = LogicalSidedProvider.CLIENTWORLD.get(ctx.get().getDirection().getReceptionSide());

            InfernalPaintingEntity paintingEntity = new InfernalPaintingEntity(world.orElse(null), message.pos, message.facing, ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(message.title)));
            paintingEntity.setEntityId(message.entityID);
            paintingEntity.setUniqueId(message.uniqueID);

            world.filter(ClientWorld.class::isInstance).ifPresent(w -> ((ClientWorld) w).addEntity(message.entityID, paintingEntity));
        });

        ctx.get().setPacketHandled(true);
    }
}
