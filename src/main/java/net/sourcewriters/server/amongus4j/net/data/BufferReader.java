package net.sourcewriters.server.amongus4j.net.data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;

public class BufferReader {

	private final ByteBuf buffer;

	public BufferReader(ByteBuf buffer) {
		this.buffer = buffer;
	}

	public BufferReader(ByteBuf buffer, int offset) {
		this.buffer = buffer;
		this.buffer.resetReaderIndex();
		this.buffer.skipBytes(offset);
	}

	public final void setOffset(int offset) {
		buffer.resetReaderIndex();
		buffer.skipBytes(offset);
	}

	public final int getOffset() {
		return buffer.readerIndex();
	}

	public final void reset() {
		buffer.resetReaderIndex();
	}

	public final ByteBuf getBuffer() {
		return buffer;
	}

	public String readString() {
		return readString(StandardCharsets.UTF_8);
	}

	public String readString(Charset charset) {
		return buffer.readCharSequence(readPackedInt(), charset).toString();
	}

	public int readPackedInt() {
		return (int) readPackedUnsignedInt();
	}

	public long readPackedUnsignedInt() {
		boolean readMore = true;
		int shift = 0;
		long output = 0;
		while (readMore) {
			short value = buffer.readUnsignedByte();
			if (value >= 0x80) {
				readMore = true;
				value ^= 0x80;
			} else {
				readMore = false;
			}
			output |= (long) (value << shift);
			shift += 7;
		}
		return output;
	}

}
