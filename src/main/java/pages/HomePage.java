package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	public HomePage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "a[title='Mothercare Home']")
	public WebElement brandLogo;

	@FindBy(css = "a[data-toggle-element='.b-top_banner']")
	public WebElement testBanner;

	@FindBy(xpath = "//*[@id='cookiehint']/div[2]/div/button")
	public WebElement cookieAccept;

	@FindBy(css = "#q")
	public WebElement searchInput;
	
	@FindBy(css = "button[class$='i-search']")
	public WebElement searchButton;

	@FindBy(css = "div[class$='toplinks'] a[title='Go to: sign in/register']")
	public WebElement signInRegisterLink;

	@FindBy(css = "a[title='Go to: Log out']")
	public WebElement logoutLink;

	@FindBy(css = "div[class$='header_toplinks'] a[title='Go to: My Account']")
	public WebElement elc_myAccountHeaderLink;

	@FindBy(css = "a[href$='MCENGB/bedding-2/']")
	public WebElement beddingCategory;

	@FindBy(css = "a[title='pushchairs']")
	public WebElement pushchairsCategory;
	
	@FindBy(css = "a[href$='/MCENGB/bathing-and-changing/']")
	public WebElement bathingAndChangingCategory;

	@FindBy(css = "a[title='3 wheeler']")
	public WebElement Home_3Wheeler;

	@FindBy(css = "a[title='learning & books']")
	public WebElement elc_learningAndBooks;

	@FindBy(xpath = "//*[@title='blankets']")
	public WebElement blanketsSubCategory;
	
	@FindBy(css = "a[title='baths']")
	public WebElement bathsSubCategory;

	@FindBy(css = "button[type='submit']+a[title='Locate Stores'] span")
	public WebElement storeFinder;

	@FindBy(css = "button[class$='search_toggle']+a:first-of-type")
	public WebElement elc_storeFinder;

	@FindBy(css = "a[title='Go to Cart']")
	public WebElement shoppingCart;

	@FindBy(css = "a[title='Request a catalogue']")
	public WebElement requestCatalogue;

	@FindBy(css = "a[title='Contact us']")
	public WebElement contactUs;

	@FindBy(css = "a[title='Sitemap']")
	public WebElement sitemap;

	@FindBy(css = "a[manual_cm_re='top-_-stripe-_-mymothercare']")
	public WebElement OneHundredPounds_JoinMyMothercare;
	
	@FindBy(xpath = "*//div[@id='t017-close']")
	public WebElement closePopup;
	
	@FindBy(css = "a[title='Gurgle']")
	public WebElement gurgle_url;
	
	

	
	

}
