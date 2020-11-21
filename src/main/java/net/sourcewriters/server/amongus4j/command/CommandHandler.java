package net.sourcewriters.server.amongus4j.command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;

import com.syntaxphoenix.syntaxapi.command.CommandManager;
import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.utils.alias.Alias;

public class CommandHandler {

	private final CommandManager manager;
	private final CommandListener listener;

	public CommandHandler(EventManager eventManager, String prefix, String splitter, ILogger logger) {
		this.manager = new CommandManager().setPrefix(prefix).setSplitter(splitter).setLogger(logger);
		this.listener = new CommandListener(manager);
		eventManager.registerEvents(listener);
	}

	/*
	 * Getter
	 */

	public CommandManager getManager() {
		return manager;
	}

	public CommandListener getListener() {
		return listener;
	}

	/*
	 * Command Loading
	 */

	public int load(PluginManager pluginManager) {

		if (manager.getCommands().length != 0)
			manager.unregisterAll();

		List<PluginWrapper> wrappers = pluginManager.getStartedPlugins();
		if (wrappers.isEmpty())
			return 0;

		int size = wrappers.size();
		for (int index = 0; index < size; index++) {
			PluginWrapper wrapper = wrappers.get(index);
			List<Class<? extends CommandExtension>> extensions = pluginManager.getExtensionClasses(CommandExtension.class, wrapper.getPluginId());
			if (extensions.isEmpty())
				continue;
			ArrayList<AmongCommand> commands = new ArrayList<>();
			int size0 = extensions.size();
			for (int index0 = 0; index0 < size0; index0++) {
				Class<? extends CommandExtension> clazz = extensions.get(index0);
				try {
					clazz.getDeclaredConstructor().newInstance().onCommandCreation(commands);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
					continue;
				}
			}
			if (commands.isEmpty())
				continue;
			size0 = commands.size();
			for (int index0 = 0; index0 < size0; index0++) {
				AmongCommand command = commands.get(index0);
				Alias alias;
				if ((alias = command.info()) == null)
					continue;
				manager.register(new PluginCommand(wrapper, command), alias);
			}
		}

		return manager.getCommands().length;
	}

	/*
	 * Command Management
	 */

	public PluginCommand[] getCommands(PluginWrapper wrapper) {
		return getCommands(wrapper == null ? null : wrapper.getPluginId());
	}

	public PluginCommand[] getCommands(String pluginId) {
		if (pluginId == null)
			return new PluginCommand[0];
		return Arrays.stream(manager.getCommands()).filter(command -> command instanceof PluginCommand).map(command -> (PluginCommand) command)
			.filter(command -> command.getWrapper().getPluginId().equals(pluginId)).toArray(size -> new PluginCommand[size]);
	}

}
