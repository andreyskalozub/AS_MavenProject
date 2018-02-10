package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MA_BigBirthdayClubPage {
	
	public MA_BigBirthdayClubPage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	
	@FindBy(css="div[class$='boy']")
	public WebElement boyBadge;
	
	@FindBy(css="div[class$='girl']")
	public WebElement girlbadge;
	
	@FindBy(css="input[id$='child1_name']")
	public WebElement childName;
	
	@FindBy(css="input[id$='birthday']")
	public WebElement datePicker;
	
	@FindBy(css="td[class$='today']")
	public WebElement currentdate;
	
	@FindBy(css="select[name$='relation']")
	public WebElement relationshipSelect;
	
	@FindBy(css="button[value='save.child']")
	public WebElement addNewFamilyMemberButton;
	
	@FindBy(css="div[class$='child_update-confirmation']")
	public  WebElement confirmationPopup;
	
	@FindBy(xpath="//*[@id='dialog-container']/div/div/div[2]/button")
	public  WebElement backToClubButtonInPopup;
	
	@FindBy(css="button[value='enter the club']")
	public  WebElement enterTheClubButton;
	
	@FindBy(css="div[class='b-due_date-name']:first-child")
	public  WebElement babyNameValueDisplayed;
	
	@FindBy(css="button[value='Save changes']")
	public  WebElement saveChangesButton;
	
	

}
