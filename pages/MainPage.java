package com.monogo.pages;

import com.monogo.common.CommonMethods;
import com.monogo.tests.Cart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class MainPage {

    @FindBy(how = How.XPATH, using = "//*[@class='b-header-main']")
    private WebElement header;

    @FindBy(how = How.XPATH, using = "//*[@id='main_menu']/div[@class='pn']")
    private WebElement mainMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='main-container']//div[@class='owl-stage']/div")
    private List<WebElement> owlIetms;

    @FindBy(how = How.XPATH, using = "//div[1][@class='b-shops-shares']/ul[@class='b-shops-shares__list']/li[@class='b-shops-shares__list-item']")
    private List<WebElement> shopsSharesList;

    @FindBy(how = How.XPATH, using = "//div[@class='promoted-products__category b-products-wrap']")
    private List<WebElement> promotedProductsCategory;

    @FindBy(how = How.XPATH, using = "//a[@class='promoted-products-title']")
    private List<WebElement> promotedProductsCategoryNames;


    private WebDriver driver;
    private CommonMethods commonMethods;
    private SoftAssert softAssert = new SoftAssert();
    private String url = "https://merlin.pl/";
    private int owlItemsToCheck = 3;
    private int expectedShopsSharesList = 3;
    private List<String> promotedProductsCategoryNamesValues = new ArrayList<>();
    CartPage cart;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        commonMethods = new CommonMethods(this.driver);
        PageFactory.initElements(driver, this);
        cart = new CartPage(this.driver);
    }


    public void checkCurrentUrl() {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, url);
    }

    public void checkMainPagesection() {
        setCategoryNames();

        //sprawdzenie widocznosci nagłówków
        commonMethods.waitForViibility(header);
        commonMethods.waitForViibility(mainMenu);

        //sprawdzam widoczność licznika koszyka
        cart.checkIfCartIsVisible();

        //sprawdzenie widoczności shareShopsList
        commonMethods.waitForVisibilityOfCollection(shopsSharesList);
        int actualShopsSharesList = shopsSharesList.size();
        softAssert.assertEquals(actualShopsSharesList, expectedShopsSharesList, "Ilość promocyjnych linków inna od oczekiwanej");

        //sprawdzenie promoted_products_category
        int actualPromotedCaterory = promotedProductsCategory.size();
        int actualPromotedCategoryNames = promotedProductsCategoryNames.size();
        int expectedSize = promotedProductsCategoryNamesValues.size();
        softAssert.assertEquals(actualPromotedCategoryNames, expectedSize, "Wyświetla się za dużo kategorii promocyjnych");
        softAssert.assertEquals(actualPromotedCaterory, expectedSize, "Wyświetla się za dużo kategorii promocoyjnych");

        //sprawdzenie sekcji promotedProducts
        for (int i = 0; i < expectedSize; i++) {
            WebElement category = promotedProductsCategory.get(i);
            String expectedName = promotedProductsCategoryNamesValues.get(i);
            String actualName = promotedProductsCategoryNames.get(i)
                    .getText();
            commonMethods.waitForViibility(promotedProductsCategoryNames.get(i));
            commonMethods.waitForViibility(category);

            commonMethods.moveToElement(promotedProductsCategory.get(i));
            softAssert.assertEquals(actualName, expectedName, "Nazawa kategorii jest nie zgodna");
        }
    }

    public void checkIfCartIsEmpty(){
        Boolean emptyCart = cart.chceckIfCartIsEmpty();
        softAssert.assertTrue(emptyCart,"Koszyk posiada zawartość, powinien byc pusty");
    }

    public void checkOwlAnimation() {
        //sprawdzanie animiacji OwlItem
        for (int i = 0; i < owlItemsToCheck; i++) {
            String active;

            for (WebElement owlItem : owlIetms) {
                commonMethods.moveToElement(owlItem);
                active = owlItem.getAttribute("class");
                if (active.contains("active")) {
                    commonMethods.waitForPictureChange(owlItem, "class", "active");
                    break;
                }
            }
        }
    }

    public void checkAllAssertions(){
        softAssert.assertAll();
    }

    private void setCategoryNames() {
        promotedProductsCategoryNamesValues.add("Wszystkie kategorie");
        promotedProductsCategoryNamesValues.add("Książki dla dzieci");
        promotedProductsCategoryNamesValues.add("Biografie");
        promotedProductsCategoryNamesValues.add("Poradniki");
        promotedProductsCategoryNamesValues.add("Pop");
        promotedProductsCategoryNamesValues.add("Rock");
        promotedProductsCategoryNamesValues.add("Filmy dla dzieci");
        promotedProductsCategoryNamesValues.add("Blu Ray");
        promotedProductsCategoryNamesValues.add("Gry");
        promotedProductsCategoryNamesValues.add("Perfumy dla kobiet");
    }
}
