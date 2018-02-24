package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RequestCataloguePage {

	public RequestCataloguePage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "input[id*='_i1_']")
	public WebElement firstCatalogue;

	@FindBy(css = "input[id*='_i0_']")
	public WebElement elc_firstCatalogue;

	@FindBy(css = "#dwfrm_cataloguerequest_customer_title")
	public WebElement catalogueTitleSelect;

	@FindBy(css = "#dwfrm_cataloguerequest_customer_firstName")
	public WebElement catalogueFirstName;

	@FindBy(css = "#dwfrm_cataloguerequest_customer_lastName")
	public WebElement catalogueLastName;

	@FindBy(css = "a[class='b-address_type']")
	public WebElement enterAddressmanuallyLink;

	@FindBy(css = "#dwfrm_cataloguerequest_address_phone")
	public WebElement phoneNumber;

	@FindBy(css = "#dwfrm_cataloguerequest_address_address1")
	public WebElement addressLine_1;

	@FindBy(css = "#dwfrm_cataloguerequest_address_city")
	public WebElement town_city;

	@FindBy(css = "#dwfrm_cataloguerequest_address_postal")
	public WebElement postalCode;

	@FindBy(css = "#dwfrm_cataloguerequest_address_country")
	public WebElement selectCountry;

	@FindBy(css = "button[name='dwfrm_cataloguerequest_sendRequest']")
	public WebElement buttonRequestCatalogue;

	@FindBy(css = "#primary > div > h1")
	public WebElement thankYouMessage;

}
