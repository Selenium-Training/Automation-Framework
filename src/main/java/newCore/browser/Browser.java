package newCore.browser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.JsonException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.xml.sax.SAXException;

import newCore.ExecutionEssentials;
import newCore.pageObjectModel.Locator;
import newCore.pageObjectModel.PageElement;
import newCore.pageObjectModel.pageElements.FileUploadPageElement;
import newCore.pageObjectModel.pageElements.IframePageElement;
import newCore.pageObjectModel.pageElements.ListboxPageElement;

public class Browser extends BrowserSettings{

	public static final String BROWSERTYPE_CHROME = "CHROME";
	public static final String BROWSERTYPE_FF = "FF";
	public static final String BROWSERTYPE_IE = "IE";

	private final String BORDER_COLOR = "Red";
	private static final String ITEM_COLOR = "Blue";
    
	public Browser(final String browserType){
		super(browserType);
	}


	/**
	 * Accept the Alert. <<br>
	 * First , it wait for the alert to be present within default timeout. <br>
	 * If the alert is present, it accepts the alert. <br>
	 * If the alert doesn't present, it throws the exception.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void alertAccept() throws ParserConfigurationException, SAXException, IOException {
		if (isAlertPresentAsBool()) {
			final Alert alert = switchToAlert();
			alert.accept();
		}

		else {
			throw new NoAlertPresentException();
		}
	}

	/**
	 * Dismiss the alert. <<br>
	 * First , it wait for the alert to be present within default timeout. <br>
	 * If the alert is present, it dismiss the alert. <br>
	 * If the alert doesn't present, it throws the exception.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void alertDismiss() throws ParserConfigurationException, SAXException, IOException {
		if (isAlertPresentAsBool()) {
			final Alert alert = switchToAlert();
			alert.dismiss();
		} else {
			throw new NoAlertPresentException();
		}
	}

	/**
	 * Change the parameter value for the URL based on the Parameter that's
	 * passed in. <br>
	 * URLs are expected to be in one of the following formats: <br>
	 * Google friendly - www.google.com/param1.2/param2.1 <br>
	 * Not Google friendly - www.google.com?param1=2&amp;param2=1 <br>
	 * Example, when using this method, Parameter is param1, NewValue is test,
	 * and the URL_opt is www.google.com/param1.2/param2.1 <br>
	 * This method will return the URL with this string www.google.com/param1.
	 * <b>test</b>/param2.1
	 *
	 * @param Parameter
	 * @param NewValue
	 * @param URL_opt
	 * @return String URL that is updated
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String changeURLParameterValueAsString(final String parameter, final String newValue, String url_opt)
			throws ParserConfigurationException, SAXException, IOException {
		String newURL;
		String delimitingCharacter; //Delimiter between Parameter/Value pairs in the URL
		String assignmentCharacter; //Character used to assign values to parameters in the URL

		//Default value for URL_opt if not provided
		if ((url_opt == null) || url_opt.equalsIgnoreCase("")) {
			url_opt = getBrowserURL();
		}

		if (isGoogleFriendlyAsBool(url_opt)) {
			delimitingCharacter = "/";
			assignmentCharacter = ".";
		} else {
			delimitingCharacter = "&";
			assignmentCharacter = "=";
		}
		final String oldValue = getURLParameterValueAsString(parameter, url_opt);
		if (!oldValue.equals("")) {
			newURL = url_opt.replaceAll(assignmentCharacter + oldValue, assignmentCharacter + newValue);
		} else {
			newURL = url_opt + delimitingCharacter + parameter + assignmentCharacter + newValue;
		}
		return newURL;
	}

	/**
	 * This method uses javascript implementation to clear the session data
	 */
	public void clearSessionData() {
		javascriptExecutor.executeScript(String.format("window.sessionStorage.clear();"));
	}

	/**
	 * This is to close all browser windows.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void closeAllBrowsers() throws ParserConfigurationException, SAXException, IOException {
		try {
			this.driver.quit();
		} catch (final WebDriverException e) {

		}

		this.driver = null;
	}

	/**
	 * This is to close all browser windows (IE, FF, CHROMES) when debug mode =
	 * TRUE
	 *
	 * @throws IOException
	 */
	public void closeAllBrowsersBeforeRun() throws IOException {
		if (true) {
			if (getBrowserType().equalsIgnoreCase(Browser.BROWSERTYPE_IE)) {
				Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
			} else if (getBrowserType().equalsIgnoreCase(Browser.BROWSERTYPE_FF)) {
				Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			} else if (getBrowserType().equalsIgnoreCase(Browser.BROWSERTYPE_CHROME)) {
				Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
			} else {

			}
		}
	}

