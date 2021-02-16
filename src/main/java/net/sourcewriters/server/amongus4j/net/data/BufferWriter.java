package net.sourcewriters.server.amongus4j.net.data;

import static net.sourcewriters.server.amongus4j.net.data.DataSerialization.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufferWriter {

	private byte[] buffer = new byte[0];

	public void writeByte(byte value) {
		append(fromByte(value));
	}

	public void writeShort(short value) {
		append(fromShort(value));
	}

	public void writeInt(int value) {
		append(fromInt(value));
	}

	public void writeFloat(float value) {
		append(fromFloat(value));
	}

	public void writeLong(long value) {
		append(fromLong(value));
	}

	public void writeDouble(double value) {
		append(fromDouble(value));
	}

	public void writeBoolean(boolean value) {
		append(fromBoolean(value));
	}

	public void writeString(String value) {
		writeString(value, StandardCharsets.UTF_8);
	}

	public void writeString(String value, Charset charset) {
		byte[] bytes = fromString(value, charset);
		writePackedInt(bytes.length);
		append(bytes);
	}

	public void writePackedInt(int value) {
		writePackedUnsignedInt(Integer.toUnsignedLong(value));
	}

	public void writePackedUnsignedInt(long value) {
		do {
			short b = (short) (value & 0xFF);
			if (value >= 0x80) {
				b |= 0x80;
			}
			writeShort(b);
			value >>= 7;
		} while (value > 0);
	}
	
	public int length() {
	    return buffer.length;
	}

	public ByteBuf asBuffer() {
		return Unpooled.wrappedBuffer(buffer);
	}

	public ByteBuf asHazelBuffer(boolean reliable) {
		ByteBuf buf = Unpooled.buffer(2048);
		if(reliable) {
			buf.writeByte((byte) 0);
		} else {
			buf.writeByte((byte) buffer.length);
			buf.writeByte((byte) (buffer.length >> 8));
			buf.writeByte((byte) 1);
		}
		buf.writeBytes(buffer);
		return buf;
	}

	private byte[] merge(byte[] var0, byte[] var1) {
		byte[] output = new byte[var0.length + var1.length];
		System.arraycopy(var0, 0, output, 0, var0.length);
		System.arraycopy(var1, 0, output, var0.length, var1.length);
		return output;
	}

	private void append(byte[] data) {
		buffer = merge(buffer, data);
	}

}
