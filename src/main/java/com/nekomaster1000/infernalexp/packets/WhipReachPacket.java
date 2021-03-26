package com.nekomaster1000.infernalexp.packets;

import com.nekomaster1000.infernalexp.packets.IIEPacket.IEPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class WhipReachPacket extends IEPacket {
	private final UUID playerUUID;
	private final int targetEntityID;

	public WhipReachPacket(UUID playerUUID, int target) {
		this.playerUUID = playerUUID;
		this.targetEntityID = target;
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeLong(this.playerUUID.getMostSignificantBits()).writeLong(this.playerUUID.getLeastSignificantBits());
		buf.writeVarInt(this.targetEntityID);
	}

	public static WhipReachPacket decode(PacketBuffer buf) {
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		return new WhipReachPacket(uuid, buf.readVarInt());
	}

	@Override
	public void execute(PlayerEntity playerEntity) {
		if (playerEntity != null && playerEntity.getServer() != null) {
			ServerPlayerEntity player = playerEntity.getServer().getPlayerList().getPlayerByUUID(this.playerUUID);
			Entity target = playerEntity.getEntityWorld().getEntityByID(this.targetEntityID);
			if (player != null && target != null) {
				double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
				if (player.getDistanceSq(target) < reach * reach) {
					player.attackTargetEntityWithCurrentItem(target);
				}
			}
		}
	}
}
