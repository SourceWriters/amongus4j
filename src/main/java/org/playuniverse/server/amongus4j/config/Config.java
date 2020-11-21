package org.playuniverse.server.amongus4j.config;

import java.io.File;
import java.util.Map;
import java.util.Set;

import com.syntaxphoenix.syntaxapi.config.BaseConfig;
import com.syntaxphoenix.syntaxapi.config.BaseSection;
import com.syntaxphoenix.syntaxapi.config.IBaseSection;
import com.syntaxphoenix.syntaxapi.config.SectionMap;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

public abstract class Config implements IBaseSection {

	public static final ConfigAccess ACCESS = new ConfigAccess();

	protected final BaseConfig config;
	protected final BaseSection data;
	protected final File file;

	protected final ILogger logger;

	public Config(ILogger logger, BaseSection data, File file) {
		if (!(data instanceof BaseConfig))
			throw new IllegalStateException("BaseSection needs to implement BaseConfig as well!");

		this.config = (BaseConfig) data;
		this.logger = logger;
		this.data = data;
		this.file = file;
	}

	/*
	 * 
	 */

	public ILogger getLogger() {
		return logger;
	}

	public BaseConfig getConfig() {
		return config;
	}

	public BaseSection getData() {
		return data;
	}

	public File getFile() {
		return file;
	}

	/*
	 * 
	 */

	public void reload() {
		onReload();
		load();
		save();
	}

	public void load() {

		try {
			config.load(file);
		} catch (Throwable throwable) {
			if (logger != null)
				logger.log(throwable);
		}

		onLoad();
	}

	public void save() {
		onSave();

		try {
			config.save(file);
		} catch (Throwable throwable) {
			if (logger != null)
				logger.log(throwable);
		}

	}

	/*
	 * 
	 */

	protected Class<?> loadAfter() {
		return null;
	}

	protected void onReload() {
	}

	protected void onLoad() {
	}

	protected void onSave() {
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */

	@Override
	public Map<String, Object> getValues() {
		return data.getValues();
	}

	@Override
	public Set<String> getKeys() {
		return data.getKeys();
	}

	@Override
	public String getName() {
		return data.getName();
	}

	@Override
	public boolean isValid() {
		return data.isValid();
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public boolean contains(String path) {
		return data.contains(path);
	}

	@Override
	public <E> boolean isInstance(String path, E value) {
		return data.isInstance(path, value);
	}

	@Override
	public <E> boolean isInstance(String path, Class<E> value) {
		return data.isInstance(path, value);
	}

	public Object checkObj(String path, Object value) {
		return data.check(path, value);
	}

	@Override
	public <E> E check(String path, E value) {
		return data.check(path, value);
	}

	@Override
	public Object get(String path) {
		return data.get(path);
	}

	@Override
	public <E> E get(String path, Class<E> sample) {
		return data.get(path, sample);
	}

	@Override
	public <E> E get(String path, E sample) {
		return data.get(path, sample);
	}

	@Override
	public BaseSection getSection(String path) {
		return data.getSection(path);
	}

	@Override
	public BaseSection createSection(String path) {
		return data.createSection(path);
	}

	@Override
	public void set(String path, Object value) {
		data.set(path, value);
	}

	@Override
	public void set(String key, String[] path, Object value) {
		data.set(key, path, value);
	}

	@Override
	public SectionMap<String, Object> toMap() {
		return data.toMap();
	}

	@Override
	public void fromMap(SectionMap<String, Object> input) {
		data.fromMap(input);
	}

	/*
	 * 
	 */

	protected Number safeNumber(Object input) {
		if (input instanceof Character) {
			return Character.getNumericValue(((Character) input).charValue());
		}
		if (input instanceof Number) {
			return ((Number) input);
		}
		return 0;
	}

	protected int safeInt(Object input) {
		return safeNumber(input).intValue();
	}

	protected long safeLong(Object input) {
		return safeNumber(input).longValue();
	}

}
