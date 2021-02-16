package net.sourcewriters.server.amongus4j.net.hazel;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

public class HazelPacketEncoder extends MessageToMessageEncoder<HazelPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HazelPacket msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(msg.getTypeId());
        buffer.writeShort(msg.getId());
        if (msg.getLength() != -1) {
            buffer.writeShort(msg.getLength());
            buffer.writeByte(msg.getTag());
            buffer.writeBytes(msg.getData());
        }
        out.add(new DatagramPacket(buffer, msg.getRecipient(), msg.getSender()));
    }

}
