package mothercareCI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
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
import pages.WishlistPage;
import webLibrary.Library;

public class MC_RunnerTest extends Library {

	public static final Logger logger = LogManager.getLogger(MC_RunnerTest.class.getName());

	public static WebDriver driver;

	private static ResourceBundle rb = ResourceBundle.getBundle("config");

	@Rule
	public final TestName name = new TestName();

	@Before
	public void before() {

		driver = new ChromeDriver();
		setDriverConfiguration(driver, 15);
		driver.get(rb.getString("mc_ci"));

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
	public void mc_loginWithValidCredentialsTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.loginAction_MC(driver);

		LoginPage loginPage = new LoginPage(driver);
		assertTrue(loginPage.assertMyAccount.isDisplayed());

		logger.info(name.getMethodName() + " -Nice");
	}

	@Test
	public void mc_SignUpToEmailSubscriptionTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.loginAction_MC(driver);

		LoginPage.subscribeToEmails(driver);

		LoginPage loginPage = new LoginPage(driver);

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

		LoginPage.creatingNewAccount(driver);

		LoginPage loginPage = new LoginPage(driver);
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

		CheckoutPage.proccessingCheckoutUntilPaymentMethodAsGuest(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitUntilElementIsClickable(driver, checkoutPage.creditCardOption);
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

		CheckoutPage.proccessingCheckoutUntilPaymentMethodAsGuest(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitUntilElementIsClickable(driver, checkoutPage.creditCardOption);
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

		CheckoutPage.proccessingCheckoutUntilPaymentMethodAsGuest(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickByJavascript(driver, checkoutPage.payPalOption);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);

		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, rb.getString("paypal_login"));
		setText(checkoutPage.paypalLoginPassword, rb.getString("paypal_password"));
		clickElement(checkoutPage.loginPaypalButton);

		waitUntilElementIsClickable(driver, checkoutPage.makePrefferedCheckbox);
		focusOnElement(driver, checkoutPage.makePrefferedCheckbox);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Nice");

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
		setText(loginPage.emailSignIn, rb.getString("my_email_mc"));
		setText(loginPage.passwordSignIn, rb.getString("my_password"));
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

		LoginPage.logInAsRegistereduser_MC(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		waitUntilElementIsClickable(driver, checkoutPage.deliverInternational);
		clickByJavascript(driver, checkoutPage.deliverInternational);
		waitUntilElementIsClickable(driver, checkoutPage.internationalSelectFromSavedAddress);
		clickByJavascript(driver, checkoutPage.internationalSelectFromSavedAddress);

		waitUntilElementIsClickable(driver, checkoutPage.channelIslandsOption);
		clickByJavascript(driver, checkoutPage.channelIslandsOption);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPayment);
		clickByJavascript(driver, checkoutPage.proceedToPayment);
		clickByJavascript(driver, checkoutPage.payPalOption);

		waitUntilElementIsClickable(driver, checkoutPage.proceedToPaypal);
		clickByJavascript(driver, checkoutPage.proceedToPaypal);

		overwriteCurrentInputValue(checkoutPage.paypalLoginUsername, rb.getString("paypal_login"));
		setText(checkoutPage.paypalLoginPassword, rb.getString("paypal_password"));
		clickElement(checkoutPage.loginPaypalButton);

		waitUntilElementIsClickable(driver, checkoutPage.makePrefferedCheckbox);
		focusOnElement(driver, checkoutPage.makePrefferedCheckbox);
		clickByJavascript(driver, checkoutPage.payNowByPaypal);

		assertTrue(
				"actual text is " + checkoutPage.confirmation.getAttribute("textContent") + " expected text is "
						+ "Thank you for your order",
				checkoutPage.confirmation.getAttribute("textContent").equals("Thank you for your order"));

		logger.info(name.getMethodName() + " -Nice");

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

		LoginPage.logInAsRegistereduser_MC(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		actions.moveToElement(checkoutPage.deliverToUKoption, 1, 1).click().perform();
		waitUntilElementIsClickable(driver, checkoutPage.selectFromSavedForRegisteredUser);
		clickByJavascript(driver, checkoutPage.selectFromSavedForRegisteredUser);

		waitUntilElementIsClickable(driver, checkoutPage.standartDelivery);
		clickByJavascript(driver, checkoutPage.standartDelivery);
		waitUntilElementIsClickable(driver, checkoutPage.proceedToPayment);
		clickByJavascript(driver, checkoutPage.proceedToPayment);

		clickElement(checkoutPage.creditCardOption);
		waitUntilElementIsClickable(driver, checkoutPage.visaSavedCard);
		clickByJavascript(driver, checkoutPage.visaSavedCard);
		setText(checkoutPage.visaCVN, rb.getString("CVV"));
		waitUntilElementIsClickable(driver, checkoutPage.visaPlaceOrder);
		clickByJavascript(driver, checkoutPage.visaPlaceOrder);

		waitUntilElementIsClickable(driver, checkoutPage.iframe);
		switchToFrame(driver, checkoutPage.iframe);
		CheckoutPage.approveAuthentificationForOrder(driver);

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

		assertEquals(rb.getString("contactUs_url"), newUrl);

		logger.info(name.getMethodName() + "-Nice!");

	}

	@Test
	public void mc_addingVariationProductToGiftlistTest() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		clickElement(homePage.signInRegisterLink);

		LoginPage.creatingNewAccount(driver);

		scroolToThisElement(driver, homePage.brandLogo);
		clickByJavascript(driver, homePage.brandLogo);

		Actions actions = new Actions(driver);
		actions.moveToElement(homePage.bathingAndChangingCategory).click().perform();
		clickByJavascript(driver, homePage.bathsSubCategory);

		PLP_Page plp_Page = new PLP_Page(driver);
		waitUntilElementIsClickable(driver, plp_Page.listFilterOption);
		clickByJavascript(driver, plp_Page.listFilterOption);
		clickByJavascript(driver, plp_Page.bathWithVariationButton);

		PDP_Page pdp_Page = new PDP_Page(driver);
		clickElement(pdp_Page.bathWithWhiteColour);
		waitUntilElementIsInvisible(driver, plp_Page.loader);
		clickElement(pdp_Page.addToMyGiftlistButton);

		GiftlistPage.newEventCreating_MC(driver);

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		clickElement(checkoutPage.enterAddressManually);

		GiftlistPage.newNicknameCreating_MC(driver);

		GiftlistPage giftlistPage = new GiftlistPage(driver);
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

		setText(joinMyMothercarePage.password, rb.getString("my_password"));
		setText(joinMyMothercarePage.confirmPassword, rb.getString("my_password"));
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
		waitUntilElementIsClickable(driver, plp_Page.backToTopButton);
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
		setText(loginPage.passwordRegistration, rb.getString("my_password"));
		setText(loginPage.confirmPasswordRegistration, rb.getString("my_password"));

		Actions actions = new Actions(driver);
		actions.moveToElement(loginPage.createAccount).click().perform();

		assertTrue(loginPage.assertMyAccount.isDisplayed());

		scrollToTop(driver);
		clickByJavascript(driver, homePage.logoutLink);
		clickElement(homePage.signInRegisterLink);

		setText(loginPage.emailSignIn, copyEmail);
		setText(loginPage.passwordSignIn, rb.getString("my_password"));
		actions.moveToElement(loginPage.signInButton).click().perform();

		assertTrue(loginPage.assertMyAccount.isDisplayed());

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

		scrollTillBottom(driver);
		waitUntilElementIsVisible(driver, plp_Page.blankets_241_onButton);
		waitTextIsPresentedInElement(driver, plp_Page.blankets_241_onButton, "241");

		assertEquals(true, plp_Page.blankets_241_onButton.getAttribute("textContent").contains("241"));

		assertEquals("241", plp_Page.blankets_241_showingProducts.getAttribute("innerText"));

		logger.info(name.getMethodName() + "-Nice!");

	}

}
