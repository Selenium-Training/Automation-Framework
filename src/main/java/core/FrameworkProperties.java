package core;

import java.io.IOException;

import properties.ExecutionProperties;
import properties.PlatformProperties;

public class FrameworkProperties {

	public PlatformProperties platformProperties;
	public ExecutionProperties executionProperties;
	
	public FrameworkProperties() throws IOException{
		platformProperties = new PlatformProperties();
		executionProperties = new ExecutionProperties();
	}
}
