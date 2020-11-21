package net.sourcewriters.server.amongus4j.net.packet.server;

import net.sourcewriters.server.amongus4j.net.packet.Packet;

public abstract class ServerPacket extends Packet {
	
	private final ServerPacketType type;
	
	public ServerPacket(ServerPacketType type) {
		this.type = type;
	}

	@Override
	public final int getId() {
		return type.packetId();
	}

	@Override
	public final boolean isClient() {
		return false;
	}

	public final ServerPacketType getType() {
		return type;
	}

}
