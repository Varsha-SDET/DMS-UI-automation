package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/*
 *@className - PendingDocRevisionStage
 *@Objective - To define all the web elements and actions required for QA PendingApproval Stage.
 */
public class PendingDocRevisionStage extends CommonUtils {
    public WebDriver driver;
    Logger logger = LogManager.getLogger(PendingDocRevisionStage.class);

    public PendingDocRevisionStage(WebDriver driver){
        super(driver);
        this.driver= driver;
        //initialize
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//select[@id='select-div']")
    WebElement activitySelection ;
    @FindBy(xpath = "//select[@id='select-div']/option[@value='16']")
    WebElement submitDocActivitySelection;
    @FindBy(css = "#submit")
    WebElement saveAndProceedButton;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successTransactionMsg;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;
    public String QAReviewCompleteActivitySelection() {

        activitySelection.click();
        submitDocActivitySelection.click();
        saveAndProceedButton.click();
        logger.info("activity selected-> submit Doc Activity Selection");
        waitForWebElementToAppear(successTransactionMsg);
        return successTransactionMsg.getText();
    }

}
