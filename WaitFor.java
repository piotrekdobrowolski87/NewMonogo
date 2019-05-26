package com.monogo;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

public class WaitFor {

    private WebDriver driver;
    private long milis = 200;
    private  Duration timeout = Duration.ofSeconds(10);
    private  Duration pollingTime = Duration.ofMillis(200);

    public WaitFor(WebDriver driver){
        this.driver = driver;
    }

    public void waitForPictureChange(WebElement element, String attribute, String attributeValue){
        waitFor().until(ExpectedConditions.not(ExpectedConditions.attributeContains(element,attribute,attributeValue)));
    }

    public void waitForViibility(WebElement element){
        waitFor().until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForVisibilityOfCollection(List<WebElement> collectionOfElements){
        for(WebElement element : collectionOfElements){
            waitFor().until(ExpectedConditions.visibilityOf(element));
        }
    }

    public void justWait(){
        waitFor();
    }

    private Wait waitFor(){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout)
                .pollingEvery(pollingTime)
                .ignoring(NoSuchElementException.class);
        return wait;
    }
}
