package net.sourcewriters.server.amongus4j.command;

import com.syntaxphoenix.syntaxapi.command.CommandManager;
//import com.syntaxphoenix.syntaxapi.command.CommandProcess;
import com.syntaxphoenix.syntaxapi.event.EventHandler;
import com.syntaxphoenix.syntaxapi.event.EventListener;
import com.syntaxphoenix.syntaxapi.event.EventPriority;

public class CommandListener implements EventListener {

	private final CommandManager commandManager;

	public CommandListener(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMessage() {
//		if (event.getMessageAuthor().isYourself())
//			return;

//		CommandProcess process = commandManager
//			.setInfoConstructor((manager, command) -> new AmongInfo(event.getApi(), event.getChannel(),
//				event.getMessageAuthor(), manager, command))
//			.process(event.getMessageContent());
//
//		switch (process.asState()) {
//		case NO_COMMAND:
//			break;
//		case NOT_EXISTENT:
//			event.getChannel().sendMessage("Command doesn't exist!");
//			break;
//		case READY:
//			process.execute();
//			break;
//		default:
//			break;
//		}
	}

}
