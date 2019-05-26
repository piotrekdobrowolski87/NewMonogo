package com.monogo.pages;

import com.monogo.common.CommonMethods;
import com.monogo.tests.Cart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    @FindBy(how = How.XPATH, using = "//*[@class='b-header-main__cart-holder']")
    private WebElement cart;

    @FindBy(how = How.ID, using = "header-cart-item-count-text")
    private WebElement cartCouter;

    @FindBy(how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']/button[1]")
    private WebElement backToShopAfterAddToCart;

    @FindBy(how = How.XPATH, using = "//div[@class='ui-dialog-buttonset']/button[2]")
    private WebElement goToCartAfterAddToCart;


    WebDriver driver;
    CommonMethods commonMethods;
    private String emptyCart = "pusty";


    public CartPage(WebDriver driver){
        this.driver = driver;
        commonMethods = new CommonMethods(driver);
        PageFactory.initElements(driver, this);
    }

    public void checkIfCartIsVisible(){
        commonMethods.waitForViibility(cart);
    }

    public boolean chceckIfCartIsEmpty() {
        commonMethods.moveToElement(cartCouter);
        String actualCartState = cartCouter.getText();
        if(actualCartState.equals(emptyCart)){
            return true;
        }
        return false;
    }
}
