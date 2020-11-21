package net.sourcewriters.server.amongus4j.command;

import java.util.ArrayList;

import org.pf4j.ExtensionPoint;

public interface CommandExtension extends ExtensionPoint {
	
	public void onCommandCreation(ArrayList<AmongCommand> list);

}
