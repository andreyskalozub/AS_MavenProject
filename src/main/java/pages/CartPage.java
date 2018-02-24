package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

	public CartPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[@id='checkout-form']/fieldset/button")
	public WebElement topCheckoutButton;

	@FindBy(css = ".b-cart_items-content .b-product_title")
	public WebElement itemsInCartSection;

	@FindBy(css = "input[id$='quantity']")
	public WebElement quantity;

	@FindBy(css = "img[title='Baby K Knitted Blanket']+button[value='Remove']")
	public WebElement mc_removeLink;

	@FindBy(css = "#delete-confirm")
	public WebElement confirmDeletionButtonInPopup;

	@FindBy(css = "h1[class='t-page-title']")
	public WebElement yourbasketIsEmpty;

}
