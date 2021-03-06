package webLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import data.FakeData;

public class Library {
	
	public static final Logger logger = LogManager.getLogger(Library.class.getName());

	protected FakeData fakeData = new FakeData();
	

	// SETTING DRIVER:
	public static void setDriverConfiguration(WebDriver driver, int implicitlyWaitInSeconds) {

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(implicitlyWaitInSeconds, TimeUnit.SECONDS);
	}

	// SENDING TEXT TO INPUT:
	public static void setText(WebElement element, String text) {

		element.sendKeys(text);

	}

	// WAITING INSTEAD OF THREAD.SLEEP
	public static void wait(int timeToWaitInSec) {

		try {
			Thread.sleep(timeToWaitInSec * 1000);
		} catch (InterruptedException e) {

			e.getMessage();
		}
	}

	// GENERATING RANDOM EMAIL
	public static String generateRandomEmail(int length) {
		Random rnd = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rnd.nextInt(characters.length()));
		}
		String email = new String(text) + "@gmail.com";
		return email;

	}

	// SWITCHING TO NEW WINDOW
	public static void switchToNewWindow(WebDriver driver) {

		String parentHandle = driver.getWindowHandle();
		Set<String> allHandles = driver.getWindowHandles();

		for (String newHandle : allHandles) {

			if (newHandle != parentHandle)
				driver.switchTo().window(newHandle);

		}

	}

	// SCROLLING TILL BOTTOM/FOOTER
	public static void scrollTillBottom(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

	// SCROLLING TO TOP/HEADER
	public static void scrollToTop(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");

	}

	// SCROLLING TO THIS ELEMENT
	public static void scrollToThisElement(WebDriver driver, WebElement element) {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

	}

	// CLICKING THE ELEMENT
	public static void clickElement(WebElement element) {

		element.click();

	}

	// VERIFYING IS ELEMENT PRESENT ON THE PAGE USING XPATH
	public static boolean isElementPresentUsingXpath(WebDriver driver, String xpath) throws Exception {
		driver.findElement(By.xpath(xpath));
		try {
			driver.findElement(By.xpath(xpath));
		} catch (NoSuchElementException e) {

			return false;
		} catch (Exception e) {
			throw new Exception("Unexpected exception");
		}

		return true;
	}

	// VERIFYING IS ELEMENT PRESENT ON THE PAGE USING CSS
	public static boolean isElementPresentUsingCSS(WebDriver driver, String css) throws Exception {
		driver.findElement(By.cssSelector(css));
		try {
			driver.findElement(By.cssSelector(css));
		} catch (NoSuchElementException e) {

			return false;
		} catch (Exception e) {
			throw new Exception("Unexpected exception");
		}

		return true;
	}

	// VERIFYING IS ELEMENT PRESENT AND UNIQUE
	public static boolean isElementPresentAndUniqueUsingXpath(WebDriver driver, String xpath) throws Exception {

		List<WebElement> elements = driver.findElements(By.xpath(xpath));

		if (elements.size() == 1) {

			return true;
		} else if (elements.size() == 0) {

			return false;
		} else {

			throw new Exception("There are more than one elements found with this locator");
		}
	}

	// CLICKING ELEMENT USING JAVASCRIPT EXECUTOR
	public static void clickByJavascript(WebDriver driver, WebElement element) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	// ZOOM WINDOW USING JAVASCRIPT EXECUTOR
	public static void zoomWindow(WebDriver driver, double size) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.body.style.zoom =" + size);
		;
	}

	// HIGHLIGHTING DESIRED ELEMENT
	public static void highlightElement(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px groove red'", element);
	}

	// RETURNING BACK IN BROWSER
	public static void returnBackInBrowser(WebDriver driver) {

		driver.navigate().back();

	}

	// HOVERING THE ELEMENT
	public static void hoverElement(WebDriver driver, WebElement element) {

		Actions action = new Actions(driver);

		action.moveToElement(element);
		action.moveToElement(element, 1, 1);

		action.perform();

	}

	// FOCUSING ON ELEMENT
	public static void focusOnElement(WebDriver driver, WebElement element) {

		element.sendKeys("");

	}

	// OVERWRITING EXISTING TEXT INSIDE INPUT
	public static void overwriteCurrentInputValue(WebElement element, String newValue) {

		element.clear();
		element.sendKeys(newValue);

	}

	// SWITCHING TO IFRAME
	public static void switchToFrame(WebDriver driver, WebElement element) {

		driver.switchTo().frame(element);

	}

	// SETTING CHECKBOX STATE
	public static void setCheckboxState(WebElement checkbox, String expectedState) {

		// Variable expectedState can be "checked" or "unchecked" only
		boolean currentState = checkbox.isSelected();
		if (currentState == true && expectedState == "unchecked") {

			clickElement(checkbox);
		} else if (currentState == false && expectedState == "checked") {

			clickElement(checkbox);
		}
	}

	// VERIFYING IS TEXT PRESENT ON PAGE OR NOT
	public static void verifyingPresentedTextOnPage(WebDriver driver, String yourText) throws MyException {

		boolean isTheTextPresent = driver.getPageSource().contains(yourText);

		if (isTheTextPresent == false) {

			throw new MyException("No element located!!");
		}

	}

	// CHOOSING RANDOMLY ONE UK POSTCODE FROM THE LIST
	public static String pickRandomUKPostcode() {

		String[] postcodes = { "EX7 0PP", "CT15 7JR", "B35 6NR", "TQ6 9EG", "DT10 1DS", "LE7 7AD", "M9 8AX", "AB15 9EA",
				"AB15 9EA", "GL19 3LE", "GL7 9TE", "E14 8JR", "TF2 8LD", "MK45 2JJ", "YO7 4QJ" };
		int rnd = new Random().nextInt(postcodes.length);

		return postcodes[rnd];

	}

	// SELECT FROM SELECT-BOX BY INDEX
	public static void selectByIndex(WebElement element, int index) {

		Select element1 = new Select(element);
		element1.selectByIndex(index);

	}

	// SELECT FROM SELECT-BOX BY VALUE
	public static void selectByValue(WebElement element, String value) {

		Select element1 = new Select(element);
		element1.selectByValue(value);

	}

	// WAIT UNTIL ELEMENT IS CLICKABLE
	public static void waitUntilElementIsClickable(WebDriver driver, WebElement element) {

		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	// WAIT UNTIL ELEMENT IS VISIBLE
	public static void waitUntilElementIsVisible(WebDriver driver, WebElement element) {

		try

		{
			Wait<WebDriver> wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOf(element));
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	// WAIT UNTIL ELEMENT IS INVISIBLE
	public static void waitUntilElementIsInvisible(WebDriver driver, WebElement element) {

		try

		{
			Wait<WebDriver> wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.invisibilityOf(element));
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	// WAIT UNTIL TEXT IS PRESENTED IN ELEMENT
	public static void waitTextIsPresentedInElement(WebDriver driver, WebElement element, String text) {

		try

		{
			Wait<WebDriver> wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	// WAIT UNTIL IFRAME IS AVAILABLE
	public static void waitUntilIframeIsAvailable(WebDriver driver, WebElement iframe) {

		try

		{
			Wait<WebDriver> wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	// ADDING TO CART FIRST IN-STOCK STANDART PRODUCT(MOTHERCARE)
	public static void mc_addToCartFirstInstockStandartProduct(WebDriver driver) {

		String instockProduct = "div[class='b-search_result_items'] a[data-product-availabilitystatus='YES'][data-product-variation-attribute='']";
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElements(By.cssSelector(instockProduct));

		WebElement first = elements.get(0);
		clickByJavascript(driver, first);

	}

}
