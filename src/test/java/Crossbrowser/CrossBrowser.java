package Crossbrowser;

import java.io.IOException;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import core.Browser;
import core.ExecutionEssentials;

public class CrossBrowser {

	//	public String browserType = "Chrome";

	@Parameters("browserType")
	@Test
	public void crossBrowser(@Optional(Browser.BROWSERTYPE_CHROME) String browserType) throws IOException{
		ExecutionEssentials essentials = new ExecutionEssentials(browserType);
		essentials.browser.openURL("http:\\www.bing.com");
		essentials.browser.click("google_gmail");

		System.out.println("");
		

	}



}
