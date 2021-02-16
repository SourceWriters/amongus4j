package net.sourcewriters.server.amongus4j.net.hazel;

import java.net.InetSocketAddress;

import io.netty.buffer.Unpooled;

public class ControlHazelPacket extends HazelPacket {

    private final HazelControlType type;

    public ControlHazelPacket(int id, int length, short tag, InetSocketAddress sender, InetSocketAddress recipient, HazelControlType type) {
        super(id, length, tag, Unpooled.EMPTY_BUFFER, sender, recipient);
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

    public static ControlHazelPacket acknowledge(HazelPacket packet) {
        return new ControlHazelPacket(packet.getId(), -1, (short) 0, packet.getRecipient(), packet.getSender(),
            HazelControlType.ACKNOWLEDGED);
    }

    public static ControlHazelPacket disconnect(HazelPacket packet) {
        return new ControlHazelPacket(packet.getId(), -1, (short) 0, packet.getRecipient(), packet.getSender(),
            HazelControlType.DISCONNECT);
    }

}
