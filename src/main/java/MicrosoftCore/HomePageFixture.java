package MicrosoftCore;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Pages.dell.HomePage;
import newCore.ExecutionEssentials;
import newCore.browser.Browser;

public class HomePageFixture {
	
	private ExecutionEssentials executionEssentials;
	public Browser browser;
	
	public HomePageFixture(ExecutionEssentials executionEssentials){
		this.executionEssentials = executionEssentials;
		this.browser = executionEssentials.browser;
	}
	
	public void openHomePageURL() throws ParserConfigurationException, SAXException, IOException{
		browser.openURL(executionEssentials.frameworkProperties.platformProperties.getDellHomePageURL());
	}
	
	public void viewAllHomeLaptops() throws ParserConfigurationException, SAXException, IOException{
		browser.click(HomePage.productsMenuLink);
		browser.mouseOver(HomePage.laptopsMenuLink);
		browser.click(HomePage.viewAllHomeLaptopsLink);
	}
}

