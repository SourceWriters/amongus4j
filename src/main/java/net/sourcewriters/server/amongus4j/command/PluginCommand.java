package net.sourcewriters.server.amongus4j.command;

import org.pf4j.PluginWrapper;

import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseCommand;
import com.syntaxphoenix.syntaxapi.command.BaseCompletion;
import com.syntaxphoenix.syntaxapi.command.BaseInfo;

public class PluginCommand extends BaseCommand {

	private final PluginWrapper wrapper;
	private final BaseCommand command;

	public PluginCommand(PluginWrapper wrapper, BaseCommand command) {
		this.wrapper = wrapper;
		this.command = command;
	}

	public PluginWrapper getWrapper() {
		return wrapper;
	}

	public BaseCommand getCommand() {
		return command;
	}

	/*
	 * BaseCommand Implementation
	 */

	@Override
	public void execute(BaseInfo info, Arguments arguments) {
		command.execute(info, arguments);
	}

	@Override
	public BaseCompletion complete(BaseInfo info, Arguments arguments) {
		return command.complete(info, arguments);
	}

}
