package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import webLibrary.Library;

import org.openqa.selenium.WebDriver;

public class PLP_Page extends Library{

	public PLP_Page(WebDriver driver) {

		PageFactory.initElements(driver, this);

	}
	
	public static void elc_AddProductToCart(WebDriver driver) {

		PLP_Page plp_Page = new PLP_Page(driver);
		clickByJavascript(driver, plp_Page.elc_cinderellaPLP);
		waitUntilElementIsClickable(driver, plp_Page.cinderellaButton);
		clickByJavascript(driver, plp_Page.cinderellaButton);
		

	}

	@FindBy(css = "a[title='Refine by:Cinderella']")
	public WebElement elc_cinderellaPLP;

	@FindBy(css = "#primary > h1")
	public WebElement nameOfSubcategory;

	@FindBy(css = "#category-level-2 > li:nth-child(5) > a")
	public WebElement blankets;

	@FindBy(xpath = "//*[@id='category-level-2']/li[7]/a/span[1]")
	public WebElement pushchairsOffers;

	@FindBy(css = "a[class$='all-products']")
	public WebElement showAllLink;
	
	@FindBy(css = "#grid-paging-header")
	public WebElement switcherForPLP;
	
	@FindBy(css = "option[value$='sz=12'][selected='selected']")
	public WebElement _12_products;
	
	@FindBy(css = "option[value$='sz=24'][selected='selected']")
	public WebElement _24_products;

	@FindBy(css = "b[class='js-items-shown']")
	public WebElement itemsShownOnBackToTopButton;

	@FindBy(css = "a[class$='scroll_top']")
	public WebElement backToTopButton;

	@FindBy(css = "div[class$='js-tabs']")
	public WebElement customersAreAlsoBuying;

	@FindBy(css = ".loader-indicator")
	public WebElement loader;

	@FindBy(css = "a[title='Go to Product: Silver Cross Pop Stroller - Sand']")
	public WebElement productLink2;

	@FindBy(css = "div[class$='searchresult_content'] a[title='Washpod Baby Bath'] span")
	public WebElement bathWithVariationButton;

	@FindBy(css = "div[class='b-product_title'] a[title='Go to Product: Disney Cinderella My Storybook Library']")
	public WebElement elc_productLink;

	@FindBy(xpath = "//*[@id='main']/div[2]/div[1]/ul/li[3]/span[contains(text(),'blankets')]")
	public WebElement blanketsPLPconfirmation;

	@FindBy(xpath = "//*[@id='main']/div[1]/div[1]/span[2]/span[2][contains(text(),'Cinderella')]")
	public WebElement elc_cinderellaPLPconfirmation;

	@FindBy(css = "select[id='grid-sort-header']")
	public WebElement gridFilter;

	@FindBy(css = "div[class='b-filters'] i[data-option='wide']:last-of-type")
	public WebElement listFilterOption;

	@FindBy(css = "a[data-product-name='Baby K Knitted Blanket']")
	public WebElement knittedBlanketButton;

	@FindBy(css = "a[href*='baby-k-knitted-blanket/'][class$='quick_view']")
	public WebElement knittedBlanketQuickViewButton;

	@FindBy(xpath = "//*[@data-product-name='Disney Cinderella My Storybook Library']")
	public WebElement cinderellaButton;

	@FindBy(css = "span[class='items js-items-chunk']")
	public WebElement blankets_241_onButton;

	@FindBy(css = "span[class='js-up-to-items']")
	public WebElement blankets_241_showingProducts;

}
