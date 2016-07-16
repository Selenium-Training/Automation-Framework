package core;

import java.io.IOException;

import core.properties.ExecutionProperties;
import core.properties.PlatformProperties;

public class FrameworkProperties {

	public PlatformProperties platformProperties;
	public ExecutionProperties executionProperties;
	
	public FrameworkProperties() throws IOException{
		platformProperties = new PlatformProperties();
		executionProperties = new ExecutionProperties();
	}
}
