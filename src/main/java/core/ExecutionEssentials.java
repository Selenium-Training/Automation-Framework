package core;

import java.io.IOException;

public class ExecutionEssentials {

	public Browser browser;
	public FrameworkProperties frameworkProperties;
	
	public ExecutionEssentials() throws IOException{
		browser = new Browser();
		frameworkProperties = new FrameworkProperties();
	}
}
