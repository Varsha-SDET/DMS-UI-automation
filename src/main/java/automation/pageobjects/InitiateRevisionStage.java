package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
/*
 *@className - InitiateRevisionStage
 *@Objective - To define all the web elements and actions required for Initiate Revision Stage.
 */
public class InitiateRevisionStage extends CommonUtils {
    Logger logger = LogManager.getLogger(QAPendingApprovalStage.class);
    public WebDriver driver;
    public InitiateRevisionStage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//select[@id='select-div']")
    WebElement activitySelectionDropdown;
    @FindBy(xpath = "//select[@id='select-div']/option[@value='14' and contains(text(), 'Initiate Revision')]")
    WebElement initiateRevisionActivity;
    @FindBy(xpath = "//select[@id='select-div']/option[@value='13' and contains(text(), 'Cancel')]")
    WebElement cancelRevisionActivity;
    @FindBy(xpath = "//button[@id='submit']")
    WebElement saveAndProceedButton;
    @FindBy(css = "span[id='alert'] b")
    WebElement errorTextForNotSelectingActivity;
   // @FindBy(xpath = "//*[contains(text(),'Transaction Completed successfully')]")
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successTransactionMsg;

    @FindBy(xpath = "//form[@id='reasonSubmit']")
    WebElement alertForm;
    @FindBy(css = "textarea[name='rejectReason']")
    WebElement reasonForCancelActivity;
    @FindBy(xpath = "//form[@id='reasonSubmit']//button[@id='formSubmit']")
    WebElement proceedButtonInAlertForm;
    @FindBy(xpath = "//div[contains(text(),'Close-Cancelled')]")
    WebElement closedCancelText;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;

    //not selecting activity and try to proceed further stages-> alert displayed -> error message displayed
    public void notSelectionOfActivityForInitiateRevision(){

    }

    //selecting initiate revision activity
    public void inititateRevisionActivitySelection(){
        //for the current stage = revision effective
        if(currentBarStep.getText().contains("Opened")) {
            waitForElementToBeClickable(activitySelectionDropdown);
            activitySelectionDropdown.click();
            initiateRevisionActivity.click();
            saveAndProceedButton.click();
            waitForWebElementToAppear(successTransactionMsg);
            logger.info("success transaction message displayed for initiating revision.");
        }
    }

    //selecting cancel revision activity
    public void cancelRevisionActivitySelection(){

        waitForElementToBeClickable(activitySelectionDropdown);
        activitySelectionDropdown.click();
       // System.out.println(driver.getTitle());
        cancelRevisionActivity.click();
        saveAndProceedButton.click();

        //cancelling the child SOP record created in revision
        reasonForCancelActivity.sendKeys("canceling activity");
        proceedButtonInAlertForm.click();
        waitForWebElementToAppear(closedCancelText);
        System.out.println(closedCancelText.getText());
        logger.info("text for cancelling the activity appears ");

      //  return closedCancelText.getText();

    }

}
