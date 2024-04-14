package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

/*
 *@className - MyTaskPage
 * @classObjective- To define all the web elements and action method for MyTask page
 */
public class MyTaskPage extends CommonUtils {
    Logger logger = LogManager.getLogger(MyTaskPage.class);
    public WebDriver driver;
    public  MyTaskPage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    // Store the handle of the original tab
   // String currentTab = driver.getWindowHandle();
  //  String windowHandleID = null;

    @FindBy(css="button[onclick='openPopup()']")
    WebElement createSOP;
    @FindBy(xpath="(//tr//a)[1]")
    WebElement newSOPRecord;


    public DivisionPage clickCreateSOP(){
        createSOP.click();
        logger.info("create SOP button clicked");
        String childId = getChildWindowId(this.driver);
        waitForNewWindow(driver);
        driver.switchTo().window(childId).manage().window().maximize();
       // Thread.sleep(2000);
       // System.out.println("currentWindowID: "+ driver.getWindowHandle() + " current url:"+ driver.getCurrentUrl());
        logger.info("switched to child window handler and driver object returning");
        return new DivisionPage(driver);

    }


    //click on new record opened
    public void clickNewRecordOpened(){
        newSOPRecord.click();
    }

}
