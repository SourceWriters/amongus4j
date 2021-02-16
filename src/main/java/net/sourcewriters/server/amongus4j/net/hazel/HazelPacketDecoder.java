package net.sourcewriters.server.amongus4j.net.hazel;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

public class HazelPacketDecoder extends MessageToMessageDecoder<DatagramPacket> {

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = msg.content();
        short type = buffer.readUnsignedByte();
        int id = buffer.readUnsignedShort();
        int length;
        short tag;
        if (type == 10) {
            length = -1;
            tag = -1;
        } else {
            length = buffer.readUnsignedShort();
            tag = buffer.readUnsignedByte();
        }
        boolean reliable = (type & 0b0000_0001) == 1;
        if ((type & 0b0000_1000) == 8) {
            out.add(new ControlHazelPacket(id, length, tag, buffer, msg.sender(), msg.recipient(),
                HazelControlType.of(reliable, (type & 0b000_0010) == 2)));
        } else if (reliable) {
            out.add(new ReliableHazelPacket(id, length, tag, buffer, msg.sender(), msg.recipient()));
        } else {
            out.add(new UnreliableHazelPacket(id, length, tag, buffer, msg.sender(), msg.recipient()));
        }
    }

}
