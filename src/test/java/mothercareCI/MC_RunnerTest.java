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

	WebDriver driver;

	@Rule
	public final TestName name = new TestName();

	  //@Rule 
	 //public ScreenShotRule screenShotRule = new ScreenShotRule();
	 

	@Before
	public void before() {

		driver = new ChromeDriver();
		setDriverConfiguration(driver, 30);
		driver.get(data.mc_ci);

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
	public void mc_loginTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailSignIn.sendKeys(data.my_email_mc);
		loginPage.passwordSignIn.sendKeys(data.my_password);

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.signInButton).click().perform();

		assertTrue("Actual text is" + loginPage.assertMyAccount.getAttribute("textContent") + "Expected text is "
				+ "My Account", loginPage.assertMyAccount.getAttribute("textContent").equals("My Account"));

		logger.info(name.getMethodName() + " -Nice");
	}

	@Test
	public void mc_emailSignUpTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.signInRegisterLink.click();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailSignIn.sendKeys(data.my_email_mc);
		loginPage.passwordSignIn.sendKeys(data.my_password);

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
	public void mc_createAccountTest() throws InterruptedException {

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

		assertTrue(
				"Actual text is" + loginPage.registrationConfirmation.getAttribute("textContent") + "Expected text is "
						+ " thank you for registering with mothercare",
				loginPage.registrationConfirmation.getAttribute("textContent")
						.equals("thank you for registering with mothercare"));

		logger.info(name.getMethodName() + "-Nice!");
	}

	@Test
	public void mc_guestCheckoutCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();

		clickByJavascript(driver, homePage.blanketsSubCategory);
		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		List<WebElement> elements = driver.findElements(By.cssSelector(
				"div[class='b-search_result_items'] a[data-product-availabilitystatus='YES'][data-product-variation-attribute='']"));

		WebElement first = elements.get(0);
		clickByJavascript(driver, first);
		wait(2);

		clickByJavascript(driver, homePage.shoppingCart);
		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.checkoutAsGuestButton.click();
		waitUntilElementIsClickable(driver, checkoutPage.deliverToUKoption);
		checkoutPage.deliverToUKoption.click();

		selectByIndex(checkoutPage.title, 2);
		setText(checkoutPage.firstname, fakeData.firstName);
		setText(checkoutPage.lastname, fakeData.lastName);
		setText(checkoutPage.email, generateRandomEmail(5));
		setText(checkoutPage.phone, fakeData.phoneNumber);

		checkoutPage.enterAddressManually.click();
		setText(checkoutPage.addressLine1, fakeData.address1);
		setText(checkoutPage.town_city, fakeData.city);
		setText(checkoutPage.postalCode, pickRandomUKPostcode());
		clickByJavascript(driver, checkoutPage.deliverToThisAddress);

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		checkoutPage.proceedToPayment.click();
		waitUntilElementIsClickable(driver, checkoutPage.creditCardOption);
		checkoutPage.creditCardOption.click();

		switchToFrame(driver, checkoutPage.paymentIframe);
		actions.moveToElement(checkoutPage.nameOnCard, 1, 1).click().perform();
		setText(checkoutPage.nameOnCard, fakeData.firstName);
		setText(checkoutPage.cardNumber, data.creditCardNumber);

		selectByIndex(checkoutPage.expirationYear, 5);
		setText(checkoutPage.CVV, data.CVV);
		checkoutPage.guestPlaceOrder.click();

		driver.switchTo().defaultContent();
		switchToFrame(driver, checkoutPage.iframe);

		waitUntilElementIsClickable(driver, checkoutPage.choiceAuth);
		checkoutPage.choiceAuth.click();

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc2_guestCheckoutCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickByJavascript(driver, plp_Page.knittedBlanketButton);
		wait(1);

		clickByJavascript(driver, homePage.shoppingCart);
		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.checkoutAsGuestButton.click();

		waitUntilElementIsClickable(driver, checkoutPage.deliverToUKoption);
		checkoutPage.deliverToUKoption.click();
		selectByIndex(checkoutPage.title, 2);

		setText(checkoutPage.firstname, fakeData.firstName);
		setText(checkoutPage.lastname, fakeData.lastName);
		setText(checkoutPage.email, generateRandomEmail(5));
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

		switchToFrame(driver, checkoutPage.paymentIframe);
		actions.moveToElement(checkoutPage.nameOnCard, 1, 1).click().perform();
		setText(checkoutPage.nameOnCard, fakeData.firstName);
		setText(checkoutPage.cardNumber, data.creditCardNumber);

		selectByIndex(checkoutPage.expirationYear, 5);
		setText(checkoutPage.CVV, data.CVV);
		checkoutPage.guestPlaceOrder.click();

		driver.switchTo().defaultContent();
		switchToFrame(driver, checkoutPage.iframe);
		waitUntilElementIsClickable(driver, checkoutPage.choiceAuth);
		checkoutPage.choiceAuth.click();

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		assertEquals("Thank you for your order", checkoutPage.confirmation.getAttribute("textContent"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_paypalGuestCheckoutTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);

		waitUntilElementIsClickable(driver, plp_Page.knittedBlanketButton);
		clickByJavascript(driver, plp_Page.knittedBlanketButton);

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
		setText(checkoutPage.email, generateRandomEmail(5));
		setText(checkoutPage.phone, fakeData.phoneNumber);
		checkoutPage.enterAddressManually.click();

		setText(checkoutPage.addressLine1, fakeData.address1);
		setText(checkoutPage.town_city, fakeData.city);
		setText(checkoutPage.postalCode, pickRandomUKPostcode());
		actions.moveToElement(checkoutPage.deliverToThisAddress).click().perform();

		wait(1);
		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
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
	public void mc_requestCatalogueTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.requestCatalogue.click();
		RequestCataloguePage requestCataloguePage = new RequestCataloguePage(driver);
		requestCataloguePage.firstCatalogue.click();

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

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_addToWishlistFromPDPTest() throws InterruptedException {

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
		pdp_Page.addToMyWishlistButton.click();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.emailSignIn.sendKeys(data.my_email_mc);
		loginPage.passwordSignIn.sendKeys(data.my_password);
		actions.moveToElement(loginPage.signInButton).click().perform();

		WishlistPage wishlistPage = new WishlistPage(driver);
		assertEquals("265738", wishlistPage.itemInWishlist_IDConfirmation.getAttribute("textContent"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_payPalRegisteredCheckoutTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);
		clickByJavascript(driver, plp_Page.knittedBlanketButton);
		wait(1);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickByJavascript(driver, homePage.shoppingCart);
		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);
		clickElement(loginPage.signInButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.deliverInternational.click();
		waitUntilElementIsClickable(driver, checkoutPage.internationalSelectFromSavedAddress);
		clickByJavascript(driver, checkoutPage.internationalSelectFromSavedAddress);

		waitUntilElementIsClickable(driver, checkoutPage.channelIslandsOption);
		wait(1);
		clickByJavascript(driver, checkoutPage.channelIslandsOption);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPayment);
		checkoutPage.proceedToPayment.click();
		clickByJavascript(driver, checkoutPage.payPalOption);
		wait(1);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);
		switchToFrame(driver, checkoutPage.payPalIframe);

		checkoutPage.paypalLoginUsername.clear();
		checkoutPage.paypalLoginUsername.sendKeys(data.paypal_login);
		setText(checkoutPage.paypalLoginPassword, data.paypal_password);
		checkoutPage.loginPaypalButton.click();

		driver.switchTo().defaultContent();
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_registeredCheckoutSavedCreditCardTest() throws InterruptedException {

		Actions actions = new Actions(driver);
		HomePage homePage = new HomePage(driver);
		actions.moveToElement(homePage.beddingCategory).click().perform();
		clickByJavascript(driver, homePage.blanketsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		selectByIndex(plp_Page.gridFilter, 3);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickByJavascript(driver, plp_Page.knittedBlanketButton);
		wait(1);

		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		cartPage.topCheckoutButton.click();

		LoginPage loginPage = new LoginPage(driver);
		setText(loginPage.emailSignIn, data.my_email_mc);
		setText(loginPage.passwordSignIn, data.my_password);
		clickElement(loginPage.signInButton);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.deliverToUKoption.click();
		waitUntilElementIsClickable(driver, checkoutPage.selectFromSavedForRegisteredUser);
		checkoutPage.selectFromSavedForRegisteredUser.click();
		wait(1);

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		checkoutPage.proceedToPayment.click();

		checkoutPage.creditCardOption.click();
		waitUntilElementIsClickable(driver, checkoutPage.visaSavedCard);
		checkoutPage.visaSavedCard.click();
		setText(checkoutPage.visaCVN, data.CVV);
		clickByJavascript(driver, checkoutPage.visaPlaceOrder);

		switchToFrame(driver, checkoutPage.iframe);
		checkoutPage.choiceAuth.click();

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_contactUsOpeningTest() throws InterruptedException {

		scrollTillBottom(driver);
		HomePage homePage = new HomePage(driver);
		waitUntilElementIsVisible(driver, homePage.contactUs);
		homePage.contactUs.click();

		switchToNewWindow(driver);
		String newUrl = driver.getCurrentUrl();

		assertEquals(data.contactUs_url, newUrl);

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_addingVariationProductToGiftlistTest() throws InterruptedException {

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
		scrollTillBottom(driver);
		clickByJavascript(driver, homePage.sitemap);

		SitemapPage sitemapPage = new SitemapPage(driver);
		sitemapPage.bathingAndChangingOffers.click();

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.bathingAndChanging);
		clickByJavascript(driver, plp_Page.babyBathsAndAccessories);

		waitUntilElementIsVisible(driver, plp_Page.baths);
		clickByJavascript(driver, plp_Page.baths);
		clickByJavascript(driver, plp_Page.listFilterOption);
		clickByJavascript(driver, plp_Page.bathWithVariationButton);

		PDP_Page pdp_Page = new PDP_Page(driver);
		pdp_Page.bathWithWhiteColour.click();
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		pdp_Page.addToMyGiftlistButton.click();

		GiftlistPage giftlistPage = new GiftlistPage(driver);
		selectByIndex(giftlistPage.eventTypeSelect, 1);
		giftlistPage.eventName.sendKeys(fakeData.fullName);
		giftlistPage.eventDatePicker.click();
		giftlistPage.todayDate.click();

		giftlistPage.eventCity.sendKeys(fakeData.city);
		giftlistPage.continueButton.click();
		giftlistPage.createNewAddress.click();

		waitUntilElementIsVisible(driver, giftlistPage.newUKaddress);
		clickByJavascript(driver, giftlistPage.newUKaddress);

		waitUntilElementIsVisible(driver, giftlistPage.phone);
		focusOnElement(driver, giftlistPage.phone);
		giftlistPage.phone.sendKeys(fakeData.phoneNumber);
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.enterAddressManually.click();

		giftlistPage.nickname.sendKeys(fakeData.firstName);
		giftlistPage.addressLine1.sendKeys(fakeData.streetAddress);
		giftlistPage.city.sendKeys(fakeData.city);
		giftlistPage.county.sendKeys(fakeData.county);

		giftlistPage.postcode.sendKeys(pickRandomUKPostcode());
		giftlistPage.continueOnAddressesButton.click();
		giftlistPage.confirmButton.click();

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
	public void mc_joiningMyMothercareTest() {

		HomePage homePage = new HomePage(driver);
		hoverElement(driver, homePage.OneHundredPounds_JoinMyMothercare);
		clickElement(homePage.OneHundredPounds_JoinMyMothercare);

		JoinMyMothercarePage joinMyMothercarePage = new JoinMyMothercarePage(driver);
		joinMyMothercarePage.JoinNowAfterHomePageButton.click();
		focusOnElement(driver, joinMyMothercarePage.emailInputOnFirstStep);

		String generatedEmail = generateRandomEmail(7);
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
	public void mc_addProductToCartFromQuickViewTest() {

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
		clickByJavascript(driver, homePage.shoppingCart);

		CartPage cartPage = new CartPage(driver);
		assertEquals("Baby K Knitted Blanket", cartPage.itemsInCartSection.getAttribute("innerText"));

		assertEquals("2", cartPage.quantity.getAttribute("value"));

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_backToTopButtonOnPLPTest() {

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
	public void mc_removeJustAddedProductFromCartTest() {

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
		loginPage.firstName.sendKeys(fakeData.firstName);
		loginPage.lastName.sendKeys(fakeData.lastName);
		loginPage.emailAddress.sendKeys(generateRandomEmail(7));

		String copyEmail = loginPage.emailAddress.getAttribute("value");
		loginPage.confirmEmail.sendKeys(copyEmail);

		loginPage.passwordRegistration.sendKeys(data.my_password);
		loginPage.confirmPasswordRegistration.sendKeys(data.my_password);

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

		loginPage.emailSignIn.sendKeys(copyEmail);
		loginPage.passwordSignIn.sendKeys(data.my_password);
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
