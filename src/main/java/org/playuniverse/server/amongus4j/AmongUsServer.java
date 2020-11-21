package org.playuniverse.server.amongus4j;

import org.playuniverse.server.amongus4j.utils.ReflectionProvider;

import com.syntaxphoenix.syntaxapi.command.ArgumentMap;
import com.syntaxphoenix.syntaxapi.command.helper.JVMArgumentHelper;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

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
