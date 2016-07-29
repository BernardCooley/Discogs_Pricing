package frameworkUtils;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserLauncher {

	private WebDriver driver = null;
	
	public BrowserLauncher() {
		
	}
	
	public WebDriver launchBrowser(String BrowserName) {
		if(BrowserName.equalsIgnoreCase("Firefox")) {
//			FirefoxProfile p = new FirefoxProfile(new File("C:\\Users\\Bernard\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\0g0i8bia.default"));
//			p.setPreference("permissions.default.image", 2);
//			p.setPreference("permissions.default.stylesheet", 2);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else if(BrowserName.equalsIgnoreCase("IE")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
	    	System.setProperty("webdriver.ie.driver", "C:\\Eclipse EE x86 Workspace\\ResidentAdvisorLabels\\IEDriverServer.exe");
	    	driver = new InternetExplorerDriver(capabilities);
	    	driver.get("javascript:document.getElementById('overridelink').click();");
		} else if(BrowserName.equalsIgnoreCase("Chrome")) {
			ChromeOptions op = new ChromeOptions();
			op.addArguments("--user-data-dir=C:\\Users\\Bernard\\AppData\\Local\\Google\\Chrome\\User Data");
			op.addArguments("--start-maximized");
			DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty("webdriver.chrome.driver", "C:\\Eclipse Workspace\\LabelsBase\\chromedriver.exe");
			driver = new ChromeDriver(op);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} 
//		else if(BrowserName.equalsIgnoreCase("PhantomJS")) {
//			DesiredCapabilities capabilities = new DesiredCapabilities();
//			capabilities.setCapability("takesScreenshot", false);
//			String d = "\\";
//			capabilities.setCapability("phantomjs.binary.path", "C:"+d+"Eclipse EE x86 Workspace"+d+"phantomjs-2.0.0-windows"+d+"bin"+d+"phantomjs.exe");
//	        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//	        capabilities.setCapability("load-images", false);
//			driver = new PhantomJSDriver(capabilities);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//		}
		
        return driver;
    }
	
	
}
