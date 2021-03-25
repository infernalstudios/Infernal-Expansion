package com.nekomaster1000.infernalexp.network;

import com.nekomaster1000.infernalexp.entities.InfernalPaintingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;
import java.util.function.Supplier;

public class SpawnInfernalPaintingPacket {

	private int entityID;
	private UUID uniqueID;
	private BlockPos pos;
	private Direction facing;
	private String title;

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
			ClientWorld world = Minecraft.getInstance().world;

			InfernalPaintingEntity paintingEntity = new InfernalPaintingEntity(world, message.pos, message.facing, ForgeRegistries.PAINTING_TYPES.getValue(new ResourceLocation(message.title)));
			paintingEntity.setEntityId(message.entityID);
			paintingEntity.setUniqueId(message.uniqueID);

			world.addEntity(message.entityID, paintingEntity);
		});

		ctx.get().setPacketHandled(true);
	}
}
