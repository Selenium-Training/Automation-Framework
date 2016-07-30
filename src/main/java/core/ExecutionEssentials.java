package core;

import java.io.IOException;

public class ExecutionEssentials {

	public BrowserOld browser;
	public FrameworkProperties frameworkProperties;
	
	public ExecutionEssentials(String browserType) throws IOException{
		browser = new BrowserOld(browserType);
		frameworkProperties = new FrameworkProperties();
	}
}
