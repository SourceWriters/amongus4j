package net.sourcewriters.server.amongus4j.plugin;

import org.pf4j.PluginWrapper;

public class PluginEnableEvent extends PluginEvent {

	public PluginEnableEvent(SafePluginManager manager, PluginWrapper wrapper) {
		super(manager, wrapper);
	}
	
}
