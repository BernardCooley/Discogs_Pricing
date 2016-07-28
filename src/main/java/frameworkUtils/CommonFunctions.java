package frameworkUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonFunctions {

	private static int timeOut = 5;

	// Click elements
	public static void clickElement(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		driver.findElement(element).click();
	}

	public static void clickElement(WebDriver driver, String linkText) {
		Assert.assertTrue(isLinkVisibleByText(driver, linkText), "Element " + linkText + " not found");
		driver.findElement(By.linkText(linkText)).click();
	}

	public static void clickElementByContainingText(WebDriver driver, String containingText) {
		Assert.assertTrue(isElementVisible(driver, By.xpath("//*[contains(text(), '" + containingText + "')]")),
				"Element containing " + containingText + " not found");
		driver.findElement(By.xpath("//*[contains(text(), '" + containingText + "')]")).click();
	}

	public static void clickElementInArray(WebDriver driver, By element, int buttonNumber) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> we = driver.findElements(element);
		we.get(buttonNumber - 1).click();
	}
	
	public static void clickElementInArray(WebDriver driver, By element, String linkText) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		for (WebElement we : getArrayOfElements(driver, element)) {
			if (we.getText().contains(linkText)) {
				we.click();
			}
		}
	}

	public static void clickArrayOfElements(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> buttonArray = driver.findElements(element);

		for (WebElement we : buttonArray) {
			we.click();
			customWait(driver, 1 / 2);
		}
	}

	public static void clickArrayOfElements(WebDriver driver, ArrayList<By> byArray) {
		for (By element : byArray) {
			Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
			driver.findElement(element).click();
		}
	}

	public static String clickRandomElement(WebDriver driver, By element, int randomNumber) {
		String selection = null;
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		WebElement we = CommonFunctions.getArrayOfElements(driver, element).get(randomNumber - 1);
		we.click();
		selection = we.getText();
		return selection;
	}
	
	// Get Elements
	public static WebElement getElementByText(WebDriver driver, String elementText) {
		Assert.assertTrue(isLinkVisibleByText(driver, elementText), "Element " + elementText + " not found");
		return driver.findElement(By.xpath("//*[contains(text(), '" + elementText + "')]"));
	}

	public static WebElement getElementByTextContains(WebDriver driver, String containingText) {
		Assert.assertTrue(isElementVisible(driver, By.xpath("//*[contains(text(), '" + containingText + "')]")),
				"Element containing " + containingText + " not found");
		return driver.findElement(By.xpath("//*[text() = '" + containingText + "']"));
	}

	public static WebElement getElement(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		return driver.findElement(element);
	}

	public static WebElement getElement(WebDriver driver, WebElement we, By xpathExtension) {
		Assert.assertTrue(isElementVisible(driver, we), "Element " + we + " not found");
		return we.findElement(xpathExtension);
	}

	public static WebElement getElement(WebDriver driver, String elementType, String attributeName, String attributeValue) {
		Assert.assertTrue(isElementVisible(driver, By.cssSelector(elementType + "[" + attributeName + "='" + attributeValue + "']")),
				"Element " + elementType + " where " + attributeName + " = " + attributeValue + " not found");
		return driver.findElement(By.cssSelector(elementType + "[" + attributeName + "='" + attributeValue + "']"));
	}

	public static WebElement getElement(WebDriver driver, String cssSelectorString) {
		Assert.assertTrue(isElementVisible(driver, By.cssSelector(cssSelectorString)), "Element " + cssSelectorString + " not found");
		return driver.findElement(By.cssSelector(cssSelectorString));
	}

	public static List<WebElement> getArrayOfElements(WebDriver driver, String containingText) {
		Assert.assertTrue(isElementVisible(driver, By.xpath("//*[contains(text(), '" + containingText + "')]")), "Element " + containingText + " not found");
		return driver.findElements(By.xpath("//*[contains(text(), '" + containingText + "')]"));
	}

	public static List<WebElement> getArrayOfElements(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		return driver.findElements(element);
	}

	public static ArrayList<ArrayList<WebElement>> getTableWebElements(WebDriver driver, By table) {
		ArrayList<ArrayList<WebElement>> tableArray = new ArrayList<ArrayList<WebElement>>();

		Assert.assertTrue(isElementVisible(driver, table), "Element " + table + " not found");

		int rows = driver.findElement(table).findElements(By.cssSelector("tr")).size();
		int columns = driver.findElement(table).findElements(By.cssSelector("tbody > tr > td")).size() / (rows - 1);

		for (int i = 0; i < rows; i++) {
			tableArray.add(new ArrayList<WebElement>());
		}

		for (int i = 0; i < columns; i++) {
			tableArray.get(0).add(driver.findElement(table).findElements(By.cssSelector("th")).get(i));
		}

		int counter = 0;

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				tableArray.get(i).add(driver.findElement(table).findElements(By.cssSelector("td")).get(counter));
				counter++;
			}
		}

		return tableArray;
	}

	// Get Element Contents
	public static String getTextBoxContents(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		return driver.findElement(element).getAttribute("value");
	}

	public static ArrayList<String> getStringArrayOfElements(WebDriver driver, By element) {
		ArrayList<String> aggregateElementsText = new ArrayList<String>();
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> aggregateElements = driver.findElements(element);
		for (int i = 0; i < driver.findElements(element).size(); i++) {
			aggregateElementsText.add(aggregateElements.get(i).getText());
		}
		return aggregateElementsText;
	}

	public static ArrayList<String> getStringArrayOfElements(WebDriver driver, By element, String attributeName) {
		ArrayList<String> aggregateElementsAttribute = new ArrayList<String>();
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> aggregateElements = driver.findElements(element);
		for (int i = 0; i < driver.findElements(element).size(); i++) {
			aggregateElementsAttribute.add(aggregateElements.get(i).getAttribute(attributeName));
		}
		return aggregateElementsAttribute;
	}

	public static String getElementText(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		return driver.findElement(element).getText();
	}

	public static String getStringOfMultipleElementsText(WebDriver driver, By element) {
		String str = null;
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> elements = driver.findElements(element);
		for (int i = 0; i < driver.findElements(element).size(); i++) {
			str += elements.get(i).getText();
		}
		return str;
	}

	public static String getAttributeOfElementInArray(WebDriver driver, By element, String attributeName, int elementNumber) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> elementList = driver.findElements(element);
		return elementList.get(elementNumber - 1).getAttribute(attributeName);
	}

	public static String getAttributeValue(WebDriver driver, By element, String attributeName) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		return driver.findElements(element).get(0).getAttribute(attributeName);
	}

	public static ArrayList<ArrayList<String>> getTableContents(WebDriver driver, By table) {
		ArrayList<ArrayList<String>> tableArray = new ArrayList<ArrayList<String>>();

		Assert.assertTrue(isElementVisible(driver, table), "Element " + table + " not found");

		int rows = driver.findElement(table).findElements(By.cssSelector("tr")).size();
		int columns = driver.findElement(table).findElements(By.cssSelector("tbody > tr > td")).size() / (rows - 1);

		for (int i = 0; i < rows; i++) {
			tableArray.add(new ArrayList<String>());
		}

		for (int i = 0; i < columns; i++) {
			System.out.println(columns);
			tableArray.get(0).add(driver.findElement(table).findElements(By.cssSelector("th")).get(i).getText());
		}

		int counter = 0;

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				tableArray.get(i).add(driver.findElement(table).findElements(By.cssSelector("td")).get(counter).getText());
				counter++;
			}
		}

		return tableArray;
	}

	// Assertions
	public static boolean compareTwoStringArrays(ArrayList<String> arrayList1, ArrayList<String> arrayList2) {
		boolean bool = true;
		for (String s : arrayList1) {
			if (s.equals(arrayList2.get(arrayList1.indexOf(s)))) {
				System.out.println("Element: \"" + s + "\" Found");
			} else {
				System.err.println("Element: \"" + s + "\" Not Found");
				bool = false;
			}
		}

		if (arrayList1.size() == arrayList2.size()) {
			System.out.println("Correct amount of elements");
		} else {
			System.err.println("Incorrect amount of elements");
			bool = false;
		}
		return bool;
	}

	public static boolean findArrayOfSubStrings(String fullString, ArrayList<String> arrayOfSubStrings, int elementAmount) {
		boolean bool = true;
		int actualElementAmount = 0;

		for (String s : arrayOfSubStrings) {
			if (fullString.contains(s)) {
				System.out.println("Label: \"" + s + "\" Found");
				actualElementAmount++;
			} else {
				System.err.println("Label: \"" + s + "\" Not Found");
				bool = false;
			}
		}

		if (actualElementAmount != elementAmount) {
			bool = false;
		}
		return bool;
	}

	public static boolean findSubString(String fullString, String subString) {
		boolean bool = true;
		if (fullString.contains(subString)) {
			System.out.println("Label: \"" + subString + "\" found");
		} else {
			System.err.println("Label: \"" + subString + "\" not found");
			bool = false;
		}
		return bool;
	}

	public static boolean isElementVisible(WebDriver driver, By element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
			return true;
		} catch (Exception e) {
//			System.err.println("Element " + element + " not found");
			return false;
		}
	}

	public static boolean isElementVisible(WebDriver driver, WebElement we) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOf(we));
			return true;
		} catch (Exception e) {
			System.err.println("Element " + we + " not found");
			return false;
		}
	}

	public static boolean isLinkVisibleByText(WebDriver driver, String linkText) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
			return true;
		} catch (Exception e) {
			System.err.println("Element " + linkText + " not found");
			return false;
		}
	}

	public static boolean isElementVisibleByText(WebDriver driver, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("*:contains('" + text + "')")));
			return true;
		} catch (Exception e) {
			System.err.println("Element " + text + " not found");
			return false;
		}
	}

	// Set Element State
	public static String setSliderLimits(WebDriver driver, By minSlider, By maxSlider, double range, String attributeName) {
		Assert.assertTrue(isElementVisible(driver, minSlider), "Element " + minSlider + " not found");
		Assert.assertTrue(isElementVisible(driver, maxSlider), "Element " + maxSlider + " not found");

		double move = Math.random() * range / 2;

		for (int i = 0; i < move; i++) {
			driver.findElement(minSlider).sendKeys(Keys.RIGHT);
			driver.findElement(maxSlider).sendKeys(Keys.LEFT);
		}

		return driver.findElement(minSlider).getAttribute(attributeName) + " - " + driver.findElement(maxSlider).getAttribute(attributeName);
	}

	public static void deselectAllButOneElement(WebDriver driver, By element, int btnNumber, String attributeName, String containingText) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		List<WebElement> elementList = driver.findElements(element);

		for (WebElement we : elementList) {
			if (we.getAttribute(attributeName).contains(containingText)) {
				we.click();
			}
		}
		customWait(driver, 1);
		elementList.get(btnNumber - 1).click();
	}

	public static void enterString(WebDriver driver, By element, String inputText) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		driver.findElement(element).sendKeys(inputText);
	}

	public static void clearTextField(WebDriver driver, By element) {
		Assert.assertTrue(isElementVisible(driver, element), "Element " + element + " not found");
		driver.findElement(element).clear();
	}

	// Generate Randomness
	public static List<String> generateRandomDateRangeStringList() {
		List<String> dateList = new ArrayList<String>();
		Random rand = new Random();

		int year = rand.nextInt((2016 - 1970) + 1) + 1970;
		int dayOfYear = rand.nextInt(366);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
		Date randomDate1 = calendar.getTime();
		dateList.add(randomDate1.toString());

		year = rand.nextInt((2016 - 1970) + 1) + 1970;
		dayOfYear = rand.nextInt(366);
		calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
		Date randomDate2 = calendar.getTime();

		if (randomDate1.before(randomDate2)) {
			dateList.add(randomDate2.toString());
		}

		while (randomDate1.after(randomDate2)) {
			year = rand.nextInt((2016 - 1970) + 1) + 1970;
			dayOfYear = rand.nextInt(366);
			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
			randomDate2 = calendar.getTime();

			if (randomDate1.before(randomDate2)) {
				dateList.add(randomDate2.toString());
			}
		}
		return dateList;
	}

	public static int generateRandomNumber(WebDriver driver, int lowerLimit, int higherLimit) {
		return (lowerLimit + (int) (Math.random() * higherLimit));
	}

	// Custom Waits
	public static void customWait(WebDriver driver, int secondsToWait) {
		try {
			Thread.sleep(secondsToWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Sortable items
	public static boolean isTableSortable(WebDriver driver, ArrayList<ArrayList<WebElement>> tableElements, By table, By element) {

		ArrayList<ArrayList<String>> currentTableContents = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < tableElements.get(0).size() - 1; i++) {
			driver.findElements(element).get(i).click();
			currentTableContents = getTableContents(driver, table);
			if (CommonFunctions.getArrayOfElements(driver, element).get(0).getAttribute("class").contains("md-asc")) {
				Assert.assertTrue(isSorted(currentTableContents, i + 1), "Column " + (i + 1) + " is in ascending order");
				driver.findElements(element).get(i).click();
				currentTableContents = getTableContents(driver, table);
				Assert.assertFalse(isSorted(currentTableContents, i + 1), "Column " + (i + 1) + " is in descending order");
			} else if (CommonFunctions.getArrayOfElements(driver, element).get(0).getAttribute("class").contains("md-desc")) {
				Assert.assertFalse(isSorted(currentTableContents, i + 1), "Column " + (i + 1) + " is in descending order");
				driver.findElements(element).get(i).click();
				currentTableContents = getTableContents(driver, table);
				Assert.assertTrue(isSorted(currentTableContents, i + 1), "Column " + (i + 1) + " is in ascending order");
			}

		}
		return true;
	}

	public static boolean isSorted(ArrayList<ArrayList<String>> currentTableContents, int columnNumber) {
		for (int i = 1; i < currentTableContents.size() - 1; i++) {
			try {
				int num1 = Integer.parseInt(currentTableContents.get(i).get(columnNumber - 1));
				int num2 = Integer.parseInt(currentTableContents.get(i + 1).get(columnNumber - 1));
				if (num1 > num2) {
					return false;
				}
			} catch (NumberFormatException nfe) {
				String s1 = currentTableContents.get(i).get(columnNumber - 1).toLowerCase();
				String s2 = currentTableContents.get(i + 1).get(columnNumber - 1).toLowerCase();
				if (s1.compareTo(s2) > 0) {
					return false;
				}
			}
		}
		return true;
	}

	// Table navigation
	public static boolean checkTableMaxItemsPerPage(WebDriver driver, By pageInfoLabel, List<Integer> rowsPerPageList, By perPageDropdown, By table) {
		String rowAmountLabel = CommonFunctions.getElementText(driver, pageInfoLabel);
		String[] noOfFieldsSplit = rowAmountLabel.split(" ");
		int totalRowAmount = Integer.parseInt(noOfFieldsSplit[noOfFieldsSplit.length - 1]);

		for (Integer i : rowsPerPageList) {
			CommonFunctions.clickElement(driver, perPageDropdown);
			CommonFunctions.getElement(driver, "md-option", "value", i.toString()).click();
			CommonFunctions.customWait(driver, 1);
			ArrayList<ArrayList<WebElement>> actualTableElements = CommonFunctions.getTableWebElements(driver, table);

			switch (i) {
			case 5:
				if (i < totalRowAmount) {
					Assert.assertTrue(actualTableElements.size() - 1 == i);
				} else {
					Assert.assertTrue(actualTableElements.size() - 1 == totalRowAmount);
				}
				break;
			case 10:
				if (i < totalRowAmount) {
					Assert.assertTrue(actualTableElements.size() - 1 == i);
				} else {
					Assert.assertTrue(actualTableElements.size() - 1 == totalRowAmount);
				}
				break;
			case 20:
				if (i < totalRowAmount) {
					Assert.assertTrue(actualTableElements.size() - 1 == i);
				} else {
					Assert.assertTrue(actualTableElements.size() - 1 == totalRowAmount);
				}
				break;
			case 50:
				if (i < totalRowAmount) {
					Assert.assertTrue(actualTableElements.size() - 1 == i);
				} else {
					Assert.assertTrue(actualTableElements.size() - 1 == totalRowAmount);
				}
				break;
			}
		}
		return true;
	}

	public static boolean checkTablePagination(WebDriver driver, int rowsPerPage, By perPageDropdown, By pageInfoLabel, By nextButton, By previousButton,
			By table) {
		String rowAmountLabel = CommonFunctions.getElementText(driver, pageInfoLabel);
		String[] noOfFieldsSplit = rowAmountLabel.split(" ");
		int totalRowAmount = Integer.parseInt(noOfFieldsSplit[noOfFieldsSplit.length - 1]);

		CommonFunctions.clickElement(driver, perPageDropdown);
		CommonFunctions.getElement(driver, "md-option", "value", Integer.toString(rowsPerPage)).click();
		CommonFunctions.customWait(driver, 1);
		ArrayList<ArrayList<String>> actualTableContents = CommonFunctions.getTableContents(driver, table);

		if (rowsPerPage < totalRowAmount) {
			CommonFunctions.clickElement(driver, nextButton);
			CommonFunctions.customWait(driver, 1);
			Assert.assertNotEquals(actualTableContents, CommonFunctions.getTableContents(driver, table));
		}

		CommonFunctions.clickElement(driver, previousButton);
		CommonFunctions.customWait(driver, 1);

		Assert.assertEquals(actualTableContents, CommonFunctions.getTableContents(driver, table));

		if (rowsPerPage < totalRowAmount) {
			Assert.assertEquals(CommonFunctions.getTableContents(driver, table).size() - 1, rowsPerPage);
		}
		return true;
	}
}
