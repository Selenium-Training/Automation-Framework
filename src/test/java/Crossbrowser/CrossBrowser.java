package Crossbrowser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import newCore.pageObjectModel.LocatorFactory;
import newCore.pageObjectModel.PageElementFactory;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import core.BrowserOld;
import core.ExecutionEssentials;

public class CrossBrowser {

	//	public String browserType = "Chrome";

	@Parameters("browserType")
	@Test
	public void crossBrowser(@Optional(BrowserOld.BROWSERTYPE_CHROME) String browserType) throws IOException, ParserConfigurationException, SAXException{
		newCore.browser.Browser browser = new newCore.browser.Browser("FF");
		browser.openURL("http://www.google.com");
		browser.click(PageElementFactory.link("", LocatorFactory.linkText("Gmail")));
		

	}



}
