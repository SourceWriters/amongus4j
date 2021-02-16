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
            handleControlPacket((ControlHazelPacket) packet);
            break;
        case RELIABLE:
            handleReliablePacket((ReliableHazelPacket) packet);
            break;
        case UNRELIABLE:
            break;
        }
    }

    private void handleControlPacket(ControlHazelPacket packet) {
        if (packet.getControlType() == HazelControlType.ACKNOWLEDGED) {
            return;
        }

    }

    private void handleReliablePacket(ReliableHazelPacket packet) {

    }

}
