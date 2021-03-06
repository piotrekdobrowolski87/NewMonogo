package com.monogo.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SetBrowser {

    private WebDriver driver;

    public WebDriver openBrowserWindow(String browser, int width, int height, String url){
        Dimension dimension = new Dimension(width,height);
        setDriver(browser);

        driver.manage().deleteAllCookies();

        driver.manage()
                .window()
                .setSize(dimension);
        driver.get(url);

        return driver;
    }

    private void setDriver(String browser) {
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver()
                        .setup();
                this.driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver()
                        .setup();
                this.driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver()
                        .setup();
                this.driver = new EdgeDriver();
                break;

        }
    }
}
