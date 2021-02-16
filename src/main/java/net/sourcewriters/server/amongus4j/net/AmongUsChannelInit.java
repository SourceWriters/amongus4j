package net.sourcewriters.server.amongus4j.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import net.sourcewriters.server.amongus4j.net.hazel.HazelPacketDecoder;
import net.sourcewriters.server.amongus4j.net.hazel.HazelPacketEncoder;

public class AmongUsChannelInit extends ChannelInitializer<NioDatagramChannel> {

	@Override
	protected void initChannel(NioDatagramChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
		// Decoding
		pipeline.addLast(new HazelPacketDecoder()); 
		// Listening
		pipeline.addLast(new AmongUsPacketListener()); 
		// Encoding
		pipeline.addLast(new HazelPacketEncoder()); 
	}

}