	/**
	 * To close the current browser. <br>
	 * If you have only 1 browser window, PLEASE DO NOT USE THIS METHOD. You
	 * need to use BrowserCloseAllBrowsers() as this will cause some side
	 * effect. <br>
	 * If you have more than 1 browser window, you can use this method to close
	 * the current browser window.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void closeCurrentBrowser() throws ParserConfigurationException, SAXException, IOException {
		try {
			this.driver.close();
		} catch (final WebDriverException e) {

		}

	}

	/**
	 * This is to delete all cookies.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void deleteAllCookies() throws ParserConfigurationException, SAXException, IOException {
		this.driver.manage().deleteAllCookies();
	}

	/**
	 * To delete the cookie provided with the cookie name
	 *
	 * @param CookieName
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void BrowserDeleteCookie(final String CookieName)
			throws ParserConfigurationException, SAXException, IOException {
		this.driver.manage().deleteCookieNamed(CookieName);
	}

	/**
	 * Spawn or navigate to a new browser
	 *
	 * @param URL
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void openURL(final String url) throws ParserConfigurationException, SAXException, IOException {
		if(driver == null){
			setDriver();
		}

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		System.out.println("Navigated To URL: "+ url);
		driver.get(url);
		maximizeBrowser();

	}

	/**
	 * This is to get the current Browser Window Handle ID
	 *
	 * @return String
	 */
	public String getCurrentWindowHandler() {
		final String currentWindowsHandler = this.driver.getWindowHandle();
		System.out.println("Current Windows Handler " + currentWindowsHandler);

		return currentWindowsHandler;
	}

	/**
	 * This is to get the New Browser Window Handler IDs.
	 *
	 * @param PreviousWindowHandler This will be all the previous Window
	 *            Handlers ID. You can use method BrowserGetWindowHandlers() to
	 *            get this
	 * @param NewWindowHandlers This will be all the Window Handlers ID
	 *            including the New Window. You can use method
	 *            BrowserGetWindowHandlers() to get this
	 * @return String
	 */
	public String getNewWindowHandlerID(final Set<String> previousWindowHandler, final Set<String> newWindowHandlers) {
		newWindowHandlers.removeAll(previousWindowHandler);
		final Iterator<String> it = newWindowHandlers.iterator();
		String newWindowHandlerID = "";
		while (it.hasNext()) {
			newWindowHandlerID = it.next();
		}

		return newWindowHandlerID;
	}

	/**
	 * This is to get the HTML Source Page from the page.
	 *
	 * @return String
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getPageSource() throws ParserConfigurationException, SAXException, IOException {
		return this.driver.getPageSource();
	}

	/**
	 * This is to get the Page Text from the page.
	 *
	 * @return String
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getPageText() throws ParserConfigurationException, SAXException, IOException {
		final String pageText = this.driver.findElement(By.tagName("body")).getText();
		return pageText;
	}


	/**
	 * To get the current URL
	 *
	 * @return String URL in String
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getBrowserURL() throws ParserConfigurationException, SAXException, IOException {
		return this.driver.getCurrentUrl();
	}

	/**
	 * Gets a parameter value from a URL based on the parameter name passed in.
	 * <br>
	 * URLs are expected to be in one of the following formats: <br>
	 * Google friendly - www.google.com/param1.2/param2.1 <br>
	 * Not Google friendly - www.google.com?param1=2&amp;param2=1 <br>
	 * Example, when using this method, Parameter is param1, and the URL_opt is
	 * www.google.com/param1.2/param2.1 <br>
	 * This method will return 2
	 *
	 * @param Parameter String - Paramter name to find.
	 * @param URL_opt Sting Optional - URL to retrieve the parameter from
	 * @return String - Parameter value extracted out of the URL.
	 * @author DR Software Test Automation
	 * @date 12/6/2013
	 */
	public String getURLParameterValueAsString(final String parameter, String url_opt)
			throws ParserConfigurationException, SAXException, IOException {
		String delimitingCharacter; //Delimiter between Parameter/Value pairs in the URL
		String assignmentCharacter; //Character used to assign values to parameters in the URL

		//Default value for URL_opt if not provided
		if ((url_opt == null) || url_opt.equalsIgnoreCase("")) {
			url_opt = getBrowserURL();
		}

		if (isGoogleFriendlyAsBool(url_opt)) {
			delimitingCharacter = "/";
			assignmentCharacter = ".";
		} else {
			delimitingCharacter = "&";
			assignmentCharacter = "=";
		}

		String[] tempArray, tempArray2;
		tempArray = url_opt.split(parameter + assignmentCharacter);
		if (tempArray.length > 1) {
			tempArray2 = tempArray[1].split(delimitingCharacter);
			return tempArray2[0];
		} else {
			return "";
		}

	}

