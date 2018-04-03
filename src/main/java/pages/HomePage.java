package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	public HomePage(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}

	@FindBy(css = "a[title='Mothercare Home']")
	@CacheLookup
	public WebElement brandLogo;

	@FindBy(css = "a[data-toggle-element='.b-top_banner']")
	@CacheLookup
	public WebElement testBanner;

	@FindBy(xpath = "//*[@id='cookiehint']/div[2]/div/button")
	@CacheLookup
	public WebElement cookieAccept;

	@FindBy(css = "input[id='q']")
	@CacheLookup
	public WebElement searchInput;

	@FindBy(css = "div[class$='toplinks'] a[title='Go to: sign in/register']")
	@CacheLookup
	public WebElement signInRegisterLink;

	@FindBy(css = "a[title='Go to: Log out']")
	@CacheLookup
	public WebElement logoutLink;

	@FindBy(css = "div[class$='header_toplinks'] a[title='Go to: My Account']")
	@CacheLookup
	public WebElement elc_myAccountHeaderLink;

	@FindBy(css = "a[href$='MCENGB/bedding-2/']")
	@CacheLookup
	public WebElement beddingCategory;

	@FindBy(css = "a[title='pushchairs']")
	@CacheLookup
	public WebElement pushchairsCategory;
	
	@FindBy(css = "a[href$='/MCENGB/bathing-and-changing/']")
	@CacheLookup
	public WebElement bathingAndChangingCategory;

	@FindBy(css = "a[title='3 wheeler']")
	@CacheLookup
	public WebElement Home_3Wheeler;

	@FindBy(css = "a[title='learning & books']")
	@CacheLookup
	public WebElement elc_learningAndBooks;

	@FindBy(xpath = "//*[@title='blankets']")
	@CacheLookup
	public WebElement blanketsSubCategory;
	
	@FindBy(css = "a[title='baths']")
	@CacheLookup
	public WebElement bathsSubCategory;

	@FindBy(css = "button[type='submit']+a[title='Locate Stores'] span")
	@CacheLookup
	public WebElement storeFinder;

	@FindBy(css = "button[class$='search_toggle']+a:first-of-type")
	@CacheLookup
	public WebElement elc_storeFinder;

	@FindBy(css = "a[title='Go to Cart']")
	@CacheLookup
	public WebElement shoppingCart;

	@FindBy(css = "a[title='Request a catalogue']")
	@CacheLookup
	public WebElement requestCatalogue;

	@FindBy(css = "a[title='Contact us']")
	@CacheLookup
	public WebElement contactUs;

	@FindBy(css = "a[title='Sitemap']")
	@CacheLookup
	public WebElement sitemap;

	@FindBy(css = "a[manual_cm_re='top-_-stripe-_-mymothercare']")
	@CacheLookup
	public WebElement OneHundredPounds_JoinMyMothercare;
	
	@FindBy(xpath = "*//div[@id='t017-close']")
	@CacheLookup
	public WebElement closePopup;

}
