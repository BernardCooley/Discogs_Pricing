package Discogs_Price.Discogs_Price;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import frameworkUtils.CommonFunctions;
import uI_Maps.UIMap_Discogs;

public class Pricing {

	private static ArrayList<ArrayList<String>> tableContents;
	private static ArrayList<ArrayList<WebElement>> tableElements;
	private static ArrayList<String> priceStringList = new ArrayList<String>();
	private static ArrayList<Double> priceDoubleList = new ArrayList<Double>();
	private static int backNavControl = 0;
	private static boolean extraBackNavigation = false;
	private static String currentURL;
	private static boolean match;

	public static void priceRecords(WebDriver driver, String url) {
		driver.get(url);

		for (int i = 0; i < CommonFunctions.getStringArrayOfElements(driver, UIMap_Discogs.releases).size(); i++) {
			match = false;
			backNavControl = 0;
			extraBackNavigation = false;
			String[] sSplit = CommonFunctions.getStringArrayOfElements(driver, UIMap_Discogs.releases).get(i).split("\\r?\\n");
			CommonFunctions.clickElement(driver, sSplit[0]);
			System.out.println(sSplit[0]);
			System.out.println(sSplit[1]);

			if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.buyVinyllBtn)) {
				if (CommonFunctions.getElementText(driver, UIMap_Discogs.buyVinyllBtn).equals("Vinyl and CD")
						|| CommonFunctions.getElementText(driver, UIMap_Discogs.buyVinyllBtn).equals("Buy Vinyl")) {

					CommonFunctions.clickElement(driver, UIMap_Discogs.buyVinyllBtn);

					if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.priceColumnItems)) {
						if (CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.priceColumnItems).size() > 1) {
							currentURL = driver.getCurrentUrl();
							if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.priceColumnHeaderChevron)) {
								if (CommonFunctions.getAttributeValue(driver, UIMap_Discogs.priceColumnHeaderChevron, "class").contains("icon-chevron-down")) {
									CommonFunctions.clickElement(driver, UIMap_Discogs.priceColumnHeaderTitle);
									CommonFunctions.customWait(driver, 1);
									backNavControl++;
								}
							} else {
								CommonFunctions.clickElement(driver, UIMap_Discogs.priceColumnHeaderTitle);
								CommonFunctions.customWait(driver, 1);
								backNavControl++;
								if (CommonFunctions.getAttributeValue(driver, UIMap_Discogs.priceColumnHeaderChevron, "class").contains("icon-chevron-down")) {
									CommonFunctions.clickElement(driver, UIMap_Discogs.priceColumnHeaderTitle);
									CommonFunctions.customWait(driver, 1);
									backNavControl++;
								}
							}
							for (WebElement we : CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.priceColumnItems)) {
								priceStringList.add(we.findElement(By.cssSelector("span")).getText());
							}
						}

					}
					for (int j = 0; j < backNavControl; j++) {
						driver.navigate().back();
						CommonFunctions.customWait(driver, 1);
					}
					driver.navigate().back();
				}
			}

			for (String str : priceStringList) {
				switch (str.charAt(0)) {
				case '£':
					priceDoubleList.add(Double.parseDouble(str.substring(1)));
					break;
				case '$':
					priceDoubleList.add(Double.parseDouble(str.substring(1)));
					break;
				case '€':
					priceDoubleList.add(Double.parseDouble(str.substring(1)));
					break;
				case 'A':
					priceDoubleList.add(Double.parseDouble(str.substring(2)));
					break;
				case 'C':
					priceDoubleList.add(Double.parseDouble(str.substring(3)));
					break;
				case '¥':
					priceDoubleList.add(Double.parseDouble(str.substring(1)));
					break;
				case 'S':
					priceDoubleList.add(Double.parseDouble(str.substring(3)));
					break;
				case 'N':
					priceDoubleList.add(Double.parseDouble(str.substring(3)));
					break;
				case 'M':
					priceDoubleList.add(Double.parseDouble(str.substring(3)));
					break;
				case 'R':
					priceDoubleList.add(Double.parseDouble(str.substring(2)));
					break;
				case 'Z':
					priceDoubleList.add(Double.parseDouble(str.substring(3)));
					break;
				}
			}

			for (int j = 0; j < priceDoubleList.size() - 1; j++) {
				System.out.println(priceDoubleList.get(j));
				if (priceDoubleList.get(j + 1) - priceDoubleList.get(j) > 10.0) {
					match = true;
				}
			}
			if (match) {
				System.out.println("Match: " + currentURL);
			}

			System.out.println();

			driver.navigate().back();
		}
	}
}
