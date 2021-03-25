package com.nekomaster1000.infernalexp.network;

import com.nekomaster1000.infernalexp.InfernalExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkManager {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(InfernalExpansion.MOD_ID, "data"),
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);

	public static void registerPackets() {
		int i = 0;

		INSTANCE.registerMessage(
			i,
			SpawnInfernalPaintingPacket.class,
			SpawnInfernalPaintingPacket::encode,
			SpawnInfernalPaintingPacket::decode,
			SpawnInfernalPaintingPacket::handle
		);
	}

}
