package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/*
 *@className - PendingHODReviewStage
 *@Objective - To define all the web elements and actions required for Pending HOD Review Stage
 */
public class PendingHODReviewStage extends CommonUtils {
    public WebDriver driver;
    public ElectronicSignatureForm eSign;
    Logger logger = LogManager.getLogger(PendingHODReviewStage.class);

    public PendingHODReviewStage(WebDriver driver){
        super(driver);
        this.driver= driver;
        //initialize
        this.eSign = new ElectronicSignatureForm(driver);
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//select[@id='select-div']")
    WebElement activitySelection ;
    @FindBy(xpath = "//select[@id='select-div']/option[@value='19']")
    WebElement requestChangesActivitySelection;
    @FindBy(xpath = "//select[@id='select-div']/option")
    List<WebElement> activitySelectionsList;
    @FindBy(xpath = "//button[@id='submit']")
    WebElement saveAndProceedButton;
    @FindBy(css = "div[id='ReasonModal'] div[class='modal-content']")
    WebElement reasonForm;
    @FindBy(css = "textarea[name='rejectReason']")
    WebElement rejectReason;
    @FindBy(xpath = "//form[@id='reasonSubmit']//button[@id='formSubmit']")
    WebElement proceedButtonInReasonForm;
    @FindBy(xpath = "//button[@class='cancel']")
    WebElement cancelButtonInReasonForm;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successTransactionMsg;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;

    //select HOD review activity
    public String HODReviewCompleteActivitySelection() {
        //fill data which (same as previous stage but if required can fill it here in this stage then click on qa review activity)
        // dataFillInPendingReview();
        driver.navigate().refresh();
        waitForWebElementToAppear(activitySelection);
        if(currentBarStep.getText().contains("Pending HOD Review")) {
            activitySelection.click();
            // QA review activity selection
            activitySelectionsList.stream().filter(activity -> activity.getText()
                    .contains("HOD Review Complete")).findFirst().ifPresent(WebElement::click);
            saveAndProceedButton.click();
            logger.info("activity selected-> HOD Review Complete and then save and proceed button click");
            waitForWebElementToAppear(eSign.electronicSignatureForm);
            eSign.submitElectronicSignature();
            waitForWebElementToAppear(successTransactionMsg);
            return successTransactionMsg.getText();
        }else {
            return "Not found";
        }
    }

    //select request changes activity
    public String requestChangesActivitySelection(){
        driver.navigate().refresh();
        activitySelectionsList.stream().filter(activity->activity.getText()
                .contains("Request Changes")).findFirst().ifPresent(WebElement::click);
        saveAndProceedButton.click();
        waitForElementToBeClickable(rejectReason);
        rejectReason.sendKeys("Testing");
        waitForElementToBeClickable(proceedButtonInReasonForm);
        proceedButtonInReasonForm.click();
        logger.info("activity selected-> request changes for pending HOD review ");
        waitForWebElementToAppear(successTransactionMsg);
        return successTransactionMsg.getText();

    }
}
