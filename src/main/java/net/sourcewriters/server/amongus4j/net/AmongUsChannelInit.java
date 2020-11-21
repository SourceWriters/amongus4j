package net.sourcewriters.server.amongus4j.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class AmongUsChannelInit extends ChannelInitializer<NioDatagramChannel> {

	@Override
	protected void initChannel(NioDatagramChannel channel) throws Exception {

		ChannelPipeline pipeline = channel.pipeline();
		pipeline.addLast(new AmongUsPacketDecoder());
		
	}

}
