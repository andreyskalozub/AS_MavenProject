package mothercareCI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MA_BigBirthdayClubPage;
import pages.MyAccountPage;
import pages.PDP_Page;
import pages.PLP_Page;
import pages.RequestCataloguePage;
import pages.StoreFinderPage;
import pages.WishlistPage;
import webLibrary.Library;
import webLibrary.MyException;

public class ELC_RunnerTest extends Library {

	public static final Logger logger = LogManager.getLogger(ELC_RunnerTest.class.getName());

	public static WebDriver driver;


	@Rule
	public final TestName name = new TestName();
	

	@Before
	public void before()  {

		driver = new ChromeDriver();
		setDriverConfiguration(driver, 20);
		driver.get(data.elc_ci);

		HomePage homePage = new HomePage(driver);
		homePage.cookieAccept.click();

	}

	@After
	public void gettingScreenshotAndClosingDriver() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = "C:\\tmp";
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		try {
			FileUtils.copyFile(scrFile,
					new File(currentDir + "\\screenshots\\" + timeStamp + "-- " + name.getMethodName() + ".png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		driver.quit();

	}

	@Test
	public void elc_loginTest() throws InterruptedException, IOException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailSignIn.sendKeys(data.my_email_elc);
		loginPage.passwordSignIn.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();

		assertTrue("Actual text is" + loginPage.assertMyAccount.getAttribute("textContent") + "Expected text is "
				+ "My Account", loginPage.assertMyAccount.getAttribute("textContent").equals("My Account"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_emailSignUpTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailSignIn.sendKeys(data.my_email_elc);
		loginPage.passwordSignIn.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();
		setText(loginPage.inputSignup, generateRandomEmail(7));
		clickByJavascript(driver, loginPage.buttonSignup);

		assertTrue(
				"actual text is " + loginPage.signUpConfirmationPopup.getAttribute("textContent") + " expected text is "
						+ "Thank you for signing up to ELC emails",
				loginPage.signUpConfirmationPopup.getAttribute("textContent")
						.equals("Thank you for signing up to ELC emails"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_createAccountTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);

		loginPage.firstName.sendKeys(fakeData.firstName);
		loginPage.lastName.sendKeys(fakeData.lastName);
		loginPage.emailAddress.sendKeys(generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		loginPage.confirmEmail.sendKeys(copyEmail);
		loginPage.passwordRegistration.sendKeys(data.my_password);
		loginPage.confirmPasswordRegistration.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();
		assertEquals("thank you for registering with Early Learning Centre",
				loginPage.registrationConfirmation.getAttribute("textContent"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_guestCheckoutCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);

		waitUntilElementIsClickable(driver, plp_Page.cinderellaButton);
		clickByJavascript(driver, plp_Page.cinderellaButton);
		wait(1);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.checkoutAsGuestButton.click();
		checkoutPage.deliverToUKoption.click();
		selectByIndex(checkoutPage.title, 2);

		setText(checkoutPage.firstname, fakeData.firstName);
		setText(checkoutPage.lastname, fakeData.lastName);
		setText(checkoutPage.email, generateRandomEmail(7));
		setText(checkoutPage.phone, fakeData.phoneNumber);

		checkoutPage.enterAddressManually.click();
		setText(checkoutPage.addressLine1, fakeData.address1);
		setText(checkoutPage.town_city, fakeData.city);
		setText(checkoutPage.postalCode, pickRandomUKPostcode());

		actions.moveToElement(checkoutPage.deliverToThisAddress).click().perform();
		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		checkoutPage.proceedToPayment.click();
		checkoutPage.creditCardOption.click();

		waitUntilElementIsVisible(driver, checkoutPage.paymentIframe);
		switchToFrame(driver, checkoutPage.paymentIframe);
		actions.moveToElement(checkoutPage.nameOnCard, 1, 1).click().perform();
		setText(checkoutPage.nameOnCard, fakeData.firstName);
		setText(checkoutPage.cardNumber, data.creditCardNumber);
		selectByIndex(checkoutPage.expirationYear, 5);

		setText(checkoutPage.CVV, data.CVV);
		checkoutPage.guestPlaceOrder.click();

		driver.switchTo().defaultContent();
		driver.switchTo().frame(checkoutPage.iframe);
		clickByJavascript(driver, checkoutPage.choiceAuth);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_paypalGuestCheckoutTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);
		waitUntilElementIsClickable(driver, plp_Page.cinderellaButton);
		clickByJavascript(driver, plp_Page.cinderellaButton);

		wait(1);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.checkoutAsGuestButton.click();

		clickByJavascript(driver, checkoutPage.deliverToUKoption);
		selectByIndex(checkoutPage.title, 2);

		setText(checkoutPage.firstname, fakeData.firstName);
		setText(checkoutPage.lastname, fakeData.lastName);
		setText(checkoutPage.email, generateRandomEmail(7));
		setText(checkoutPage.phone, fakeData.phoneNumber);

		checkoutPage.enterAddressManually.click();
		setText(checkoutPage.addressLine1, fakeData.address1);
		setText(checkoutPage.town_city, fakeData.city);
		setText(checkoutPage.postalCode, pickRandomUKPostcode());

		scroolToThisElement(driver, checkoutPage.deliverToThisAddress);
		actions.moveToElement(checkoutPage.deliverToThisAddress).click().perform();
		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		checkoutPage.proceedToPayment.click();

		clickByJavascript(driver, checkoutPage.payPalOption);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		checkoutPage.proceedToPaypal.click();

		switchToFrame(driver, checkoutPage.payPalIframe);
		checkoutPage.paypalLoginUsername.clear();

		checkoutPage.paypalLoginUsername.sendKeys(data.paypal_login);
		setText(checkoutPage.paypalLoginPassword, data.paypal_password);
		checkoutPage.loginPaypalButton.click();

		driver.switchTo().defaultContent();
		waitUntilElementIsClickable(driver, checkoutPage.payNowByPaypal);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_requestCatalogueTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.requestCatalogue.click();

		RequestCataloguePage requestCataloguePage = new RequestCataloguePage(driver);
		requestCataloguePage.elc_firstCatalogue.click();
		selectByValue(requestCataloguePage.catalogueTitleSelect, "Mr");

		setText(requestCataloguePage.catalogueFirstName, fakeData.firstName);
		setText(requestCataloguePage.catalogueLastName, fakeData.lastName);
		requestCataloguePage.enterAddressmanuallyLink.click();

		requestCataloguePage.phoneNumber.sendKeys(fakeData.phoneNumber);
		setText(requestCataloguePage.addressLine_1, fakeData.address1);
		setText(requestCataloguePage.town_city, fakeData.city);
		setText(requestCataloguePage.postalCode, fakeData.postalCode);

		requestCataloguePage.selectCountry.click();
		selectByValue(requestCataloguePage.selectCountry, "CN");
		requestCataloguePage.buttonRequestCatalogue.click();

		assertTrue(
				"Actual text is " + requestCataloguePage.thankYouMessage.getAttribute("textContent")
						+ "Expected text is" + "thank You",
				requestCataloguePage.thankYouMessage.getAttribute("textContent").equals("thank you"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_addToWishlistFromPDPAndCheckoutByPaypalTest() throws InterruptedException, MyException {

		
		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();
		
		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);

		loginPage.firstName.sendKeys(fakeData.firstName);
		loginPage.lastName.sendKeys(fakeData.lastName);
		loginPage.emailAddress.sendKeys(generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		loginPage.confirmEmail.sendKeys(copyEmail);
		loginPage.passwordRegistration.sendKeys(data.my_password);
		loginPage.confirmPasswordRegistration.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);

		waitUntilElementIsClickable(driver, plp_Page.elc_productLink);
		clickByJavascript(driver, plp_Page.elc_productLink);

		PDP_Page pdp_Page = new PDP_Page(driver);
		pdp_Page.addToMyWishlistButton.click();

		verifyingPresentedTextOnPage(driver, "143479");
		WishlistPage wishlistPage = new WishlistPage(driver);
		clickElement(wishlistPage.addToBasketButton);
		
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);
		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);
		
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.clickAndCollectOption);
		setText(checkoutPage.town_postcodeInput, pickRandomUKPostcode());
		clickElement(checkoutPage.findByPostcodeButton);
		clickByJavascript(driver, checkoutPage.selectStoreButton);
		
		selectByIndex(checkoutPage.title, 2);
		setText(checkoutPage.firstname, fakeData.firstName);
		setText(checkoutPage.lastname, fakeData.lastName);
		
		setText(checkoutPage.email, generateRandomEmail(5));
		setText(checkoutPage.phone, fakeData.phoneNumber);
		checkoutPage.proceedToPayment.click();
		
		clickByJavascript(driver, checkoutPage.payPalOption);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);

		switchToFrame(driver, checkoutPage.payPalIframe);
		checkoutPage.paypalLoginUsername.clear();
		checkoutPage.paypalLoginUsername.sendKeys(data.paypal_login);
		Library.setText(checkoutPage.paypalLoginPassword, data.paypal_password);
		checkoutPage.loginPaypalButton.click();

		driver.switchTo().defaultContent();
		waitUntilElementIsClickable(driver, checkoutPage.payNowByPaypal);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);
		waitUntilElementIsVisible(driver, checkoutPage.confirmation);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");
		

	}

	@Test
	public void elc_payPalRegisteredCheckoutTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);

		waitUntilElementIsClickable(driver, plp_Page.cinderellaButton);
		clickByJavascript(driver, plp_Page.cinderellaButton);
		wait(1);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_elc);
		setText(loginPage.passwordSignIn, data.my_password);
		clickElement(loginPage.signInButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.deliverToUKoption.click();
		wait(1);

		clickByJavascript(driver, checkoutPage.elc_selectFromSavedForRegistered);
		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.proceedToPayment);
		checkoutPage.payPalOption.click();

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);

		switchToFrame(driver, checkoutPage.payPalIframe);
		checkoutPage.paypalLoginUsername.clear();
		checkoutPage.paypalLoginUsername.sendKeys(data.paypal_login);
		setText(checkoutPage.paypalLoginPassword, data.paypal_password);
		checkoutPage.loginPaypalButton.click();

		driver.switchTo().defaultContent();
		waitUntilElementIsClickable(driver, checkoutPage.payNowByPaypal);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_registeredCheckoutCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);

		waitUntilElementIsClickable(driver, plp_Page.cinderellaButton);
		clickByJavascript(driver, plp_Page.cinderellaButton);
		wait(1);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_elc);
		setText(loginPage.passwordSignIn, data.my_password);
		clickElement(loginPage.signInButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.deliverToUKoption.click();
		checkoutPage.elc_selectFromSavedForRegistered.click();
		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		;
		clickByJavascript(driver, checkoutPage.standartDelivery);

		clickByJavascript(driver, checkoutPage.proceedToPayment);
		checkoutPage.creditCardOption.click();

		waitUntilElementIsClickable(driver, checkoutPage.visaSavedCard);
		checkoutPage.visaSavedCard.click();
		setText(checkoutPage.visaCVN, data.CVV);
		clickByJavascript(driver, checkoutPage.visaPlaceOrder);

		switchToFrame(driver, checkoutPage.iframe);
		waitUntilElementIsClickable(driver, checkoutPage.choiceAuth);
		clickByJavascript(driver, checkoutPage.choiceAuth);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_errorWhileIncorrectCredentialsDuringSignInTest() throws Exception {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailSignIn.sendKeys(data.my_email_elc);
		loginPage.passwordSignIn.sendKeys(data.my_password + "wrong");

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();
		isElementPresentAndUnique(driver, "//*[@class='form-row f_error_message']");

		assertEquals("Sorry, this does not match our records. Check your spelling and try again.",
				loginPage.errorWhileSigninMessage.getAttribute("innerText"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_addToBigBirthdayClubTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);
		loginPage.firstName.sendKeys(fakeData.firstName);
		loginPage.lastName.sendKeys(fakeData.lastName);
		loginPage.emailAddress.sendKeys(generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		loginPage.confirmEmail.sendKeys(copyEmail);

		loginPage.passwordRegistration.sendKeys(data.my_password);
		loginPage.confirmPasswordRegistration.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();

		assertEquals("thank you for registering with Early Learning Centre",
				loginPage.registrationConfirmation.getAttribute("textContent"));

		homePage.elc_myAccountHeaderLink.click();
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.bigBirthdayClub.click();
		wait(2);

		MA_BigBirthdayClubPage ma_BigBirthdayClubPage = new MA_BigBirthdayClubPage(driver);
		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.girlbadge);
		actions.moveToElement(ma_BigBirthdayClubPage.girlbadge).click().perform();

		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.childName);

		String babyNameValue = fakeData.firstName;
		ma_BigBirthdayClubPage.childName.sendKeys(babyNameValue);

		actions.moveToElement(ma_BigBirthdayClubPage.datePicker).click().perform();
		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.currentdate);
		clickByJavascript(driver, ma_BigBirthdayClubPage.currentdate);

		selectByIndex(ma_BigBirthdayClubPage.relationshipSelect, 2);
		ma_BigBirthdayClubPage.addNewFamilyMemberButton.click();
		ma_BigBirthdayClubPage.confirmationPopup.isDisplayed();
		clickByJavascript(driver, ma_BigBirthdayClubPage.backToClubButtonInPopup);

		waitUntilElementIsVisible(driver, ma_BigBirthdayClubPage.babyNameValueDisplayed);
		assertEquals(babyNameValue, ma_BigBirthdayClubPage.babyNameValueDisplayed.getAttribute("textContent"));

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_addNewAddressForJustRegisteredTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);

		loginPage.firstName.sendKeys(fakeData.firstName);
		loginPage.lastName.sendKeys(fakeData.lastName);
		loginPage.emailAddress.sendKeys(generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		loginPage.confirmEmail.sendKeys(copyEmail);

		loginPage.passwordRegistration.sendKeys(data.my_password);
		loginPage.confirmPasswordRegistration.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();

		assertEquals("thank you for registering with Early Learning Centre",
				loginPage.registrationConfirmation.getAttribute("textContent"));

		homePage.elc_myAccountHeaderLink.click();

		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.addressBook.click();
		myAccountPage.createNewAddress.click();
		clickByJavascript(driver, myAccountPage.newUKaddress);

		setText(myAccountPage.phoneNumber, fakeData.phoneNumber);
		scroolToThisElement(driver, myAccountPage.enterAddressManually);
		clickByJavascript(driver, myAccountPage.enterAddressManually);

		setText(myAccountPage.addressNickname, fakeData.nickname);
		setText(myAccountPage.address_1, fakeData.address1);
		setText(myAccountPage.city, fakeData.city);
		setText(myAccountPage.county, fakeData.county);
		setText(myAccountPage.postcode, pickRandomUKPostcode());

		setCheckboxState(myAccountPage.saveAsPreferredCheckbox, "unchecked");
		clickByJavascript(driver, myAccountPage.addAddressButton);

		assertTrue(myAccountPage.preferredAddressInAddressBook.isDisplayed());

		logger.info(name.getMethodName() + " -Nice");

	}

	@Test
	public void elc_findStoresUsingPostcodeViaStoreLocator() {

		HomePage homePage = new HomePage(driver);
		clickByJavascript(driver, homePage.elc_storeFinder);
		String user_postcode = pickRandomUKPostcode();

		StoreFinderPage storeFinderPage = new StoreFinderPage(driver);
		setText(storeFinderPage.enterLocationOrPostcode, user_postcode);
		clickElement(storeFinderPage.magnifier);

		waitUntilElementIsClickable(driver, storeFinderPage.mapWithStores);
		assertTrue(storeFinderPage.mapWithStores.isDisplayed());

		assertTrue(storeFinderPage.storesWereFoundMessage.isDisplayed());

		logger.info(name.getMethodName() + " -Nice");
	}

}
