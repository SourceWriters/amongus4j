package net.sourcewriters.server.amongus4j.net.hazel;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;

public class ControlHazelPacket extends HazelPacket {

    private final HazelControlType type;

    public ControlHazelPacket(int id, int length, short tag, ByteBuf data, InetSocketAddress sender, InetSocketAddress recipient,
        HazelControlType type) {
        super(id, length, tag, data, sender, recipient);
        this.type = type;
    }

    @Override
    public HazelPacketType getType() {
        return HazelPacketType.CONTROL;
    }

    public HazelControlType getControlType() {
        return type;
    }

    @Override
    public short getTypeId() {
        short output = 8;
        if (type.isReliable()) {
            output += 2;
        }
        if (type.isFragmented()) {
            output += 4;
        }
        return output;
    }

}
