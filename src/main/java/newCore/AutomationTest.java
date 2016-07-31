package newCore;

import java.io.IOException;

import dellCore.Dell;

public class AutomationTest {

	public ExecutionEssentials executionEssentials;
	public Dell dell;
	
	public AutomationTest(final Object object, final String browserType) throws IOException{
		executionEssentials = new ExecutionEssentials(object,browserType);
		executionEssentials.browser.setExecutionEssentails(executionEssentials);
		dell = new Dell(executionEssentials);
	}
}
