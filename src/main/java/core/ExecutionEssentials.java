package core;

import java.io.IOException;

import newCore.FrameworkProperties;

public class ExecutionEssentials {

	public BrowserOld browser;
	public FrameworkProperties frameworkProperties;
	
	public ExecutionEssentials(String browserType) throws IOException{
		browser = new BrowserOld(browserType);
		frameworkProperties = new FrameworkProperties();
	}
}
