package net.sourcewriters.server.amongus4j.net.hazel;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;

public abstract class HazelPacket {

    private final int id;

    private final InetSocketAddress sender;
    private final InetSocketAddress recipient;

    private final int length;
    private final short tag;
    
    private final ByteBuf data;

    public HazelPacket(int id, int length, short tag, ByteBuf data, InetSocketAddress sender, InetSocketAddress recipient) {
        this.id = id;
        this.length = length;
        this.tag = tag;
        this.data = data;
        this.recipient = recipient;
        this.sender = sender;
    }

    public InetSocketAddress getRecipient() {
        return recipient;
    }

    public InetSocketAddress getSender() {
        return sender;
    }

    public int getLength() {
        return length;
    }

    public short getTag() {
        return tag;
    }
    
    public int getId() {
        return id;
    }
    
    public ByteBuf getData() {
        return data;
    }

    public abstract HazelPacketType getType();
    
    public abstract short getTypeId();

}
