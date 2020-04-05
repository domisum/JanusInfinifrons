package io.domisum.janus.config.object;

import io.domisum.lib.auxiliumlib.config.ConfigObject;
import io.domisum.lib.auxiliumlib.config.ConfigObjectLoader;
import io.domisum.lib.auxiliumlib.config.ConfigObjectRegistry;
import io.domisum.lib.auxiliumlib.config.InvalidConfigException;

import java.io.File;

public abstract class JanusConfigObjectLoader<T extends ConfigObject>
		extends ConfigObjectLoader<T>
{
	
	// CONSTANT METHODS
	@Override
	protected String FILE_EXTENSION()
	{
		return OBJECT_NAME()+".json";
	}
	
	
	// LOADING
	@Override
	public ConfigObjectRegistry<T> load(File configDirectory)
			throws InvalidConfigException
	{
		var configSubDir = new File(configDirectory, OBJECT_NAME_PLURAL());
		return super.load(configSubDir);
	}
	
}
