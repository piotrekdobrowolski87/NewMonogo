package com.monogo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class First {

    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    String url = "https://merlin.pl/";
    int owlItemsToCheck  = 3;
    int expectedShopsSharesList = 3;

    @Test
    public void First(){
        System.out.println("bbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver(ops);
        driver.manage().window().setSize(new Dimension(1600, 900));
        driver.get(url);
        WaitFor waitFor = new WaitFor(driver);

        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualUrl,url);

        WebElement header = driver.findElement(By.xpath("//*[@class='b-header-main']"));
        WebElement mainMenu = driver.findElement(By.xpath("//*[@id='main_menu']/div[@class='pn']"));
        List<WebElement> owlIetms = driver.findElements(By.xpath("//*[@id='main-container']//div[@class='owl-stage']/div"));
        List<WebElement> shopsSharesList = driver.findElements(By.xpath("//div[1][@class='b-shops-shares']/ul[@class='b-shops-shares__list']/li[@class='b-shops-shares__list-item']"));
        List<WebElement> promotedProducts = driver.findElements(By.xpath("//div[@class='promoted-products__category b-products-wrap']"));
        List<WebElement> promotedProductsNames = driver.findElements(By.xpath("//a[@class='promoted-products-title']"));
        System.out.println(header.isDisplayed());
        System.out.println(mainMenu.isDisplayed());


        //sprawdzanie animiacji
        for (int i = 0; i < owlItemsToCheck; i++) {
            String active;

            for (WebElement owlItem : owlIetms) {
                active = owlItem.getAttribute("class");
                System.out.println(active);
                if (active.contains("active")) {
                    System.out.println(owlIetms.indexOf(owlItem) + " " + active);
                    waitFor.waitForPictureChange(owlItem, "class", "active");
                    break;
                }
            }
        }

        //sprawdzenie widoczności shareShopsList
        int actualShopsSharesList = shopsSharesList.size();
        softAssert.assertEquals(actualShopsSharesList,expectedShopsSharesList, "Ilość promocyjnych linków inna od oczekiwanej");
        waitFor.waitForVisibilityOfCollection(shopsSharesList);



    }

    @AfterTest
    public void quite(){
        this.driver.quit();
    }
}
