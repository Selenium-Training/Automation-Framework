package newCore;

import java.io.IOException;

import newCore.properties.ExecutionProperties;
import newCore.properties.PlatformProperties;

public class FrameworkProperties {

	public PlatformProperties platformProperties;
	public ExecutionProperties executionProperties;
	
	public FrameworkProperties() throws IOException{
		platformProperties = new PlatformProperties();
		executionProperties = new ExecutionProperties();
	}
}
