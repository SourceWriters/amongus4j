package net.sourcewriters.server.amongus4j.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class AmongUsPacketDecoder extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		
		
		// TODO: Discover how to serialize C# Hazel Packets and how the handshake works
		
	}

}
