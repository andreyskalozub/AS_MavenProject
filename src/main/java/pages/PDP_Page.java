package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PDP_Page {

	public PDP_Page(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "a[title='Add this product to wishlist']")
	public WebElement addToMyWishlistButton;

	@FindBy(css = "a[title='White']")
	public WebElement bathWithWhiteColour;

	@FindBy(css = "a[title='Add this product to gift registry']")
	public WebElement addToMyGiftlistButton;

	@FindBy(css = "#Quantity")
	public WebElement quantitySelector;
}
