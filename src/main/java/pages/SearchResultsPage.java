package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage {
	
	public SearchResultsPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	@FindBy(css = "h1[class='t-page-title m-search-title']")
	public WebElement searchResultsTitle;

}
