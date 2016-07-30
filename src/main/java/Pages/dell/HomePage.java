package Pages.dell;

import newCore.pageObjectModel.LocatorFactory;
import newCore.pageObjectModel.PageElementFactory;
import newCore.pageObjectModel.pageElements.LinkPageElement;
import newCore.pageObjectModel.pageElements.TextboxPageElement;

public class HomePage {

	public static LinkPageElement productsMenuLink =
			PageElementFactory.link("productsMenubar", LocatorFactory.xpath("//div[@class='main-nav-section hidden-xs hidden-sm']//ul[@class='tier1']//a[contains(.,'Products')]"));

	public static LinkPageElement laptopsMenuLink =
			PageElementFactory.link("laptopSubMenu", LocatorFactory.xpath("//a[contains(.,'Laptops')]"));
	
	public static LinkPageElement viewAllHomeLaptopsLink = 
			PageElementFactory.link("ForHome", LocatorFactory.xpath("//p[text()='View all Laptops for Home']"));
			
	public static TextboxPageElement searchTextbox = 
			PageElementFactory.textbox("Search Textbox", LocatorFactory.id("search-input"));
}
