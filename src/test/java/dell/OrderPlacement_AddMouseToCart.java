package dell;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import newCore.AutomationTest;
import newCore.browser.Browser;

public class OrderPlacement_AddMouseToCart {

	@Parameters("browser")
	@Test
	public void test(@Optional(Browser.BROWSERTYPE_CHROME) String browserType){
		AutomationTest automationTest = null;
		try{
			automationTest = new AutomationTest(this, browserType);
			automationTest.executionEssentials.log.logNote("My First Script", "With Logs");
			
			automationTest.dell.homePageFixture.openHomePageURL();
			automationTest.dell.homePageFixture.viewAllHomeLaptops();
			automationTest.executionEssentials.log.logRunCheck("Verifying", true == true, "Actual | Exepected");
		} catch(Exception e){
			automationTest.executionEssentials.log.logException(e); 
		} finally {
			
		}
		
	}
}
