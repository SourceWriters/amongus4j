package net.sourcewriters.server.amongus4j.utils;

public final class JavaHelper {
	
	private JavaHelper() {};
	
	/*
	 * Array Helper
	 */

	public static byte[][] partition(byte[] args, int length) {
		int size = (int) Math.floor(args.length / (float) length);
		if(args.length % length != 0) {
			size++;
		}
		byte[][] output = new byte[size][length];
		for(int index = 0; index < size; index++) {
			System.arraycopy(args, index * length, output[index], 0, length);
		}
		return output;
	}

}
