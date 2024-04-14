package automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

/*
 *@className - CommonUtils
 * @classObjective- To define all the common utilities method required for PO and waits for element.
 */
public class CommonUtils {
    WebDriver driver;
    public CommonUtils(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    // visibility of findBy
    public void waitForElementToAppear(By findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    //invisiblity of webelement
    public void waitForElementToDisappear(WebElement ele){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(ele));

    }

    // Get handles of all open windows/tabs and switch to window tab by passing its window ID
    public WebDriver switchToNewWindowTab(String windowHandleID) {
        Set<String> windowHandles = driver.getWindowHandles();

       //  Switch to the new tab
        for (String windowHandle : windowHandles) {
            if (windowHandle.equals(windowHandleID)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        return driver;
    }


    // Method to switch to the latest opened tab
    public WebDriver switchToNewTab(WebDriver driver) {
        String originalHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        handles.remove(originalHandle);
        String newTabHandle = handles.iterator().next();
        driver.switchTo().window(newTabHandle);
        return driver;
    }


    //visiblity of webelement
    public void waitForWebElementToAppear (WebElement ele){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    //wait for new window to load content after displayed.
    public void waitForNewWindow(WebDriver driver){
        // Wait for a new window to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Set<String> windowHandles = driver.getWindowHandles();
                // Wait until there's more than one window handle
                return windowHandles.size() > 1;
            }
        });
    }

    //wait for element to be clickable
    public void waitForElementToBeClickable(WebElement ele){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ele));
        element.click();
    }


    //child window returned
    public String getChildWindowId(WebDriver driver){
        Set<String> windows = driver.getWindowHandles();

        Iterator<String> iterator = windows.iterator();

        String parentId = iterator.next();

        String childId = iterator.next();
        return childId;
    }


    public  void waitForNewWindow(String childID, WebDriver driver) {
        // Get the current window handle
      //  String mainWindowHandle = driver.getWindowHandle();

        // Wait for a new window to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Set<String> windowHandles = driver.getWindowHandles();
                return windowHandles.size() > 1; // Wait until there's more than one window handle
            }
        });

        //[parent id,child id
//        Set<String> windows = driver.getWindowHandles();
//
//        Iterator<String> iterator = windows.iterator();
//
//        String parentId = iterator.next();
//
//        String childId = iterator.next();
//        System.out.println("ParentID :" +parentId);
//        System.out.println("ChildID :" + childId);
//
//        driver.switchTo().window(childId);


        for (String windowHandle : driver.getWindowHandles()) {
            if (windowHandle.equals(childID)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

    }
//        // Now there's a new window, switch to it
//        for (String windowHandle : driver.getWindowHandles()) {
//            if (!windowHandle.equals(mainWindowHandle)) {
//                driver.switchTo().window(windowHandle);
//                break;
//            }
//        }
//    }


    // Method to wait for the child window to be opened
//    public static void waitForChildWindow(WebDriver driver) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.numberOfWindowsToBe(2)); // Change the number if needed
//    }

    // Method to switch to the child window
//    public static void switchToChildWindow(WebDriver driver) {
//        Set<String> handles = driver.getWindowHandles();
//        for (String handle : handles) {
//            driver.switchTo().window(handle);
//        }
//    }

    // Method to switch to the parent window
//    public static void switchToParentWindow(WebDriver driver) {
//        Set<String> handles = driver.getWindowHandles();
//        String parentHandle = driver.getWindowHandle();
//        handles.remove(parentHandle);
//        driver.switchTo().window(handles.iterator().next());
//    }

    //scroll down by screen height
    public void scrollDownByScreenHeight () {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long screenHeight = (long) js.executeScript("return window.innerHeight");

        // Scroll down by screen height until reaching the bottom of the page
        long currentScroll = 0;
        while (currentScroll < screenHeight) {
            js.executeScript("window.scrollBy(0, " + screenHeight + ")");
            currentScroll += screenHeight;
            // Add a small delay to allow content to load before scrolling further
            wait(2000);
        }
    }

    // Method to add a wait time
    private void wait ( int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //scroll to top
    public void scrollToTop(){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Execute JavaScript to scroll to the top of the page
        js.executeScript("window.scrollTo(0, 0);");
        wait(2000);
    }

}
