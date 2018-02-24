package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {

	public MyAccountPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "div[class$='col-tablet-6'] a[href*='BirthdayClub-Show'][class$='account_navigation']")
	public WebElement bigBirthdayClub;

	@FindBy(css = ":not(a)+a[href$='addresses']")
	public WebElement addressBook;

	@FindBy(css = "a[title='Create New Address for this account']")
	public WebElement createNewAddress;

	@FindBy(css = "a[data-type='uk_address']")
	public WebElement newUKaddress;

	@FindBy(css = "input[type='tel']")
	public WebElement phoneNumber;

	@FindBy(css = "div[class$='allow-manually'] a:first-child")
	public WebElement enterAddressManually;

	@FindBy(css = "input[name$='address_nickname']")
	public WebElement addressNickname;

	@FindBy(css = "input[name$='address1']")
	public WebElement address_1;

	@FindBy(css = "input[name$='city']")
	public WebElement city;

	@FindBy(css = "input[name$='province']")
	public WebElement county;

	@FindBy(css = "input[name$='postal']")
	public WebElement postcode;

	@FindBy(css = "label[for$='ispreferred']")
	public WebElement saveAsPreferredCheckbox;

	@FindBy(css = "button[name$='address_create']")
	public WebElement addAddressButton;

	@FindBy(css = "div[class$='address_item'] h3[class$='m-default']")
	public WebElement preferredAddressInAddressBook;

}
