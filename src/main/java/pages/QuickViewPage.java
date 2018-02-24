package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QuickViewPage {

	public QuickViewPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "[data-input-stepper-increase]")
	public WebElement plus;

	@FindBy(css = "[title='add to basket']")
	public WebElement addToBasketButton;

}
