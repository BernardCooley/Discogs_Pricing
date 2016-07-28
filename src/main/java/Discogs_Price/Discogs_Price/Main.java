package Discogs_Price.Discogs_Price;
import org.openqa.selenium.WebDriver;

import frameworkUtils.BrowserLauncher;

public class Main {

	public static void main(String[] args) {
		
		String browser = "Chrome";
		String url = "https://www.discogs.com/search/?sort=title%2Casc&format_exact=Vinyl&format_exact[]=Vinyl&format_exact[]=12%22&page=16&style_exact=Techno";
		
		BrowserLauncher bL = new BrowserLauncher();
    	WebDriver driver = bL.lauchBrowser(browser);
    	
    	Pricing.priceRecords(driver, url);
    	
	}

}
