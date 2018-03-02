package mothercareCI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import pages.CartPage;
import pages.CheckoutPage;
import pages.GiftlistPage;
import pages.HomePage;
import pages.JoinMyMothercarePage;
import pages.LoginPage;
import pages.PDP_Page;
import pages.PLP_Page;
import pages.QuickViewPage;
import pages.RequestCataloguePage;
import pages.SitemapPage;
import pages.WishlistPage;
import webLibrary.Library;

public class MC_RunnerTest extends Library {

	public static final Logger logger = LogManager.getLogger(MC_RunnerTest.class.getName());

	public static WebDriver driver;

	@Rule
	public final TestName name = new TestName();

	@Before
	public void before() {

		driver = new ChromeDriver();
		setDriverConfiguration(driver, 15);
		driver.get(data.mc_ci);

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.cookieAccept);
		
		

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
	public void mc_loginWithValidCredentialsTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();

		assertTrue(loginPage.assertMyAccount.isDisplayed());

		logger.info(name.getMethodName() + " -Nice");
	}

	@Test
	public void mc_SignUpToEmailSubscriptionTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();
		setText(loginPage.inputSignup, generateRandomEmail(7));
		clickByJavascript(driver, loginPage.buttonSignup);

		assertTrue(
				"actual text is " + loginPage.signUpConfirmationPopup.getAttribute("textContent") + " expected text is "
						+ "Thank you for signing up to mothercare emails",
				loginPage.signUpConfirmationPopup.getAttribute("textContent")
						.equals("Thank you for signing up to mothercare emails"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_creatingNewAccountTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);

		setText(loginPage.firstName, fakeData.firstName);
		setText(loginPage.lastName, fakeData.lastName);
		setText(loginPage.emailAddress, generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		setText(loginPage.confirmEmail, copyEmail);

		setText(loginPage.passwordRegistration, data.my_password);
		setText(loginPage.confirmPasswordRegistration, data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();

		assertTrue(
				"Actual text is" + loginPage.registrationConfirmation.getAttribute("textContent") + "Expected text is "
						+ " thank you for registering with mothercare",
				loginPage.registrationConfirmation.getAttribute("textContent")
						.equals("thank you for registering with mothercare"));

		logger.info(name.getMethodName() + "-Nice!");
	}

	@Test
	public void mc_guestCheckoutByCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		mc_addToCartFirstInstockStandartProduct(driver);
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		actions.moveToElement(cartPage.topCheckoutButton, 1, 1).click().perform();

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
		clickElement(checkoutPage.proceedToPayment);
		waitUntilElementIsClickable(driver, checkoutPage.creditCardOption);
		clickElement(checkoutPage.creditCardOption);

		waitUntilElementIsClickable(driver, checkoutPage.paymentIframe);
		switchToFrame(driver, checkoutPage.paymentIframe);
		actions.moveToElement(checkoutPage.nameOnCard, 1, 1).click().perform();
		setText(checkoutPage.nameOnCard, fakeData.firstName);
		setText(checkoutPage.cardNumber, data.creditCardNumber);

		selectByIndex(checkoutPage.expirationYear, 5);
		setText(checkoutPage.CVV, data.CVV);
		clickElement(checkoutPage.guestPlaceOrder);

		driver.switchTo().defaultContent();
		switchToFrame(driver, checkoutPage.iframe);

		waitUntilElementIsClickable(driver, checkoutPage.choiceAuth);
		clickElement(checkoutPage.choiceAuth);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_guest2CheckoutByCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		
		clickByJavascript(driver, plp_Page.knittedBlanketButton);
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

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
		actions.moveToElement(checkoutPage.deliverToThisAddress).click().perform();

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		clickElement(checkoutPage.proceedToPayment);
		clickElement(checkoutPage.creditCardOption);
		waitUntilElementIsClickable(driver, checkoutPage.paymentIframe);

		switchToFrame(driver, checkoutPage.paymentIframe);
		actions.moveToElement(checkoutPage.nameOnCard, 1, 1).click().perform();
		setText(checkoutPage.nameOnCard, fakeData.firstName);
		setText(checkoutPage.cardNumber, data.creditCardNumber);

		selectByIndex(checkoutPage.expirationYear, 5);
		setText(checkoutPage.CVV, data.CVV);
		clickElement(checkoutPage.guestPlaceOrder);

		driver.switchTo().defaultContent();
		switchToFrame(driver, checkoutPage.iframe);
		waitUntilElementIsClickable(driver, checkoutPage.choiceAuth);
		clickElement(checkoutPage.choiceAuth);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		assertEquals("Thank you for your order", checkoutPage.confirmation.getAttribute("textContent"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_guestCheckoutByPaypalTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		
		mc_addToCartFirstInstockStandartProduct(driver);
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.checkoutAsGuestButton);
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
		actions.moveToElement(checkoutPage.deliverToThisAddress).click().perform();

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		clickElement(checkoutPage.proceedToPayment);

		clickByJavascript(driver, checkoutPage.payPalOption);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);
		waitUntilElementIsClickable(driver, checkoutPage.payPalIframe);

		switchToFrame(driver, checkoutPage.payPalIframe);
		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, data.paypal_login);
		setText(checkoutPage.paypalLoginPassword, data.paypal_password);
		clickElement(checkoutPage.loginPaypalButton);

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
	public void mc_requestingCatalogueTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.requestCatalogue);

		RequestCataloguePage requestCataloguePage = new RequestCataloguePage(driver);
		clickElement(requestCataloguePage.firstCatalogue);

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

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_addingProductToWishlistFromPDPTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.pushchairsCategory).click().perform();
		actions.click().perform();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.pushchairsOffers);
		waitUntilElementIsVisible(driver, plp_Page.gridFilter);

		selectByIndex(plp_Page.gridFilter, 4);
		waitUntilElementIsClickable(driver, plp_Page.productLink2);
		scroolToThisElement(driver, plp_Page.productLink2);
		clickByJavascript(driver, plp_Page.productLink2);

		PDP_Page pdp_Page = new PDP_Page(driver);
		clickElement(pdp_Page.addToMyWishlistButton);

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);
		actions.moveToElement(loginPage.signInButton).click().perform();

		WishlistPage wishlistPage = new WishlistPage(driver);
		assertEquals("265738", wishlistPage.itemInWishlist_IDConfirmation.getAttribute("textContent"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_registeredUserCheckoutByPaypalTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		
		mc_addToCartFirstInstockStandartProduct(driver);
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);
		clickElement(loginPage.signInButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.deliverInternational);
		waitUntilElementIsClickable(driver, checkoutPage.internationalSelectFromSavedAddress);
		clickByJavascript(driver, checkoutPage.internationalSelectFromSavedAddress);

		waitUntilElementIsClickable(driver, checkoutPage.channelIslandsOption);
		clickByJavascript(driver, checkoutPage.channelIslandsOption);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPayment);
		clickElement(checkoutPage.proceedToPayment);
		clickByJavascript(driver, checkoutPage.payPalOption);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);
		waitUntilElementIsClickable(driver, checkoutPage.payPalIframe);
		switchToFrame(driver, checkoutPage.payPalIframe);

		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, data.paypal_login);
		setText(checkoutPage.paypalLoginPassword, data.paypal_password);
		clickElement(checkoutPage.loginPaypalButton);

		driver.switchTo().defaultContent();
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_registeredUserCheckoutBySavedCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		
		mc_addToCartFirstInstockStandartProduct(driver);
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		clickElement(cartPage.topCheckoutButton);

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);
		clickElement(loginPage.signInButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		actions.moveToElement(checkoutPage.deliverToUKoption, 1, 1).click().perform();
		waitUntilElementIsClickable(driver, checkoutPage.selectFromSavedForRegisteredUser);
		clickByJavascript(driver, checkoutPage.selectFromSavedForRegisteredUser);

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPayment);
		clickElement(checkoutPage.proceedToPayment);

		clickElement(checkoutPage.creditCardOption);
		waitUntilElementIsClickable(driver, checkoutPage.visaSavedCard);
		clickByJavascript(driver, checkoutPage.visaSavedCard);
		setText(checkoutPage.visaCVN, data.CVV);
		waitUntilElementIsClickable(driver, checkoutPage.visaPlaceOrder);
		clickByJavascript(driver, checkoutPage.visaPlaceOrder);

		waitUntilElementIsClickable(driver, checkoutPage.iframe);
		switchToFrame(driver, checkoutPage.iframe);
		clickElement(checkoutPage.choiceAuth);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_contactUsLinkOpeningInNewTabTest() throws InterruptedException {

		scrollTillBottom(driver);

		HomePage homePage = new HomePage(driver);
		waitUntilElementIsVisible(driver, homePage.contactUs);
		clickElement(homePage.contactUs);
		switchToNewWindow(driver);

		String newUrl = driver.getCurrentUrl();

		assertEquals(data.contactUs_url, newUrl);

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_addingVariationProductToGiftlistTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);

		setText(loginPage.firstName, fakeData.firstName);
		setText(loginPage.lastName, fakeData.lastName);
		setText(loginPage.emailAddress, generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		setText(loginPage.confirmEmail, copyEmail);

		setText(loginPage.passwordRegistration, data.my_password);
		setText(loginPage.confirmPasswordRegistration, data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();
		scrollTillBottom(driver);
		clickByJavascript(driver, homePage.sitemap);

		SitemapPage sitemapPage = new SitemapPage(driver);
		clickElement(sitemapPage.bathingAndChangingOffers);

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.bathingAndChanging);
		clickByJavascript(driver, plp_Page.babyBathsAndAccessories);

		waitUntilElementIsVisible(driver, plp_Page.baths);
		clickByJavascript(driver, plp_Page.baths);
		clickByJavascript(driver, plp_Page.listFilterOption);
		clickByJavascript(driver, plp_Page.bathWithVariationButton);

		PDP_Page pdp_Page = new PDP_Page(driver);
		clickElement(pdp_Page.bathWithWhiteColour);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickElement(pdp_Page.addToMyGiftlistButton);

		GiftlistPage giftlistPage = new GiftlistPage(driver);
		selectByIndex(giftlistPage.eventTypeSelect, 1);
		setText(giftlistPage.eventName, fakeData.fullName);

		clickElement(giftlistPage.eventDatePicker);
		clickElement(giftlistPage.todayDate);

		setText(giftlistPage.eventCity, fakeData.city);
		clickElement(giftlistPage.continueButton);
		clickElement(giftlistPage.createNewAddress);

		waitUntilElementIsVisible(driver, giftlistPage.newUKaddress);
		clickByJavascript(driver, giftlistPage.newUKaddress);

		waitUntilElementIsVisible(driver, giftlistPage.phone);
		focusOnElement(driver, giftlistPage.phone);
		setText(giftlistPage.phone, fakeData.phoneNumber);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.enterAddressManually);
		setText(giftlistPage.nickname, fakeData.firstName);
		setText(giftlistPage.addressLine1, fakeData.streetAddress);
		setText(giftlistPage.city, fakeData.city);
		setText(giftlistPage.county, fakeData.county);

		setText(giftlistPage.postcode, pickRandomUKPostcode());
		clickElement(giftlistPage.continueOnAddressesButton);
		clickElement(giftlistPage.confirmButton);

		assertTrue("actual code is " + giftlistPage.assertionElementID.getAttribute("textContent")
				+ " expected code is " + "320296",
				giftlistPage.assertionElementID.getAttribute("textContent").equals("320296"));

		assertTrue(
				"actual colour is " + giftlistPage.assertionElementColour.getAttribute("innerText")
						+ " expected colour is " + " White",
				giftlistPage.assertionElementColour.getAttribute("innerText").equals("White"));

		logger.info("addingVariationProductToGiftlistTest" + "-Nice!");
	}

	@Test
	public void mc_joiningMyMothercareFlowTest() {

		HomePage homePage = new HomePage(driver);
		hoverElement(driver, homePage.OneHundredPounds_JoinMyMothercare);
		clickElement(homePage.OneHundredPounds_JoinMyMothercare);

		JoinMyMothercarePage joinMyMothercarePage = new JoinMyMothercarePage(driver);
		clickElement(joinMyMothercarePage.JoinNowAfterHomePageButton);
		focusOnElement(driver, joinMyMothercarePage.emailInputOnFirstStep);

		String generatedEmail = generateRandomEmail(6);
		setText(joinMyMothercarePage.emailInputOnFirstStep, generatedEmail);
		clickElement(joinMyMothercarePage.nextButton);

		setText(joinMyMothercarePage.password, data.my_password);
		setText(joinMyMothercarePage.confirmPassword, data.my_password);
		clickElement(joinMyMothercarePage.nextButton);

		clickByJavascript(driver, joinMyMothercarePage.familyFriend);
		clickElement(joinMyMothercarePage.friendOfExpecting);
		focusOnElement(driver, joinMyMothercarePage.whatIsYourDuedateInput);

		clickElement(joinMyMothercarePage.datePickerInCalendar);
		clickElement(joinMyMothercarePage.currentDayInCalendar);
		clickElement(joinMyMothercarePage.nextButton);

		selectByIndex(joinMyMothercarePage.selectTitle, 1);
		setText(joinMyMothercarePage.firstName, fakeData.firstName);
		setText(joinMyMothercarePage.lastName, fakeData.lastName);
		clickElement(joinMyMothercarePage.completeButton);

		assertTrue(joinMyMothercarePage.startShoppingButton.isDisplayed());

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_addingProductToCartFromQuickViewTest() {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		waitUntilElementIsVisible(driver, plp_Page.knittedBlanketQuickViewButton);
		clickByJavascript(driver, plp_Page.knittedBlanketQuickViewButton);

		QuickViewPage quickViewPage = new QuickViewPage(driver);
		clickElement(quickViewPage.plus);
		clickElement(quickViewPage.addToBasketButton);
		waitUntilElementIsClickable(driver, homePage.shoppingCart);
		actions.moveToElement(homePage.shoppingCart, 1, 1).click().perform();

		CartPage cartPage = new CartPage(driver);
		assertEquals("Baby K Knitted Blanket", cartPage.itemsInCartSection.getAttribute("innerText"));

		assertEquals("2", cartPage.quantity.getAttribute("value"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_clickingBackToTopButtonOnPLPTest() {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		clickElement(plp_Page.showAllLink);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		scrollTillBottom(driver);
		clickByJavascript(driver, plp_Page.backToTopButton);

		assertTrue(homePage.brandLogo.isDisplayed());

		logger.info(name.getMethodName() + "-Nice!");
	}

	@Test
	public void mc_removingJustAddedProductFromCartTest() {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		waitUntilElementIsClickable(driver, plp_Page.knittedBlanketQuickViewButton);
		clickByJavascript(driver, plp_Page.knittedBlanketQuickViewButton);

		QuickViewPage quickViewPage = new QuickViewPage(driver);
		clickElement(quickViewPage.plus);
		clickElement(quickViewPage.addToBasketButton);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		assertEquals("Baby K Knitted Blanket", cartPage.itemsInCartSection.getAttribute("innerText"));
		assertEquals("2", cartPage.quantity.getAttribute("value"));

		clickElement(cartPage.mc_removeLink);
		clickElement(cartPage.confirmDeletionButtonInPopup);

		assertEquals("shopping basket is empty (0 items)", cartPage.yourbasketIsEmpty.getAttribute("outerText"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_creatingAccountLogOutAndLoginAgainTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		selectByIndex(loginPage.title, 5);
		setText(loginPage.firstName, fakeData.firstName);
		setText(loginPage.lastName, fakeData.lastName);
		setText(loginPage.emailAddress, generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		setText(loginPage.confirmEmail, copyEmail);
		setText(loginPage.passwordRegistration, data.my_password);
		setText(loginPage.confirmPasswordRegistration, data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();
		assertTrue(

				"Actual text is" + loginPage.registrationConfirmation.getAttribute("textContent") + "Expected text is "
						+ " thank you for registering with mothercare",
				loginPage.registrationConfirmation.getAttribute("textContent")
						.equals("thank you for registering with mothercare"));

		scrollToTop(driver);
		clickByJavascript(driver, homePage.logoutLink);
		clickElement(homePage.signInRegisterLink);

		setText(loginPage.emailSignIn, copyEmail);
		setText(loginPage.passwordSignIn, data.my_password);
		actions.moveToElement(loginPage.signInButton).click().perform();

		assertTrue("Actual text is" + loginPage.assertMyAccount.getAttribute("textContent") + "Expected text is "
				+ "My Account", loginPage.assertMyAccount.getAttribute("textContent").equals("My Account"));

		logger.info(name.getMethodName() + "-Nice!");
	}

	@Test
	public void mc_veryfyingAllProductsLinkOnPLPTest() throws Exception {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		clickElement(plp_Page.showAllLink);
		waitUntilElementIsInvisible(driver, plp_Page.loader);

		List<WebElement> elements = driver.findElements(By.cssSelector("a[data-product-name]"));
		WebElement productBeforeSecondLoader = elements.get(195);
		scroolToThisElement(driver, productBeforeSecondLoader);

		waitTextIsPresentedInElement(driver, plp_Page.blankets_241_onButton, "241");
		scrollTillBottom(driver);

		assertEquals(true, plp_Page.blankets_241_onButton.getAttribute("textContent").contains("241"));

		assertEquals("241", plp_Page.blankets_241_showingProducts.getAttribute("innerText"));

		logger.info(name.getMethodName() + "-Nice!");

	}

}
