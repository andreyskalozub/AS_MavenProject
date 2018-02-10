package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SitemapPage {
	
	public SitemapPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	@FindBy(css = "a[title='bathing & changing offers']")
	public WebElement bathingAndChangingOffers;

	
	
}
