package net.sourcewriters.server.amongus4j.net.packet;

public abstract class Packet {
	
	public abstract boolean isClient();
	
	public abstract int getId();
	
	public abstract byte[] serialize();

}
