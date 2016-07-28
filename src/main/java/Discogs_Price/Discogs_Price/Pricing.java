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

	public static void priceRecords(WebDriver driver, String url) {
		driver.get(url);

		for (int i = 0; i < CommonFunctions.getStringArrayOfElements(driver, UIMap_Discogs.releases).size(); i++) {
			backNavControl = 0;
			extraBackNavigation = false;
			String sSplit[] = CommonFunctions.getStringArrayOfElements(driver, UIMap_Discogs.releases).get(i + 17).split("\\r?\\n");
			CommonFunctions.clickElement(driver, sSplit[0]);
			System.out.println(sSplit[0]);
			System.out.println(sSplit[1]);

			if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.buyVinyllBtn)) {
				if (CommonFunctions.getElementText(driver, UIMap_Discogs.buyVinyllBtn).equals("Vinyl and CD")
						|| CommonFunctions.getElementText(driver, UIMap_Discogs.buyVinyllBtn).equals("Buy Vinyl")) {

					CommonFunctions.clickElement(driver, UIMap_Discogs.buyVinyllBtn);

					if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.priceColumnItems)) {
						if (CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.priceColumnItems).size() > 1) {
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
				priceDoubleList.add(Double.parseDouble(str.substring(1)));
			}
			
			for (int j = 0; j < priceDoubleList.size()-1; j++) {
				System.out.println(priceDoubleList.get(i));
				if (priceDoubleList.get(i+1) - priceDoubleList.get(i) > 5.5) {
					System.out.println("Match");
				}
			}

			System.out.println();

			driver.navigate().back();
		}
	}
}
