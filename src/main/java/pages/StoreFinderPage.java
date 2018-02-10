package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class StoreFinderPage {
	
	public StoreFinderPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	@FindBy(css = "input[id$='storelocator_searchquery']")
	public WebElement enterLocationOrPostcode;

	@FindBy(css = " button[name$='findbycoordinates'][class$='i-search']")
	public WebElement magnifier;

	@FindBy(css = "#mCSB_2")
	public WebElement listWithStores;

	@FindBy(css = "div[data-view='map']")
	public WebElement mapWithStores;

	@FindBy(css = "div[data-view='list']  h1")
	public WebElement storesWereFoundMessage;

	@FindBy(css ="input[id*='footer_newsletter_email_']")
	public WebElement inputSignup;
}