	/**
	 * This is to get all the Browser window handler IDs in String. <br>
	 * All the browser window handler IDs will be in String that stored in data
	 * type Set.
	 *
	 * @return Set<String>
	 */
	public Set<String> getWindowHandlers() {
		final Set<String> allWindowsHandler = this.driver.getWindowHandles();
		System.out.println("All Windows Handlers: " + allWindowsHandler);

		return allWindowsHandler;
	}

	/**
	 * Simulate user clicks on the go back button on the browser.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void navigateBack() throws ParserConfigurationException, SAXException, IOException {
		this.driver.navigate().back();
	}

	/**
	 * Simulate the user clicks on the forward button on the browser.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void navigateForward() throws ParserConfigurationException, SAXException, IOException {
		this.driver.navigate().forward();
	}

	/**
	 * To check if the Javascript Alert is present or not on the storefront page
	 * UI within default timeout. <br>
	 * If the Javascript Alert is present, it returns true. <br>
	 * Otherwise, it returns false.
	 *
	 * @return boolean
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean isAlertPresentAsBool() throws ParserConfigurationException, SAXException, IOException {
		return waitForAlertIsPresent();
	}

	/**
	 * Check if the URL is google friendly or not
	 *
	 * @param URL
	 * @return boolean. This will return true if the URL is goole friendly.
	 *         Otherwise, it return false.
	 */
	public boolean isGoogleFriendlyAsBool(final String URL) {
		if (URL.equalsIgnoreCase("")) {
			return false;
		}

		if (URL.contains("?")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * To check if the text is on the page source or not. <br>
	 * If the text is present on the page, it returns true. <br>
	 * Otherwise, it returns false.
	 *
	 * @param Text text to be search for on the page source
	 * @return boolean
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean isTextInPageSourceAsBool(final String Text)
			throws IOException, ParserConfigurationException, SAXException {
		if ((Text == null) || ((Text != null) && Text.equalsIgnoreCase(""))) {
			return false;
		} else {
			final String PageSource = getPageSource();
			if (PageSource.contains(Text)) {
				return true;
			} else {
				return false;
			}
		}
	}


	/**
	 * To maximixe the browser.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void maximizeBrowser() throws ParserConfigurationException, SAXException, IOException {
		this.driver.manage().window().maximize();
	}


	/**
	 * To refresh the browser.
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void refreshBrowser() throws ParserConfigurationException, SAXException, IOException {
		this.driver.navigate().refresh();
	}


	/**
	 * To capture the screen shot.
	 *
	 * @return String return the file name in String
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public String BrowserScreenShotCapture() throws IOException, ParserConfigurationException, SAXException {
		File scrFile = null;

		final String fileName = "";
		final String filePath = "";

		if (true) {
			scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		} else {
			final Augmenter augmenter = new Augmenter();
			final TakesScreenshot ts = (TakesScreenshot) augmenter.augment(this.driver);
			scrFile = ((ts).getScreenshotAs(OutputType.FILE));
		}

		FileUtils.copyFile(scrFile, new File(filePath));
		return fileName;
	}


	/**
	 * Switch to the alert
	 *
	 * @return Alert
	 */
	private Alert switchToAlert() {
		final Alert alert = driver.switchTo().alert();
		return alert;
	}

	/**
	 * Switch to Frame using Index <br>
	 * The index is 0 base <br>
	 * This means the 1st frame is 0, 2nd frame is 1 for index.
	 *
	 * @param FrameIndex
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void switchToFrameByIndex(final int frameIndex) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Swiching to frame at index "+  frameIndex);
		this.driver.switchTo().frame(frameIndex);
	}

	/**
	 * Switch to Frame by name or ID
	 *
	 * @param nameOrId
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void switchToFrameByName(final String nameOrId)
			throws ParserConfigurationException, SAXException, IOException {
		this.driver.switchTo().frame(nameOrId);
	}


	/**
	 * Switch to the new window with the given Browser Window Handler IDs or
	 * Browser Window Name
	 *
	 * @param WindowHandlerIDOrWindowName
	 */
	public void switchToWindowByWindwoHandlerID(final String windowHandlerIDOrWindowName) {
		this.driver.switchTo().window(windowHandlerIDOrWindowName);
	}


	/**
	 * Waiting for the Alert to be present within default time out.
	 *
	 * @return boolean
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean waitForAlertIsPresent() throws ParserConfigurationException, SAXException, IOException {
		return this.BrowserWaitForAlertIsPresent(this.defaultTimeoutInSeconds);
	}

	/**
	 * Waiting for the Alert to be present within passed in TimeOutInSeconds.
	 *
	 * @param TimeOutInSeconds
	 * @return boolean
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private boolean BrowserWaitForAlertIsPresent(final int TimeOutInSeconds)
			throws ParserConfigurationException, SAXException, IOException {
		return false;
		/*final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);

		this.Log.Log_Step("Waiting for Alert to be present in " + TimeOutInSeconds + " seconds.");

		try {
			(new WebDriverWait(this.driver, TimeOutInSeconds)).until(ExpectedConditions.alertIsPresent());

			//return true or false for the alert is present or not.
			try {
				this.driver.switchTo().alert();
				return true;
			} catch (final NoAlertPresentException e) {
				e.printStackTrace();
				this.Log.Log_Warning("No alert present");
				return false;
			}

		} catch (final TimeoutException e) {
			System.out.println(
					"Timeout with " + TimeOutInSeconds + " seconds has reached but still cannot find the alert ");
			this.Log.Log_Warning("No alert present after " + TimeOutInSeconds + " seconds");
			return false;
		} catch (final Exception e) {
			e.printStackTrace();
			e.printStackTrace(pw);
			this.Log.Log_Warning("No alert present and with exception occurs " + sw.toString());
			return false;
		}
*/
	}

	/**
	 * Click a page element
	 *
	 * @param pageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void click(final PageElement pageElement) throws ParserConfigurationException, SAXException, IOException {
		this.waitForClickableElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());

		webElement.click();
	}

	public void clickAndSwitch(final PageElement pageElement) throws Exception {
		final Set<String> previousWindowHandlers = getWindowHandlers();
		this.click(pageElement);

		String newWindowHandlerID = null;
		boolean hasNewWindow = false;
		int maxAttempts = 10;

		while (hasNewWindow != true) {
			if (maxAttempts == 0) {
				break;
			}

			maxAttempts -= 1;

			Thread.sleep(50);

			final Set<String> newWindowHandlers = getWindowHandlers();
			newWindowHandlerID = getNewWindowHandlerID(previousWindowHandlers, newWindowHandlers);

			hasNewWindow = ((newWindowHandlerID != null) && !newWindowHandlerID.isEmpty());
		}

		switchToWindowByWindwoHandlerID(newWindowHandlerID);
	}

	private String convertFileName(final String filePath) throws IOException {

		final String target = (File.separator.equals("/") ? "\\" : "/");
		String convertedPath = filePath.replace(target, File.separator);

		if (convertedPath.startsWith(".")) {
			convertedPath = new File("").getCanonicalPath() + convertedPath.substring(1);
		}

		return convertedPath;
	}

	/**
	 * Check if page element exists on page
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean elementExists(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		boolean results = false;
		final WebElement webElement = this.getElement(pageElement.getLocator());

		if (webElement != null) {
			results = true;
		}

		return results;
	}

	public boolean elementRemoved(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		return this.waitForElementExistence(pageElement, false);
	}


	/**
	 * Enter fully qualified file path name to upload
	 *
	 * @param fileUploadPageElement
	 * @param filePath
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void enterFileName(final FileUploadPageElement fileUploadPageElement, final String filePath)
			throws ParserConfigurationException, SAXException, IOException {
		final WebElement webElement = this.getElement(fileUploadPageElement.getLocator());

		webElement.sendKeys(this.convertFileName(filePath));
	}

	/**
	 * Enter text into page element
	 *
	 * @param pageElement
	 * @param text
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void enterText(final PageElement pageElement, final String text)
			throws ParserConfigurationException, SAXException, IOException {
		if (text != null) {
			this.waitForVisibleElement(pageElement);
			final WebElement webElement = this.getElement(pageElement.getLocator());
			
			webElement.clear();
			webElement.sendKeys(text);
		}
	}

	/**
	 * Check if browser exists
	 *
	 * @return true if browser exists, false otherwise.
	 */
	public boolean browserExists() {
		if (this.driver == null) {
			return false;
		} else {
			return true;
		}
	}



	public List<String> findStrings(final Locator locator) {
		final List<WebElement> elements = this.getElements(locator);
		final List<String> elementTextList = new ArrayList<>();

		for (final WebElement element : elements) {
			final String text = element.getText();

			if ((text != null) && !text.isEmpty()) {
				elementTextList.add(text);
			}
		}

		return elementTextList;
	}

	/**
	 * Get a property value of a page element
	 *
	 * @param pageElement
	 * @param attributeName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getAttribute(final PageElement pageElement, final String attributeName)
			throws ParserConfigurationException, SAXException, IOException {
		this.waitForPresenceOfElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		return webElement.getAttribute(attributeName);
	}

	private WebElement getElement(final Locator locator)
			throws ParserConfigurationException, SAXException, IOException {
		WebElement webElement = null;

		// Reset implicit wait if changed
		this.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		try {
			switch (locator.getLocatorType()) {
			case CLASS_NAME:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			case ID:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			case LINK_TEXT:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			case NAME:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			case PARTIAL_LINK_TEXT:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			case TAG_NAME:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			case XPATH:
				webElement = this.driver.findElement(locator.getLocator());
				break;
			default:
				break;
			}
		} catch (final Exception e) {
			// Ignore exceptions
		}

		return webElement;
	}

	private List<WebElement> getElements(final Locator locator) {
		List<WebElement> webElements = null;

		// Reset implicit wait if changed
		this.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		try {
			switch (locator.getLocatorType()) {
			case CLASS_NAME:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			case ID:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			case LINK_TEXT:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			case NAME:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			case PARTIAL_LINK_TEXT:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			case TAG_NAME:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			case XPATH:
				webElements = this.driver.findElements(locator.getLocator());
				break;
			default:
				break;
			}
		} catch (final Exception e) {
			// Ignore exceptions
		}

		return webElements;
	}

		/**
	 * Return a list of options from a list box
	 *
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public List<String> getOptions(final ListboxPageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		final List<String> options = new ArrayList<String>();

		final WebElement webElement = this.getElement(pageElement.getLocator());
		final Select select = new Select(webElement);

		final List<WebElement> selectedElements = select.getOptions();
		final ListIterator<WebElement> listIterator = selectedElements.listIterator();

		while (listIterator.hasNext()) {
			final WebElement selectOption = listIterator.next();
			options.add(selectOption.getText());
		}

		return options;
	}

	public List<String> getOptionValues(final ListboxPageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		final List<String> options = new ArrayList<String>();

		final WebElement webElement = this.getElement(pageElement.getLocator());
		final Select select = new Select(webElement);

		final List<WebElement> selectedElements = select.getOptions();
		final ListIterator<WebElement> listIterator = selectedElements.listIterator();

		while (listIterator.hasNext()) {
			final WebElement selectOption = listIterator.next();
			options.add(selectOption.getAttribute("value"));
		}

		return options;
	}

	/**
	 * The title of the current page.
	 *
	 * @return
	 */
	public String getPageTitle() {
		return this.driver.getTitle();
	}

	public String getReadyState() {
		return (String) ((JavascriptExecutor) this.driver).executeScript("return document.readyState");
	}

	/**
	 * Get selected option in list box
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getSelectedOption(final ListboxPageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		final Select select = new Select(webElement);

		String selectedOptionText = null;

		final List<WebElement> selectedOptions = select.getAllSelectedOptions();

		if (selectedOptions.size() == 1) {
			final WebElement selectedOption = selectedOptions.get(0);
			selectedOptionText = selectedOption.getText();
		} else {
			final ListIterator<WebElement> listIterator = selectedOptions.listIterator();

			while (listIterator.hasNext()) {
				final WebElement selectOption = listIterator.next();
			}
		}

		return selectedOptionText;
	}

	/**
	 * Get list of selected options in list box
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public List<String> getSelectedOptions(final ListboxPageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		final List<String> selectedOptions = new ArrayList<String>();

		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		final Select select = new Select(webElement);

		final List<WebElement> selectedElements = select.getAllSelectedOptions();
		final ListIterator<WebElement> listIterator = selectedElements.listIterator();

		while (listIterator.hasNext()) {
			final WebElement selectOption = listIterator.next();
			selectedOptions.add(selectOption.getText());
		}

		return selectedOptions;
	}

	/**
	 * Get the current selection state
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean getSelectionState(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());

		return webElement.isSelected();
	}

	/**
	 * Returns the page element's text value
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getTextFromElement(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {

		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		return webElement.getText();
	}

	/**
	 * Highlight the page element
	 *
	 * @param pageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void highlight(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		final JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement,
				"color: " + ITEM_COLOR + ";  border: 2px solid " + BORDER_COLOR + ";");
	}

	/**
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean isDisplayed(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		boolean elementIsDisplayed = false;

		if (this.elementExists(pageElement)) {
			final WebElement webElement = this.getElement(pageElement.getLocator());

			if (webElement != null) {
				elementIsDisplayed = webElement.isDisplayed();
			}
		}

		return elementIsDisplayed;
	}

	/**
	 * Check is element is enabled or not
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean isEnabled(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		boolean elementIsEnabled = false;

		if (this.elementExists(pageElement)) {
			final WebElement webElement = this.getElement(pageElement.getLocator());

			if (webElement != null) {
				elementIsEnabled = webElement.isEnabled();
			}
		}

		return elementIsEnabled;
	}

		/**
	 * Check if a page element is checked or selected
	 *
	 * @param pageElement
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public boolean isSelected(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		boolean elementIsSelected = false;

		if (this.elementExists(pageElement)) {
			final WebElement webElement = this.getElement(pageElement.getLocator());

			if (webElement != null) {
				elementIsSelected = webElement.isSelected();
			}
		}

		return elementIsSelected;
	}

	/**
	 * Mouse over page element
	 *
	 * @param pageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void mouseOver(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());

		final Actions action = new Actions(this.driver);
		action.moveToElement(webElement);
	}

	/**
	 * Move to page element
	 *
	 * @param pageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void moveTo(final PageElement pageElement) throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		new Actions(this.driver).moveToElement(webElement).perform();
	}

	/**
	 * Open a new browser window
	 */
	public void openBrowser() {
		((JavascriptExecutor) this.driver).executeScript("window.open()");
	}

	/**
	 * Remove the highlight from the page element
	 *
	 * @param pageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void removeHighlight(final PageElement pageElement)
			throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		final JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");
	}

	/**
	 * Select option in List box
	 *
	 * @param pageElement
	 * @param option
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void select(final ListboxPageElement pageElement, final String option)
			throws ParserConfigurationException, SAXException, IOException {
		if (option != null) {

			this.waitForClickableElement(pageElement);
			final WebElement webElement = this.getElement(pageElement.getLocator());

			final Select select = new Select(webElement);

			try {
				select.selectByVisibleText(option);
			} catch (final Exception e) {
				final List<WebElement> options = select.getOptions();

				final ListIterator<WebElement> listIterator = options.listIterator();

				while (listIterator.hasNext()) {
					final WebElement selectOption = listIterator.next();
				}
			}
		}
	}

	public void selectByIndex(final ListboxPageElement pageElement, final Integer index) throws Exception {
		if (index != null) {
			
			this.waitForClickableElement(pageElement);
			final WebElement webElement = this.getElement(pageElement.getLocator());

			final Select select = new Select(webElement);

			try {
				select.selectByIndex(index);
			} catch (final Exception e) {
				final List<WebElement> options = select.getOptions();

				final ListIterator<WebElement> listIterator = options.listIterator();

				while (listIterator.hasNext()) {
					final WebElement selectOption = listIterator.next();
				}
			}
		}
	}

	/**
	 * Select desired value given the dropdown page element
	 *
	 * @param pageElement of the dropdown
	 * @param optionValue desired value to be selected
	 * @throws Exception
	 */
	public void selectByValue(final ListboxPageElement pageElement, final String optionValue) throws Exception {
		if (optionValue != null) {
			
			this.waitForClickableElement(pageElement);
			final WebElement webElement = this.getElement(pageElement.getLocator());

			final Select select = new Select(webElement);

			try {
				select.selectByValue(optionValue);
			} catch (final Exception e) {
				final List<WebElement> options = select.getOptions();

				final ListIterator<WebElement> listIterator = options.listIterator();

				while (listIterator.hasNext()) {
					final WebElement selectOption = listIterator.next();
				}
			}
		}
	}

	/**
	 * Set focus on page element
	 *
	 * @param pageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void setFocus(final PageElement pageElement) throws ParserConfigurationException, SAXException, IOException {
		this.waitForVisibleElement(pageElement);
		final WebElement webElement = this.getElement(pageElement.getLocator());
		webElement.sendKeys(Keys.TAB);
	}

	public void setResolution(final int width, final int height) {
		final Dimension targetSize = new Dimension(width, height);
		this.driver.manage().window().setSize(targetSize);
	}

	/**
	 * Set a page element to be checked or selected
	 *
	 * @param pageElement
	 * @param state
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void setSelectionState(final PageElement pageElement, final Boolean state)
			throws ParserConfigurationException, SAXException, IOException {
		if (state != null) {
			

			this.waitForVisibleElement(pageElement);
			final WebElement webElement = this.getElement(pageElement.getLocator());

			if (webElement.isSelected() != state) {
				webElement.click();
			}
		}
	}

	/**
	 * Switch to an iframe
	 *
	 * @param iframePageElement
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void switchToFrame(final IframePageElement iframePageElement)
			throws ParserConfigurationException, SAXException, IOException {
		
		final WebElement webElement = this.getElement(iframePageElement.getLocator());
		this.driver.switchTo().frame(webElement);
	}

	/**
	 * Switch to parent window
	 *
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void switchToParentWindow() throws ParserConfigurationException, SAXException, IOException {
		this.driver.switchTo().defaultContent();
	}

	private void waitForClickableElement(final PageElement pageElement) {
		try {
			this.elementWait().until(ExpectedConditions.elementToBeClickable(pageElement.getLocator().getLocator()));
		} catch (final TimeoutException e) {
			// ignore error
		}
	}

	private boolean waitForElementExistence(final PageElement pageElement, final Boolean shouldExist)
			throws ParserConfigurationException, SAXException, IOException {
		final String waitingFor = (shouldExist) ? "exists" : "does not exist";
		final By by = pageElement.getLocator().getLocator();

		try {
			if (shouldExist) {
				this.elementWait().until(ExpectedConditions.presenceOfElementLocated(by));
			} else {
				this.elementWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
			}
		} catch (final TimeoutException e) {
			// Ignore exception
		}

		if (!shouldExist.equals(this.elementExists(pageElement))) {
			//this.Log.Log_Warning(pageElement.getTestName() + " " + waitingFor + " on the page and should not.");
		}

		return (shouldExist.equals(this.elementExists(pageElement)));
	}

	protected boolean waitForPageLoad() throws ParserConfigurationException, SAXException, IOException {
		final ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		final FluentWait<WebDriver> browserWait = new FluentWait<>(this.driver)
				.withTimeout(this.defaultTimeoutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(this.defaultPollingInterval, TimeUnit.MILLISECONDS);
		final boolean isPageLoaded = browserWait.until(pageLoadCondition);

		if (!isPageLoaded) {
		}

		return isPageLoaded;
	}

	public void waitForPresenceOfElement(final PageElement pageElement) {
		try {
			this.elementWait().until(
					ExpectedConditions.presenceOfElementLocated(pageElement.getLocator().getLocator()));
		} catch (final TimeoutException e) {
			// ignore error
		}
	}

	private void waitForVisibleElement(final PageElement pageElement) {
		try {
			this.elementWait().until(
					ExpectedConditions.visibilityOfElementLocated(pageElement.getLocator().getLocator()));
		} catch (final TimeoutException e) {
			// ignore error
		}
	}
}

