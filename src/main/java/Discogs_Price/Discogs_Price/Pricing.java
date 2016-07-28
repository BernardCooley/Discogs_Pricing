package Discogs_Price.Discogs_Price;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import frameworkUtils.CommonFunctions;
import frameworkUtils.DBConnection;
import uI_Maps.UIMap_Discogs;

public class Pricing {

	private static ArrayList<String> priceStringList = new ArrayList<String>();
	private static ArrayList<Double> priceDoubleList = new ArrayList<Double>();
	private static ArrayList<String> releaseUrlList = new ArrayList<String>();
	private static boolean match;
	private static double euroConversion = 0.841331;
	private static double priceLimit = 20.00;
	private static int pageCounter = 0;

	public static void priceRecords(WebDriver driver, String url) {
		for (int i = 0; i < 50; i++) {
			driver.get(url);

			for (int j = 0; j < pageCounter; j++) {
				CommonFunctions.clickElement(driver, UIMap_Discogs.nextPageBtn);
				CommonFunctions.customWait(driver, 1);
			}

			Select select = new Select(driver.findElement(UIMap_Discogs.itemsPerPageLimitDropdown));
//			select.selectByVisibleText("250");

			for (WebElement we : CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.releases)) {
				if (!WriteToDatabase.doesUrlExist(we.findElement(By.cssSelector("h4 > a")).getAttribute("href"))) {
					releaseUrlList.add(we.findElement(By.cssSelector("h4 > a")).getAttribute("href"));
				}
			}

			for (String s : releaseUrlList) {
				driver.get(s);
				
				WriteToDatabase.writeNewRecordToDatabase(s);

				if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.buyBtnSection)) {
					if (CommonFunctions.getElement(driver, UIMap_Discogs.buyBtnSection)
							.findElement(By.cssSelector("div > a")).isDisplayed()) {
						if (CommonFunctions.getElement(driver, UIMap_Discogs.buyBtnSection)
								.findElement(By.cssSelector("div > a")).getText().equals("Vinyl and CD")
								|| CommonFunctions.getElement(driver, UIMap_Discogs.buyBtnSection)
										.findElement(By.cssSelector("div > a")).getText().equals("Buy Vinyl")) {

							CommonFunctions.clickElement(driver, UIMap_Discogs.buyVinyllBtn);

							if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.priceColumnItems)) {
								if (CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.priceColumnItems)
										.size() > 1) {
									for (WebElement we : CommonFunctions.getArrayOfElements(driver,
											UIMap_Discogs.priceColumnItems)) {
										priceStringList.add(we.findElement(By.cssSelector("span")).getText());
									}
								}
							}
						}
					}
					for (String str : priceStringList) {
						switch (str.charAt(0)) {
						case '£':
							priceDoubleList.add(round(Double.parseDouble(str.substring(1)), 2));
							break;
						case '€':
							priceDoubleList.add(round(Double.parseDouble(str.substring(1)) * euroConversion, 2));
							break;
						}
					}

					Collections.sort(priceDoubleList);

					if (priceDoubleList.size() > 0) {
						for (int j = 0; j < 1; j++) {
							if (priceDoubleList.get(j + 1) - priceDoubleList.get(j) > priceLimit) {
								match = true;
							}
						}
					}
					System.out.println((releaseUrlList.indexOf(s) + 1) + " of " + releaseUrlList.size());
					if (match) {
//						try (FileWriter fw = new FileWriter("Matches.txt", true);
//								BufferedWriter bw = new BufferedWriter(fw);
//								PrintWriter out = new PrintWriter(bw)) {
//							out.println(s);
//						} catch (IOException e) {
//						}
//						WriteToDatabase.updateDatabase(s);
						
						
						Connection con = DBConnection.dbConnector();
						PreparedStatement pst = null;
						
						String sqlUpdate = "UPDATE matches SET matched='Yes' WHERE url=?";
						
						try {
							pst = con.prepareStatement(sqlUpdate);
							pst.setString(1, s);
							pst.executeUpdate();
						} catch (SQLException e) {
						}
						
						System.out.println("Match: " + s);
					}
				}

				priceStringList.clear();
				priceDoubleList.clear();
				match = false;
			}
			System.out.println("-------------------------------------------------------------------------------");
			pageCounter++;
			releaseUrlList.clear();
		}

	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
