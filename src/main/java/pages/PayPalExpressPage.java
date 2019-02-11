package pages;

import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webLibrary.Library;

public class PayPalExpressPage extends Library {

	public static WebDriver driver;

	public static ResourceBundle rb = ResourceBundle.getBundle("config");

	public PayPalExpressPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	public void selectClickAndCollectStore(WebDriver driver) {

		String store = "button[class$='store-select']";
		List<WebElement> storeList = driver.findElements(By.cssSelector(store));
		
		WebElement firstStore = storeList.get(0);
		clickByJavascript(driver, firstStore);

	}

	@FindBy(css = "div[class$='clearfix'] span[class*='click_collect']")
	public WebElement deliveryPopup_clickAndCollect;

	@FindBy(css = "div[class$='clearfix'] button[name$='paypalproceed']:last-of-type")
	public WebElement deliveryPopup_continueButton;

	@FindBy(css = "input[id$='searchquery']")
	public WebElement paypalExpress_searchInput;

	@FindBy(css = "button[name$='findbycoordinates']")
	public WebElement paypalExpress_findButton;
	
	@FindBy(css = "#continueToPaypalX")
	public WebElement paypalExpress_continueToPaypal;
}
