package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishlistPage {

	public WishlistPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[@id='primary']/h1")
	public WebElement messageMyWishlist; 

	@FindBy(css = "div[class='b-product_code'] :last-child")
	public WebElement itemInWishlist_IDConfirmation;

	@FindBy(css = "[title='add to basket']")
	public WebElement addToBasketButton;

}
