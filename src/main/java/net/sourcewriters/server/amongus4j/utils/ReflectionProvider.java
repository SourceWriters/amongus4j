package net.sourcewriters.server.amongus4j.utils;

import java.util.HashMap;

import org.pf4j.Plugin;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import com.syntaxphoenix.syntaxapi.utils.java.Arrays;

public final class ReflectionProvider {

	private static ReflectionProvider PROVIDER;

	public static ReflectionProvider build(PluginManager pluginManager) {
		if (PROVIDER != null) {
			return null;
		}
		return PROVIDER = new ReflectionProvider(pluginManager);
	}

	public static ReflectionProvider get() {
		return PROVIDER;
	}

	private final HashMap<String, Reflections> reflections = new HashMap<>();
	private final PluginManager pluginManager;

	private ReflectionProvider(PluginManager pluginManager) {
		this.pluginManager = pluginManager;
	}

	/*
	 * ClassLoaders
	 */

	private ClassLoader[] defaults;

	private ClassLoader[] classLoaders() {
		if (defaults != null)
			return defaults;
		return defaults = Arrays.merge(size -> new ClassLoader[size], ClasspathHelper.classLoaders(), getClass().getClassLoader(),
			ClassLoader.getSystemClassLoader(), ClassLoader.getPlatformClassLoader(), Runtime.getRuntime().getClass().getClassLoader());
	}

	private Object[] buildParameters(String packageName, ClassLoader... loaders) {
		return Arrays.merge(new Object[] {
				packageName
		}, loaders.length == 0 ? classLoaders() : Arrays.merge(size -> new ClassLoader[size], classLoaders(), loaders));
	}

	/*
	 * Reflections
	 */

	public Reflections of(String packageName) {
		return of((PluginWrapper) null, packageName);
	}

	public Reflections of(Class<? extends Plugin> clazz, String packageName) {
		return of(clazz == null ? null : pluginManager.whichPlugin(clazz), packageName);
	}

	public Reflections of(PluginWrapper wrapper, String packageName) {
		synchronized (reflections) {
			if (reflections.containsKey(packageName)) {
				return reflections.get(packageName);
			}
		}
		Reflections reflect = new Reflections(wrapper == null ? buildParameters(packageName) : buildParameters(packageName, wrapper.getPluginClassLoader()));
		synchronized (reflections) {
			reflections.put(packageName, reflect);
		}
		return reflect;
	}

	public boolean has(String packageName) {
		synchronized (reflections) {
			return reflections.containsKey(packageName);
		}
	}

	public boolean delete(String packageName) {
		synchronized (reflections) {
			return reflections.remove(packageName) != null;
		}
	}

	/*
	 * Data
	 */

	public ReflectionProvider flush() {
		synchronized (reflections) {
			reflections.clear();
		}
		return this;
	}

}
