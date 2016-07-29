package Discogs_Price.Discogs_Price;
import org.openqa.selenium.WebDriver;

import frameworkUtils.BrowserLauncher;

public class Main {

	public static void main(String[] args) {
		
		String browser = "Firefox";
		String url = "https://www.discogs.com/search/?sort=title%2Casc&format_exact=12%22&format_exact%5B%5D=Vinyl&format_exact%5B%5D=12%22&layout=big&country_exact=UK";
		
		BrowserLauncher bL = new BrowserLauncher();
    	WebDriver driver = bL.launchBrowser(browser);
    	
    	Pricing.populateDatabase(driver, url);
    	Pricing.priceRecords(driver);
    	
	}

}
