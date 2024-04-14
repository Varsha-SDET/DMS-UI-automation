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
 *@className - PendingReviewStage
 *@Objective - To define all the web elements and actions required for Pending Review Stage.
 */
public class PendingReviewStage extends CommonUtils {
    public WebDriver driver;
    public ElectronicSignatureForm eSign;
    Logger logger = LogManager.getLogger(PendingReviewStage.class);

    public PendingReviewStage(WebDriver driver){
        super(driver);
        this.driver= driver;
        //initialize
        this.eSign = new ElectronicSignatureForm(driver);
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//select[@id='select-div']")
    WebElement activitySelection ;
    @FindBy(xpath = "//select[@id='select-div']/option[@value='16']")
    WebElement QAReviewActivitySelection;
    @FindBy(xpath = "//select[@id='select-div']/option")
    List<WebElement> activitySelectionsList;
    @FindBy(xpath = "//button[@id='sop_revision']")
    WebElement sopRevisionSubTabButton;
    @FindBy(xpath = "//input[@id='reason_Training']")
    WebElement reasonInputField;
    @FindBy(xpath = "//input[@id='reason_Training']")
    WebElement SMEReasonInputField;
    @FindBy(xpath = "//input[@id='yes']")
    WebElement trainingRequiredYesRadioButton;
    @FindBy(xpath = "//input[@id='no']")
    WebElement trainingRequiredNoRadioButton;
    @FindBy(xpath = "#document_information2")
    WebElement documentInfoSubtabButton;
    @FindBy(xpath = "//select[@id='date_duration']")
    WebElement dateDurationDropdown;
    @FindBy(xpath = "//select[@id='date_duration']/option")
    WebElement dataDurationOptionList;
    @FindBy(xpath = "//button[@id='submit']")
    WebElement saveAndProceedButton;

    //reason alert form web elements
    @FindBy(xpath = "//div[@id='ReasonModal']//div[@class='modal-content']")
    WebElement reasonAlertForm;
    @FindBy(xpath = "textarea[name='rejectReason']")
    WebElement rejectReasonInForm;
    @FindBy(xpath = "//form[@id='reasonSubmit']//button[@id='formSubmit']")
    //@FindBy(xpath = "(//button[@id='formSubmit'])[1]")
    WebElement proceedButtonInAlertForm;
    @FindBy(xpath = "//button[@class='cancel']")
    WebElement cancelButtonInAlertForm;
    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement successTransactionMsg;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;
    //select QA review activity
    public String QAReviewCompleteActivitySelection() {
        //fill data which (same as previous stage but if required can fill it here in this stage then click on qa review activity)
       // dataFillInPendingReview();
        driver.navigate().refresh();
        if(currentBarStep.getText().contains("Pending Review")) {
            waitForWebElementToAppear(activitySelection);
            activitySelection.click();
            // QA review activity selection
            activitySelectionsList.stream().filter(activity -> activity.getText()
                    .contains("QA Review Complete")).findFirst().ifPresent(WebElement::click);
            saveAndProceedButton.click();
            logger.info("activity selected-> QA review complete and then save and proceed button click");
            waitForWebElementToAppear(eSign.electronicSignatureForm);
            eSign.submitElectronicSignature();
            waitForWebElementToAppear(successTransactionMsg);
            return successTransactionMsg.getText();
        }else {
            return "Not found";
        }
    }

    //select SME review complete activity
    public String SMEReviewCompleteActivitySelection(){
        driver.navigate().refresh();
        activitySelectionsList.stream().filter(activity->activity.getText()
                .contains("SME Review Complete")).findFirst().ifPresent(WebElement::click);
        saveAndProceedButton.click();
        logger.info("activity selected-> SME review complete");
        waitForWebElementToAppear(eSign.electronicSignatureForm);
        eSign.submitElectronicSignature();
        waitForWebElementToAppear(successTransactionMsg);
        return successTransactionMsg.getText();

    }

    //select request changes activity
    public String requestChangesActivitySelection(){
        driver.navigate().refresh();
        activitySelectionsList.stream().filter(activity->activity.getText()
                .contains("Request Changes")).findFirst().ifPresent(WebElement::click);
        saveAndProceedButton.click();
        logger.info("activity selected-> request changes");
        waitForWebElementToAppear(eSign.electronicSignatureForm);
        eSign.submitElectronicSignature();
        waitForWebElementToAppear(successTransactionMsg);
        return successTransactionMsg.getText();

    }

    //data fill in pending review stage
   public void dataFillInPendingReview(){
    sopRevisionSubTabButton.click();
    trainingRequiredYesRadioButton.click();
    reasonInputField.sendKeys("Explore more");
    logger.info("data filled in pending review stage (if required)");
   }

}

