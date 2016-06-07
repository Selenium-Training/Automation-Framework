package guru99scripts;

import java.io.IOException;

import org.testng.annotations.Test;

import core.ExecutionEssentials;

public class FrameworkLevelScript {

	@Test 
	public void test() throws IOException{
		ExecutionEssentials ee = new ExecutionEssentials();
		ee.browser.openURL(ee.frameworkProperties.platformProperties.getValue("googleHomePageURL"));
	}
}
