package net.sourcewriters.server.amongus4j.net.hazel;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;

public class UnreliableHazelPacket extends HazelPacket {

    public UnreliableHazelPacket(int id, int length, short tag, ByteBuf data, InetSocketAddress sender, InetSocketAddress recipient) {
        super(id, length, tag, data, sender, recipient);
    }

    @Override
    public HazelPacketType getType() {
        return HazelPacketType.UNRELIABLE;
    }

    @Override
    public short getTypeId() {
        return 0;
    }

}
