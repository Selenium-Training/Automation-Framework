package core;

import java.io.IOException;

public class ExecutionEssentials {

	public Browser browser;
	public FrameworkProperties frameworkProperties;
	
	public ExecutionEssentials(String browserType) throws IOException{
		browser = new Browser(browserType);
		frameworkProperties = new FrameworkProperties();
	}
}
