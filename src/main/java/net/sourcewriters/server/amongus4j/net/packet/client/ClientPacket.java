package net.sourcewriters.server.amongus4j.net.packet.client;

import net.sourcewriters.server.amongus4j.net.packet.Packet;

public abstract class ClientPacket extends Packet {

	private final ClientPacketType type;

	public ClientPacket(ClientPacketType type) {
		this.type = type;
	}

	@Override
	public final int getId() {
		return type.packetId();
	}

	@Override
	public final boolean isClient() {
		return true;
	}

	public final ClientPacketType getType() {
		return type;
	}

}
