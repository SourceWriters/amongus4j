package net.sourcewriters.server.amongus4j.net.data;

public class Offset {
	
	private int offset;
	
	public int set(int amount) {
		return offset = amount;
	}
	
	public int inc(int amount) {
		int buf = offset;
		offset += amount;
		return buf;
	}
	
	public int cur() {
		return offset;
	}

}
