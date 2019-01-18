package mothercareCI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
import pages.PayPalExpressPage;
import pages.RequestCataloguePage;
import pages.SearchResultsPage;
import pages.StoreFinderPage;
import pages.WishlistPage;
import webLibrary.Library;
import webLibrary.MyException;

public class ELC_RunnerTest extends Library {

	public static final Logger logger = LogManager.getLogger(ELC_RunnerTest.class.getName());

	public static WebDriver driver;

	private static ResourceBundle rb = ResourceBundle.getBundle("config");

	@Rule
	public final TestName name = new TestName();

	@Before
	public void before() {

		driver = new ChromeDriver();
		setDriverConfiguration(driver, 15);
		driver.get(rb.getString("elc_ci"));

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.cookieAccept);

	}

	@After
	public void gettingScreenshotAndClosingDriver() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		try {
			FileUtils.copyFile(scrFile, new File("screenshots/" + timeStamp + "-- " + name.getMethodName() + ".png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		driver.quit();

	}

	@Test
	public void elc_loginWithValidCredentialsTest() throws InterruptedException, IOException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.loginAction_ELC(driver);

		LoginPage loginPage = new LoginPage(driver);
		assertTrue("true", loginPage.assertMyAccount.isDisplayed());

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_loginWithIncorrectCredentialsTest() throws Exception {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, rb.getString("my_email_elc"));
		setText(loginPage.passwordSignIn, rb.getString("my_password") + "wrong");

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();
		isElementPresentAndUniqueUsingXpath(driver, "//*[@class='form-row f_error_message']");

		assertEquals("Sorry, this does not match our records. Check your spelling and try again.",
				loginPage.errorWhileSigninMessage.getAttribute("innerText"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_signupToEmailSubscriptionTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.loginAction_ELC(driver);

		LoginPage.subscribeToEmails(driver);

		LoginPage loginPage = new LoginPage(driver);

		assertTrue(
				"actual text is " + loginPage.signUpConfirmationPopup.getAttribute("textContent") + " expected text is "
						+ "Thank you for signing up to ELC emails",
				loginPage.signUpConfirmationPopup.getAttribute("textContent")
						.equals("Thank you for signing up to ELC emails"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_creatingNewAccountTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.creatingNewAccount(driver);

		LoginPage loginPage = new LoginPage(driver);
		assertTrue(loginPage.assertMyAccount.isDisplayed());

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_guestCheckoutByCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page.elc_AddProductToCart(driver);

		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickElement(homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		CheckoutPage.proccessingCheckoutUntilPaymentMethodAsGuest(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.creditCardOption);

		waitUntilElementIsClickable(driver, checkoutPage.paymentIframe);
		switchToFrame(driver, checkoutPage.paymentIframe);
		actions.moveToElement(checkoutPage.nameOnCard, 1, 1).click().perform();
		setText(checkoutPage.nameOnCard, fakeData.firstName);
		setText(checkoutPage.cardNumber, rb.getString("creditCardNumber"));
		selectByIndex(checkoutPage.expirationYear, 5);

		setText(checkoutPage.CVV, rb.getString("CVV"));
		clickElement(checkoutPage.guestPlaceOrder);

		driver.switchTo().defaultContent();
		switchToFrame(driver, checkoutPage.iframe);
		CheckoutPage.approveAuthentificationForOrder(driver);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_guestCheckoutByPaypalTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page.elc_AddProductToCart(driver);

		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickElement(homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		CheckoutPage.proccessingCheckoutUntilPaymentMethodAsGuest(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickByJavascript(driver, checkoutPage.payPalOption);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);
		
		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, rb.getString("paypal_login"));
		setText(checkoutPage.paypalLoginPassword, rb.getString("paypal_password"));
		clickElement(checkoutPage.loginPaypalButton);

		waitUntilElementIsClickable(driver, checkoutPage.paypalContinueButton);
		clickByJavascript(driver, checkoutPage.paypalContinueButton);
		waitUntilElementIsClickable(driver, checkoutPage.payNowByPaypal);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_requestingCatalogueTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.requestCatalogue);

		RequestCataloguePage requestCataloguePage = new RequestCataloguePage(driver);
		clickElement(requestCataloguePage.elc_firstCatalogue);
		selectByValue(requestCataloguePage.catalogueTitleSelect, "Mr");

		setText(requestCataloguePage.catalogueFirstName, fakeData.firstName);
		setText(requestCataloguePage.catalogueLastName, fakeData.lastName);
		clickElement(requestCataloguePage.enterAddressmanuallyLink);

		setText(requestCataloguePage.phoneNumber, fakeData.phoneNumber);
		setText(requestCataloguePage.addressLine_1, fakeData.address1);
		setText(requestCataloguePage.town_city, fakeData.city);
		setText(requestCataloguePage.postalCode, fakeData.postalCode);

		clickElement(requestCataloguePage.selectCountry);
		selectByValue(requestCataloguePage.selectCountry, "CN");
		clickElement(requestCataloguePage.buttonRequestCatalogue);

		assertTrue(
				"Actual text is " + requestCataloguePage.thankYouMessage.getAttribute("textContent")
						+ "Expected text is" + "thank You",
				requestCataloguePage.thankYouMessage.getAttribute("textContent").equals("thank you"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_addingProductToWishlistFromPDPAndCheckoutByPaypalTest() throws InterruptedException, MyException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.creatingNewAccount(driver);

		Actions actions = new Actions(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);
		waitUntilElementIsClickable(driver, plp_Page.elc_productLink);
		clickByJavascript(driver, plp_Page.elc_productLink);

		PDP_Page pdp_Page = new PDP_Page(driver);
		clickElement(pdp_Page.addToMyWishlistButton);
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

		waitUntilElementIsClickable(driver, checkoutPage.findByPostcodeButton);
		clickByJavascript(driver, checkoutPage.findByPostcodeButton);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickByJavascript(driver, checkoutPage.selectStoreButton);

		waitUntilElementIsClickable(driver, checkoutPage.title);
		selectByIndex(checkoutPage.title, 2);
		
		overwriteCurrentInputValue(checkoutPage.firstname, fakeData.firstName);
		overwriteCurrentInputValue(checkoutPage.lastname, fakeData.lastName);
		overwriteCurrentInputValue(checkoutPage.email, generateRandomEmail(5));
		
		setText(checkoutPage.phone, fakeData.phoneNumber);
		checkoutPage.proceedToPayment.click();

		clickByJavascript(driver, checkoutPage.payPalOption);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);

		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, rb.getString("paypal_login"));
		setText(checkoutPage.paypalLoginPassword, rb.getString("paypal_password"));
		clickElement(checkoutPage.loginPaypalButton);

		waitUntilElementIsClickable(driver, checkoutPage.paypalContinueButton);
		clickByJavascript(driver, checkoutPage.paypalContinueButton);
		waitUntilElementIsClickable(driver, checkoutPage.payNowByPaypal);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_registeredUserCheckoutByPaypalTest() throws Exception {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page.elc_AddProductToCart(driver);

		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		LoginPage.logInAsRegistereduser_ELC(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitUntilElementIsClickable(driver, checkoutPage.deliverToUKoption);
		clickByJavascript(driver, checkoutPage.deliverToUKoption);
		waitUntilElementIsClickable(driver, checkoutPage.elc_selectFromSavedForRegistered);
		clickByJavascript(driver, checkoutPage.elc_selectFromSavedForRegistered);

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPayment);
		clickByJavascript(driver, checkoutPage.proceedToPayment);
		clickElement(checkoutPage.payPalOption);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);

		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, rb.getString("paypal_login"));
		setText(checkoutPage.paypalLoginPassword, rb.getString("paypal_password"));
		clickElement(checkoutPage.loginPaypalButton);

		waitUntilElementIsClickable(driver, checkoutPage.paypalContinueButton);
		clickByJavascript(driver, checkoutPage.paypalContinueButton);
		waitUntilElementIsClickable(driver, checkoutPage.payNowByPaypal);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_registeredUserCheckoutByCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page.elc_AddProductToCart(driver);

		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		LoginPage.logInAsRegistereduser_ELC(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.deliverToUKoption);
		waitUntilElementIsVisible(driver, checkoutPage.elc_selectFromSavedForRegistered);

		clickByJavascript(driver, checkoutPage.elc_selectFromSavedForRegistered);
		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);

		clickByJavascript(driver, checkoutPage.proceedToPayment);
		clickElement(checkoutPage.creditCardOption);
		waitUntilElementIsClickable(driver, checkoutPage.visaSavedCard);

		clickByJavascript(driver, checkoutPage.visaSavedCard);
		setText(checkoutPage.visaCVN, rb.getString("CVV"));
		clickByJavascript(driver, checkoutPage.visaPlaceOrder);

		waitUntilElementIsClickable(driver, checkoutPage.iframe);
		switchToFrame(driver, checkoutPage.iframe);
		CheckoutPage.approveAuthentificationForOrder(driver);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_addingChildToBigBirthdayClubTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.creatingNewAccount(driver);

		LoginPage loginPage = new LoginPage(driver);
		assertTrue(loginPage.assertMyAccount.isDisplayed());

		clickElement(homePage.elc_myAccountHeaderLink);

		MyAccountPage myAccountPage = new MyAccountPage(driver);
		waitUntilElementIsClickable(driver, myAccountPage.bigBirthdayClub);
		clickByJavascript(driver, myAccountPage.bigBirthdayClub);

		MA_BigBirthdayClubPage ma_BigBirthdayClubPage = new MA_BigBirthdayClubPage(driver);
		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.BBB_image);
		scrollTillBottom(driver);
		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.girlbadge);

		Actions actions = new Actions(driver);
		actions.moveToElement(ma_BigBirthdayClubPage.girlbadge, 1, 1).click().perform();
		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.childName);

		String babyNameValue = fakeData.firstName;
		setText(ma_BigBirthdayClubPage.childName, babyNameValue);
		actions.moveToElement(ma_BigBirthdayClubPage.datePicker).click().perform();
		waitUntilElementIsClickable(driver, ma_BigBirthdayClubPage.currentdate);
		clickByJavascript(driver, ma_BigBirthdayClubPage.currentdate);

		selectByIndex(ma_BigBirthdayClubPage.relationshipSelect, 2);
		clickElement(ma_BigBirthdayClubPage.addNewFamilyMemberButton);
		waitUntilElementIsVisible(driver, ma_BigBirthdayClubPage.confirmationPopup);
		clickByJavascript(driver, ma_BigBirthdayClubPage.backToClubButtonInPopup);

		waitUntilElementIsVisible(driver, ma_BigBirthdayClubPage.babyNameValueDisplayed);
		assertEquals(babyNameValue, ma_BigBirthdayClubPage.babyNameValueDisplayed.getAttribute("textContent"));

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_addingNewAddressForJustRegisteredUserTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.creatingNewAccount(driver);

		LoginPage loginPage = new LoginPage(driver);
		assertTrue(loginPage.assertMyAccount.isDisplayed());

		clickElement(homePage.elc_myAccountHeaderLink);

		MyAccountPage myAccountPage = new MyAccountPage(driver);
		clickElement(myAccountPage.addressBook);
		clickElement(myAccountPage.createNewAddress);
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

		logger.info(name.getMethodName() + " -Passed");

	}

	@Test
	public void elc_findingStoresUsingPostcodeViaStoreLocator() {

		HomePage homePage = new HomePage(driver);
		clickByJavascript(driver, homePage.elc_storeFinder);
		String user_postcode = pickRandomUKPostcode();

		StoreFinderPage storeFinderPage = new StoreFinderPage(driver);
		setText(storeFinderPage.enterLocationOrPostcode, user_postcode);
		clickElement(storeFinderPage.magnifier);

		waitUntilElementIsClickable(driver, storeFinderPage.mapWithStores);
		assertTrue(storeFinderPage.mapWithStores.isDisplayed());

		assertTrue(storeFinderPage.storesWereFoundMessage.isDisplayed());

		logger.info(name.getMethodName() + " -Passed");
	}

	
	@Test
	public void elc_searchByKeyword() {

		String searchQuery = "horse";
		HomePage homePage = new HomePage(driver);
		setText(homePage.searchInput, searchQuery);
		clickElement(homePage.searchButton);
		
		SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
		assertTrue("Actual text is " + searchResultsPage.searchResultsTitle.getAttribute("innerText"), searchResultsPage.searchResultsTitle.getAttribute("innerText").contains(searchQuery) && searchResultsPage.searchResultsTitle.getAttribute("innerText").contains("returned"));
		
		logger.info(name.getMethodName() + " -Passed");
	}

	@Test
	public void elc_guestCheckoutByPayPalExpress() {
		
		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.elc_learningAndBooks).click().perform();
		actions.click().perform();

		PLP_Page.elc_AddProductToCart(driver);

		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickElement(homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickByJavascript(driver,cartPage.payPalExpressButton);
		
		PayPalExpressPage payPalExpressPage = new PayPalExpressPage(driver);
		clickElement(payPalExpressPage.deliveryPopup_clickAndCollect);
		clickElement(payPalExpressPage.deliveryPopup_continueButton);
		setText(payPalExpressPage.paypalExpress_searchInput, pickRandomUKPostcode());
		clickElement(payPalExpressPage.paypalExpress_findButton);
		
		payPalExpressPage.selectClickAndCollectStore(driver);
		clickByJavascript(driver, payPalExpressPage.paypalExpress_continueToPaypal);
		
		String currentURL = driver.getCurrentUrl();
		String paypalURL = "www.sandbox.paypal.com";
		while(!currentURL.contains(paypalURL)) {
			wait(1);
			driver.getCurrentUrl();
			break;
		}
		
		assertTrue(
				"actual URL is " + driver.getCurrentUrl() + " expected URL contains paypal.com ",
						
				driver.getCurrentUrl().contains("www.sandbox.paypal.com"));

		logger.info(name.getMethodName() + " -Passed");
		
	}
}
