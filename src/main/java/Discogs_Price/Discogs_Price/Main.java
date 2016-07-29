package Discogs_Price.Discogs_Price;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;

import frameworkUtils.BrowserLauncher;

public class Main {

	public static void main(String[] args) {

		String url = "https://www.discogs.com/search/?sort=title%2Casc&format_exact=12%22&format_exact%5B%5D=Vinyl&format_exact%5B%5D=12%22&layout=big&country_exact=UK";
		int choice;

		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Please choose (1-4) \n1. Populate Databse \n2. Get record price matches \n3. Get all releases to be reviewed \n4. Set all records to reviewed state");

		choice = sc.nextInt();

		switch (choice) {
		case 1:
			Pricing.populateDatabase(launchBrowser(), url);
			break;
		case 2:
			Pricing.priceRecords(launchBrowser());
			break;
		case 3:
			Pricing.returnAllToBeReviewed();
			break;
		case 4:
			Pricing.setAllToReviewed();
			break;
		}
	}

	public static WebDriver launchBrowser() {
		BrowserLauncher bL = new BrowserLauncher();
		WebDriver driver = bL.launchBrowser("Firefox");
		return driver;
	}

}
