package guru99scripts;

import java.io.IOException;

import org.testng.annotations.Test;

import core.Browser;

public class Guru99Testcase1 

{

	@Test
	
	public void Test1() throws IOException
	{
		Browser browser = new Browser("");
		browser.openURL("http://live.guru99.com/");
		browser.click("homepage_mobile");
		browser.click("samsung_mobileAddtoCart");
		browser.closeAllBroser();
	}
	
	
	
}
