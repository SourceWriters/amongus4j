package net.sourcewriters.server.amongus4j.net;

import java.net.InetAddress;
import java.util.Optional;

import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {

	private final Bootstrap bootstrap = new Bootstrap();

	private final Container<Channel> channel = Container.of();

	private final InetAddress address;
	private final int port;

	public UdpServer(int port) {
		this.address = null;
		this.port = port;
	}

	public UdpServer(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}

	public void start() {
		if (isRunning()) {
			return;
		}
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(new NioEventLoopGroup()).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new AmongUsChannelInit());
		ChannelFuture channelFuture;
		if (address == null) {
			channelFuture = bootstrap.bind(port);
		} else {
			channelFuture = bootstrap.bind(address, port);
		}
		this.channel.replace(channelFuture.channel());
	}

	public void stop() {
		if (!isRunning()) {
			return;
		}
		channel.get().close();
		channel.replace(null);
	}

	public boolean isRunning() {
		return channel.isPresent();
	}

	public Optional<Channel> getChannel() {
		return channel.asOptional();
	}
	
	public Bootstrap getBootstrap() {
		return bootstrap;
	}

}
