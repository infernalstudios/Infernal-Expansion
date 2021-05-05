package com.nekomaster1000.infernalexp.network;

import com.nekomaster1000.infernalexp.InfernalExpansion;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class IENetworkHandler {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(InfernalExpansion.MOD_ID, "main"),
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);

	private static int index;

	public static synchronized void register() {
        INSTANCE.messageBuilder(WhipReachPacket.class, index++).encoder(WhipReachPacket::encode).decoder(WhipReachPacket::decode).consumer(WhipReachPacket::handle).add();
        INSTANCE.messageBuilder(SpawnInfernalPaintingPacket.class, index++).encoder(SpawnInfernalPaintingPacket::encode).decoder(SpawnInfernalPaintingPacket::decode).consumer(SpawnInfernalPaintingPacket::handle).add();
    }

	public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	public static <MSG> void sendToAll(MSG message) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), message);
	}

	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}
}
