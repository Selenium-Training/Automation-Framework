package core.pom;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.openqa.selenium.By;

public class ObjectRepMap 
{
	private ObjectCSVReader Objcsv = new ObjectCSVReader();

	private Map<String, String> objectStore = new HashMap<String, String >();

	public void createObjectStore () throws IOException
	{
		List<ObjectRepReader> objectDetails = Objcsv.csvreader();




		//Traditional For loop 
/*		for( int i= 0; i < objectDetails.size();i++)
		{

			System.out.println("Page =" +objectDetails.get(i).getPage());
			System.out.println("LocatorName =" +objectDetails.get(i).getLogicalName());
			System.out.println("locator Type =" +objectDetails.get(i).getLocatorType());
			System.out.println("value =" +objectDetails.get(i).getValue());
		}
*/

		for(ObjectRepReader tempObj : objectDetails)
		{
/*			System.out.println("Page =" +tempObj.getPage());
			System.out.println("LocatorName =" +tempObj.getLogicalName());
			System.out.println("locator Type=" +tempObj.getLocatorType());
			System.out.println("Value =" +tempObj.getValue());
*/			
			String logicalname = tempObj.getLogicalName();
			String locatorType = tempObj.getLocatorType();
			String value = tempObj.getValue();
			
			objectStore.put(logicalname, locatorType + "=>"+value);
		}
	}
	
	public By getLocator(final String logicalName){
		
		String objectProperties = getValueFromMap(logicalName);
		String locatorType = objectProperties.split("=>")[0];
		String value = objectProperties.split("=>")[1];
		
		By by = null;
		
		if(locatorType.equalsIgnoreCase("linktext")){
			by = By.linkText(value);
		}
		else if(locatorType.equalsIgnoreCase("id")){
			by = By.id(value);
		}
		else if(locatorType.equalsIgnoreCase("xpath")){
			by = By.xpath(value);
		}
		return by;
	}
	
	private String getValueFromMap(final String key){
		return objectStore.get(key);
	}
	
	public void setValueForKey(final String key, final String value){
		objectStore.put(key, value);
	}
}

