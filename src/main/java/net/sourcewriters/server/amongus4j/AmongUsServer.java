package net.sourcewriters.server.amongus4j;

import com.syntaxphoenix.syntaxapi.command.ArgumentMap;
import com.syntaxphoenix.syntaxapi.command.helper.JVMArgumentHelper;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

import net.sourcewriters.server.amongus4j.net.UdpServer;
import net.sourcewriters.server.amongus4j.utils.ReflectionProvider;

public final class AmongUsServer {
	
	private static AmongUsServer INSTANCE;
	
	public static void main(String[] args) {
		INSTANCE = new AmongUsServer(JVMArgumentHelper.DEFAULT.serialize(args));
	}
	
	public static AmongUsServer get() {
		return INSTANCE;
	}
	
	/*
	 * 
	 */

	private final ReflectionProvider reflectionProvider = null;
	private final ILogger logger = null;
	
	public AmongUsServer(ArgumentMap args) {
		
		UdpServer server = new UdpServer(22023);
		server.start();
		
	}
	
	/*
	 * Getter
	 */
	
	public ReflectionProvider getReflectionProvider() {
		return reflectionProvider;
	}
	
	public ILogger getLogger() {
		return logger;
	}

}
