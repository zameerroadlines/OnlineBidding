package com.bosch.automation.testbase;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.*;

//import static freemarker.template.utility.Collections12.singletonList;
import static java.time.Duration.ofMillis;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;

/** Actions to interact with web pages **/
@Slf4j
public class DriverActions extends BaseClass{
    //region initializations
    public WebDriver driver;
    public ExtentTest logger;
    //endregion
    public DriverActions(WebDriver driver, ExtentTest logger)
    {
        this.driver=driver;
        this.logger=logger;
    }
//region Driver Actions
    public void click(By elementToClick, int timeToWaitInSeconds){
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
            if(element!=null){
            element.click();
            log("Clicked element : "+elementToClick);}
            else
            {
                Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
            }
        } catch (TimeoutException var8) {
            logger.fail("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
        }
    }

    public void click(By elementToClick,String elementName, int timeToWaitInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
            log("Clicked element : "+elementName);
        }
        catch (Exception e)
        {
            log("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ e.getMessage());
            Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds +"Exception "+e.getMessage());
        }
    }
    public void commonFindAndClick( By elementToClick,int timeToWaitInSeconds){
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
            if(element!=null){
                driver.findElement(elementToClick).click();
                log("Clicked element : "+elementToClick);}
            else
            {
                Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
            }
        } catch (TimeoutException var8) {
            logger.fail("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
        }
    }

    public Boolean verifyElementShown(By elementToFind, int timeToWaitInSeconds){
        WebElement element=null;
        try{
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToFind));

        } catch (Exception e) {
            log("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ e);
            Assert.fail("Element not found with xpath "+elementToFind + "after wait time "+timeToWaitInSeconds);
        }
        return element.isDisplayed();
    }

    public Boolean findElementPresenceReturnBool( By elementToFind,int timeToWaitInSeconds){
        WebElement element=null;
        boolean result=false;
        try{
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(elementToFind));
            log("Element found "+elementToFind);
            result=true;

        } catch (Exception e) {
            log("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ e);
            Assert.fail("Element not found with xpath "+elementToFind + "after wait time "+timeToWaitInSeconds);
        }
        return result;
    }

    public Boolean findElementAbsenceReturnBool( By elementToFind,int timeToWaitInSeconds){

        boolean result=false;
        try{
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(elementToFind));
            //log("Element not found "+elementToFind);
            result=true;

        } catch (Exception e) {
            log("Element found after wait time  :"+timeToWaitInSeconds+" Exception "+ e);
            Assert.fail("Element not found with xpath "+elementToFind + "after wait time "+timeToWaitInSeconds);
        }
        return result;
    }

    public WebElement findElementReturnElement( By elementToFind,int timeToWaitInSeconds){
        WebElement element=null;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(elementToFind));

        } catch (Exception e) {
            log("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ e);
            Assert.fail("Element not found with xpath "+elementToFind + "after wait time "+timeToWaitInSeconds);
        }
        return element;
    }

    public void scrollElementIntoViewWeb(By element,int waitTime){
        try {
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", findElementReturnElement(element,waitTime));
        } catch (Exception e) {
            log("Unable to scroll to element :"+element+"  Exception "+ e.getMessage());
            Assert.fail("Unable to scroll to element :"+element+"  Exception "+ e.getMessage());
        }
    }

    public boolean findElementReturnBoolLogWarning( By elementToFind,int timeToWaitInSeconds){
        WebElement element=null;
        boolean result=false;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            result = wait.until(ExpectedConditions.elementToBeClickable(elementToFind)).isDisplayed();
        } catch (Exception e) {
            logWarning("Element not found   :" +elementToFind+"after wait time "+timeToWaitInSeconds+" Exception ");
        }
        return result;
    }

    public boolean findElementReturnBoolWarn( By elementToFind,int timeToWaitInSeconds){
        WebElement element=null;
        boolean result;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(elementToFind));
            result=true;

        } catch (Exception e) {
            logWarning("Element not found after wait time  :"+elementToFind);
            result=false;
        }
        return result;
    }

    public boolean findElementWebReturnBoolWarn( By elementToFind,int timeToWaitInSeconds){
        WebElement element=null;
        setAppContextWeb();
        boolean result;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(elementToFind));
            result=true;

        } catch (Exception e) {
            log("Element not found after wait time  :"+elementToFind);
            result=false;
        }
        return result;
    }
    public void waitForElementToBeVisible( By elementToFind,int timeToWaitInSeconds){

        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(elementToFind));
            log("Found element with xpath "+elementToFind);

        } catch (TimeoutException var8) {
            log("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementToFind + "after wait time "+timeToWaitInSeconds);
        }
    }

    public void commonClickNativeWeb( By elementToClickNative,By elementToClickWeb,int timeToWaitInSeconds, int timeToSleepInMilliSec){
        WebElement element;
        try {
            setAppContextNative();
            Thread.sleep(timeToSleepInMilliSec);
            if((driver.findElements(elementToClickNative).size()>0))
            {
                driver.findElement(elementToClickNative).click();
                log("Clicked element : "+elementToClickNative);}
            else
            {
                logWarning("Element not found with xpath "+elementToClickNative + "after wait time "+timeToWaitInSeconds);
                setAppContextWeb();
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
                element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickWeb));
                if(element!=null){
                    element.click();
                    log("Clicked element : "+elementToClickWeb);}
                else
                {
                    logWarning("Element not found with xpath "+elementToClickWeb + "after wait time "+timeToWaitInSeconds);
                }
            }
        } catch (TimeoutException var8) {
            logWarning("Element not found with xpath "+elementToClickWeb + "after wait time "+timeToWaitInSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public void commonClickNativeWeb( By elementToClickNative,By elementToClickWeb,int timeToWaitInSeconds){
        WebElement element;
        try {
            setAppContextNative();
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickNative));
            element.click();
            log("Clicked element : "+elementToClickNative);
        } catch (Exception var8) {
            log("Element not found with xpath "+elementToClickNative + "after wait time "+timeToWaitInSeconds);
            try {
                setAppContextWeb();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWaitInSeconds));
                element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickWeb));
                element.click();
                log("Clicked element : " + elementToClickWeb);
            }
            catch (NoSuchElementException e)
            {
                Assert.fail("Element not found with xpath "+elementToClickWeb + " and with xpath "+elementToClickNative +"after wait time "+timeToWaitInSeconds +e.fillInStackTrace());
            }
        }
    }

    public void commonClickNativeAndWeb( By elementToClickNative,By elementToClickWeb,int timeToWaitInSeconds){
        WebElement element;
        try {
            setAppContextNative();
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickNative));
            element.click();
            log("Clicked native element: "+elementToClickNative);
            hardWait(2000);
            setAppContextWeb();
            if(driver.findElements(elementToClickWeb).size()>0){
                setAppContextWeb();
                wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWaitInSeconds));
                element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickWeb));
                element.click();
                log("Clicked web element as native does not work : " + elementToClickWeb);
            }
        } catch (Exception var8) {
            log("Element not found with xpath "+elementToClickNative + "after wait time "+timeToWaitInSeconds);
            try {
                setAppContextWeb();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWaitInSeconds));
                wait.until(ExpectedConditions.elementToBeClickable(elementToClickWeb)).click();
                //driver.findElement(elementToClickWeb).click();
                log("Clicked element : " + elementToClickWeb);
            }
            catch (NoSuchElementException e)
            {
                Assert.fail("Element not found with xpath "+elementToClickWeb + " and with xpath "+elementToClickNative +"after wait time "+timeToWaitInSeconds +e.fillInStackTrace());
            }
        }
    }


    public void commonClickNativeWebWithWait( By elementToClickNative,By elementToClickWeb,int timeToWaitInSeconds,int hardWait){
        WebElement element;
        try {
            Thread.sleep(hardWait);
            setAppContextNative();
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickNative));
            element.click();
            log("Clicked element : "+elementToClickNative);
        } catch (Exception var8) {
            logWarning("Element not found with xpath "+elementToClickNative + "after wait time "+timeToWaitInSeconds);
            try {
                setAppContextWeb();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWaitInSeconds));
                element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickWeb));
                element.click();
                log("Clicked element : " + elementToClickWeb);
            }
            catch (NoSuchElementException e)
            {
                Assert.fail("Element not found with xpath "+elementToClickWeb + " and with xpath "+elementToClickNative +"after wait time "+timeToWaitInSeconds +e.fillInStackTrace());
            }
        }
    }


    public void commonClickWebNative( By elementToClickNative,By elementToClickWeb,int timeToWaitInSeconds){
        WebElement element;
        try {
            setAppContextWeb();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickWeb));
            element.click();
            log("Clicked element : " + elementToClickWeb);

        } catch (Exception var8) {
            logWarning("Element not found with xpath "+elementToClickWeb + "after wait time "+timeToWaitInSeconds);
            try {
                setAppContextNative();
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
                element = wait.until(ExpectedConditions.elementToBeClickable(elementToClickNative));
                element.click();
                log("Clicked element : "+elementToClickNative);
            }
            catch (NoSuchElementException e)
            {
                Assert.fail("Element not found with xpath "+elementToClickWeb + " and with xpath "+elementToClickNative +"after wait time "+timeToWaitInSeconds +e.fillInStackTrace());
            }
        }
    }


    public boolean commonClickWarn( By elementToClick,int timeToWaitInSeconds){
        WebElement element;
        boolean result=true;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
            if(element!=null){
                element.click();
                log("Clicked element : "+elementToClick);}
            else
            {
                logWarning("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
                result=false;
            }
        } catch (TimeoutException var8) {
            logWarning("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
            result=false;
        }
        return  result;
    }
    public void commonActionClick( By elementToClick,int timeToWaitInSeconds){
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
            Actions act  = new Actions(driver);
            if(element!=null){
                act.click(element).build().perform();

                log("Clicked element : "+elementToClick);}
            else
            {
                Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
            }
        } catch (TimeoutException var8) {
            logger.fail("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitInSeconds);
        }
    }

    public void commonClickImplicitWait( By elementToClick,int timeToWaitElementToBeClickableInSeconds,int timeToWaitForPageLoad){
        WebElement element;
        try {
            Thread.sleep(timeToWaitForPageLoad);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitElementToBeClickableInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
            if(element!=null){
                element.click();
                log("Clicked element : "+elementToClick);}
            else
            {
                Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitElementToBeClickableInSeconds);
            }
        } catch (TimeoutException | InterruptedException var8) {
            logger.fail("Element not found after wait time  :"+timeToWaitElementToBeClickableInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitElementToBeClickableInSeconds);
        }
    }

