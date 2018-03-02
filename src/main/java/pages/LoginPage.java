package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public LoginPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[contains(@id,'login_user')]")
	public WebElement emailSignIn;

	@FindBy(css = "input[id *= 'dwfrm_login_password_']")
	public WebElement passwordSignIn;

	@FindBy(css = "button[name ='dwfrm_login_login'][value='sign in']")
	public WebElement signInButton;

	@FindBy(xpath = "//*[@class=\"form-row f_error_message\"]")
	public WebElement errorWhileSigninMessage;

	@FindBy(css = "select[id='dwfrm_profile_customer_title']")
	public WebElement title;

	@FindBy(css = "input[id='dwfrm_profile_customer_firstname']")
	public WebElement firstName;

	@FindBy(css = "input[id='dwfrm_profile_customer_lastname']")
	public WebElement lastName;

	@FindBy(css = "input[id='dwfrm_profile_customer_email']")
	public WebElement emailAddress;

	@FindBy(css = "input[id='dwfrm_profile_customer_emailconfirm']")
	public WebElement confirmEmail;

	@FindBy(css = "input[id *= 'dwfrm_profile_login_password_']")
	public WebElement passwordRegistration;

	@FindBy(css = "input[id *= 'dwfrm_profile_login_passwordconfirm']")
	public WebElement confirmPasswordRegistration;

	@FindBy(css = "button[value='Apply']")
	public WebElement createAccount;

	@FindBy(xpath = "//*[@id='primary']/div/h1")
	public WebElement registrationConfirmation;

	@FindBy(css = "input[id*='footer_newsletter_email_']")
	public WebElement inputSignup;

	@FindBy(css = "button[value='Signup']")
	public WebElement buttonSignup;

	@FindBy(xpath = "//*[@id='dialog-container']/div/h3")
	public WebElement signUpConfirmationPopup;

	@FindBy(css = "a[title='Go to: Log out']")
	public WebElement assertMyAccount;

	@FindBy(css = "input[id$='requestpassword_email']")
	public WebElement inputForEmailInForgotPasswordPopup;

	@FindBy(css = "button[name$='dwfrm_requestpassword_send']")
	public WebElement sendPasswordInForgotPasswordPopup;

}
