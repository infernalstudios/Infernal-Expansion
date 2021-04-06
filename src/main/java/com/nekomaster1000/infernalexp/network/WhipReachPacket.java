package com.nekomaster1000.infernalexp.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class WhipReachPacket {
	private final UUID playerUUID;
	private final int targetEntityID;

	public WhipReachPacket(UUID playerUUID, int target) {
		this.playerUUID = playerUUID;
		this.targetEntityID = target;
	}

	public static void encode(WhipReachPacket message, PacketBuffer buffer) {
		buffer.writeUniqueId(message.playerUUID);
		buffer.writeVarInt(message.targetEntityID);
	}

	public static WhipReachPacket decode(PacketBuffer buffer) {
		return new WhipReachPacket(buffer.readUniqueId(), buffer.readVarInt());
	}

	public static void handle(WhipReachPacket message, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			PlayerEntity playerEntity = context.get().getSender();

			if (playerEntity != null && playerEntity.getServer() != null) {
				ServerPlayerEntity player = playerEntity.getServer().getPlayerList().getPlayerByUUID(message.playerUUID);
				Entity target = playerEntity.getEntityWorld().getEntityByID(message.targetEntityID);

				if (player != null && target != null) {
					double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get());

					if (player.getDistanceSq(target) < (reach * reach) * player.getCooledAttackStrength(0.0F)) {
						player.attackTargetEntityWithCurrentItem(target);
					}
				}
			}
		});

		context.get().setPacketHandled(true);
	}
}
