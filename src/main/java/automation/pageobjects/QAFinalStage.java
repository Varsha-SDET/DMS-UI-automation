package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/*
 *@className - QAFinalStage
 *@Objective - To define all the web elements and actions required for QAFinalStage.
 */
public class QAFinalStage extends CommonUtils {
    WebDriver driver ;
    Logger logger = Logger.getLogger(QAFinalStage.class);
    public ElectronicSignatureForm eSign;

    public QAFinalStage(WebDriver driver){
        super(driver);
        this.driver = driver;
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

    @FindBy(xpath = "//button[@id='document_information']")
    WebElement documentInformationTab;
    @FindBy(xpath = "//button[@id='obsoleted_sops']")
    WebElement obsoletedSOPsubTab;
    @FindBy(xpath = "//select[@id='obsoletes']")
    WebElement obsoletesDropdown;
    @FindBy(xpath = "//select[@id='obsoletes']/option")
    List<WebElement> obsoletesList;
    @FindBy(xpath = "//span[@id='select2-obsoletes_by-container']")
    WebElement obsoletesBy;
    @FindBy(xpath = "//li[@class='select2-results__option']")
    List<WebElement> obsoletesByDropdownList;
    @FindBy(xpath = "//select[@id='obsoletes']//option[contains(text(),'Obsoletes1')]")
    WebElement obsolete1Value;
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
    public String QAFinalApprovalCompleteActivitySelection() {
        //fill data which (same as previous stage but if required can fill it here in this stage then click on qa review activity)
        // dataFillInPendingReview();
        driver.navigate().refresh();
        fillDataInObsoltedSOP();
        waitForWebElementToAppear(activitySelection);
        if(currentBarStep.getText().contains("QA Final Approval")) {
            activitySelection.click();
            // QA review activity selection
            activitySelectionsList.stream().filter(activity -> activity.getText()
                    .contains("QA Final Approval Complete")).findFirst().ifPresent(WebElement::click);
            saveAndProceedButton.click();
            logger.info("activity selected-> QA Final Approval Complete and then save and proceed button click");
            waitForWebElementToAppear(eSign.electronicSignatureForm);
            eSign.submitElectronicSignature();
            waitForWebElementToAppear(successTransactionMsg);
            return successTransactionMsg.getText();
        }else{
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
        logger.info("activity selected-> request changes for QA final approval ");
        waitForWebElementToAppear(successTransactionMsg);
        return successTransactionMsg.getText();

    }

    //pass data in obsoleted SOPs.
    public void fillDataInObsoltedSOP() {
        documentInformationTab.click();
        obsoletedSOPsubTab.click();
        obsoletesDropdown.click();
        obsolete1Value.click();
        obsoletesBy.click();
        obsoletesByDropdownList.stream().filter(obsolteby -> obsolteby.getText()
                .contains("User QA 2")).findFirst().ifPresent(WebElement::click);
    }

}

