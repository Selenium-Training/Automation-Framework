package newCore;

import java.io.IOException;
import newCore.browser.Browser;

public class ExecutionEssentials {

	public Browser browser;
	public FrameworkProperties frameworkProperties;
	public ExecutionLog log;
	
	public ExecutionEssentials(final Object object, final String browserType) throws IOException{
		browser = new Browser(browserType);
		frameworkProperties = new FrameworkProperties();
		log = new ExecutionLog(object);
	}
}

