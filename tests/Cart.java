package com.monogo.tests;

import com.monogo.common.SetBrowser;
import com.monogo.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Cart {

    WebDriver driver;
    MainPage mainPage;

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser","url","width","height"})
    public void openMainPage(String browser, String url, int width, int height){
        System.out.println(browser);
        driver = new SetBrowser().openBrowserWindow(browser,width,height,url);
    }

    @Test(priority = 1)
    public void mainPageUrl(){
        mainPage = new MainPage(driver);
        mainPage.checkCurrentUrl();
    }

    @AfterTest(alwaysRun = true)
    public void closeDriver(){
        driver.quit();
    }
}
