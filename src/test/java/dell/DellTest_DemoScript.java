package dell;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import newCore.AutomationTest;
import newCore.browser.Browser;

public class DellTest_DemoScript {

	@Parameters("browser")
	@Test
	public void test(@Optional(Browser.BROWSERTYPE_CHROME) String browserType){
		AutomationTest automationTest = null;
		
		try{
			automationTest = new AutomationTest(this, browserType);
			automationTest.executionEssentials.log.logNote("Test execution start", "");
			automationTest.executionEssentials.log.logRunCheck("My Check", true, "Dummy Check");
			automationTest.executionEssentials.log.logRunCheck("My Check New", "abc".equals("pqr"), "Dummy Check New");
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			automationTest.executionEssentials.log.logScriptStatus();
		}
		
	}
}
