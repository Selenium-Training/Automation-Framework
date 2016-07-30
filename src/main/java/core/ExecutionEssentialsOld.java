package core;

import java.io.IOException;

import newCore.FrameworkProperties;

public class ExecutionEssentialsOld {

	public BrowserOld browser;
	public FrameworkProperties frameworkProperties;
	
	public ExecutionEssentialsOld(String browserType) throws IOException{
		browser = new BrowserOld(browserType);
		frameworkProperties = new FrameworkProperties();
	}
}
