package automation.pageobjects;

import automation.utils.CommonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/*
 *@className - QAPendingApprovalStage
 *@Objective - To define all the web elements and actions required for QA PendingApproval Stage.
 */
public class QAPendingApprovalStage extends CommonUtils {
    Logger logger = LogManager.getLogger(QAPendingApprovalStage.class);
    public WebDriver driver;
    public  QAPendingApprovalStage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    //activity selection
    @FindBy(xpath="//select[@id='select-div']")
    WebElement activitySelectionDropdown;

    //activity -> reject
    @FindBy(xpath="//select[@id='select-div']/option[@value='5' and contains(text(), 'QA Approved')]")
    WebElement QAApprovedActivity;
    //activity ->QA approved
    @FindBy(xpath="//select[@id='select-div']/option[@value='4' and contains(text(), 'Cancel')]")
    WebElement cancelActivity;
    //activity-> cancel
    @FindBy(xpath="//select[@id='select-div']/option[@value='3' and contains(text(), 'Reject')]")
    WebElement  rejectActivity;
    //save and proceed button
    @FindBy(css="button[id='submit']")
    WebElement saveAndProceedButton;
    @FindBy(xpath = "//div[contains(text(),'Close-Cancelled')]")
    WebElement closedCancelText;
    @FindBy(xpath="//div[@class ='bar']//div[@class='bar-step  incomplete ']")
    WebElement currentBarStep;
    public void performQAPendingApprovalActivity(){
        if(currentBarStep.getText().contains("Pending QA Approval")) {

            waitForElementToBeClickable(activitySelectionDropdown);
            activitySelectionDropdown.click();
            QAApprovedActivity.click();
            saveAndProceedButton.click();
            ElectronicSignatureForm electronicSignatureForm = new ElectronicSignatureForm(driver);
            electronicSignatureForm.submitElectronicSignature();
            logger.info("electronic signature submit for QA approval");
        }
    }

    //cancel activity performing
    public String performCancelActivity(){
        waitForElementToBeClickable(activitySelectionDropdown);
        activitySelectionDropdown.click();
        cancelActivity.click();
        saveAndProceedButton.click();
        waitForWebElementToAppear(closedCancelText);
        return closedCancelText.getText();

    }

    //cancel activity performing
    public void performRejectActivity(){
        waitForElementToBeClickable(activitySelectionDropdown);
        activitySelectionDropdown.click();
        rejectActivity.click();
        saveAndProceedButton.click();
        ElectronicSignatureForm electronicSignatureForm= new ElectronicSignatureForm(driver);
        electronicSignatureForm.submitElectronicSignature();

    }

    //verify electronic signature cancel button functionality
    public Boolean cancelClickOnElectronicSignatureForm(){
        waitForElementToBeClickable(activitySelectionDropdown);
        activitySelectionDropdown.click();
        QAApprovedActivity.click();
        saveAndProceedButton.click();
        ElectronicSignatureForm electronicSignatureForm= new ElectronicSignatureForm(driver);
        Boolean formPresence =electronicSignatureForm.cancelElectronicSignature();
        return formPresence;
    }

    //verify electronic signature cancel button functionality
    public String invalidCredentialsSignInEsignForm() throws InterruptedException {
        waitForElementToBeClickable(activitySelectionDropdown);
        activitySelectionDropdown.click();
        QAApprovedActivity.click();
        saveAndProceedButton.click();
        ElectronicSignatureForm electronicSignatureForm= new ElectronicSignatureForm(driver);
        return electronicSignatureForm.invalidCredentialsPassedInESignForm();
    }

    //close button clicked in e signature form
    public Boolean closeButtonInEsignatureForm() throws InterruptedException {
        waitForElementToBeClickable(activitySelectionDropdown);
        activitySelectionDropdown.click();
        QAApprovedActivity.click();
        saveAndProceedButton.click();
        ElectronicSignatureForm electronicSignatureForm= new ElectronicSignatureForm(driver);
        return electronicSignatureForm.closeButtonInESignForm();
    }

}
