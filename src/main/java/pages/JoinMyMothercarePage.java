package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class JoinMyMothercarePage {
	
	public JoinMyMothercarePage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	@FindBy(css="a[class='g-button-default button-full-width']")
	public WebElement JoinNowAfterHomePageButton;
	
	@FindBy(css="input[id$='username']")
	public WebElement emailInputOnFirstStep;
	
	@FindBy(css="button[value='next']")
	public WebElement nextButton;
	
	@FindBy(css="input[id*='_newpassword_']")
	public WebElement password;
	
	@FindBy(css="input[id*='_passwordconfirm_']")
	public WebElement confirmPassword;
	
	@FindBy(css="button[name$='familyfriend']")
	public WebElement familyFriend;
	
	@FindBy(css="button[name$='expecting']")
	public WebElement friendOfExpecting;
	
	@FindBy(css="input[id$='duedate']")
	public WebElement whatIsYourDuedateInput;
	
	@FindBy(css="i[class$='i-date']")
	public WebElement datePickerInCalendar;
	
	@FindBy(css="td[class='day']")
	public WebElement currentDayInCalendar;
	
	@FindBy(css="select[name$='title']")
	public WebElement selectTitle;
	
	@FindBy(css="input[name$='firstname']")
	public WebElement firstName;
	
	@FindBy(css="input[name$='lastname']")
	public WebElement lastName;
	
	@FindBy(css="button[value='complete']")
	public WebElement completeButton;
	
	@FindBy(css="a[class$='g-button-mymothercare']")
	public WebElement startShoppingButton;
	
	
	
}
