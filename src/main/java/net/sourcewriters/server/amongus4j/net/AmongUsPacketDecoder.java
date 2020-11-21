package net.sourcewriters.server.amongus4j.net;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.sourcewriters.server.amongus4j.net.data.BufferReader;
import net.sourcewriters.server.amongus4j.net.data.BufferWriter;

public class AmongUsPacketDecoder extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {

		ByteBuf buffer = msg.content();

		if (buffer.readableBytes() < 3) {
			return;
		}

		BufferReader reader = new BufferReader(buffer);

		// Read Hazel Packet length and tag

		int length = buffer.readUnsignedShort();
		short tag = buffer.readUnsignedByte();

		System.out.println("length=" + length + " / tag=" + tag);

		// Read AmongUs Packet type and afterwards the join packet data (version, name)

		short flag = buffer.readUnsignedByte();

		int version = buffer.readIntLE();
		String name = reader.readString();

		System.out.println("flag=" + flag + " / version=" + version + " / name='" + name + "'");

		// Reject AmongUs Connection
		
		// TODO: Work out why this isn't being received by the AmongUs Client

		BufferWriter writer = new BufferWriter();
		writer.writeByte((byte) 1);
		writer.writeByte((byte) 8);
		writer.writeString("This server isn't ready for Lobby creation yet!");
		
		Channel channel = ctx.channel();
		for (int tries = 0; tries < 100; tries++) {
			channel.writeAndFlush(new DatagramPacket(writer.asHazelBuffer(true), msg.sender(), (InetSocketAddress) ctx.channel().remoteAddress()));
		}
	}

}
