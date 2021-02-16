package net.sourcewriters.server.amongus4j.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sourcewriters.server.amongus4j.net.hazel.ControlHazelPacket;
import net.sourcewriters.server.amongus4j.net.hazel.HazelPacket;
import net.sourcewriters.server.amongus4j.net.hazel.HazelControlType;
import net.sourcewriters.server.amongus4j.net.hazel.ReliableHazelPacket;

public class AmongUsPacketListener extends SimpleChannelInboundHandler<HazelPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HazelPacket packet) throws Exception {
        switch (packet.getType()) {
        case CONTROL:
            handleControlPacket(ctx, (ControlHazelPacket) packet);
            break;
        case RELIABLE:
            handleReliablePacket(ctx, (ReliableHazelPacket) packet);
            break;
        case UNRELIABLE:
            break;
        }
    }

    private void handleControlPacket(ChannelHandlerContext ctx, ControlHazelPacket packet) throws Exception {
        if (packet.getControlType() == HazelControlType.ACKNOWLEDGED) {
            return;
        }
        ctx.writeAndFlush(ControlHazelPacket.acknowledge(packet));
    }

    private void handleReliablePacket(ChannelHandlerContext ctx, ReliableHazelPacket packet) throws Exception {
        ctx.writeAndFlush(ControlHazelPacket.acknowledge(packet));
    }

}
