package Discogs_Price.Discogs_Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import frameworkUtils.CommonFunctions;
import frameworkUtils.DBConnection;
import uI_Maps.UIMap_Discogs;

public class Pricing {

	private static ArrayList<String> priceStringList = new ArrayList<String>();
	private static ArrayList<Double> priceDoubleList = new ArrayList<Double>();
	private static ArrayList<String> releaseUrlList = new ArrayList<String>();
	private static boolean match;
	private static double euroConversion = 0.841331;
	private static double priceLimit = 15.00;
	private static int pageCounter = 0;
	private static List<WebElement> releasesList;
	private static List<String> releasesStringList;
	private static String currentUrl;
	private static ArrayList<String> databaseUrlList = new ArrayList<String>();
	private static ArrayList<String> removeList = new ArrayList<String>();
	
	public static void populateDatabase(WebDriver driver, String url) {
		driver.get(url);

		String[] split = CommonFunctions.getElementText(driver, UIMap_Discogs.resultsTotal).split(" ");
		String total = split[4];
		total = total.replace(",", "");

//		String sqlSelect = "SELECT url FROM matches";
//		try {
//			pst = con.prepareStatement(sqlSelect);
//			ResultSet rs = pst.executeQuery();
//			
//			while(rs.next()) {
//				databaseUrlList.add(rs.getString(1));
//			}
//		} catch (SQLException e1) {
//		}
//		releasesStringList = CommonFunctions.getStringArrayOfAttributeValues(driver, UIMap_Discogs.releases, "href", "h4 > a");
//		
//		doesUrlsExist(databaseList, siteList);
		
		
		
		
		for (int i = 0; i < Integer.parseInt(total) / 50; i++) {
			System.out.println();
			System.out.println("Scanning page " + (i+1) + " of " + (Integer.parseInt(total) / 50));
			
			releasesStringList = CommonFunctions.getStringArrayOfAttributeValues(driver, UIMap_Discogs.releases, "href", "h4 > a");
			
			Connection con = DBConnection.dbConnector();
			PreparedStatement pst = null;
			
			String sqlInsert = "INSERT INTO matches VALUES (?,'N/A','No','No')";
			
			for (String s : releasesStringList) {
				System.out.println(releasesStringList.indexOf(s) + "release of " + releasesStringList.size());
				if (!WriteToDatabase.doesUrlExist(s)) {
					try {
						pst = con.prepareStatement(sqlInsert);
						pst.setString(1, s);
						pst.executeUpdate();
					} catch (SQLException e) {
					}
				}
			}
			
//			for (WebElement we : releasesList) {
//				System.out.println(releasesList.indexOf(we) + " releases of " + releasesList.size());
//				currentUrl = we.findElement(By.cssSelector("h4 > a")).getAttribute("href");
//				if (!WriteToDatabase.doesUrlExist(currentUrl)) {
//					String sqlInsert = "INSERT INTO matches (url,matched,checked) VALUES (?,'N/A','No')";
//					try {
//						pst = con.prepareStatement(sqlInsert);
//						pst.setString(1, currentUrl);
//						pst.executeUpdate();
//					} catch (SQLException e) {
//					}
//				}
//			}
			CommonFunctions.clickElement(driver, UIMap_Discogs.nextPageBtn);
			CommonFunctions.customWait(driver, 1);
		}
	}
	
	public static void priceRecords(WebDriver driver) {
		Connection con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		String sqlSelect = "SELECT * FROM matches";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				if (rs.getString(3).equals("No")) {
					releaseUrlList.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
		}

		for (String s : releaseUrlList) {
			System.out.println("Scanning release " + releaseUrlList.indexOf(s) + " of " + releaseUrlList.size());
			driver.get(s);

			if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.buyBtnSection)) {
				if (CommonFunctions.getElement(driver, UIMap_Discogs.buyBtnSection).findElement(By.cssSelector("div > a")).isDisplayed()) {
					if (CommonFunctions.getElement(driver, UIMap_Discogs.buyBtnSection).findElement(By.cssSelector("div > a")).getText().equals("Vinyl and CD")
							|| CommonFunctions.getElement(driver, UIMap_Discogs.buyBtnSection).findElement(By.cssSelector("div > a")).getText()
									.equals("Buy Vinyl")) {

						CommonFunctions.clickElement(driver, UIMap_Discogs.buyVinyllBtn);

						if (CommonFunctions.isElementVisible(driver, UIMap_Discogs.priceColumnItems)) {
							if (CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.priceColumnItems).size() > 1) {
								for (WebElement we : CommonFunctions.getArrayOfElements(driver, UIMap_Discogs.priceColumnItems)) {
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
						try {
							if (priceDoubleList.get(j + 1) - priceDoubleList.get(j) > priceLimit) {
								match = true;
							}
						} catch (IndexOutOfBoundsException e) {
						}
					}
				}
				if (match) {
					String sqlUpdate = "UPDATE matches SET matched='Yes' WHERE url=?";

					try {
						pst = con.prepareStatement(sqlUpdate);
						pst.setString(1, s);
						pst.executeUpdate();
					} catch (SQLException e) {
					}

					System.out.println("Match: " + s);
				}
				
				String sqlUpdate = "UPDATE matches SET checked='Yes' WHERE url=?";

				try {
					pst = con.prepareStatement(sqlUpdate);
					pst.setString(1, s);
					pst.executeUpdate();
				} catch (SQLException e) {
				}
				
			}
			priceStringList.clear();
			priceDoubleList.clear();
			match = false;
		}

	}
	
	public static void setAllToReviewed() {
		Connection con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		String sqlUpdate = "UPDATE matches SET reviewed='Yes' WHERE matched='Yes'";

		try {
			pst = con.prepareStatement(sqlUpdate);
			pst.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public static void returnAllToBeReviewed() {
		Connection con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		String sqlSelect = "SELECT url FROM matches WHERE matched='Yes' AND reviewed IS NULL";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	
	public static ArrayList<String> doesUrlsExist(ArrayList<String> databaseList, ArrayList<String> siteList) {
		
		ArrayList<String> removeList = new ArrayList<String>();
		ArrayList<String> siteList1 = siteList;
		
		for (String s : databaseList) {
			for (String s1 : siteList1) {
				if (s1.equals(s)) {
					removeList.add(s);
				}
			}
		}
		
		
		
		
		return removeList;
	}
}
