package org.playuniverse.server.amongus4j.command;

import com.syntaxphoenix.syntaxapi.command.Arguments;
import com.syntaxphoenix.syntaxapi.command.BaseCommand;
import com.syntaxphoenix.syntaxapi.command.BaseInfo;
import com.syntaxphoenix.syntaxapi.utils.alias.Alias;

public abstract class AmongCommand extends BaseCommand {

	@Override
	public void execute(BaseInfo info, Arguments arguments) {
		if (info instanceof AmongInfo)
			execute((AmongInfo) info, arguments);
	}

	public abstract void execute(AmongInfo info, Arguments arguments);
	
	protected abstract Alias info();

}
