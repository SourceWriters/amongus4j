package org.playuniverse.server.amongus4j.plugin;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.pf4j.PluginWrapper;
import org.playuniverse.server.amongus4j.command.CommandHandler;
import org.playuniverse.server.amongus4j.command.PluginCommand;
import org.playuniverse.server.amongus4j.config.Config;
import org.playuniverse.server.amongus4j.utils.ReflectionProvider;

import com.syntaxphoenix.syntaxapi.command.CommandManager;
import com.syntaxphoenix.syntaxapi.event.Event;
import com.syntaxphoenix.syntaxapi.event.EventExecutor;
import com.syntaxphoenix.syntaxapi.event.EventListener;
import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.logging.ILogger;
import com.syntaxphoenix.syntaxapi.service.ServiceManager;
import com.syntaxphoenix.syntaxapi.utils.java.Collect;
import com.syntaxphoenix.syntaxapi.utils.java.tools.Container;

public class SafePluginManager extends DefaultPluginManager implements PluginStateListener {

	private final Container<ReflectionProvider> provider;

	private final ServiceManager service;

	private final CommandHandler command;

	private final EventManager event;

	private final ILogger logger;

	public SafePluginManager(ILogger logger, Container<ReflectionProvider> provider, CommandHandler command,
		EventManager event, ServiceManager service) {
		super();
		this.provider = provider;
		this.event = event;
		this.logger = logger;
		this.service = service;
		this.command = command;
		super.addPluginStateListener(this);
	}

	public SafePluginManager(Path pluginsRoot, ILogger logger, Container<ReflectionProvider> provider,
		CommandHandler command, EventManager event, ServiceManager service) {
		super(pluginsRoot);
		this.provider = provider;
		this.event = event;
		this.logger = logger;
		this.service = service;
		this.command = command;
		super.addPluginStateListener(this);
	}

	/*
	 * Getter
	 */

	public ReflectionProvider getProvider() {
		return provider.get();
	}

	public ServiceManager getServiceManager() {
		return service;
	}

	public EventManager getEventManager() {
		return event;
	}

	public ILogger getLogger() {
		return logger;
	}

	/*
	 * Plugin Listener
	 */

	@Override
	public synchronized void addPluginStateListener(PluginStateListener listener) {
		return;
	}

	@Override
	public synchronized void removePluginStateListener(PluginStateListener listener) {
		return;
	}

	@Override
	public void pluginStateChanged(PluginStateEvent event) {

		if (event.getPluginState() == PluginState.STARTED) {
			Config.ACCESS.load(event.getPlugin());
			this.event.call(new PluginEnableEvent(this, event.getPlugin()));
			return;
		}

		if (event.getOldState() != PluginState.STARTED)
			return;
		switch (event.getPluginState()) {
		case STOPPED:
		case DISABLED:
			break;
		default:
			return;
		}

		PluginWrapper wrapper = event.getPlugin();

		this.event.call(new PluginDisableEvent(this, wrapper));

		List<Class<? extends EventListener>> owners = this.event.getOwnerClasses();
		int size = owners.size();
		for (int index = 0; index < size; index++) {
			if (wrapper.equals(whichPlugin(owners.get(index))))
				continue;
			owners.remove(index);
			index--;
			size--;
		}

		owners.stream().forEach(clazz -> this.event.unregisterEvents(clazz));
		List<Class<? extends Event>> events = this.event
			.getEvents()
			.stream()
			.filter(clazz -> isFromPlugin(wrapper, clazz))
			.collect(Collectors.toList());
		this.event.unregisterExecutors(events.stream().collect(collectExecutor()));
		events.forEach(clazz -> this.event.unregisterEvent(clazz));

		service
			.getContainers()
			.stream()
			.filter(service -> isFromPlugin(wrapper, service.getOwner()))
			.forEach(container -> service.unsubscribe(container));
		service
			.getServices()
			.stream()
			.filter(service -> isFromPlugin(wrapper, service.getOwner()))
			.forEach(service -> this.service.unregister(service));

		CommandManager manager = command.getManager();
		PluginCommand[] commands = command.getCommands(wrapper);
		for (int index = 0; index < commands.length; index++)
			manager.unregister(commands[index]);
		
		Config.ACCESS.unload(wrapper);

		ClassLoader loader = wrapper.getPluginClassLoader();
		Package[] packages = loader.getDefinedPackages();
		ReflectionProvider current = provider.get();
		for (int index = 0; index < packages.length; index++)
			current.delete(packages[index].getName());

	}

	/*
	 * Utilities
	 */

	private Collector<Class<? extends Event>, List<EventExecutor>, List<EventExecutor>> collectExecutor() {
		return Collect
			.collectList((output, clazz) -> this.event.getExecutorsForEvent(clazz, true).stream().forEach(executor -> {
				if (output.contains(executor))
					return;
				output.add(executor);
			}));
	}

	public boolean isFromPlugin(PluginWrapper wrapper, Object object) {
		return wrapper.equals(whichPlugin(!(object instanceof Class) ? object.getClass() : (Class<?>) object));
	}

}