/*    public void commonFluentClick( By elementToClick,int timeToWaitElementToBeClickableInSeconds,int poolingTimeInSec){
        WebElement element;
        try {
            element= fluentWait(timeToWaitElementToBeClickableInSeconds,poolingTimeInSec,elementToClick);
            if(element!=null){
                element.click();
                log("Clicked element : "+elementToClick);}
            else
            {
                Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitElementToBeClickableInSeconds);
            }
        } catch (NoSuchElementException var8) {
            logger.fail("Element not found after wait time  :"+timeToWaitElementToBeClickableInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementToClick + "after wait time "+timeToWaitElementToBeClickableInSeconds);
        }
    }*/

   /* public WebElement fluentWait(int timeoutInSec, int checkEveryInSec, By elementToClick )
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSec))
                .pollingEvery(Duration.ofSeconds(checkEveryInSec))
                .ignoring(NoSuchElementException.class);
        return  wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
    }*/

    public int findAndReturnElementsCount(By byElementToFind )
    {
       // log("No of element found are "+driver.findElements(byElementToFind).size());
            return driver.findElements(byElementToFind).size();
    }

    public int convertStringToInt(String text){
        return Integer.parseInt(text);
    }

    public List<WebElement> findAndReturnElements(By byElementToFind )
    {
        //log("No of element found are "+driver.findElements(byElementToFind).size());
        return driver.findElements(byElementToFind);
    }
    public void log(String message)
    {
        logger.info(message);
        loggerSLF.info(message);
    }
    public void logWarning(String message)
    {
        logger.warning(message);
        loggerSLF.warn(message);
    }
    public boolean commonFindElementReturnBool( By elementTofind,int timeToWaitInSeconds){
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(elementTofind));
            log("Found element : "+elementTofind);
        } catch (TimeoutException var8) {
            logger.fail("Element not found after wait time  :"+timeToWaitInSeconds+" Exception "+ var8);
            Assert.fail("Element not found with xpath "+elementTofind);
        }
        return element != null;
    }

    public boolean findElementPrintWarningIfNotFound(By elementTofind, int timeToWaitInSeconds){
        WebElement element = null;
        try {

            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementTofind));
            if(element.isDisplayed()) {
                log("Found element : " + elementTofind);
            }
            else
            {
                logWarning("Element not found"+elementTofind);
            }

        } catch (TimeoutException var8) {
            logWarning("Element not found"+elementTofind);
        }
        return element != null;
    }

    public boolean clickElementFindErrorPrintWarning(By elementToClick, By errorElementToFind, int timeToWaitInSeconds){
        WebElement element = null;
        try {
            hardWait(1000);
            click(elementToClick);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(errorElementToFind));
            if(element.isDisplayed()) {
                log("Found element : " + errorElementToFind);
            }
            else
            {
                logWarning("Element not found"+errorElementToFind);
            }

        } catch (TimeoutException var8) {
            logWarning("Element not found"+errorElementToFind);
        }
        return element != null;
    }

    public boolean clickElementFindErrorPrintWarning(By elementToClick, By errorElementToFind, int timeToWaitInSeconds,boolean setAppContextNative){
        WebElement element = null;
        try {
            if(setAppContextNative)
            {
                setAppContextNative();
            }
            else
            {
                setAppContextWeb();
            }
            click(elementToClick);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(errorElementToFind));
            if(element.isDisplayed()) {
                log("Found element : " + errorElementToFind);
            }
            else
            {
                logWarning("Element not found"+errorElementToFind);
            }

        } catch (TimeoutException var8) {
            logWarning("Element not found"+errorElementToFind);
        }
        return element != null;
    }

    public boolean clickElementEnterInvalidTextFindErrorPrintWarning(By elementToClick, String invalidText, By elementToFind, int timeToWaitInSeconds){
        WebElement element = null;
        try {
            enterTextAfterClick(elementToClick,invalidText,5);
            driver.findElement(elementToClick).sendKeys(Keys.TAB);
            click(elementToClick);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementToFind));
            log("Found element : " + elementToFind);

        } catch (TimeoutException var8) {
            logWarning("Element not found"+elementToFind);
        }
        return element != null;
    }

    public void click(By elementToClick)
    {
        try {
                driver.findElement(elementToClick).click();
                log("Clicked element : "+elementToClick);
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+elementToClick+ e);
            throw e;
        }
    }
    public void click(By elementToClick,String elementName)
    {
        try {
            driver.findElement(elementToClick).click();
            log("Clicked element : "+elementName);
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+elementToClick+ e);
            throw e;
        }
    }

    public void commonClickTab(By elementToClick)
    {
        try {
            if(driver.findElements(elementToClick).size()>0) {
                driver.findElement(elementToClick).click();
                driver.findElement(elementToClick).sendKeys(Keys.TAB);
                log("Clicked element : "+elementToClick);

            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+elementToClick, ExtentColor.RED));
                Assert.fail("Element not found with xpath "+elementToClick);
            }

        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+elementToClick+ e);
            throw e;
        }
    }


    public void enterText(By byElement, Object text)
    {
        try {
            if(driver.findElements(byElement).size()>0) {
                driver.findElement(byElement).sendKeys(text.toString());
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }
    public void enterTextForPassword(By byElement, Object text)
    {
        try {
            if(driver.findElements(byElement).size()>0) {
                driver.findElement(byElement).sendKeys(text.toString());
                log("Entered text ******* in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void keyboardAction(By byElement, Keys key)
    {
        try {
            if(driver.findElements(byElement).size()>0) {
                driver.findElement(byElement).sendKeys(key);
                log("Pressed key " + key + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to find control with xpath "+byElement+ e);
            throw e;
        }
    }

    public Set<String> getContext()
    {
    Set<String> handles = ((SupportsContextSwitching) driver).getContextHandles();
        log("Switched to Native view");
        return handles;
    }

    public void setAppContextNative()
    {
        ((SupportsContextSwitching) driver).context("NATIVE_APP");
        log("Switched to Native view");

    }

    // this method is not working, use swipe down instead
    public void scrollWeb(String x, String y)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy("+x,y+")", "");
    }
    public void scrollDownWeb()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-250)", "");

    }

    public void swipeDown(By by)
    {
        if(verifyElementShown(by,10)) {
            Point source = driver.findElement(by).getLocation();
            PointerInput finger = new PointerInput(TOUCH, "finger");
            Sequence sequence = new Sequence(finger, 1);
            sequence.addAction(finger.createPointerMove(ofMillis(0),
                    PointerInput.Origin.viewport(), source.x, source.y));
            sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()));
            sequence.addAction(new Pause(finger, ofMillis(600)));
            sequence.addAction(finger.createPointerMove(ofMillis(600),
                    PointerInput.Origin.viewport(), source.x, source.y - 800));
            sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
            ((AndroidDriver) driver).perform(List.of(sequence));
            log("swipe down");
        }
    }

    public void switchWifi()
    {
        ((AndroidDriver) driver).toggleWifi();
        log("Toggled WIFI");
    }

    public void switchWifi(int waitTimeMilliSec)
    {
        hardWait(waitTimeMilliSec);
        ((AndroidDriver) driver).toggleWifi();
        log("Toggled WIFI");
        hardWait(1000);
        if(findElementNativeContainsTextReturnBool("OK")) {
            clickElementWithTextNative("OK",5);
        }
    }

    public void changeDeviceLanguage(String languageCode) {
        try {
            String command = String.format("adb shell am broadcast -a android.intent.action.LOCALE_CHANGED --es lang %s", languageCode);
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swipeDown(By by,int value)
    {
        Point source = driver.findElement(by).getLocation();
        PointerInput finger = new PointerInput(TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), source.x, source.y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.FORWARD.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));
        sequence.addAction(finger.createPointerMove(ofMillis(600),
                PointerInput.Origin.viewport(), source.x , source.y-value));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.FORWARD.asArg()));
        try {
            ((AndroidDriver) driver).perform(List.of(sequence));
            log("swipe down, element "+by+ " value points "+value);
        }
        catch (Exception e)
        {
            log("Error in swiping down, "+e.getMessage());
            Assert.fail();
        }
    }

/*    public void swipeDown(By startby, By endBy){
        WebElement start = driver.findElement(startby);
        WebElement end = driver.findElement(endBy);
        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        action.press((PointOption) start).moveTo((PointOption) end).release().perform();
    }*/

    public void swipeDownUntilElementLocated(By byElementToView){
        String previousPageSource="";
        int count =0;
        while (driver.findElements(byElementToView).size()==0 && !previousPageSource.equals(driver.getPageSource())){
            previousPageSource=driver.getPageSource();
            swipeDown();
            count++;
            if(count>30){
                log("Swipe count exceeded 30");
                break;
            }
        }
        if(driver.findElements(byElementToView).size()>0){
        log("Element located "+byElementToView);}
        else if(previousPageSource.equals(driver.getPageSource())){
            log("Element not located and end of page is reached, element "+byElementToView);
        }
        else{
            log("Error in swipe");
        }
    }

    public void swipeCompleteDownUntilElementLocated(By byElementToView){
        String previousPageSource="";
        while (driver.findElements(byElementToView).size()==0 && !previousPageSource.equals(driver.getPageSource())){
            previousPageSource=driver.getPageSource();
            swipeCompleteDown();
        }
        if(driver.findElements(byElementToView).size()>0){
            log("Element located "+byElementToView);}
        else if(previousPageSource.equals(driver.getPageSource())){
            log("Element not located and end of page is reached, element "+byElementToView);
        }
        else{
            log("Error in swipe");
        }
    }

    public void swipeCompleteRightUntilElementLocated(By byElementToView){
        String previousPageSource="";
        while (driver.findElements(byElementToView).size()==0 && !previousPageSource.equals(driver.getPageSource())){
            previousPageSource=driver.getPageSource();
            swipeCompleteRight();
        }
        if(driver.findElements(byElementToView).size()>0){
            log("Element located "+byElementToView);}
        else if(previousPageSource.equals(driver.getPageSource())){
            log("Element not located and end of page is reached, element "+byElementToView);
        }
        else{
            log("Error in right swipe");
        }
    }

    public boolean swipeDownAfterClickUntilElementLocatedReturnBool(By byElementToView){
        String previousPageSource="";
        boolean result = true;
        while (driver.findElements(byElementToView).size()==0 && !previousPageSource.equals(driver.getPageSource())){
            previousPageSource=driver.getPageSource();
            swipeDownAfterClick();
        }
        if(driver.findElements(byElementToView).size()>0){
            log("Element located "+byElementToView);}
        else if(previousPageSource.equals(driver.getPageSource())){
            result=false;
            log("Element not located and end of page is reached, element "+byElementToView);
        }
        else{
            result=false;
            log("Error in swipe");
        }
        return  result;
    }
    public boolean swipeDownUntilElementLocatedReturnBool(By byElementToView){
        String previousPageSource="";
        boolean result = true;
        while (driver.findElements(byElementToView).size()==0 && !previousPageSource.equals(driver.getPageSource())){
            previousPageSource=driver.getPageSource();
            swipeDown();
        }
        if(driver.findElements(byElementToView).size()>0){
            log("Element located "+byElementToView);}
        else if(previousPageSource.equals(driver.getPageSource())){
            result=false;
            log("Element not located and end of page is reached, element "+byElementToView);
        }
        else{
            result=false;
            log("Error in swipe");
        }
        return  result;
    }
    public boolean swipeDownUntilElementLocatedReturnBool(By byElementToView,By byElementToStartScroll, int timeToWaitInSeconds)
    {
        String previousPageSource="";
        boolean result = true;
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(byElementToStartScroll));
        element.click();
        if(element.isDisplayed()){
        while (driver.findElements(byElementToView).size()==0 && !previousPageSource.equals(driver.getPageSource()))
        {
            previousPageSource=driver.getPageSource();
            swipeDown();
        }
        if(driver.findElements(byElementToView).size()>0){
            swipeDownAfterView();
            log("Element located "+byElementToView);
        }
        else if(previousPageSource.equals(driver.getPageSource())){
            result=false;
            log("Element not located and end of page is reached, element "+byElementToView);
        }
        else{
            result=false;
            log("Error in swipe");
        }
        }
        else {
            result=false;
            log("Page load error, element not located"+byElementToStartScroll);
        }
        return  result;
    }
    public void swipeDown(){
        Dimension size = driver.manage().window().getSize();
        int startX=size.getWidth()/2;
        int startY=size.getHeight()/2;
        int endX=size.getWidth()/2;
        int endY=(int)(size.getHeight()*0.25);
        swipeDown(startX,startY,endX,endY);
    }

    public void swipeDownAfterClick(){
        Dimension size = driver.manage().window().getSize();
        int startX=size.getWidth()/2;
        int startY=size.getHeight()/2;
        int endX=size.getWidth()/2;
        int endY=(int)(size.getHeight()*0.25);
        Actions act = new Actions(driver);
        act.moveByOffset(startX,startY).click().build().perform();
        log("Click on coordinates "+startX+ " "+startY);
        swipeDown(startX,startY,endX,endY);
    }

    public void swipeDownAfterView(){
        Dimension size = driver.manage().window().getSize();
        int startX=size.getWidth()/2;
        int startY=size.getHeight()/2;
        int endX=size.getWidth()/2;
        int endY=(int)(size.getHeight()*0.1);
        swipeDown(startX,startY,endX,endY);
    }
    public void swipeCompleteDown(){
        Dimension size = driver.manage().window().getSize();
        int startX=size.getWidth()/2;
        int startY=size.getHeight()/2;
        int endX=size.getWidth()/2;
        int endY=size.getHeight();
        swipeDown(startX,startY,endX,endY);
    }

    public void swipeCompleteRight(){
        Dimension size = driver.manage().window().getSize();
        int startX=size.getWidth()/2;
        int startY=size.getHeight()/2;
        int endX=size.getWidth();
        int endY=size.getHeight()/2;
        swipeDown(startX,startY,endX,endY);
    }


    public void swipeDownSmartEx(){
        Dimension size = driver.manage().window().getSize();
        int startX=size.getWidth()/2;
        int startY=size.getHeight()/2;
        int endX=size.getWidth()/2;
        int endY=(int)(size.getHeight()*0.25);
        swipeDown(startX,startY,endX,endY);
    }
    public void swipeDown(int startX,int startY, int endX,int endY){

        PointerInput finger = new PointerInput(TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(finger.createPointerMove(ofMillis(600), PointerInput.Origin.viewport(),endX,endY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
        try {
            ((AndroidDriver) driver).perform(List.of(sequence));
            log("swipe down");
        }
        catch (Exception e)
        {
            log("Error in swiping down, "+e.getMessage());
            Assert.fail();
        }

    }
    public void hideKeyboard()
    {
        ((AndroidDriver)driver).hideKeyboard();
        log("Hide Keyboard");
    }

    public void scrollAndClick(By byElementToClick,By byElementToScroll)
    {
        int count =0;
        if(verifyElementShown(byElementToScroll,10)) {
            while (findAndReturnElementsCount(byElementToClick) == 0 && count < 8) {
                swipeDown(byElementToScroll, 400);
                count++;
            }
            click(byElementToClick, 5);
        }
    }

    public void setAppContextWeb()
    {
        ((SupportsContextSwitching) driver).context("WEBVIEW_com.sap.csc.dcs.mobile");
        log("Switched to Web view");
    }

    public void switchWindowsTab(boolean navigateBack)
    {
        Set<String> handles = driver.getWindowHandles();
        String currentHandle = driver.getWindowHandle();
        for (String handle : handles) {
            if (!handle .equals(currentHandle))
            {
                driver.switchTo().window(handle);
                log("Switched to new tab");

            }
        }
        if(navigateBack){
            driver.switchTo().window(currentHandle);
        }
    }

    public void setContext(String context)
    {
        ((SupportsContextSwitching) driver).context(context);
        log("Switched to"+context+"view");
    }



    public int getRandomNumber(int rangeMin,int rangeMax)
    {
        Random random = new Random();
        return random.nextInt(rangeMin) + rangeMax;
    }
    public String randomStringGen(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public void enterText(By byElement, Object text,int timeToWaitInSeconds)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                element.sendKeys(text.toString());
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void enterText(By byElement, Object text,int timeToWaitInSeconds,boolean clear)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                if(clear){
                element.clear();}
                element.sendKeys(text.toString());
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }


    public void enterTextAfterClick(By byElement, String text,int timeToWaitInSeconds)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
                element.click();
                element.sendKeys(text);
                log("Entered text " + text + " in text box with xpath " + byElement);

        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void enterTextAfterClickTab(By byElement, String text,int timeToWaitInSeconds)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                driver.findElement(byElement).click();
                driver.findElement(byElement).sendKeys(text);
                driver.findElement(byElement).sendKeys(Keys.TAB);
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void enterTextAfterClickTab(By byElement, String text,int timeToWaitInSeconds,Boolean clear)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                if(clear){
                    element.clear();
                }
                driver.findElement(byElement).click();
                driver.findElement(byElement).sendKeys(text);
                driver.findElement(byElement).sendKeys(Keys.TAB);
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void sendKeys(By byElement, Keys key,int timeToWaitInSeconds)
    {
        try{
                driver.findElement(byElement).sendKeys(key);
                log("sendkeys " + key);
        }
        catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void enterTextAfterClickWithWait(By byElement, String text,int timeToWaitInSeconds)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                element.click();
                wait.until(ExpectedConditions.elementToBeClickable(byElement)).sendKeys(text);
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }
    public void navigateBack(){
        driver.navigate().back();
        log("Navigate Back to hide keyboard");
    }

    public void enterTextAfterClick(By byElement, String text,int timeToWaitInSeconds,boolean clear)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                if(clear)
                {
                    element.clear();
                }
                element.click();
                element.sendKeys(text);
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void enterTextAfterClickFast(WebElement byElement, String text)
    {
        try {
            byElement.click();
            byElement.sendKeys(text);
            byElement.sendKeys(Keys.TAB);
            log("Entered bid amount : "+text);

        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }

    public void enterTextAfterClick(WebElement byElement, String text,int timeToWaitInSeconds,boolean clear)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if(element!=null) {
                if(clear)
                {
                    element.clear();
                }
                element.click();
                element.sendKeys(text);
                log("Entered text " + text + " in text box with xpath " + byElement);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+byElement, ExtentColor.RED));
                Assert.fail ("Element not found with xpath "+byElement);
            }
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+ e);
            throw e;
        }
    }


    public void
    enterTextAfterClick(By byElement, String text,String elementName,int timeToWaitInSeconds,boolean clear)
    {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeToWaitInSeconds));
            element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
                if(clear)
                {
                    element.clear();
                }
                element.click();
                element.sendKeys(text);
                log("Entered text " + text + " in text box with element name " + elementName);
        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+byElement+" Exception "+e.getMessage());
            Assert.fail ("Element not found  "+elementName);
        }
    }
    public void click(AppiumBy elementToClick)
    {
        try {
            if(driver.findElements(elementToClick).size()>0) {
                driver.findElement(elementToClick).click();
                log("Clicked element : "+ elementToClick);
            }
            else
            {
                logger.log(Status.FAIL, MarkupHelper.createLabel( "Element not found with xpath "+elementToClick, ExtentColor.RED));
                Assert.fail("Element not found with xpath "+elementToClick);
            }

        } catch (Exception e) {
            logger.fail("Failed to click control with xpath "+elementToClick+  e);
            throw e;
        }
    }

    public void clickElementWithText(String text,int waitTime){
            By by = By.xpath("(//*[text()='"+text+"'])[1]");
            click(by,waitTime);
    }

    public Boolean findTicketFieldRuleReturnBool(String fieldName,String display,String defaultValue){
        if(defaultValue==null){
            defaultValue="NO";
        }
        By by = By.xpath("//td/*[text()='"+fieldName+"']/parent::td/following-sibling::td/*[text()='"+display+"']/parent::td/following-sibling::td/*[text()='"+defaultValue+"']");
        int count = findAndReturnElementsCount(by);
        return findAndReturnElementsCount(by)>0;
    }

    public void clickElementWithTextActions(String text,int waitTime){
        By by = By.xpath("(//*[text()='"+text+"'])[1]");
        commonActionClick(by,waitTime);
    }
    public void clickElementWithContainsText(String text,int waitTime){
        commonFindAndClick(By.xpath("//*[contains(text(),'"+text+"')]"),waitTime);
    }
    public void clickElementWithContainsTextNative(String text,int waitTime){
        commonFindAndClick(By.xpath("//*[contains(@text,'"+text+"')]"),waitTime);
    }

    public void clickElementWithContainsTextNative(String text,String elementName,int waitTime){
        click(By.xpath("//*[contains(@text,'"+text+"')]"),elementName,waitTime);
    }
    public boolean verifyRulePresent(String areaChecked,String fieldName){
        if(fieldName.contains(" ")){
            fieldName=fieldName.replace(" ","");
        }
        if(fieldName.contains("  ")){
            fieldName=fieldName.replace("  ","");
        }
        if(fieldName.equals("AviationCallOutCharge")){
            fieldName="AviationCalloutCharge";
        }
        if(fieldName.equals("AviationOverWingServiceFee")){
            fieldName="AviationOverwingServiceFee";
        }
        return verifyElementShown(By.xpath("//*[@aria-checked='"+areaChecked+"' and contains(@id,'tc"+fieldName+"')]"),5);
    }
    public boolean verifyRuleMessagePrompt(String fieldName){
        return verifyElementShown(By.xpath("//*[text()='"+fieldName+" enabled by default for this ticket. Do you want to continue?']"),10);
    }
    public boolean verifyRuleNotPresent(String fieldName){
        if(fieldName.contains(" ")){
            fieldName=fieldName.replace(" ","");
        }
        if(fieldName.contains("  ")){
            fieldName=fieldName.replace("  ","");
        }
      return findAndReturnElementsCount(By.xpath("//*[contains(@id,'ticketCaptureDetails--tc"+fieldName+"Id')]"))==0;
    }
    public void actionClickElementWithContainsText(String text,int waitTime){
        commonActionClick(By.xpath("//*[contains(text(),'"+text+"')]"),waitTime);
    }
    public void clickElementWithContainsText(String xpath,String text,int waitTime){
        By by = By.xpath(xpath+"[contains(text(),'"+text+"')]");
        commonFindAndClick(by,waitTime);
    }

    public void clickElementWithContainsTextWithAnd(String xpath,String text,int waitTime){
        By by = By.xpath(xpath+"contains(text(),'"+text+"')]");
        scrollElementIntoViewWeb(by,5);
        commonFindAndClick(by,waitTime);
    }

    public void clickElementWithText(String xpath,String text,int waitTime){
        By by = By.xpath(xpath+"[text()='"+text+"']");
        commonFindAndClick(by,waitTime);
    }

    public void moveAndClickClickElementWithText(String xpath,String text,int waitTime){
        By by = By.xpath(xpath+"[text()='"+text+"']");
        scrollElementIntoViewWeb(by,5);
        hardWait(2000);
        click(by,5);
    }
    public void clickElementWithText(String text,int waitTime,String htmlIdentifier){

        By by = By.xpath("(//"+htmlIdentifier+"[text()='"+text+"'])[1]");
        click(by,waitTime);
    }

    public void clickElementWithTextNative(String text,int waitTime,String htmlIdentifier){
        By by = By.xpath("(//"+htmlIdentifier+"[@text='"+text+"'])[1]");
        click(by,waitTime);
    }

    public By returnByWithText(String text){
        return By.xpath("(//*[text()='"+text+"'])[1]");
    }

    public void clickElementWithTextNative(String text,int waitTime){
        By by = By.xpath("(//*[@text='"+text+"'])[1]");
        click(by,waitTime);
    }

    public void clickElementWithTextNativeAndWeb(String text,int waitTime)
    {
        By byNative = By.xpath("(//*[@text='"+text+"'])[1]");
        By byWeb = By.xpath("(//*[text()='"+text+"'])[1]");
        setAppContextNative();
        if(findAndReturnElementsCount(byNative)>0)
        {
        click(byNative,waitTime);
        }
        setAppContextWeb();
        if(findAndReturnElementsCount(byWeb)>0)
        {
            click(byWeb,waitTime);
        }
        setAppContextNative();
    }


    public boolean findElementWithTextReturnBool(String text,int waitTime){
        By by = By.xpath("//*[text()='"+text+"']");
        hardWait(waitTime);
        if(driver.findElements(by).size()>0)
        {
            log("Found Element with text: "+by);
        }
       return driver.findElements(by).size()>0;
    }
    public boolean findElementWithValueReturnBool(String text,int waitTime){
        By by = By.xpath("//*[@value='"+text+"']");
        if(driver.findElements(by).size()>0)
        {
            log("Found Element with text: "+by);
        }
        return driver.findElements(by).size()>0;
    }
    public boolean findElementWithTextReturnBool(String text){
        By by = By.xpath("(//*[text()='"+text+"'])[1]");
        if(driver.findElements(by).size()>0)
        {
            log("Element found with text "+text);
        }
        else
        {
            logger.warning("Element not found with text "+text);
        }
        return driver.findElements(by).size()>0;
    }

    public boolean verifyElementWithTextShown(String text){
        By by = By.xpath("(//*[@text='"+text+"'])[1]");
        if(driver.findElements(by).size()>0)
        {
            log("Element found with text "+text);
        }
        else
        {
            logger.warning("Element not found with text "+text);
        }
        return driver.findElements(by).size()>0;
    }

    public boolean findElementContainsTextReturnBool(String text){
        By by = By.xpath("//*[contains(text(),'"+text+"')]");
        if(driver.findElements(by).size()>0)
        {
            log("Element found with text "+text);
        }
        else
        {
            logger.warning("Element not found which contains text "+text);
        }
        return driver.findElements(by).size()>0;
    }


    public boolean findElementNativeContainsTextReturnBool(String text){
        By by = By.xpath("//*[contains(@text,'"+text+"')]");
        if(driver.findElements(by).size()>0)
        {
            log("Element found with text "+text);
        }
        else
        {
            log("Element not found with text "+text);
        }
        return driver.findElements(by).size()>0;
    }

    public By findElementNativeContainsTextReturnElement(String text){
        return By.xpath("//*[contains(@text,'"+text+"')]");
    }

    public By findElementNativeTextReturnElement(String text){
        return By.xpath("//*[(@text='"+text+"')]");
    }

    public By findElementWebContainsTextReturnElement(String text){
        return By.xpath("//*[contains(text(),'"+text+"')]");
    }

    public boolean findElementNativeContainsTextReturnBoolWithoutWarn(String text){
        By by = By.xpath("//*[contains(@text,'"+text+"')]");
        if(driver.findElements(by).size()>0)
        {
            log("Element found with text "+text);
        }
        else
        {
            log("Element not found with text "+text);
        }
        return driver.findElements(by).size()>0;
    }



    public void hardWait(int millSec)
    {
        try {
            Thread.sleep(millSec);
            log("Hard wait in milli sec "+millSec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String findElementGetTextAttribute(By elementToFind){
        if(driver.findElements(elementToFind).size()>0)
        {
            return driver.findElement(elementToFind).getAttribute("text");
        }
        else
        {
            Assert.fail("Failed to fimd the control with xpath"+elementToFind);
            return null;
        }
    }

    public String findElementGetAttribute(By elementToFind, String attribute){
        if(driver.findElements(elementToFind).size()>0)
        {
            var test =driver.findElement(elementToFind).getAttribute(attribute);
            return driver.findElement(elementToFind).getAttribute(attribute);
        }
        else
        {
            Assert.fail("Failed to fimd the control with xpath"+elementToFind);
            return null;
        }
    }

    public String findElementGetText(By elementToFind){
        if(driver.findElements(elementToFind).size()>0)
        {
            String test =driver.findElement(elementToFind).getText();
            return driver.findElement(elementToFind).getText();
        }
        else
        {
            Assert.fail("Failed to find the control with xpath"+elementToFind);
            return null;
        }

    }




    public String getTimeInFormatAndRemove(String format,String timezone, String separator){
        String time = getCurrentDateTimeAsString(format,timezone);
        return time.replace(separator,"");
    }

    public String getDateInFormatAndRemove(String format,String timezone, String separator){
        String time = getCurrentDateTimeAsString(format,timezone);
        return time.replace(separator,"");
    }

    public String getTimeInFormatAndRemove(String format,String timezone, String separator,int addTimeInMins){
        String time = getCurrentDateTimeAsString(format,timezone,addTimeInMins);
        return time.replace(separator,"");
    }





    public void clickElementWithAttributeWhichContainsText(String attribute,String text){
        try {
            By byClickElement = By.xpath("//*[contains(@"+attribute+",'"+text+"')]");
            click(byClickElement);
        } catch (Exception e) {
            Assert.fail("Exception in login {}",e);
            throw e;
        }
    }

    public boolean findElementWithTextInListWithIndex(String xpathExpression,String text,int index){
            By byText = By.xpath(xpathExpression+index+"')]//*[contains(text(),'"+text+"')])[1]");
            verifyResult(driver.findElements(byText).size()>0,"Element verified with text"+text);
            return driver.findElements(byText).size()>0;
    }

    public void findTicketNumberVerifyShown(String ticketNumber){
        setAppContextNative();
      By byXpath=By.xpath("//android.widget.TextView[@text='"+ticketNumber+"' and contains(@resource-id,'Table')]");
      verifyResult(driver.findElements(byXpath).size()>0,"Ticket number is shown "+ticketNumber);
    }

    public void findTicketNumberVerifyShown(String ticketNumber,String resourceId){
        setAppContextNative();
        By byXpath=By.xpath("//android.widget.TextView[@text='"+ticketNumber+"' and contains(@resource-id,"+resourceId+")]");
        verifyResult(driver.findElements(byXpath).size()>0,"Ticket number is shown "+ticketNumber);
        click(byXpath);
    }
    public void findTicketNumberVerifyShownClick(String ticketNumber){
        By byXpath=By.xpath("//android.widget.TextView[@text='"+ticketNumber+"' and contains(@resource-id,'allTickets--ticketsTable-0')]");
        verifyResult(driver.findElements(byXpath).size()>0,"Ticket number is shown "+ticketNumber);
        driver.findElement(byXpath).click();
    }

    public void findTicketNumberVerifyNotShown(String ticketNumber){
        By byXpath=By.xpath("//android.widget.TextView[@text='"+ticketNumber+"' and contains(@resource-id,'allTickets--ticketsTable-0')]");
        verifyResult(driver.findElements(byXpath).size()==0,"Ticket number is not shown "+ticketNumber);
    }




    public boolean findElementWithTextInTableWithIndex(String xpathExpression,String text,int index){
        By byText = By.xpath(xpathExpression+index+"]/td[2]//*[contains(text(),'"+text+"')]");
        verifyResult(driver.findElements(byText).size()>0,"Element verified with text"+text);
        return driver.findElements(byText).size()>0;
    }

    public void verifyResult(Boolean condition, String message)
    {
        if(condition)log(message);
        else Assert.fail("Condition not satisfied for message  -: "+message);
    }

    public void verifyConditionPrintMessageWarning(Boolean condition,String message)
    {
        if(condition) log(message);
        else logWarning("Condition not satisfied for message "+message);
    }


    public String getCurrentDateTimeAsString(String format,String timeZone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String getCurrentDateTimeAsString(String format,String timeZone,int addTimeMinutes) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date();
        Calendar timeObject = Calendar.getInstance();
        timeObject.add(Calendar.MINUTE, addTimeMinutes);
        return dateFormat.format(timeObject.getTime());
    }


    public String getCurrentDateTimeAsStringAddDays(String format,String timeZone,int addDays) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar timeObject = Calendar.getInstance();
        timeObject.add(Calendar.DATE, addDays);
        return dateFormat.format(timeObject.getTime());
    }

    public FileInputStream getFileInputStreamFromDownloadPathAs(String downloadPath, String fileName)
    {
        hardWait(7000);
        FileInputStream input;
        File dir = new File(downloadPath);
        //Change to Agent User id while commit
        FilenameFilter txtFileFilter = (dir1, name) -> name.startsWith(fileName);
        File[] files = dir.listFiles(txtFileFilter);
        Assert.assertNotNull(files);
        if (files.length > 0) {
            logger.info("File Downloaded successfully");
        } else {
            logger.fail("File not downloaded successfully :");

        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        try {
             input = new FileInputStream(downloadPath + "//" + files[0].getName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return input;
    }


    public Map<String,String> getDownloadedExcelDataForUniqueID(FileInputStream input,String uniqueId, String sheetName,int rowNumberColumnNames,int uniqueColumnNumber,String[] sequenceColumns) throws IOException {

        Map<String, String> rowMap = new HashMap<>();
        String[] columnNames = null;
        boolean result=false;
        int count = 0;
        int columnNameCounter=0;

        XSSFWorkbook wb = new XSSFWorkbook(input);
        Sheet excelSheet = wb.getSheet(sheetName);

        for (Row row : excelSheet)
        {if (count == rowNumberColumnNames) {
                int columnCount = row.getLastCellNum() - row.getFirstCellNum();
                columnNames = new String[columnCount];
                for (Cell cell : row) {
                    columnNames[columnNameCounter] = cell.toString();
                    columnNameCounter++;
                }
                verifyResult(Arrays.equals(columnNames, sequenceColumns),"Column sequence matched actual "+ Arrays.toString(columnNames) +" expected "+ Arrays.toString(sequenceColumns));
                count++;
            }
            else if(count>rowNumberColumnNames)
            {for (Cell cell : row)
                {
                    if(uniqueId.equals(row.getCell(uniqueColumnNumber).getStringCellValue()))
                    {
                        if(cell.getCellType()!= CellType.BLANK){
                            if(cell.getCellType()==CellType.STRING) {
                                assert columnNames != null;
                                rowMap.put(columnNames[cell.getColumnIndex()], cell.toString());
                            }
                            else if(cell.getCellType()==CellType.NUMERIC) {
                                assert columnNames != null;
                                rowMap.put(columnNames[cell.getColumnIndex()], Integer.toString((int) cell.getNumericCellValue()));
                            }
                            else
                                Assert.fail("Data provided in excel is not in correct format");
                        }
                        else
                        {
                            assert columnNames != null;
                            rowMap.put(columnNames[cell.getColumnIndex()], "");
                        }
                        result = true;
                        //cellCount++;
                    }
                }
            }
            if(result)
                break;
            count++;
        }
        Assert.assertTrue(result,"Unique Id :"+ uniqueId + " not found in excel sheet ");


        return rowMap;
    }
/*    public Map<String,String> getDownloadedExcelDataForUniqueID(FileInputStream input,String uniqueId, String sheetName,int rowNumberColumnNames,int uniqueColumnNumber) throws IOException {

        Map<String, String> rowMap = new HashMap<>();
        String[] columnNames = null;
        boolean result=false;
        int count = 0;
        int columnNameCounter=0;

        XSSFWorkbook wb = new XSSFWorkbook(input);
        Sheet excelSheet = wb.getSheet(sheetName);

        for (Row row : excelSheet)
        {if (count == rowNumberColumnNames) {
            int columnCount = row.getLastCellNum() - row.getFirstCellNum();
            columnNames = new String[columnCount];
            for (Cell cell : row) {
                columnNames[columnNameCounter] = cell.toString();
                columnNameCounter++;
            }
            count++;
        }
        else if(count>rowNumberColumnNames)
        {for (Cell cell : row)
        {
            if(uniqueId.equals(row.getCell(uniqueColumnNumber).getStringCellValue()))
            {
                if(cell.getCellType()!= CellType.BLANK){
                    if(cell.getCellType()==CellType.STRING) {
                        assert columnNames != null;
                        rowMap.put(columnNames[cell.getColumnIndex()], cell.toString());
                    }
                    else if(cell.getCellType()==CellType.NUMERIC) {
                        assert columnNames != null;
                        rowMap.put(columnNames[cell.getColumnIndex()], Integer.toString((int) cell.getNumericCellValue()));
                    }
                    else
                        Assert.fail("Data provided in excel is not in correct format");
                }
                else
                {
                    assert columnNames != null;
                    rowMap.put(columnNames[cell.getColumnIndex()], "");
                }
                result = true;
                //cellCount++;
            }

        }
        }
            if(result)
                break;
            count++;
        }
        Assert.assertTrue(result,"Unique Id :"+ uniqueId + " not found in excel sheet ");


        return rowMap;
    }*/
   /* public Boolean verifyDownloadedExcelColumnSequence(FileInputStream input,String[] sequence, String sheetName,int rowNumberColumnNames) throws IOException {
        Map<String, String> rowMap = new HashMap<>();
        String[] columnNames = null;
        boolean result = false;
        int count = 0;
        int columnNameCounter = 0;

        XSSFWorkbook wb = new XSSFWorkbook(input);
        Sheet excelSheet = wb.getSheet(sheetName);
        for (Row row : excelSheet) {
            if (count == rowNumberColumnNames) {
                int columnCount = row.getLastCellNum() - row.getFirstCellNum();
                columnNames = new String[columnCount];
                for (Cell cell : row) {
                   verifyConditionPrintMessage(Objects.equals(sequence[columnNameCounter], cell.toString()),"Column sequence matched actual "+ cell +" Expected "+sequence[columnNameCounter]);
                    columnNameCounter++;
                }
                break;
            }
            Assert.assertTrue(result, "Sequence not correct Expected :" + sequence + " Actual :" + columnNames);
        }
        return result;
    }*/



    //endregion

}

