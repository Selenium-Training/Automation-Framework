package newCore;

import java.io.IOException;

import dellCore.Dell;

public class AutomationTest {

	public ExecutionEssentials executionEssentials;
	public Dell dell;
	
	public AutomationTest(final String browserType) throws IOException{
		executionEssentials = new ExecutionEssentials(browserType);
		dell = new Dell(executionEssentials);
	}
}
