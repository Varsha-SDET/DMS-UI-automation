package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *@className - DocumentOpenedStage
 *@Objective - To define all the web elements and actions required for document information page.
 */
public class DocumentOpenedStage extends CommonUtils {
    Logger logger = LogManager.getLogger(DocumentOpenedStage.class);
    public WebDriver driver;
    public  DocumentOpenedStage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }


}
