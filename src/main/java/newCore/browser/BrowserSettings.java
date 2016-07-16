package newCore.browser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.FluentWait;
import org.xml.sax.SAXException;

public abstract class BrowserSettings {

	private String browserType;
	private final String FILEPATH_CHROMEBROWSER = "Selenium\\SeleniumGridTesting\\WebDriver\\chromedriver.exe";
	private final String FILEPATH_IEBROWSER = "Selenium\\SeleniumGridTesting\\WebDriver\\IEDriverServer.exe";
	private final String filePath_ModifyHeaders = "Selenium\\SeleniumGridTesting\\WebDriver\\modify_headers.xpi";

	protected final int defaultPollingInterval = 250;
	protected final int defaultTimeoutInSeconds = 180;

	private FirefoxProfile profile;

	protected WebDriver driver;
	protected JavascriptExecutor javascriptExecutor;

	public BrowserSettings(final String browserType){
		setBrowserType(browserType);
	}

	private void setBrowserType(final String browserType){
		this.browserType = browserType;
	}

	public void setDriver() throws ParserConfigurationException, SAXException, IOException{
		boolean local = true;

		if(local){
			setLocalDriver();
		} else {
			setGridDriver();
		}
	}

	private void setJavaScriptExecutor(){
		javascriptExecutor = (JavascriptExecutor) driver;
	}
	
	private void setLocalDriver() throws ParserConfigurationException, SAXException, IOException{
		if(browserType.equals(Browser.BROWSERTYPE_FF)){
			driver = new FirefoxDriver(setFireFoxProfileAutomaticDownload());
		} else if(browserType.equals(Browser.BROWSERTYPE_IE)) {
			final File file = new File("");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			driver = new InternetExplorerDriver();
		} else if(browserType.equals(Browser.BROWSERTYPE_CHROME)){
			final File file = new File("");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

			final ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-popup-blocking");

			final Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", "");
			options.setExperimentalOption("prefs", prefs);

			this.driver = new ChromeDriver(options);
		} else {

		}
	}

	private void setGridDriver() throws ParserConfigurationException, SAXException, IOException{
		final File file = new File("");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		final DesiredCapabilities capability = setCapabilityBrowser();

		try {
			this.driver = new RemoteWebDriver(new URL("" + "/wd/hub"),
					capability);
		} catch (final UnreachableBrowserException e) {
			System.out.println("HUB MACHINE OR NODE MACHINES MAY NOT BE REGISTERED.  PLEASE CHECK.....");
			throw e;
		}
	}

	private FirefoxProfile setFireFoxProfileAutomaticDownload()
			throws ParserConfigurationException, SAXException, IOException {
		profile = new FirefoxProfile();


		String defaultAutomaticDownloadDirectory = "";
		defaultAutomaticDownloadDirectory = defaultAutomaticDownloadDirectory.replace("/", "\\");
		this.profile.setPreference("browser.download.dir", defaultAutomaticDownloadDirectory);
		this.profile.setPreference("browser.download.folderList", 2);
		this.profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,text/xml,application/pdf,application/zip,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-msdownload,application/xml,application/xhtml+xml,application/x-octet-stream,application/vnd.adobe.xdp+xml,text/plain,video/mp4,video/x-ms-wmv");

		return this.profile;
	}

	private DesiredCapabilities setCapabilityBrowser() throws ParserConfigurationException, SAXException, IOException {
		DesiredCapabilities capability = new DesiredCapabilities();

		if (browserType.equals(Browser.BROWSERTYPE_IE)) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability("requireWindowFocus", true);
			capability.setCapability("enablePersistentHover", false);
		} else if (browserType.equals(Browser.BROWSERTYPE_FF)) {
			capability = DesiredCapabilities.firefox();
			capability.setCapability(FirefoxDriver.PROFILE, setFireFoxProfileAutomaticDownload());
			capability.setBrowserName("firefox");
		} else if (browserType.equals(Browser.BROWSERTYPE_CHROME)) {
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");

			final ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-popup-blocking");

			final Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", "");
			options.setExperimentalOption("prefs", prefs);
		} else {
			// This intentionally blank
		}

		return capability;
	}
	
	protected String getBrowserType(){
		return browserType;
	}
	
	/**
	 * Use Seleniums FluentWait to wait for an element to exist
	 *
	 * @return FluentWait<WebDriver>
	 */
	protected FluentWait<WebDriver> elementWait() {
		return new FluentWait<>(driver)
				.withTimeout(defaultTimeoutInSeconds, TimeUnit.SECONDS)
				.pollingEvery(defaultPollingInterval, TimeUnit.MILLISECONDS)
				.ignoring(NotFoundException.class, NoSuchElementException.class);
	}
}
