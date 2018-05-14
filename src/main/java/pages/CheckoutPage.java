package pages;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import data.FakeData;
import webLibrary.Library;

public class CheckoutPage extends Library{
	
	public static WebDriver driver;
	
	public static ResourceBundle rb = ResourceBundle.getBundle("config");

	public CheckoutPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	public static void proccessingCheckoutUntilPaymentMethodAsGuest(WebDriver driver) {

		FakeData fakeData = new FakeData();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.checkoutAsGuestButton);
		waitUntilElementIsClickable(driver, checkoutPage.deliverToUKoption);
		clickElement(checkoutPage.deliverToUKoption);

		selectByIndex(checkoutPage.title, 2);
		setText(checkoutPage.firstname, fakeData.firstName);
		setText(checkoutPage.lastname, fakeData.lastName);
		setText(checkoutPage.email, generateRandomEmail(5));
		setText(checkoutPage.phone, fakeData.phoneNumber);

		clickElement(checkoutPage.enterAddressManually);
		setText(checkoutPage.addressLine1, fakeData.address1);
		setText(checkoutPage.town_city, fakeData.city);
		setText(checkoutPage.postalCode, pickRandomUKPostcode());
		clickByJavascript(driver, checkoutPage.deliverToThisAddress);

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver,checkoutPage.proceedToPayment);
		
		

	}
	
	public static void approveAuthentificationForOrder(WebDriver driver) {

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitUntilElementIsClickable(driver, checkoutPage.choiceAuth);
		scroolToThisElement(driver, checkoutPage.choiceAuth);
		clickByJavascript(driver, checkoutPage.choiceAuth);
		
		

	}

	@FindBy(css = "button[value='checkout as a guest']")
	public WebElement checkoutAsGuestButton;

	@FindBy(css = "a[id='uk']")
	public WebElement deliverToUKoption;

	@FindBy(css = "#cis")
	public WebElement clickAndCollectOption;

	@FindBy(css = "input[name$='storelocator_searchquery']")
	public WebElement town_postcodeInput;

	@FindBy(css = "button[value='Search'][class~='g-button-default']")
	public WebElement findByPostcodeButton;

	@FindBy(css = "#store-location-results button[class$='store-select']")
	public WebElement selectStoreButton;

	@FindBy(xpath = "//*[@id='international']/span[1]")
	public WebElement deliverInternational;

	@FindBy(css = "button[class$='js-select-address']")
	public WebElement internationalSelectFromSavedAddress;

	@FindBy(xpath = "//*[@id='channelislands']/div[2]")
	public WebElement channelIslandsOption;

	@FindBy(css = "button[class$='js-select-address']")
	public WebElement selectFromSavedForRegisteredUser;

	@FindBy(css = "button[class*='g-button-white']")
	public WebElement elc_selectFromSavedForRegistered;

	@FindBy(css = "select[id$='singleshipping_title']")
	public WebElement title;

	@FindBy(css = "input[id$='addressFields_firstName']")
	public WebElement firstname;

	@FindBy(css = "input[id$='addressFields_lastName']")
	public WebElement lastname;

	@FindBy(css = "input[id$='singleshipping_email']")
	public WebElement email;

	@FindBy(css = "input[id$='singleshipping_phoneMobile']")
	public WebElement phone;

	@FindBy(css = ".b-address_type.t-text-center a[href='#']")
	public WebElement enterAddressManually;

	@FindBy(css = "input[id$='shippingAddress_addressFields_address1']")
	public WebElement addressLine1;

	@FindBy(css = "input[id$='shippingAddress_addressFields_city']")
	public WebElement town_city;

	@FindBy(css = "input[id$='singleshipping_postalCode']")
	public WebElement postalCode;

	@FindBy(css = "button[class='g-button-additional js-shipping-validate']")
	public WebElement deliverToThisAddress;

	@FindBy(xpath = "//*[@id='standardDelivery']/div[2]")
	public WebElement standartDelivery;

	@FindBy(css = "button[name$='singleshipping_save']")
	public WebElement proceedToPayment;

	@FindBy(css = "label[for='is-CREDIT_CARD']")
	public WebElement creditCardOption;

	@FindBy(css = "label[for^='creditcardtile'] div[class=b-card_item]")
	public WebElement visaSavedCard;

	@FindBy(css = "input[id*='dwfrm_billing_paymentMethods_creditCard_cvn_']")
	public WebElement visaCVN;

	@FindBy(css = "#continue")
	public WebElement guestPlaceOrder;

	@FindBy(css = "label[for='is-PAYPAL']")
	public WebElement payPalOption;

	@FindBy(css = "#paypal-select-go")
	public WebElement proceedToPaypal;
	
	@FindBy(css = "[class='btn full ng-binding']")
	public WebElement logInToPayPal;

	@FindBy(css = "label[for='email'] + #email")
	public WebElement paypalLoginUsername;

	@FindBy(css = "#password")
	public WebElement paypalLoginPassword;

	@FindBy(css = "button[id='btnLogin']")
	public WebElement loginPaypalButton;
	
	@FindBy(css = "[data-test-id='shipToChangeLink']")
	public WebElement shipToChangeLink;
	
	@FindBy(css = "#makePreferred")
	public WebElement makePrefferedCheckbox;

	@FindBy(css = "#confirmButtonTop")
	public WebElement payNowByPaypal;

	@FindBy(css = "label[for='isUseSameAddress-yes']")
	public WebElement sameAddress;

	@FindBy(css = "label[for='isUseSameAddress-no']")
	public WebElement newBillingAddress;

	@FindBy(css = "#paymentIframe")
	public WebElement paymentIframe;

	@FindBy(css = "[name='injectedUl']")
	public WebElement payPalIframe;

	@FindBy(id = "capf1")
	public WebElement nameOnCard;

	@FindBy(id = "card_number")
	public WebElement cardNumber;

	@FindBy(css = "select[name='exp_year']")
	public WebElement expirationYear;

	@FindBy(id = "cv2_number")
	public WebElement CVV;

	@FindBy(css = "button[id='creditcard-select-go']:not([formnovalidate])")
	public WebElement visaPlaceOrder;

	@FindBy(css = "[value='Authenticated']")
	public WebElement choiceAuth;

	@FindBy(css = "iframe[name='threedsecure']")
	public WebElement iframe;

	@FindBy(xpath = "//*[@id='main']/div/h1")
	public WebElement confirmation;

}