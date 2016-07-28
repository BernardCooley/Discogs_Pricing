package frameworkUtils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {

	public static void authenticationLogin(WebDriver driver, List<String> authenticationDetails) {
		try {
			Robot robot;
			robot = new Robot();
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			for (String s : authenticationDetails) {
				StringSelection selection = new StringSelection(s);
				clipboard.setContents(selection, selection);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				CommonFunctions.customWait(driver, 1);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static void globalLogin(WebDriver driver, By usernameField, By passwordField, By loginBtn, List<String> loginDetails) {
		CommonFunctions.enterString(driver, usernameField, loginDetails.get(0));
		CommonFunctions.enterString(driver, passwordField, loginDetails.get(1));
		CommonFunctions.clickElement(driver, loginBtn);
	}
}
