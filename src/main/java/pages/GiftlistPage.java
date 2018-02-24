package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GiftlistPage {

	public GiftlistPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "#dwfrm_giftregistry_event_type")
	public WebElement eventTypeSelect;

	@FindBy(css = "#dwfrm_giftregistry_event_name")
	public WebElement eventName;

	@FindBy(css = "#dwfrm_giftregistry_event_date")
	public WebElement eventDatePicker;

	@FindBy(css = "td[class*='today']")
	public WebElement todayDate;

	@FindBy(css = "#dwfrm_giftregistry_event_town")
	public WebElement eventCity;

	@FindBy(css = "button[name*='event_setParticipants']")
	public WebElement continueButton;

	@FindBy(css = "span[class='i-plus']")
	public WebElement createNewAddress;

	@FindBy(css = "a[data-type='uk_address']")
	public WebElement newUKaddress;

	@FindBy(css = "input[id*='phone']:not([disabled=''])")
	public WebElement phone;

	@FindBy(css = "input[id*='firstname']:not([disabled=''])")
	public WebElement firstName;

	@FindBy(css = "input[id*='nickname']:not([disabled=''])")
	public WebElement nickname;

	@FindBy(css = "input[id*='address1']:not([disabled=''])")
	public WebElement addressLine1;

	@FindBy(css = "input[id*='province']:not([disabled=''])")
	public WebElement county;

	@FindBy(css = "input[id*='city']:not([disabled=''])")
	public WebElement city;

	@FindBy(css = "input[id*='postal']:not([disabled=''])")
	public WebElement postcode;

	@FindBy(css = "button[name*='setBeforeAfterAddresses']:not([class*='address_mobile'])")
	public WebElement continueOnAddressesButton;

	@FindBy(css = "button[name*='confirm']")
	public WebElement confirmButton;

	@FindBy(css = "li[class~='default'] a")
	public WebElement defaultAddress;

	@FindBy(css = "div[class='b-product_code'] :nth-child(2)")
	public WebElement assertionElementID;

	@FindBy(css = "span[class='clear-swatchcolor']")
	public WebElement assertionElementColour;

}
