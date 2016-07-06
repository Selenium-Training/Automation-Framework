package guru99scripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.Browser;


public class Guru99Testcase2 {
	
	@Test
	public void test2() throws IOException {
		//boolean flag = false;
		
		Browser browser = new Browser("");
		browser.openURL("http://live.guru99.com/");
		browser.click("homepage_mobile");
		browser.selectByIndex("mobile_sortbyDropdown",2);
		String result = browser.getSelectedValueInDropdown("mobile_sortbyDropdown");
		if(result.equalsIgnoreCase("pricqe")){
			System.out.println("THIS is TRUE");
			//flag= true;
		}
		else {
			Assert.fail("Failed!! Expected: Position || Actual: "+result);
		}
		browser.selectByLable("mobile_sortbyDropdown", "Position");
		result = browser.getSelectedValueInDropdown("mobile_sortbyDropdown");
		if(result.equalsIgnoreCase("Position")) {
			System.out.println("selected value is TRUE");
			//flag = true;
		}
		else{
			System.out.println();
			Assert.fail("Failed!! Expected: Position || Actual: "+result);
		}
		
		
	}

}
